package com.ceiba.estacionamiento_api.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.models.Vehiculo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.TipoVehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.VehiculoRepository;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.TipoVehiculoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;
import com.ceiba.estacionamiento_api.utils.Constantes;
import com.ceiba.estacionamiento_api.utils.ParqueoCalculadora;

@Service
public abstract class ParqueoVehiculo {
	
	@Autowired
	private ParqueoRepository parqueoRepository;
	
	@Autowired
	private TipoVehiculoRepository tipoVehiculoRepository ;
	
	@Autowired
	private VehiculoRepository vehiculoRepository ;
	
	public boolean espacioDisponible(int tipoVehiculo, int cantidadEspaciosDisponibles){
		boolean espaciosDisponibles = false;
		
		Integer total = parqueoRepository.obtenerParqueosActivosPorTipoVehiculo(tipoVehiculo);
		if(total < cantidadEspaciosDisponibles)
		{
			espaciosDisponibles =  true;
		}
		return espaciosDisponibles;
	}
		
	public abstract void validarGuardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException;
	
	public abstract Parqueo retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException;
	
	public void guardarParqueo(Parqueo parqueo)
	{
		VehiculoEntity vehiculoEntity = guardarVehiculo(parqueo.getVehiculo());
		ParqueoEntity parqueoEntity = new ParqueoEntity();
		parqueoEntity.setFechaIngreso(new Date());
		parqueoEntity.setVehiculo(vehiculoEntity);
		parqueoRepository.save(parqueoEntity);
	}
	
	public BigDecimal calcularPrecioPorTiempo(Date fechaIngreso, Date fechaSalida, double valorDia, double valorHora) {		
		BigDecimal total         = new BigDecimal("0");
		int horasParqueo         = ParqueoCalculadora.obtenerHorasParqueo(fechaIngreso,fechaSalida);
		int diasParqueoCompletos = (int) Math.floor(horasParqueo / Constantes.TOTAL_HORAS_DIA);
		
		if(diasParqueoCompletos >= 1)
		{
			total = BigDecimal.valueOf((double) diasParqueoCompletos * valorDia);
			horasParqueo = (int) (horasParqueo % Constantes.TOTAL_HORAS_DIA);
		}
		
		if(horasParqueo < Constantes.MAXIMO_HORAS_PARQUEO )
		{
			double valorTotalHoras = (horasParqueo * valorHora);
			total = total.add(BigDecimal.valueOf(valorTotalHoras));
		}
		else
		{
			total = total.add(BigDecimal.valueOf(valorDia));
		}
		
		return total;
	}
	
	
	
	
	public VehiculoEntity guardarVehiculo(Vehiculo vehiculo)
	{
		VehiculoEntity vehiculoEntity = vehiculoRepository.findByPlacaIgnoreCase(vehiculo.getPlaca());
		if(vehiculoEntity == null)
		{		
			vehiculoEntity = new VehiculoEntity();
			vehiculoEntity.setPlaca(vehiculo.getPlaca());
			if(vehiculo.getTipoVehiculo() == TipoVehiculo.MOTO.getCodigo())
			{
				vehiculoEntity.setCilindraje(vehiculo.getCilindraje());
			}		
			Optional<TipoVehiculoEntity> tipoVehiculoEntity = tipoVehiculoRepository.findById((long) vehiculo.getTipoVehiculo());
			if(tipoVehiculoEntity.isPresent()){
				vehiculoEntity.setTipoVehiculo(tipoVehiculoEntity.get());					
			}
		}
		
		return vehiculoRepository.save(vehiculoEntity);
	}
}