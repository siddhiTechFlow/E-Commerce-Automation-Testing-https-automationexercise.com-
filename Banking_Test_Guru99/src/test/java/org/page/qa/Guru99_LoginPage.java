package org.page.qa;

import java.io.IOException;

import org.base.qa.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Guru99_LoginPage extends BaseTest{

	public Guru99_LoginPage() throws IOException {
		
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[text()='here']")
	private WebElement hereLink;
	
	@FindBy(name= "emailid")
	private WebElement email;

	@FindBy(name = "btnLogin")
	private WebElement submitButton;
	
	
	@FindBy(xpath = "//td[text()='User ID :']/following-sibling::td")
	private WebElement userID;
	
	@FindBy(xpath =  "//td[text()='Password :']/following-sibling::td")
	private WebElement pwd;
	

    @FindBy(name = "uid")
    private WebElement uidField;

    
    @FindBy(name = "password")
    private WebElement passwordField;

	
	@FindBy(xpath = "//input[@value='LOGIN']")
	private WebElement logBtn;
	
	public void clickLink() {
		
		hereLink.click();
	}
	
	public void emailField(String em) {
		
		email.sendKeys(em);
	}
	
	public void submitButtonClick() {
		
		submitButton.click();
	}
	
	public String uid() {

        return userID.getText();
    }

	
	 public String getPassword() {

	        return pwd.getText();
	    }

	
	  public void enterUserID(String uid) {

	        uidField.sendKeys(uid);
	    }

	  
	  public void enterPasswordField(String pass) {

	        passwordField.sendKeys(pass);
	    }
    public void clickLoginButton() {

        logBtn.click();
    }
}
