package com.ceiba.estacionamiento_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("vigilante")
public class VigilanteController {
		
	private static final Logger log = LoggerFactory.getLogger(VigilanteController.class);
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@PostMapping(value = "/ingresarVehiculo")
	@CrossOrigin
	public String ingresarVehiculo(@RequestBody VehiculoDTO vehiculoDTO)
	{
		try {
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			log.error(e.getMessage());
			return e.getMessage();
		}
		
		return "Todo OK";
	}
	
	@GetMapping(value = "/retirarVehiculo/{placa}")
	@CrossOrigin
	public String retirarVehiculo(@PathVariable String placa)
	{
		try {
			return String.valueOf(parquederoService.retirarVehiculo(placa));
		} catch (VehiculoNoAdmitidoException e) {
			log.error(e.getMessage());
			return e.getMessage();
		}
	}
	
	@GetMapping(value = "/obtenerParqueados")
	@CrossOrigin
	public String obtenerVehiculosParqueados()
	{
		return "HOLA";
	}
	
}