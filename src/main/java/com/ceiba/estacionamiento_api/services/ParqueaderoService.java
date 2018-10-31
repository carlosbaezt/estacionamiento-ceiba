package com.ceiba.estacionamiento_api.services;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.models.Vehiculo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
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
	
	
	public void ingresarVehiculo(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		validarVehiculoDTO(vehiculoDTO);		
		validarAccesoAlParqueadero(vehiculoDTO);
				
		//Se crea el factory de acuerdo al tipo de vehiculo
		ParqueoVehiculo parqueoVehiculo = parqueoFactory.obtenerParqueo(vehiculoDTO.getTipoVehiculo());
		
		//Se guarda el vehiculo
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(vehiculoDTO.getPlaca());
		if(vehiculoDTO.getTipoVehiculo() == TipoVehiculo.MOTO.getCodigo())
		{
			vehiculo.setCilindraje(vehiculoDTO.getCilindraje());
		}
		
		Parqueo parqueo = new Parqueo();
		parqueo.setVehiculo(vehiculo);
		
		//Se guarda el parqueo
		parqueoVehiculo.guardarParqueo(parqueo);
	}

	private void validarVehiculoDTO(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException {

		if(vehiculoDTO == null)
		{
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.nulo",null,Locale.getDefault()));
		}
		
		if(vehiculoDTO.getPlaca() == null)
		{
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.placaNula",null,Locale.getDefault()));
		}
		
		validarTipoVehiculo(vehiculoDTO.getTipoVehiculo());
		
		if(vehiculoDTO.getTipoVehiculo() == TipoVehiculo.MOTO.getCodigo()
			&& vehiculoDTO.getCilindraje() == null)
		{
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.cilindrajeNulo",null,Locale.getDefault()));
		}
	}
	
	private void validarTipoVehiculo(Integer tipoVehiculo) throws VehiculoNoAdmitidoException {
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
	
	private void validarAccesoAlParqueadero(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		if(parqueoRepository.consultarParqueoActivoPorPlaca(vehiculoDTO.getPlaca()) != null) {
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.actualmenteParqueado",null,Locale.getDefault()));
		}
		
		placaNoValidaPorDia(vehiculoDTO.getPlaca());
	}
	
	
	private void placaNoValidaPorDia(String placa) throws VehiculoNoAdmitidoException
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		
		if(placa.charAt(0) == Constantes.LETRA_INICIAL_PLACA_NO_ADMITIDA)
		{
			switch(day) 
			{
				case Calendar.SUNDAY:break;
				case Calendar.MONDAY:break;
				
				default:
					throw new VehiculoNoAdmitidoException(messageSource.getMessage("vehiculo.invalidoPorDia",null,Locale.getDefault()));
			}					
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
	
	public void obtenerVehiculosParqueados()
	{
		
	}
}