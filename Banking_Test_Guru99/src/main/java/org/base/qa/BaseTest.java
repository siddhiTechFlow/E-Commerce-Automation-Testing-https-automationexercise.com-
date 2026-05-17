package org.base.qa;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

	public static WebDriver driver;
	public static Properties prop;
	
	public BaseTest() throws IOException {
		
		FileInputStream fs = new FileInputStream("C:\\JAVA\\CucumberFramework\\Banking_Test_Guru99\\application.properties");
		prop = new Properties();
		prop.load(fs);
	}
	
	public void start() {
		
		String bws = prop.getProperty("browser");
		
		if(bws.equals("edge")) {
			
			driver = new EdgeDriver();
		}else if(bws.equals("chrome")) {
			
			driver = new ChromeDriver();
		}else if(bws.equals("FireFox")) {
			
			driver = new FirefoxDriver();
		}else {
			System.out.println("No browser found...");
		}
		
		
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		
		
		
	}
}
