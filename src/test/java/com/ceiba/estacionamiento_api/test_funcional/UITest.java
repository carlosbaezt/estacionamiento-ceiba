package com.ceiba.estacionamiento_api.test_funcional;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ceiba.estacionamiento_api.Utilidades;

public class UITest {
	
	public static WebDriver driver;
	private static final String URL_TEST = "http://localhost:4200/";
	private static final String MENSAJE_BIENVENDIA = "Estacionamiento - ADN";
	private static final String PARTE_MENSAJE_EXITO = "exitosamente";
	
	@BeforeClass
	public static void inicializarDriver(){
		driver = new ChromeDriver();
		driver.get(URL_TEST);
	}
	
	@AfterClass
	public static void cerrarDriver()
	{
		driver.quit();
	}
	
	@Test
	public void paginaInicial() {
		//Assert
		assertTrue(driver.findElement(By.id("tituloCeiba")).getText().equals(MENSAJE_BIENVENDIA));
	}
	
	@Test
	public void ingresaVehiculo()
	{
		//Arrange
		driver.findElement(By.id("lblTipoVehiculoCarro")).click();
		
		WebElement placaElement = driver.findElement(By.id("placa"));
		placaElement.sendKeys(Utilidades.generarPlacaAleatoria());
		
		WebElement btnAceptar = driver.findElement(By.id("btnIngresarVehiculo"));
		//Act
		btnAceptar.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);// 1 minute 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeIngresarVehiculo")));

		//Assert
		assertTrue(driver.findElement(By.id("mensajeIngresarVehiculo")).getText().contains(PARTE_MENSAJE_EXITO));
		
		driver.close();
	}
}