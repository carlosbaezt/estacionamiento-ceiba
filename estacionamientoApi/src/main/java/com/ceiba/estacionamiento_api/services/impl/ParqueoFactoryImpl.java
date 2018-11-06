package com.ceiba.estacionamiento_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.services.ParqueoFactory;
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;

@Service
public class ParqueoFactoryImpl implements ParqueoFactory {
	
	@Autowired
	ParqueoCarro parqueoCarro;
	
	@Autowired
	ParqueoMoto parqueoMoto;

	@Override
	public ParqueoVehiculo obtenerParqueo(Integer tipoVehiculo)
	{
		ParqueoVehiculo parqueo = null;
		
		if(tipoVehiculo == TipoVehiculo.CARRO.getCodigo())
		{
			parqueo = parqueoCarro;			
		}else if(tipoVehiculo == TipoVehiculo.MOTO.getCodigo())
		{
			parqueo = parqueoMoto;
		}
		
		return parqueo;
	}

}