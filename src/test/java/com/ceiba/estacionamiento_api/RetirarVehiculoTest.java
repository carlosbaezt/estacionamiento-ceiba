package com.ceiba.estacionamiento_api;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;
import com.ceiba.estacionamiento_api.services.impl.ParqueoCarro;
import com.ceiba.estacionamiento_api.services.impl.ParqueoMoto;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RetirarVehiculoTest {
	
	@Autowired
	ParqueaderoService parquederoService;
	
	@Autowired
	ParqueoMoto parqueoMoto;
	
	@Autowired
	ParqueoCarro parqueoCarro;
	
	@Resource
	private MessageSource messageSource;
	
	@Test
	public void retirarVehiculoNoParqueado()
	{
		//Arrange
		
		try {
			//Act
			parquederoService.retirarVehiculo("UUU000");
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			Assert.assertEquals(e.getMessage(), messageSource.getMessage("vehiculo.noEstaParqueado",null,Locale.getDefault()));
		}
	}
	
	@Test
	public void valorParqueoMotoPorTiempoDosHoras()
	{
		//Arrange
		Date fechaIngreso = new GregorianCalendar(2018,Calendar.OCTOBER,1,10,0,0).getTime();
		Date fechaSalida = new GregorianCalendar(2018,Calendar.OCTOBER,1,12,0,0).getTime();
		
		//Act
		BigDecimal precioParqueo = parqueoMoto.calcularPrecioPorTiempo(
				fechaIngreso,
				fechaSalida,
				ParqueoMoto.VALOR_DIA,
				ParqueoMoto.VALOR_HORA);
		
		//Assert
		Assert.assertEquals(0, precioParqueo.compareTo(BigDecimal.valueOf(ParqueoMoto.VALOR_HORA * 2)));
	}
	
	@Test
	public void valorParqueoMotoPorTiempoNueveHoras()
	{
		//Arrange
		Date fechaIngreso = new GregorianCalendar(2018,Calendar.OCTOBER,1,10,0,0).getTime();
		Date fechaSalida = new GregorianCalendar(2018,Calendar.OCTOBER,1,19,0,0).getTime();
		
		//Act
		BigDecimal precioParqueo = parqueoMoto.calcularPrecioPorTiempo(
				fechaIngreso,
				fechaSalida,
				ParqueoMoto.VALOR_DIA,
				ParqueoMoto.VALOR_HORA);
		
		//Assert
		Assert.assertEquals(0, precioParqueo.compareTo(BigDecimal.valueOf(ParqueoMoto.VALOR_DIA)));
	}
	
	@Test
	public void valorParqueoCarroUnDiaTresHoras()
	{
		//Arrange
		Date fechaIngreso = new GregorianCalendar(2018,Calendar.OCTOBER,1,8,0,0).getTime();
		Date fechaSalida = new GregorianCalendar(2018,Calendar.OCTOBER,2,11,0,0).getTime();
		
		//Act
		BigDecimal precioParqueo = parqueoCarro.calcularPrecioPorTiempo(
				fechaIngreso,
				fechaSalida,
				ParqueoCarro.VALOR_DIA,
				ParqueoCarro.VALOR_HORA);
		
		//Assert
		Assert.assertEquals(0, precioParqueo.compareTo(BigDecimal.valueOf(11000)));
	}
	
	@Test
	public void valorParqueoMoto10Horas650Cilindraje()
	{
		//Arrange		
		Date fechaIngreso = new GregorianCalendar(2018,Calendar.OCTOBER,1,10,0,0).getTime();
		Date fechaSalida = new GregorianCalendar(2018,Calendar.OCTOBER,1,20,0,0).getTime();
		
		ParqueoEntity parqueoEntity = new ParqueoEntity();
		parqueoEntity.setFechaIngreso(fechaIngreso);
		parqueoEntity.setFechaSalida(fechaSalida);
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setCilindraje(650);
		parqueoEntity.setVehiculo(vehiculoEntity);
		
		//Act
		BigDecimal precioParqueo = parqueoMoto.valorParqueoMoto(parqueoEntity);
		
		//Assert
		Assert.assertEquals(0, precioParqueo.compareTo(BigDecimal.valueOf(6000)));
	}
	
	@Test
	public void retirarCarroIngresado()
	{
		//Arrange
		VehiculoDTO vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("ZXC875");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.CARRO.getCodigo());
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			parquederoService.retirarVehiculo(vehiculoDTO.getPlaca());
			
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			fail();
		}		
	}
	
	@Test
	public void retirarMotoIngresada()
	{
		//Arrange
		VehiculoDTO vehiculoDTO = new VehiculoDTO();
		vehiculoDTO.setPlaca("UGD123");
		vehiculoDTO.setTipoVehiculo(TipoVehiculo.MOTO.getCodigo());
		vehiculoDTO.setCilindraje(200);
		
		try {
			//Act
			parquederoService.ingresarVehiculo(vehiculoDTO);
			parquederoService.retirarVehiculo(vehiculoDTO.getPlaca());
			
		} catch (VehiculoNoAdmitidoException e) {
			//Assert
			fail();
		}		
	}

}