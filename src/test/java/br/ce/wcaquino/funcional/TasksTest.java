package br.ce.wcaquino.funcional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
		//WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.72:4444/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("http://192.168.1.72:8001/tasks/");
		return driver;
	}
	
	@Test
	public void DeveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
		driver.findElement(By.id("addTodo")).click();
		
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
		driver.findElement(By.id("saveButton")).click();
		
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Successo!", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
		driver.findElement(By.id("addTodo")).click();
		
		//driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
		driver.findElement(By.id("saveButton")).click();
		
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the task description", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void DeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
		driver.findElement(By.id("addTodo")).click();
		
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		//driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
		driver.findElement(By.id("saveButton")).click();
		
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the due date", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
		driver.findElement(By.id("addTodo")).click();
		
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("dueDate")).sendKeys("10/10/2019");
		driver.findElement(By.id("saveButton")).click();
		
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Due date must not be in past", message);
		} finally {
			driver.quit();
		}
		
	}

}
