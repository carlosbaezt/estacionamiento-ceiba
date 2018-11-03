package com.ceiba.estacionamiento_api.persistence.builder;

import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
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
	
	public Vehiculo toModel(VehiculoDTO vehiculoDTO)
	{
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(vehiculoDTO.getPlaca());
		vehiculo.setTipoVehiculo(vehiculoDTO.getTipoVehiculo());
		if(vehiculoDTO.getTipoVehiculo() == TipoVehiculo.MOTO.getCodigo())
		{
			vehiculo.setCilindraje(vehiculoDTO.getCilindraje());
		}
		return vehiculo;
	}

}
