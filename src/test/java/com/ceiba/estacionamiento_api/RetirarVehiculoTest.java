package com.ceiba.estacionamiento_api;

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
import com.ceiba.estacionamiento_api.exceptions.VehiculoNoAdmitidoException;
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
	public void valorParqueoMotoPorTiempoUnaHora()
	{
		//Arrange
		Date fechaIngreso = new GregorianCalendar(2018,Calendar.OCTOBER,1,10,0,0).getTime();
		Date fechaSalida = new GregorianCalendar(2018,Calendar.OCTOBER,1,11,0,0).getTime();
		
		//Act
		BigDecimal precioParqueo = parqueoMoto.calcularPrecioPorTiempo(
				fechaIngreso,
				fechaSalida,
				ParqueoMoto.VALOR_DIA,
				ParqueoMoto.VALOR_HORA);
		
		//Assert
		Assert.assertEquals(0, precioParqueo.compareTo(BigDecimal.valueOf(ParqueoMoto.VALOR_HORA)));
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
	public void valorParqueoCarroPorTiempo27Horas()
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

}