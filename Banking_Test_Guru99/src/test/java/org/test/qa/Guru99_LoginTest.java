package org.test.qa;

import java.io.IOException;
import java.time.Duration;

import org.base.qa.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.page.qa.Guru99_LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Guru99_LoginTest extends BaseTest {

	Guru99_LoginPage gl;

	public Guru99_LoginTest() throws IOException {
		super();

	}

	@BeforeMethod
	public void setUp() throws IOException {
		start();

		gl = new Guru99_LoginPage();

	}

	@Test
	public void TC_001() {

		gl.clickLink();

		// how to generate random email
		// for that do the following

		int random = (int) (Math.random() * 1000);
		String email = "test" + random + "@gmail.com";

		gl.emailField(email);

		gl.submitButtonClick();

		String userID = gl.uid();

		String pswd = gl.getPassword();

		System.out.println("Generated Email : " + email);

		System.out.println("User ID : " + userID);

		System.out.println("Password : " + pswd);

		driver.navigate().to("https://demo.guru99.com/V4/");

		gl.enterUserID(userID);

		gl.enterPasswordField(pswd);

		gl.clickLoginButton();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		wait.until(ExpectedConditions.titleContains("Guru99 Bank Manager HomePage"));

		System.out.println("Login Successful");

		String actualTitle = driver.getTitle();

		String expectedTitle = "Guru99 Bank Manager HomePage";

		Assert.assertEquals(actualTitle, expectedTitle, "No match...");
	}

	@AfterMethod
	public void tearDown() {

		driver.quit();
	}

}
