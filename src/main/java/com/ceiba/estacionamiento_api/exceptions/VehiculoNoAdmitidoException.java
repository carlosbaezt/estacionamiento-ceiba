package com.ceiba.estacionamiento_api.exceptions;

public class VehiculoNoAdmitidoException extends Exception {
	private static final long serialVersionUID = 1L;

	public VehiculoNoAdmitidoException(String mensaje)
	{
		super(mensaje);
	}
}
