<<<<<<< HEAD
package org.base.qa;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

		options.addArguments("--headless=new");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        
        // Wait for the Products link to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText("Products")));
        } catch (Exception e) {
            System.out.println("Products link not found, attempting to continue with test...");
        }
       
		

		driver.manage().deleteAllCookies();

	}
}
=======
package org.base.qa;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

		options.addArguments("--headless=new");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        
        // Wait for the Products link to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocatedBy(By.linkText("Products")));
        } catch (Exception e) {
            System.out.println("Products link not found, attempting to continue with test...");
        }
       
		

		driver.manage().deleteAllCookies();

	}
}
>>>>>>> af93cc3c3eff0fe9b45991bc6601b34ac2b839cf
