package org.test.qa;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pages.qa.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.utils.qa.ExtentManager;
import org.utils.qa.Log;

public class LoginUITest extends ExtentManager{

	

	 LoginPage lp;

	    public LoginUITest() throws IOException {
	        super();
	    }

	    @BeforeMethod
	    public void setup() throws IOException {

	        begin();

	        lp = new LoginPage();

	        lp.clickLoginPage();
	    }

	    @Test(priority = 1)
	    public void verifyEmailFieldDisplayed() {

	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    	wait.until(ExpectedConditions.visibilityOf(lp.getEmail()));
	    	
	        Assert.assertTrue(
	                lp.isEmailFieldDisplayed(),
	                "Email field not displayed");
	        Log.info("Email field is displayed.");
	    }
	    

	    @Test(priority = 2)
	    public void verifyPasswordFieldDisplayed() {

	        Assert.assertTrue(
	                lp.isPasswordFieldDisplayed(),
	                "Password field not displayed");
	        Log.info("Password field is displayed.");
	    }

	    @Test(priority = 3)
	    public void verifyLoginButtonDisplayed() {

	        Assert.assertTrue(
	                lp.isLoginButtonDisplayed(),
	                "Login button not displayed");
	        Log.info("Login button is displayed.");
	    }

	    @Test(priority = 4)
	    public void verifyLoginPageTitle() {

	        Assert.assertTrue(
	                driver.getTitle().contains("Automation"),
	                "Page title mismatch");
	        Log.info("Title Matched.");
	    }

	    @AfterMethod
	    public void tearDown() {

	        driver.quit();
	    }
}






