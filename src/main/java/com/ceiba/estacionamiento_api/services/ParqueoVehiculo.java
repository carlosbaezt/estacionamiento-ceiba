package com.ceiba.estacionamiento_api.services;

import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;

public interface ParqueoVehiculo {
	
	public boolean espacioDisponible();
		
	public void guardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException;
	
	public void calcularPrecio();

}
