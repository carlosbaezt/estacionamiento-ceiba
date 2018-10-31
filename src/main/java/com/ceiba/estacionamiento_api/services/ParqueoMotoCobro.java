package com.ceiba.estacionamiento_api.services;

import java.math.BigDecimal;

import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;

public interface ParqueoMotoCobro {
	
	public BigDecimal valorParqueoMoto(ParqueoEntity parqueoEntity);

}
