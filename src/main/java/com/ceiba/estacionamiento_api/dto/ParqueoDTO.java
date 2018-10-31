package com.ceiba.estacionamiento_api.dto;

import java.util.Date;

public class ParqueoDTO {
	
	private String placa;
	private String tipoVehiculo;
	private Date fechaIngreso;
	
	
	public ParqueoDTO() {}
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
}