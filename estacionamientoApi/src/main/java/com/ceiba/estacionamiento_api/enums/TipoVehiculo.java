package com.ceiba.estacionamiento_api.enums;

public enum TipoVehiculo {
	MOTO (1),
	CARRO (2);
	
	private final int codigo;

    private TipoVehiculo(int codigo){
        this.codigo = codigo;
    }
    
    public int getCodigo()
    {
    	return this.codigo;
    }
}