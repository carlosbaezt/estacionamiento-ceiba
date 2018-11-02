package com.ceiba.estacionamiento_api.persistence.builder;

import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento_api.models.Vehiculo;
import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;

@Component
public class VehiculoBuilder {
	
	public Vehiculo toModel(VehiculoEntity vehiculoEntity)
	{
		Vehiculo vehiculo = new Vehiculo();
		if(vehiculoEntity.getCilindraje() != null)
		{
			vehiculo.setCilindraje(vehiculoEntity.getCilindraje());
		}
		vehiculo.setPlaca(vehiculoEntity.getPlaca());
		vehiculo.setTipoVehiculo(Math.toIntExact(vehiculoEntity.getTipoVehiculo().getId()));
		return vehiculo;
	}

}
