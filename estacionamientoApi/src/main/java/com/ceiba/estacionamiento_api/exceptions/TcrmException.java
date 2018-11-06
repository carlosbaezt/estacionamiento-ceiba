package com.ceiba.estacionamiento_api.exceptions;

public class TcrmException extends Exception {

	private static final long serialVersionUID = 4025553343125272127L;

	public TcrmException(String mensaje) {
		super(mensaje);
	}
}