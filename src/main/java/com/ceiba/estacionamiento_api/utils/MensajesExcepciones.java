package com.ceiba.estacionamiento_api.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MensajesExcepciones {
	
	@Autowired
	private MessageSource messageSource;
	
	public String obtenerMensaje(String codigo)
	{
		return messageSource.getMessage(codigo,null,Locale.getDefault());
	}
}