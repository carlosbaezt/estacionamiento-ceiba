package com.ceiba.estacionamiento_api.utils;

import java.util.Date;

public final class ParqueoCalculadora {
	
	private ParqueoCalculadora() {}
	
	public static int obtenerHorasParqueo(Date fechaIngreso, Date fechaSalida){
		double diferenciaMilisegundosFechas = Double.valueOf(fechaSalida.getTime()) - Double.valueOf(fechaIngreso.getTime());
		double diferenciaHoras = diferenciaMilisegundosFechas / (60 * 60 * 1000);
		return (int) Math.ceil(diferenciaHoras);
	}

}
