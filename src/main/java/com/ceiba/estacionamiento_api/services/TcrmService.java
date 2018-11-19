package com.ceiba.estacionamiento_api.services;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento_api.exceptions.TcrmException;
import com.ceiba.estacionamiento_api.models.Tcrm;

import trm.TCRMClient;

@Service
public class TcrmService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TcrmService.class);
		
	public Tcrm consultarTcrm() throws TcrmException
	{
		try {
			TCRMClient client = new TCRMClient();
			DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
			Tcrm tcrm = new Tcrm();
			tcrm.setTrm(decimalFormat.format(client.obtenerTRMActual()));
			return tcrm;
		} catch (Exception e) {
			LOGGER.info("error al consumir el WS de TCRM", e);
			throw new TcrmException(e.getMessage());
		}
	}
}