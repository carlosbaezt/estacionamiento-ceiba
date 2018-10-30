package com.ceiba.estacionamiento_api.dto;

public class VehiculoDTO {
	
	private String placa;
	private Integer cilindraje;
	private Integer tipoVehiculo;
	
	public VehiculoDTO()
	{}
	
	public VehiculoDTO(String placa, Integer cilindraje, Integer tipoVehiculo) {
		super();
		this.placa = placa;
		this.cilindraje = cilindraje;
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public int getCilindraje() {
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