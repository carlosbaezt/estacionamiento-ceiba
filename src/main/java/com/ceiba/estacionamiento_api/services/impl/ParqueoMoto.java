package com.ceiba.estacionamiento_api.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.TipoVehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.VehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.TipoVehiculoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;

@Service
public class ParqueoMoto implements ParqueoVehiculo {
	
	private static final int TOTAL_ESPACIOS_DISPONIBLES = 1;
	
	@Autowired
	ParqueoRepository parqueoRepository;
	
	@Autowired
	VehiculoRepository vehiculoRepository ;
	
	@Autowired
	TipoVehiculoRepository tipoVehiculoRepository ;

	@Override
	public boolean espacioDisponible() {
		boolean espaciosDisponibles = false;
		
		Integer total = parqueoRepository.obtenerParqueosActivosPorTipoVehiculo(TipoVehiculo.MOTO.getCodigo());
		if(total < TOTAL_ESPACIOS_DISPONIBLES)
		{
			espaciosDisponibles =  true;
		}
		return espaciosDisponibles;
	}


	@Override
	public void guardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException {
		if(!espacioDisponible())
		{
			throw new VehiculoNoAdmitidoException("No hay espacios disponibles");
		}
		
		VehiculoEntity vehiculoEntity = vehiculoRepository.consultarPorPlaca(parqueo.getVehiculo().getPlaca());
		if(vehiculoEntity == null)
		{
			vehiculoEntity = new VehiculoEntity();
			vehiculoEntity.setPlaca(parqueo.getVehiculo().getPlaca());
			vehiculoEntity.setCilindraje(parqueo.getVehiculo().getCilindraje());
			Optional<TipoVehiculoEntity> tipoVehiculoEntity = tipoVehiculoRepository.findById((long) TipoVehiculo.MOTO.getCodigo());
			if(tipoVehiculoEntity.isPresent())
			{
				vehiculoEntity.setTipoVehiculo(tipoVehiculoEntity.get());					
			}

			vehiculoRepository.save(vehiculoEntity);
		}
		ParqueoEntity parqueoEntity = new ParqueoEntity();
		parqueoEntity.setFechaIngreso(new Date());
		parqueoEntity.setVehiculo(vehiculoEntity);
		parqueoRepository.save(parqueoEntity);

	}

	@Override
	public void calcularPrecio() {
				
	}

}
