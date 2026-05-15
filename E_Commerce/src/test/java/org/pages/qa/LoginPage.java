package org.pages.qa;

import java.io.IOException;
import java.time.Duration;

import org.base.qa.BaseTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseTest {

	private WebDriverWait wait;

	public LoginPage() throws IOException {

		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy(xpath = "//a[@href='/login']")
	private WebElement loginPageLink;

	@FindBy(xpath = "//input[@data-qa='login-email']")
	private WebElement email;

	public WebElement getEmail() {
		return email;
	}

	@FindBy(name = "password")
	private WebElement pass;

	@FindBy(xpath = "//button[@data-qa='login-button']")
	private WebElement loginbtn;

	@FindBy(xpath = "//a[@href='/logout']")
	private WebElement logoutbtn;

	@FindBy(xpath = "//p[text()='Your email or password is incorrect!']")
	private WebElement errorMsg;

	// ================= ACTION METHODS =================

	public void clickLoginPage() {

		wait.until(ExpectedConditions.elementToBeClickable(loginPageLink));

		loginPageLink.click();
	}

	public void enterEmail(String mail) {

		wait.until(ExpectedConditions.visibilityOf(email));

		email.clear();

		email.sendKeys(mail);
	}

	public void enterPass(String pwd) {

		wait.until(ExpectedConditions.visibilityOf(pass));

		pass.clear();

		pass.sendKeys(pwd);
	}

	public void clickLoginButton() {

		wait.until(ExpectedConditions.elementToBeClickable(loginbtn));

		loginbtn.click();
	}

	public void login(String em, String pwd) {

		clickLoginPage();

		enterEmail(em);

		enterPass(pwd);

		clickLoginButton();
	}

	public void loginWithEnterKey(String em, String pwd) {

		clickLoginPage();

		email.sendKeys(em);

		pass.sendKeys(pwd);

		pass.sendKeys(Keys.ENTER);
	}

	public void logout() {

		wait.until(ExpectedConditions.elementToBeClickable(logoutbtn));

		logoutbtn.click();
	}
	// ================= VALIDATION METHODS =================

	public boolean isLogoutBtnDisplayed() {

		try {

			wait.until(ExpectedConditions.visibilityOf(logoutbtn));

			return logoutbtn.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}

	public boolean isErrorMessageDisplayed() {

		try {

			wait.until(ExpectedConditions.visibilityOf(errorMsg));

			return errorMsg.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}

	public String getErrorMsg() {

		return errorMsg.getText();
	}

	public String getEmailValidationMessage() {

		return email.getAttribute("validationMessage");
	}

	public String getPasswordValidationMessage() {

		return pass.getAttribute("validationMessage");
	}
	// ================= UI METHODS =================

	public boolean isEmailFieldDisplayed() {

		return email.isDisplayed();
	}

	public boolean isPasswordFieldDisplayed() {

		return pass.isDisplayed();
	}

	public boolean isLoginButtonDisplayed() {

		return loginbtn.isDisplayed();
	}

}
