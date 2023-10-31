package com.fdp23;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class msilfirefox {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Pravalika N\\Downloads\\geckodriver1\\geckodriver.exe");
		
        WebDriver driver = new FirefoxDriver();
		
//		WebDriver driver = new ChromeDriver();
		driver.get("https://msilpoc.fdpconnect.com/");
		driver.manage().window().maximize();
		
		driver.findElement(By.id(":r0:")).sendKeys("msil2022@gmail.com");
		  driver.findElement(By.id(":r1:")).sendKeys("123456789");
		  driver.findElement(By.xpath("//button[text()='Login']")).click();
		  Thread.sleep(4000);
		  driver.findElement(By.xpath("//div[normalize-space()='HS code Clasify']")).click();
//		  Thread.sleep(5000);
//		  driver.findElement(By.xpath("//button[normalize-space()='New HS Code Classify']")).click();
		  Thread.sleep(5000);
		  driver.findElement(By.xpath("//tbody/tr[1]/td[7]/button[1]")).click();
		  Thread.sleep(5000);
		  
		for(int i=0;i<5;i++) {  
			
		  Thread.sleep(7000);
		  driver.findElement(By.xpath("(//span[contains(text(),'Upload or drop a file Accepts 25MB')])[1]")).click();
		  Thread.sleep(7000);
		  Runtime.getRuntime().exec("C:\\Users\\Pravalika N\\OneDrive - Sunsoft Solutions\\Documents\\msilfile\\msildoc"+i+".exe");
          
        

		}  
		Thread.sleep(3000);
		
		for(int i=0;i<7;i++) {
			Thread.sleep(7000);
			  driver.findElement(By.xpath("(//span[contains(text(),'Upload or drop a file Accepts 25MB')])[2]")).click();
			  Thread.sleep(7000);
			  Runtime.getRuntime().exec("C:\\Users\\Pravalika N\\OneDrive - Sunsoft Solutions\\Documents\\msilfile1\\msil"+i+".exe");
	          
		}
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)");
		
         Thread.sleep(9000);
		
		for(int i=0;i<7;i++) {
			Thread.sleep(7000);
			  driver.findElement(By.xpath("(//span[contains(text(),'Upload or drop a file Accepts 25MB')])[3]")).click();
			  Thread.sleep(7000);
			  Runtime.getRuntime().exec("C:\\Users\\Pravalika N\\OneDrive - Sunsoft Solutions\\Documents\\msilfile2\\msil"+i+".exe");
	          
		}
		
		
           Thread.sleep(9000);
		
		for(int i=0;i<9;i++) {
			Thread.sleep(7000);
			  driver.findElement(By.xpath("(//div)[110]")).click();
			  Thread.sleep(7000);
			  Runtime.getRuntime().exec("C:\\Users\\Pravalika N\\OneDrive - Sunsoft Solutions\\Documents\\msilfile3\\msil"+i+".exe");
	          
		}
		
		
		
		  
//          Thread.sleep(7000);
//          driver.findElement(By.xpath("(//button[normalize-space()='Text Extract'])[1]")).click();
//          Thread.sleep(100000);
//          driver.findElement(By.xpath("(//button[normalize-space()='Update'])[1]")).click();
//        //*[@id=":r32:"]
          
	}

}
