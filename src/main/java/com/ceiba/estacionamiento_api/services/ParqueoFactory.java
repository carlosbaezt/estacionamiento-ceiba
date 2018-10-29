package com.ceiba.estacionamiento_api.services;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;

public interface ParqueoFactory {
	public ParqueoVehiculo obtenerParqueo(TipoVehiculo tipoVehiculo);
}
