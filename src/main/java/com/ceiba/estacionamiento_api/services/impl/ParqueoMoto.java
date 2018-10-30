package com.ceiba.estacionamiento_api.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ceiba.estacionamiento_api.services.ParqueoVehiculo;
import com.ceiba.estacionamiento_api.utils.Constantes;
import com.ceiba.estacionamiento_api.utils.ParqueoCalculadora;

@Service
public class ParqueoMoto implements ParqueoVehiculo {
	
	private static final int TOTAL_ESPACIOS_DISPONIBLES = 1;
	private static final int VALOR_DIA = 4000;
	private static final int VALOR_HORA = 500;
	private static final int CILIDRAJE_MAYOR_COBRO = 500;
	private static final int VALOR_COBRO_MAYOR_CILIDRAJE = 2000;
	
	
	@Autowired
	ParqueoRepository parqueoRepository;
	
	@Autowired
	VehiculoRepository vehiculoRepository ;
	
	@Autowired
	TipoVehiculoRepository tipoVehiculoRepository ;

	@Override
	public boolean espacioDisponible() {
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
		if(!espacioDisponible())
		{
			throw new VehiculoNoAdmitidoException("No hay espacios disponibles");
		}
		
		VehiculoEntity vehiculoEntity = vehiculoRepository.consultarPorPlaca(parqueo.getVehiculo().getPlaca());
		if(vehiculoEntity == null)
		{
			vehiculoEntity = new VehiculoEntity();
			vehiculoEntity.setPlaca(parqueo.getVehiculo().getPlaca());
			vehiculoEntity.setCilindraje(parqueo.getVehiculo().getCilindraje());
			Optional<TipoVehiculoEntity> tipoVehiculoEntity = tipoVehiculoRepository.findById((long) TipoVehiculo.MOTO.getCodigo());
			if(tipoVehiculoEntity.isPresent())
			{
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
	public void retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException {
		ParqueoEntity parqueoEntity = parqueoRepository.consultarParqueoActivoPorPlaca(placa);
		parqueoEntity.setFechaSalida(new Date());
		BigDecimal valorParqueo = calcularPrecio(parqueoEntity.getFechaIngreso(), parqueoEntity.getFechaSalida());
		if(parqueoEntity.getVehiculo().getCilindraje() > CILIDRAJE_MAYOR_COBRO)
		{
			valorParqueo = valorParqueo.add(BigDecimal.valueOf(VALOR_COBRO_MAYOR_CILIDRAJE));
		}
		parqueoEntity.setPrecio(valorParqueo);
		parqueoRepository.save(parqueoEntity);
	}
	
	@Override
	public BigDecimal calcularPrecio(Date fechaIngreso, Date fechaSalida) {
		
		BigDecimal total         = new BigDecimal("0");
		int horasParqueo         = ParqueoCalculadora.obtenerHorasParqueo(fechaIngreso,fechaSalida);
		int diasParqueoCompletos = (int) Math.floor(horasParqueo / Constantes.TOTAL_HORAS_DIA);
		
		if(diasParqueoCompletos > 1)
		{
			total = BigDecimal.valueOf((double) diasParqueoCompletos * VALOR_DIA);
			horasParqueo = (int) (horasParqueo % Constantes.TOTAL_HORAS_DIA);
		}
		
		if(horasParqueo < Constantes.MAXIMO_HORAS_PARQUEO )
		{
			double valorTotalHoras = (horasParqueo * VALOR_HORA);
			total = total.add(BigDecimal.valueOf(valorTotalHoras));
		}
		else
		{
			total = total.add(BigDecimal.valueOf(VALOR_DIA));
		}
		
		return total;
		
	}
	
	

}
