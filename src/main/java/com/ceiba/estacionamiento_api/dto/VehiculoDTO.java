package com.ceiba.estacionamiento_api.dto;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.models.Vehiculo;

public class VehiculoDTO {
	
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
	
	public Vehiculo toModel()
	{
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(this.placa);
		vehiculo.setTipoVehiculo(this.tipoVehiculo);
		if(this.tipoVehiculo == TipoVehiculo.MOTO.getCodigo())
		{
			vehiculo.setCilindraje(this.getCilindraje());
		}
		return vehiculo;
	}
	
}