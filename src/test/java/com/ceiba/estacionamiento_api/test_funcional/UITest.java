package com.ceiba.estacionamiento_api.test_funcional;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ceiba.estacionamiento_api.Utilidades;

@RunWith(SpringJUnit4ClassRunner.class)
public class UITest {
	private static final String URL_TEST = "http://35.243.194.8/estacionamiento/dist/estacionamiento/";
	private static final String MENSAJE_BIENVENDIA = "Estacionamiento - ADN";
	private static final String PARTE_MENSAJE_EXITO = "exitosamente";
	
	@Test
	public void paginaInicial() {
		//Assert
		WebDriver driver = new ChromeDriver();
		driver.get(URL_TEST);
		assertTrue(driver.findElement(By.id("tituloCeiba")).getText().equals(MENSAJE_BIENVENDIA));
		driver.quit();
	}
	
	@Test
	public void ingresaVehiculo()
	{
		WebDriver driver = new ChromeDriver();
		driver.get(URL_TEST);
				
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
		
		driver.quit();
	}
}