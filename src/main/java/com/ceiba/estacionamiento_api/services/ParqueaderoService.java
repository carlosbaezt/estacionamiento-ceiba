package com.ceiba.estacionamiento_api.services;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.models.Vehiculo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;

@Controller
public class ParqueaderoService {
	
	@Autowired
	ParqueoRepository parqueoRepository;
	
	@Autowired
	ParqueoFactory parqueoFactory;
	
	public void ingresarVehiculo(VehiculoDTO vehiculoDTO) throws VehiculoNoAdmitidoException
	{
		//Se valida que los datos esten completos
		if(vehiculoDTO == null)
		{
			throw new VehiculoNoAdmitidoException("Vehiculo NULO");
		}
		
		if(vehiculoDTO.getPlaca() == null)
		{
			throw new VehiculoNoAdmitidoException("PLACA NULA");
		}
		
		//Se valida que el vehiculo sea valido por el día
		if(placaNoValidaPorDia(vehiculoDTO.getPlaca())){
			throw new VehiculoNoAdmitidoException("NO ADMITIDO POR DIA");			
		}
		
		// Se valida que el vehiculo no este parqueado actualmente
		if(parqueoRepository.consultarParqueoActivoPorPlaca(vehiculoDTO.getPlaca()) != null) {
			throw new VehiculoNoAdmitidoException("ACTUALMENTE PARQUEADO");
		}
		
		//Se crea el factory de acuerdo al tipo de vehiculo
		ParqueoVehiculo parqueoVehiculo = parqueoFactory.obtenerParqueo(TipoVehiculo.CARRO);
		
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

	public void sacarVehiculo(String placa)
	{
		
	}
	
	public void obtenerVehiculosParqueados()
	{
		
	}
	
	
	private boolean placaNoValidaPorDia(String placa)
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		
		if(
			placa.charAt(0) == 'A' && 
			(day != Calendar.MONDAY && day != Calendar.SUNDAY)
		)
		{
			return true;
		}
		
		return false;
	}

}