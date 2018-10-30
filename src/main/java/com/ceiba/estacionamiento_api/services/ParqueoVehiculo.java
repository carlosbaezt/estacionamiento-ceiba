package com.ceiba.estacionamiento_api.services;

import java.math.BigDecimal;
import java.util.Date;

import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;

public interface ParqueoVehiculo {
	
	public boolean espacioDisponible();
		
	public void guardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException;
	
	public void retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException;
	
	public BigDecimal calcularPrecio(Date fechaIngreso, Date fechaSalida);

}
