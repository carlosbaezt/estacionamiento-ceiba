package com.ceiba.estacionamiento_api.persistence.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ceiba.estacionamiento_api.models.Parqueo;

@Entity
@Table(name = "parqueo")
public class ParqueoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private VehiculoEntity vehiculo;
	
	@Column(name = "fecha_ingreso" , columnDefinition = "DATETIME DEFAULT CURRENT_DATE")
	private java.util.Date fechaIngreso;
	
	@Column(name = "fecha_salida")
	private java.util.Date fechaSalida;
	
	@Column(name = "precio")
	private BigDecimal precio;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehiculoEntity getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoEntity vehiculo) {
		this.vehiculo = vehiculo;
	}

	public java.util.Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(java.util.Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public java.util.Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(java.util.Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}	
	
	public Parqueo toModel()
	{
		Parqueo parqueo = new Parqueo();
		parqueo.setVehiculo(this.getVehiculo().toModel());
		parqueo.setFechaIngreso(this.fechaIngreso);
		if(this.fechaSalida != null)
		{
			parqueo.setFechaSalida(this.fechaSalida);
		}
		if(this.precio != null)
		{
			parqueo.setPrecio(this.precio);
		}
		return parqueo;
	}
}