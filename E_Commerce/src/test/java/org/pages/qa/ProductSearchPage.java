package org.pages.qa;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductSearchPage {

	WebDriver driver;

	WebDriverWait wait;

	// ================= LOCATORS =================

	@FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
	WebElement loginBtn;

	@FindBy(name = "email")
	WebElement email;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(xpath = "//button[contains(text(),'Login')]")
	WebElement loginSubmitBtn;

	@FindBy(xpath = "//a[contains(text(),'Products')]")
	WebElement productsBtn;

	@FindBy(id = "search_product")
	WebElement searchBox;

	@FindBy(id = "submit_search")
	WebElement searchBtn;

	@FindBy(xpath = "//h2[contains(text(),'Searched Products')]")
	WebElement searchedProductsTitle;

	@FindBy(xpath = "//div[@class='productinfo text-center']/p")
	List<WebElement> productNames;

	@FindBy(xpath = "//a[contains(text(),'View Product')]")
	List<WebElement> viewProductBtns;

	// ================= CONSTRUCTOR =================

	public ProductSearchPage(WebDriver driver) {

		this.driver = driver;

		wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		PageFactory.initElements(driver, this);
	}

	// ================= LOGIN =================

	public void login(String userEmail, String userPassword) {

		driver.get("https://automationexercise.com");

		wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();

		wait.until(ExpectedConditions.visibilityOf(email)).sendKeys(userEmail);

		password.sendKeys(userPassword);

		loginSubmitBtn.click();
	}

	// ================= CLOSE AD POPUP =================

	public void closeAdIfPresent() {

		try {

			driver.switchTo().frame(0);

			List<WebElement> closeBtn =
					driver.findElements(
							By.xpath(
									"//*[contains(@id,'close') or contains(@class,'close')]"));

			if (closeBtn.size() > 0) {

				closeBtn.get(0).click();

				System.out.println("Ad closed");
			}

			driver.switchTo().defaultContent();
		}

		catch (Exception e) {

			driver.switchTo().defaultContent();
		}
	}
	// ================= NAVIGATE TO PRODUCTS =================

	public void goToProductsPage() {

		wait.until(ExpectedConditions.visibilityOf(productsBtn));

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click();", productsBtn);

		wait.until(ExpectedConditions.visibilityOf(searchBox));
	}
	// ================= SEARCH PRODUCT =================

	public void searchProduct(String productName) {

		wait.until(ExpectedConditions.visibilityOf(searchBox));

		searchBox.clear();

		searchBox.sendKeys(productName);

		searchBtn.click();

		wait.until(ExpectedConditions.visibilityOf(searchedProductsTitle));

		scrollToElement(searchedProductsTitle);
	}

	// ================= GET RESULT COUNT =================

	public int getSearchResultCount() {

	    try {

	        wait.until(
	                ExpectedConditions.visibilityOfAllElements(productNames));

	        return productNames.size();
	    }

	    catch (Exception e) {

	        return 0;
	    }
	}

	// ================= GET PRODUCT NAMES =================

	public List<String> getAllProductNames() {

		wait.until(ExpectedConditions.visibilityOfAllElements(productNames));

		return productNames.stream().map(WebElement::getText).collect(Collectors.toList());
	}

	// ================= VERIFY KEYWORD =================

	public boolean verifyKeywordPresent(String keyword) {

		return getAllProductNames().stream().anyMatch(e -> e.toLowerCase().contains(keyword.toLowerCase()));
	}

	// ================= VIEW PRODUCT =================

	public void clickViewProduct(int index) {

	    wait.until(
	            ExpectedConditions.visibilityOfAllElements(viewProductBtns));

	    JavascriptExecutor js =
	            (JavascriptExecutor) driver;

	    js.executeScript(
	            "arguments[0].click();",
	            viewProductBtns.get(index));

	    wait.until(
	            ExpectedConditions.urlContains(
	                    "product_details"));
	}

	// ================= UTILITIES =================

	public String getCurrentUrl() {

		return driver.getCurrentUrl();
	}

	public boolean isSearchBoxDisplayed() {

		return searchBox.isDisplayed();
	}

	public boolean isSearchButtonDisplayed() {

		return searchBtn.isDisplayed();
	}

	public String getSearchBoxText() {

		return searchBox.getAttribute("value");
	}

	public String getSearchTitle() {

		return searchedProductsTitle.getText();
	}

	public void scrollToElement(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}
}