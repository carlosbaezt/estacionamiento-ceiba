package com.ceiba.estacionamiento_api.services;

import java.math.BigDecimal;
import java.util.Date;

import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.utils.Constantes;
import com.ceiba.estacionamiento_api.utils.ParqueoCalculadora;

public abstract class ParqueoVehiculo {
	
	public abstract boolean espacioDisponible();
		
	public abstract void guardarParqueo(Parqueo parqueo) throws VehiculoNoAdmitidoException;
	
	public abstract BigDecimal retirarParqueoPorPlaca(String placa) throws VehiculoNoAdmitidoException;
	
	public BigDecimal calcularPrecioPorTiempo(Date fechaIngreso, Date fechaSalida, double valorDia, double valorHora) {		
		BigDecimal total         = new BigDecimal("0");
		int horasParqueo         = ParqueoCalculadora.obtenerHorasParqueo(fechaIngreso,fechaSalida);
		int diasParqueoCompletos = (int) Math.floor(horasParqueo / Constantes.TOTAL_HORAS_DIA);
		
		if(diasParqueoCompletos > 1)
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
}