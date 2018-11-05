package com.ceiba.estacionamiento_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tcrm {
	private String trmPriceChange;
	private String trm;
	
	public Tcrm() {
	}

	public String getTrmPriceChange() {
		return trmPriceChange;
	}

	public void setTrmPriceChange(String trmPriceChange) {
		this.trmPriceChange = trmPriceChange;
	}

	public String getTrm() {
		return trm;
	}

	public void setTrm(String trm) {
		this.trm = trm;
	}
}