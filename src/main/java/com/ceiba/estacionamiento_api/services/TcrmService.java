package com.ceiba.estacionamiento_api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ceiba.estacionamiento_api.exceptions.TcrmException;
import com.ceiba.estacionamiento_api.models.Tcrm;

@Service
public class TcrmService{
	
	private static final String URL_REST = "http://www.set-fx.com/stats";
		
	public Tcrm consultarTcrm() throws TcrmException
	{
		try
		{
			RestTemplate restTemplate = new RestTemplate();
	        return restTemplate.getForObject(URL_REST, Tcrm.class);
		}catch( RestClientException e)
		{
			throw new TcrmException(e.getMessage());
		}
	}
}