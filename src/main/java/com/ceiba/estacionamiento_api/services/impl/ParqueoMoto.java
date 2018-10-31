package com.ceiba.estacionamiento_api.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.TipoVehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.VehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.TipoVehiculoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;
import com.ceiba.estacionamiento_api.services.ParqueoMotoCobro;
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;

@Service
public class ParqueoMoto implements ParqueoVehiculo,ParqueoMotoCobro {
	
	public static final int TOTAL_ESPACIOS_DISPONIBLES = 10;
	public static final int VALOR_DIA = 4000;
	public static final int VALOR_HORA = 500;
	public static final int CILIDRAJE_MAYOR_COBRO = 500;
	public static final int VALOR_ADICIONAL_COBRO_MAYOR_CILIDRAJE = 2000;
	
	@Autowired
	ParqueoRepository parqueoRepository;
	
	@Autowired
	VehiculoRepository vehiculoRepository ;
	
	@Autowired
	TipoVehiculoRepository tipoVehiculoRepository ;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean espacioDisponible(){
		boolean espaciosDisponibles = false;
		
		Integer total = parqueoRepository.obtenerParqueosActivosPorTipoVehiculo(TipoVehiculo.MOTO.getCodigo());
		if(total < TOTAL_ESPACIOS_DISPONIBLES)
		{
			espaciosDisponibles =  true;
		}
		return espaciosDisponibles;
	}

	@Override
	public void guardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException {
		if(!espacioDisponible()){
			throw new VehiculoNoAdmitidoException(messageSource.getMessage("parqueadero.sinEspacioDisponible",null,Locale.getDefault()));
		}
		
		VehiculoEntity vehiculoEntity = vehiculoRepository.findByPlacaIgnoreCase(parqueo.getVehiculo().getPlaca());
		if(vehiculoEntity == null){
			vehiculoEntity = new VehiculoEntity();
			vehiculoEntity.setPlaca(parqueo.getVehiculo().getPlaca());
			vehiculoEntity.setCilindraje(parqueo.getVehiculo().getCilindraje());
			Optional<TipoVehiculoEntity> tipoVehiculoEntity = tipoVehiculoRepository.findById((long) TipoVehiculo.MOTO.getCodigo());
			if(tipoVehiculoEntity.isPresent()){
				vehiculoEntity.setTipoVehiculo(tipoVehiculoEntity.get());					
			}
			
			vehiculoRepository.save(vehiculoEntity);
		}
		ParqueoEntity parqueoEntity = new ParqueoEntity();
		parqueoEntity.setFechaIngreso(new Date());
		parqueoEntity.setVehiculo(vehiculoEntity);
		parqueoRepository.save(parqueoEntity);
	}

	@Override
	public Parqueo retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException {
		ParqueoEntity parqueoEntity = parqueoRepository.consultarParqueoActivoPorPlaca(placa);
		parqueoEntity.setFechaSalida(new Date());
		BigDecimal valorParqueo = valorParqueoMoto(parqueoEntity);
		parqueoEntity.setPrecio(valorParqueo);
		parqueoRepository.save(parqueoEntity);
		return parqueoEntity.toModel();
	}
	
	@Override
	public BigDecimal valorParqueoMoto(ParqueoEntity parqueoEntity) {
		BigDecimal valorParqueo = calcularPrecioPorTiempo(parqueoEntity.getFechaIngreso(), parqueoEntity.getFechaSalida(), VALOR_DIA, VALOR_HORA);
		valorParqueo = valorParqueo.add(valorAdicionalMayorCilidraje(parqueoEntity.getVehiculo().getCilindraje()));
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