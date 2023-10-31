package com.fdp23;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.io.IOException;

import org.openqa.selenium.By;
public class firefoxfdp {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Pravalika N\\Downloads\\geckodriver1\\geckodriver.exe");
//		System.setProperty("webdriver.chrome.driver","D:\\fdp\\chromedriver_win32\\chromedriver.exe");


		WebDriver driver = new FirefoxDriver();
		
//		WebDriver driver = new ChromeDriver();
		driver.get("https://app.fdpconnect.com/");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("cha@module.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password@1");
		driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[contains(@class,'css-18q3pzf')]//div[3]//li[1]")).click();
		driver.findElement(By.xpath("//span[normalize-space()='Documents Set']")).click();
		
		
		
		Thread.sleep(7000);
		driver.findElement(By.xpath("//tbody/tr[1]/td[7]/a[1]//*[name()='svg']//*[name()='path' and contains(@d,'M12 4.5C7 ')]")).click();
		Thread.sleep(6000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)");
		Thread.sleep(3000);
		
		 WebElement elemnt1 = driver.findElement(By.xpath("(//button[@aria-label='View'])[1]"));
		   elemnt1.click();

			  Actions action1 = new Actions(driver);
			  action1.keyDown(Keys.CONTROL).sendKeys(Keys.ARROW_DOWN,Keys.ENTER).build().perform();
		
//		driver.findElement(By.id("(//button[normalize-space()='Text Extract'])[1]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//button[normalize-space()='Update'])[1]")).click();
		Thread.sleep(7000);
		driver.findElement(By.xpath("//button[normalize-space()='Submit to Logisys']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[normalize-space()='Products'])[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//tbody/tr[1]/th[1]/div[1]/button[1]//*[name()='svg']")).click();
		Thread.sleep(70000);
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		Thread.sleep(70000);
		driver.findElement(By.xpath("//span[normalize-space()='chapter 85-Electrical machinery and equipment']")).click();

		Thread.sleep(40000);
		
		driver.findElement(By.xpath("//span[contains(text(),'8501-ELECTRIC MOTORS AND GENERATORS (EXCLUDING GEN')]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//span[contains(text(),'Universal AC or DC motors of an output exceeding 3')]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[3]/button")).click();
		
		Thread.sleep(2000);
		driver.get("https://app.fdpconnect.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("exporter.fdp@gmail.com");
		driver.findElement(By.xpath("(//input[@id='password'])[1]")).sendKeys("Nani@1234");
		driver.findElement(By.xpath("//button[normalize-space()='Sign in']")).click();
		Thread.sleep(5000);

		
//		WebElement targetElement = driver.findElement(By.xpath("//p[contains(text(),'1. This Section does not cover : (a) transmission ')]"));
//		
//		Actions actions = new Actions(driver);
//		actions.scrollToElement(targetElement).perform();

		
//		WebElement checkbox = driver.findElement(By.xpath("//p[contains(text(),'1. This Section does not cover : (a) transmission ')]"));
//		JavascriptExecutor jse1 = (JavascriptExecutor)driver;
//		jse1.executeScript("arguments[0].scrollIntoView(true);",checkbox);
	}
	
}
