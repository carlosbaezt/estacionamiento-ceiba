package com.ceiba.estacionamiento_api.test_funcional;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ceiba.estacionamiento_api.Utilidades;


@RunWith(SpringJUnit4ClassRunner.class)
public class UITest {
	private static final String URL_TEST = "http://35.243.194.8/estacionamiento/dist/estacionamiento/";
	private static final String MENSAJE_BIENVENDIA = "Estacionamiento - ADN";
	private static final String PARTE_MENSAJE_EXITO = "exitosamente";
	private static WebDriver driver;
	
	@BeforeClass
	public static void inicializarDriver()
	{
		System.setProperty("webdriver.chrome.driver", UITest.generarRutaDriver());
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--no-sandbox"); // Bypass OS security model
		
		driver = new ChromeDriver(options);
	}
	
	@AfterClass
	public static void cerrarDriver()
	{
		driver.close();
	}
	
	@Test
	public void paginaInicial() {
		//Arrange		
		
		//Act
		driver.get(URL_TEST);
		
		//Assert
		assertTrue(driver.findElement(By.id("tituloCeiba")).getText().equals(MENSAJE_BIENVENDIA));
	}
	
	@Test
	public void ingresaVehiculo()
	{
		//Arrange
		driver.get(URL_TEST);
		driver.findElement(By.id("lblTipoVehiculoCarro")).click();
		WebElement placaElement = driver.findElement(By.id("placa"));
		placaElement.sendKeys(Utilidades.generarPlacaAleatoria());
		WebElement btnAceptar = driver.findElement(By.id("btnIngresarVehiculo"));
		
		//Act
		btnAceptar.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeIngresarVehiculo")));

		//Assert
		assertTrue(driver.findElement(By.id("mensajeIngresarVehiculo")).getText().contains(PARTE_MENSAJE_EXITO));
	}
	
	
	public static String generarRutaDriver()
	{
		String os = System.getProperty("os.name");
		if(os.contains("Windows"))
		{
			return "lib/chromedriver.exe";
		}
		else
		{
			return "/opt/Jenkins/workspace/CeibaInduccion/Ceiba-Estacionamiento(carlos.baez)/lib/chromedriver";
		}
	}
}
