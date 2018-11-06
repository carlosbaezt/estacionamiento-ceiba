package com.ceiba.estacionamiento_api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento_api.enums.TipoVehiculo;
import com.ceiba.estacionamiento_api.persistence.ParqueoRepository;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.TipoVehiculoEntity;
import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;
import com.ceiba.estacionamiento_api.services.ParqueaderoService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ListarVehiculosTest {
	
	@Autowired
	private ParqueaderoService parquederoService;
	
	@MockBean
	private ParqueoRepository parqueoRepository;
	
	@Test
	public void obtenerVehiculosParqueados()
	{
		List<ParqueoEntity> parqueosEntity = datosPrueba();
		
		Mockito.when(parqueoRepository.obtenerParqueosActivos()).thenReturn(parqueosEntity);
		
		Assert.assertEquals(parquederoService.obtenerVehiculosParqueados().size(), parqueosEntity.size());		
	}
	
	private List<ParqueoEntity> datosPrueba()
	{
		List<ParqueoEntity> parqueosEntity = new ArrayList<ParqueoEntity>();
		
		TipoVehiculoEntity tipoVehiculoFake = new TipoVehiculoEntity();
		tipoVehiculoFake.setId((long) TipoVehiculo.CARRO.getCodigo());
		
		VehiculoEntity vehiculoFake = new VehiculoEntity();
		vehiculoFake.setTipoVehiculo(tipoVehiculoFake);
		vehiculoFake.setPlaca("ABC123");
		
		ParqueoEntity parqueoEntityFake = new ParqueoEntity();
		parqueoEntityFake.setFechaIngreso(Calendar.getInstance().getTime());
		parqueoEntityFake.setFechaSalida(Calendar.getInstance().getTime());
		parqueoEntityFake.setPrecio(new BigDecimal(1000));
		parqueoEntityFake.setVehiculo(vehiculoFake);
		
		parqueosEntity.add(parqueoEntityFake);
		
		
		return parqueosEntity;
	}

}
