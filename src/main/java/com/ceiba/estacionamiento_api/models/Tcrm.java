package com.ceiba.estacionamiento_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tcrm {
	private String trm;
	
	public String getTrm() {
		return trm;
	}

	public void setTrm(String trm) {
		this.trm = trm;
	}
}