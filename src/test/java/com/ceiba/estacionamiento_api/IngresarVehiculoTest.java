package com.ceiba.estacionamiento_api;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento_api.dto.VehiculoDTO;
import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
import com.ceiba.estacionamiento_api.models.Parqueo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;
import com.ceiba.estacionamiento_api.services.impl.ParqueoCarro;
import com.ceiba.estacionamiento_api.services.impl.ParqueoMoto;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IngresarVehiculoTest {
	
	private VehiculoDTO vehiculoDTO;
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@Resource
	private MessageSource messageSource;
	
	@MockBean
	ParqueoRepository parqueoRepository;
	
	@Test
	public void vehiculoDTONulo()
	{
		//Arrange
		vehiculoDTO = null;
		try {
			//Act
			parquederoService.validarVehiculoDTONull(vehiculoDTO);
			fail();
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
			parquederoService.validarVehiculoDTONull(vehiculoDTO);
			fail();
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
			parquederoService.validarTipoVehiculo(vehiculoDTO.getTipoVehiculo());
			fail();
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
			parquederoService.validarCilindraje(vehiculoDTO);
			fail();
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
		
		parquederoService.setCalendar(new GregorianCalendar(2018,Calendar.OCTOBER,29)); // Lunes, 29 de Octubre de 2018
		
		try {
			//Act
			parquederoService.validarAccesoAlParqueadero(vehiculoDTO);
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
		vehiculoDTO.setPlaca("AXX000");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.CARRO.getCodigo());
		
		parquederoService.setCalendar(new GregorianCalendar(2018,Calendar.OCTOBER,30)); // Martes, 30 de Octubre de 2018
		
		try {
			//Act
			parquederoService.validarAccesoAlParqueadero(vehiculoDTO);
			fail();
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.invalidoPorDia",null,Locale.getDefault()));
		}		
	}
	
	@Test
	public void guardarMasMotosDeCapacidadPermitida()
	{
		//Arrange		
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("OLX123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.MOTO.getCodigo());
		vehiculoDTO.setCilindraje(125);
		
		Parqueo parqueo = new Parqueo();		
		parqueo.setVehiculo(vehiculoDTO.toModel());
				
		Mockito.when(parqueoRepository.obtenerParqueosActivosPorTipoVehiculo(TipoVehiculo.MOTO.getCodigo())).thenReturn(ParqueoMoto.TOTAL_ESPACIOS_DISPONIBLES);
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			fail();
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("parqueadero.sinEspacioDisponible",null,Locale.getDefault()));
		}
	}
	
	@Test
	public void guardarMasCarrosDeCapacidadPermitida()
	{
		//Arrange		
		vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("CBX123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.CARRO.getCodigo());
		
		Parqueo parqueo = new Parqueo();		
		parqueo.setVehiculo(vehiculoDTO.toModel());
		
		when(parqueoRepository.obtenerParqueosActivosPorTipoVehiculo(TipoVehiculo.CARRO.getCodigo())).thenReturn(ParqueoCarro.TOTAL_ESPACIOS_DISPONIBLES);
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			fail();
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("parqueadero.sinEspacioDisponible",null,Locale.getDefault()));
		}
	}
}