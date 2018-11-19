package com.ceiba.estacionamiento_api.services;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ceiba.estacionamiento_api.utils.MensajesExcepciones;

@Controller
public class ParqueaderoService {
	
	@Autowired
	private ParqueoRepository parqueoRepository;

	@Autowired
	private ParqueoFactory parqueoFactory;
		
	@Autowired
	private MensajesExcepciones mensajesExcepciones;
	
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
			throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.nulo"));
		}
		
		if(vehiculoDTO.getPlaca() == null || vehiculoDTO.getPlaca().trim().isEmpty())
		{
			throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.placaNula"));
		}
	}
	
	public void validarCilindraje(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		if(vehiculoDTO.getTipoVehiculo() == TipoVehiculo.MOTO.getCodigo())
		{
			if(vehiculoDTO.getCilindraje() == null)
			{
				throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.cilindrajeNulo"));
			}
			
			if(vehiculoDTO.getCilindraje() <= 0 )
			{
				throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.cilindrajeInvalido"));			
			}			
		}
	}
	
	public void validarTipoVehiculo(Integer tipoVehiculo) throws VehiculoNoAdmitidoException {
		if(tipoVehiculo == null){
			throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.tipoVehiculoNulo"));
		}
		
		for (TipoVehiculo enumTipoVehiculo : TipoVehiculo.values()) {
	       if (enumTipoVehiculo.getCodigo() == tipoVehiculo) {
	    	   return;
	        }
	    }
		
		throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.tipoVehiculoInvalido"));
		
	}
	
	public void validarAccesoAlParqueadero(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		validarVehiculoYaParqueado(vehiculoDTO.getPlaca());
		placaNoValidaPorDia(vehiculoDTO.getPlaca());
	}
	
	private void validarVehiculoYaParqueado(String placa) throws VehiculoNoAdmitidoException
	{
		if(parqueoRepository.consultarParqueoActivoPorPlaca(placa) != null) {
			throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.actualmenteParqueado"));
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
				throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.invalidoPorDia"));
		}
	}

	public ParqueoDTO retirarVehiculo(String placa) throws VehiculoNoAdmitidoException
	{
		ParqueoEntity parqueoEntity = parqueoRepository.consultarParqueoActivoPorPlaca(placa);
		if(parqueoEntity == null) {
			throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("vehiculo.noEstaParqueado"));
		}
		
		ParqueoVehiculo parqueoVehiculo = parqueoFactory.obtenerParqueo(parqueoEntity.getVehiculo().getTipoVehiculo().getId().intValue());
		Parqueo parqueo = parqueoVehiculo.retirarParqueoPorPlaca(placa);
		return parqueoBuilder.toDTO(parqueo);
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