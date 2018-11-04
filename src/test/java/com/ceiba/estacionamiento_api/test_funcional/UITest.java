package com.ceiba.estacionamiento_api.test_funcional;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ceiba.estacionamiento_api.Utilidades;

@RunWith(SpringJUnit4ClassRunner.class)
public class UITest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UITest.class);
	
	public static WebDriver driver;
	private static final String URL_TEST = "http://35.243.194.8/estacionamiento/dist/estacionamiento/";
	private static final String MENSAJE_BIENVENDIA = "Estacionamiento - ADN";
	private static final String PARTE_MENSAJE_EXITO = "exitosamente";
	
	@BeforeClass
	public static void inicializarDriver(){
		try
		{
			UITest.driver = new ChromeDriver();
			UITest.driver.get(URL_TEST);
		}
		catch(WebDriverException  e)
		{
			LOGGER.info("Error en /WebDriverException",e);
		}
	}
	
	@AfterClass
	public static void cerrarDriver()
	{
		UITest.driver.quit();
	}
	
	@Test
	public void paginaInicial() {
		//Assert
		assertTrue(UITest.driver.findElement(By.id("tituloCeiba")).getText().equals(MENSAJE_BIENVENDIA));
	}
	
	@Test
	public void ingresaVehiculo()
	{
		//Arrange
		UITest.driver.findElement(By.id("lblTipoVehiculoCarro")).click();
		
		WebElement placaElement = UITest.driver.findElement(By.id("placa"));
		placaElement.sendKeys(Utilidades.generarPlacaAleatoria());
		
		WebElement btnAceptar = UITest.driver.findElement(By.id("btnIngresarVehiculo"));
		//Act
		btnAceptar.click();
		WebDriverWait wait = new WebDriverWait(UITest.driver, 5);// 1 minute 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeIngresarVehiculo")));

		//Assert
		assertTrue(UITest.driver.findElement(By.id("mensajeIngresarVehiculo")).getText().contains(PARTE_MENSAJE_EXITO));
		
		UITest.driver.close();
	}
}