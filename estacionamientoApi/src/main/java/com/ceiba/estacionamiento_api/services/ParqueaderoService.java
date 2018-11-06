package com.ceiba.estacionamiento_api.services;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import com.ceiba.estacionamiento_api.dto.ParqueoDTO;
import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.builder.ParqueoBuilder;
import com.ceiba.estacionamiento_api.persistence.builder.VehiculoBuilder;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.utils.Constantes;

@Controller
public class ParqueaderoService {
	
	@Autowired
	ParqueoRepository parqueoRepository;

	@Autowired
	ParqueoFactory parqueoFactory;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ParqueoBuilder parqueoBuilder;
	
	@Autowired
	private VehiculoBuilder vehiculoBuilder;
	
	private Calendar calendar;
	
	
	public void ingresarVehiculo(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		validarVehiculoDTO(vehiculoDTO);
		validarAccesoAlParqueadero(vehiculoDTO);
		ParqueoVehiculo parqueoVehiculo = parqueoFactory.obtenerParqueo(vehiculoDTO.getTipoVehiculo());
		Parqueo parqueo = new Parqueo();
		parqueo.setVehiculo(vehiculoBuilder.toModel(vehiculoDTO));
		parqueoVehiculo.guardarParqueo(parqueo);
	}

	private void validarVehiculoDTO(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException {
		validarVehiculoDTONull(vehiculoDTO);		
		validarTipoVehiculo(vehiculoDTO.getTipoVehiculo());
		validarCilindraje(vehiculoDTO);
	}
	
	public void validarVehiculoDTONull(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		if(vehiculoDTO == null)
		{
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.nulo",null,Locale.getDefault()));
		}
		
		if(vehiculoDTO.getPlaca() == null)
		{
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.placaNula",null,Locale.getDefault()));
		}
	}
	
	public void validarCilindraje(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		if(vehiculoDTO.getTipoVehiculo() == TipoVehiculo.MOTO.getCodigo())
		{
			if(vehiculoDTO.getCilindraje() == null)
			{
				throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.cilindrajeNulo",null,Locale.getDefault()));
			}
			
			if(vehiculoDTO.getCilindraje() <= 0 )
			{
				throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.cilindrajeInvalido",null,Locale.getDefault()));			
			}			
		}
	}
	
	public void validarTipoVehiculo(Integer tipoVehiculo) throws VehiculoNoAdmitidoException {
		if(tipoVehiculo == null){
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.tipoVehiculoNulo",null,Locale.getDefault()));
		}
		
		for (TipoVehiculo enumTipoVehiculo : TipoVehiculo.values()) {
	       if (enumTipoVehiculo.getCodigo() == tipoVehiculo) {
	    	   return;
	        }
	    }
		
		throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.tipoVehiculoInvalido",null,Locale.getDefault()));
		
	}
	
	public void validarAccesoAlParqueadero(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		validarVehiculoYaParqueado(vehiculoDTO.getPlaca());
		placaNoValidaPorDia(vehiculoDTO.getPlaca());
	}
	
	private void validarVehiculoYaParqueado(String placa) throws VehiculoNoAdmitidoException
	{
		if(parqueoRepository.consultarParqueoActivoPorPlaca(placa) != null) {
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.actualmenteParqueado",null,Locale.getDefault()));
		}
	}
	
	private void placaNoValidaPorDia(String placa) throws VehiculoNoAdmitidoException
	{
		int day = getCalendar().get(Calendar.DAY_OF_WEEK);
		
		if(Character.toUpperCase(placa.charAt(0)) != Character.toUpperCase(Constantes.LETRA_INICIAL_PLACA_NO_ADMITIDA))
		{
			return;
		}
		
		switch(day) 
		{
			case Calendar.SUNDAY:break;
			case Calendar.MONDAY:break;
			
			default:
				throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.invalidoPorDia",null,Locale.getDefault()));
		}
	}

	public Parqueo retirarVehiculo(String placa) throws VehiculoNoAdmitidoException
	{
		ParqueoEntity parqueoEntity = parqueoRepository.consultarParqueoActivoPorPlaca(placa);
		if(parqueoEntity == null) {
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.noEstaParqueado",null,Locale.getDefault()));
		}
		
		ParqueoVehiculo parqueoVehiculo = parqueoFactory.obtenerParqueo(parqueoEntity.getVehiculo().getTipoVehiculo().getId().intValue());
		return parqueoVehiculo.retirarParqueoPorPlaca(placa);
	}
	
	public List<ParqueoDTO> obtenerVehiculosParqueados()
	{
		List<ParqueoEntity> parqueosEntity  = parqueoRepository.obtenerParqueosActivos();
		return parqueoBuilder.toDTOs(parqueosEntity);	
	}

	public Calendar getCalendar() {
		if(calendar == null)
		{
			calendar = Calendar.getInstance();			
		}
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}