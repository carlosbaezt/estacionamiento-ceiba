package com.ceiba.estacionamiento_api;

import static org.junit.Assert.fail;

import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IngresarVehiculoTest {
	
	private VehiculoDTO vehiculoDTO;
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@Resource
	private MessageSource messageSource;
	
	
	@Test
	public void vehiculoDTONulo()
	{
		//Arrange
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.nulo",null,Locale.getDefault()));
		}
	}
	
	@Test
	public void vehiculoDTOSinPlaca()
	{
		//Arrange
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca(null);
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.placaNula",null,Locale.getDefault()));
		}
	}
	
	@Test
	public void vehiculoDTOTipoVehiculoInvalido()
	{
		//Arrange
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("JPA123");
		vehiculoDTO.setTipoVehiculo(10);
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.tipoVehiculoInvalido",null,Locale.getDefault()));
		}
	}
	
	@Test
	public void vehiculoDTOMotoSinCilindraje()
	{
		//Arrange
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("JPA123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.MOTO.getCodigo());
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.cilindrajeNulo",null,Locale.getDefault()));
		}
	}
	
	@Test
	public void vehiculoPlacaConLetraADiaValido()
	{
		//Arrange
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("ABC123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.CARRO.getCodigo());
		
		parquederoService.setCalendar(new GregorianCalendar(2018,9,29)); // Lunes, 29 de Octubre de 2018
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			//Assert
		} catch (VehiculoNoAdmitidoException e) {
			fail();
		}		
	}
	
	@Test
	public void vehiculoPlacaConLetraADiaInvalido()
	{
		//Arrange
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("ABC123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.CARRO.getCodigo());
		
		parquederoService.setCalendar(new GregorianCalendar(2018,9,30)); // Martes, 30 de Octubre de 2018
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.invalidoPorDia",null,Locale.getDefault()));
		}		
	}

}
