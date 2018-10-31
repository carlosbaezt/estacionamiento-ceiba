package com.ceiba.estacionamiento_api.services;

import java.math.BigDecimal;

import com.ceiba.estacionamiento_api.models.Parqueo;

public interface ParqueoMotoCobro {
	
	public BigDecimal valorParqueoMoto(Parqueo parqueo);

}
