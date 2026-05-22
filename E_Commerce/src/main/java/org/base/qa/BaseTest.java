package org.base.qa;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import groovyjarjarantlr4.v4.parse.ANTLRParser.optionsSpec_return;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {

	public static WebDriver driver;
	public static Properties prop;
	
	

	public BaseTest() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/application.properties");
		prop = new Properties();
		prop.load(fis);
	}

	public void begin() {

		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

		Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);
		
		ChromeOptions options = new ChromeOptions();
		
		//CI-safe Chrome Flags 
		options.addArguments("--headless=new");
		options.addArguments("--no-sandbox");
		options.addArguments("");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");

		String bws = prop.getProperty("browser");

		if (bws.equals("Edge")) {
			driver = new EdgeDriver();
		} else if (bws.equals("Chrome")) {
			driver = new ChromeDriver(options);
		} else if (bws.equals("Safari")) {
			driver = new SafariDriver();
		} else if (bws.equals("FireFox")) {
			driver = new FirefoxDriver();
		} else {
			System.out.println("No browser found...");
		}

		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        
       
		

		driver.manage().deleteAllCookies();

	}
}
