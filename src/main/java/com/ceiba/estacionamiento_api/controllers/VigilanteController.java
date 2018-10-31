package com.ceiba.estacionamiento_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("vigilante")
public class VigilanteController {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(VigilanteController.class);
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@PostMapping(value = "/ingresarVehiculo")
	@CrossOrigin
	public ResponseEntity<String> ingresarVehiculo(@RequestBody VehiculoDTO vehiculoDTO)
	{
		try {
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException exception) {
			LOGGER.info("Error en /ingresarVehiculo",exception);
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	@GetMapping(value = "/retirarVehiculo/{placa}")
	@CrossOrigin
	public ResponseEntity<Parqueo> retirarVehiculo(@PathVariable String placa)
	{
		try {
			return new ResponseEntity<>(parquederoService.retirarVehiculo(placa), HttpStatus.OK ) ;
		} catch (VehiculoNoAdmitidoException exception) {
			LOGGER.info("Error en /retirarVehiculo",exception);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST ) ;
		}
	}
	
	@GetMapping(value = "/obtenerParqueados")
	@CrossOrigin
	public ResponseEntity<List<Parqueo>> obtenerVehiculosParqueados()
	{
		return null;
	}
	
}