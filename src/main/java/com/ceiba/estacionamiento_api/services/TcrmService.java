package com.ceiba.estacionamiento_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ceiba.estacionamiento_api.exceptions.TcrmException;
import com.ceiba.estacionamiento_api.models.Tcrm;

@Service
public class TcrmService{
	
	private static final String URL_REST = "http://www.set-fx.com/stats";
	private static final Logger LOGGER = LoggerFactory.getLogger(TcrmService.class);
		
	public Tcrm consultarTcrm() throws TcrmException
	{
		try
		{
			RestTemplate restTemplate = new RestTemplate();
	        return restTemplate.getForObject(URL_REST, Tcrm.class);
		}catch( RestClientException exception)
		{
			LOGGER.info("Error en /obtenerTrm",exception);
			throw new TcrmException(exception.getMessage());
		}
	}
}