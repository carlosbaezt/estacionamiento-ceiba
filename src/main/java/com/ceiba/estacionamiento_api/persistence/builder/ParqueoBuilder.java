package com.ceiba.estacionamiento_api.persistence.builder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento_api.dto.ParqueoDTO;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;

@Component
public class ParqueoBuilder {
	
	@Autowired
	private VehiculoBuilder vehiculoBuilder;
	
	public Parqueo toModel(ParqueoEntity parqueoEntity)
	{
		Parqueo parqueo = new Parqueo();
		parqueo.setVehiculo(vehiculoBuilder.toModel(parqueoEntity.getVehiculo()));
		parqueo.setFechaIngreso(parqueoEntity.getFechaIngreso());
		if(parqueoEntity.getFechaSalida() != null)
		{
			parqueo.setFechaSalida(parqueoEntity.getFechaSalida());
		}
		if(parqueoEntity.getPrecio() != null)
		{
			parqueo.setPrecio(parqueoEntity.getPrecio());
		}
		return parqueo;
	}
	
	public List<ParqueoDTO> toDTOs(List<ParqueoEntity> parqueosEntity)
	{
		List<ParqueoDTO> parqueosDTO = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		
		for(ParqueoEntity parqueoEntity : parqueosEntity )
		{
			ParqueoDTO parqueoDTO = new ParqueoDTO();
			parqueoDTO.setFechaIngreso(dateFormat.format(parqueoEntity.getFechaIngreso()));
			parqueoDTO.setPlaca(parqueoEntity.getVehiculo().getPlaca());
			parqueoDTO.setTipoVehiculo(parqueoEntity.getVehiculo().getTipoVehiculo().getNombre());
			parqueosDTO.add(parqueoDTO);
		}
		
		return parqueosDTO;	
	}
}