package com.ceiba.estacionamiento_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;

@RestController
@RequestMapping("vigilante")
public class VigilanteController {
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@RequestMapping(value = "/ingresarVehiculo", method = RequestMethod.POST)
	public String ingresarVehiculo(@RequestBody VehiculoDTO vehiculoDTO)
	{
		try {
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			return e.getMessage();
		}
		
		return "Todo OK";
	}
	
	@RequestMapping(value = "/sacarVehiculo", method = RequestMethod.GET)
	public void sacarVehiculo(@RequestParam String placa)
	{
		
	}
	
	@RequestMapping(value = "/obtenerParqueados", method = RequestMethod.GET)
	public String obtenerVehiculosParqueados()
	{
		return "HOLA";
	}
	
	@RequestMapping(value = "/guardarTipoVehiculo", method = RequestMethod.GET)
	public String guardarTipoVehiculo()
	{
		return "HOLAXX";
		
	}
	
}