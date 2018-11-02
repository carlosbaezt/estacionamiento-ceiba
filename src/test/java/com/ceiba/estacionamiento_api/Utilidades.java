package com.ceiba.estacionamiento_api;

public class Utilidades {
	
	private Utilidades()
	{}
	
	private static final String LETRAS_PLACA_ADMITIDOS = "BCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String NUMEROS_PLACA_ADMITIDOS = "0123456789";
	
	public static String generarPlacaAleatoria()
	{
		String placa = "";
		placa += Utilidades.generarStringAleatorio(LETRAS_PLACA_ADMITIDOS,3);
		placa += Utilidades.generarStringAleatorio(NUMEROS_PLACA_ADMITIDOS,3);
		return placa;	
	}
	
	private static String generarStringAleatorio(String caracteresAdmitidos, int cantidadCaracteres)
	{
		StringBuilder placa = new StringBuilder();
		while (cantidadCaracteres-- != 0) {
			int character = (int)(Math.random()*caracteresAdmitidos.length());
			placa.append(caracteresAdmitidos.charAt(character));
		}
		
		return placa.toString();
	}
}