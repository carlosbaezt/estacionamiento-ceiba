package com.ceiba.estacionamiento_api.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.VehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.services.ParqueoMotoCobro;
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;

@Service
public class ParqueoMoto extends ParqueoVehiculo implements ParqueoMotoCobro {
	
	public static final int TOTAL_ESPACIOS_DISPONIBLES = 10;
	public static final int VALOR_DIA = 4000;
	public static final int VALOR_HORA = 500;
	public static final int CILIDRAJE_MAYOR_COBRO = 500;
	public static final int VALOR_ADICIONAL_COBRO_MAYOR_CILIDRAJE = 2000;
	
	@Autowired
	ParqueoRepository parqueoRepository;
	
	@Autowired
	VehiculoRepository vehiculoRepository;
	
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public void validarGuardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException {
		if(!espacioDisponible(TipoVehiculo.MOTO.getCodigo(), TOTAL_ESPACIOS_DISPONIBLES)) {
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("parqueadero.sinEspacioDisponible",null,Locale.getDefault()));
		}
		
		guardarParqueo(parqueo);
	}

	@Override
	public Parqueo retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException {
		ParqueoEntity parqueoEntity = parqueoRepository.consultarParqueoActivoPorPlaca(placa);
		parqueoEntity.setFechaSalida(new Date());
		BigDecimal valorParqueo = valorParqueoMoto(parqueoEntity.toModel());
		parqueoEntity.setPrecio(valorParqueo);
		parqueoRepository.save(parqueoEntity);
		return parqueoEntity.toModel();
	}
	
	@Override
	public BigDecimal valorParqueoMoto(Parqueo parqueo) {
		BigDecimal valorParqueo = calcularPrecioPorTiempo(parqueo.getFechaIngreso(), parqueo.getFechaSalida(), VALOR_DIA, VALOR_HORA);
		valorParqueo = valorParqueo.add(valorAdicionalMayorCilidraje(parqueo.getVehiculo().getCilindraje()));
		return valorParqueo;
	}
	
	private BigDecimal valorAdicionalMayorCilidraje(int cilindraje){
		BigDecimal valorAdicional = new BigDecimal("0");
		if(cilindraje > CILIDRAJE_MAYOR_COBRO){
			valorAdicional = new BigDecimal(VALOR_ADICIONAL_COBRO_MAYOR_CILIDRAJE);
		}
		return valorAdicional;
	}

}