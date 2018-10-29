package com.ceiba.estacionamiento_api.services.impl;

import org.springframework.stereotype.Service;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.services.ParqueoFactory;
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;

@Service
public class ParqueoFactoryImpl implements ParqueoFactory {

	@Override
	public ParqueoVehiculo obtenerParqueo(TipoVehiculo tipoVehiculo)
	{
		ParqueoVehiculo parqueo = null;
		
		switch (tipoVehiculo)
		{
			case CARRO:
				parqueo = new ParqueoCarro();
			break;
			
			case MOTO:
				parqueo = new ParqueoMoto();
			break;
		
		}
		
		return parqueo;
	}

}