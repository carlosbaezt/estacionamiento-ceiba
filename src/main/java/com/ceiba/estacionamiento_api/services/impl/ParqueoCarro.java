package com.ceiba.estacionamiento_api.services.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.builder.ParqueoBuilder;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;
import com.ceiba.estacionamiento_api.utils.MensajesExcepciones;

@Service
public class ParqueoCarro extends ParqueoVehiculo {
	
	public static final int TOTAL_ESPACIOS_DISPONIBLES = 20;
	public static final int VALOR_DIA = 8000;
	public static final int VALOR_HORA = 1000;
	
	@Autowired
	private ParqueoRepository parqueoRepository;

	@Autowired
	private MensajesExcepciones mensajesExcepciones;
	
	@Autowired
	private ParqueoBuilder parqueoBuilder;

	@Override
	public void guardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException {
		if(!espacioDisponible(TipoVehiculo.CARRO.getCodigo(), TOTAL_ESPACIOS_DISPONIBLES)){
			throw new VehiculoNoAdmitidoException(mensajesExcepciones.obtenerMensaje("parqueadero.sinEspacioDisponible"));
		}
		
		guardarParqueoVehiculo(parqueo);
	}


	@Override
	public Parqueo retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException {
		ParqueoEntity parqueoEntity = parqueoRepository.consultarParqueoActivoPorPlaca(placa);
		parqueoEntity.setFechaSalida(new Date());
		BigDecimal valorParqueo = calcularPrecioPorTiempo(parqueoEntity.getFechaIngreso(), parqueoEntity.getFechaSalida(), VALOR_DIA, VALOR_HORA);
		parqueoEntity.setPrecio(valorParqueo);
		parqueoRepository.save(parqueoEntity);
		return parqueoBuilder.toModel(parqueoEntity);
	}
}
