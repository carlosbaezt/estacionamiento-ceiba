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
	
	private static final String FORMATO_FECHA = "yyyy-MM-dd hh:mm a";
	
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
	
	public ParqueoDTO toDTO(Parqueo parqueo)
	{
		DateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
		ParqueoDTO parqueoDTO = new ParqueoDTO();
		parqueoDTO.setFechaIngreso(dateFormat.format(parqueo.getFechaIngreso()));
		parqueoDTO.setPlaca(parqueo.getVehiculo().getPlaca());
		if(parqueo.getPrecio() != null)
		{
			parqueoDTO.setPrecio(parqueo.getPrecio());
		}
		if(parqueo.getFechaSalida() != null)
		{
			parqueoDTO.setFechaSalida(dateFormat.format(parqueo.getFechaSalida()));
		}
		
		return parqueoDTO;
	}
	
	public ParqueoDTO toDTO(ParqueoEntity parqueoEntity)
	{
		DateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
		ParqueoDTO parqueoDTO = new ParqueoDTO();
		parqueoDTO.setFechaIngreso(dateFormat.format(parqueoEntity.getFechaIngreso()));
		parqueoDTO.setPlaca(parqueoEntity.getVehiculo().getPlaca());
		parqueoDTO.setTipoVehiculo(parqueoEntity.getVehiculo().getTipoVehiculo().getNombre());
		if(parqueoEntity.getPrecio() != null)
		{
			parqueoDTO.setPrecio(parqueoEntity.getPrecio());
		}
		if(parqueoEntity.getFechaSalida() != null)
		{
			parqueoDTO.setFechaSalida(dateFormat.format(parqueoEntity.getFechaSalida()));
		}
		
		return parqueoDTO;
	}
	 
	public List<ParqueoDTO> toDTOs(List<ParqueoEntity> parqueosEntity)
	{
		List<ParqueoDTO> parqueosDTO = new ArrayList<>();		
		for(ParqueoEntity parqueoEntity : parqueosEntity )
		{
			parqueosDTO.add(toDTO(parqueoEntity));
		}
		
		return parqueosDTO;	
	}
}