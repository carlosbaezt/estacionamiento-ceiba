package com.ceiba.estacionamiento_api;

import static org.junit.Assert.fail;

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
public class IngresoDuplicadoTest {
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@Resource
	private MessageSource messageSource;
	
	@Test
	public void ingresarVehiculoCarroDosVeces()
	{
		//Arrange
		VehiculoDTO vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("NET456");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.CARRO.getCodigo());
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			parquederoService.ingresarVehiculo(vehiculoDTO);
			fail();
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.actualmenteParqueado",null,Locale.getDefault()));
		}		
	}
	
	@Test
	public void ingresarVehiculoMotoDosVeces()
	{
		//Arrange
		VehiculoDTO vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("BUY123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.MOTO.getCodigo());
		vehiculoDTO.setCilindraje(125);
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			parquederoService.ingresarVehiculo(vehiculoDTO);
			fail();			
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.actualmenteParqueado",null,Locale.getDefault()));
		}		
	}


}
