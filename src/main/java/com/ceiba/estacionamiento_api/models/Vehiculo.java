package com.ceiba.estacionamiento_api.models;

public class Vehiculo {
	
	private String placa;
	private Integer cilindraje;
	private Integer tipoVehiculo;
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Integer getCilindraje() {
		return cilindraje;
	}
	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}
	public Integer getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(Integer tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
}