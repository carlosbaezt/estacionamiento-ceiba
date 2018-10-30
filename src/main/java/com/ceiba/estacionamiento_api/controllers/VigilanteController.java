package com.ceiba.estacionamiento_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping(value = "/ingresarVehiculo")
	@CrossOrigin
	public String ingresarVehiculo(@RequestBody VehiculoDTO vehiculoDTO)
	{
		try {
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			return e.getMessage();
		}
		
		return "Todo OK";
	}
	
	@GetMapping(value = "/sacarVehiculo")
	@CrossOrigin
	public String sacarVehiculo(@RequestParam String placa)
	{
		return "HOLA";		
	}
	
	@GetMapping(value = "/obtenerParqueados")
	@CrossOrigin
	public String obtenerVehiculosParqueados()
	{
		return "HOLA";
	}
	
}