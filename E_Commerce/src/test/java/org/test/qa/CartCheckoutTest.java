package org.test.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pages.qa.CartCheckoutPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.utils.qa.ExtentManager;
import org.utils.qa.ScreenshotUtility;

import java.io.IOException;
import java.time.Duration;
import org.utils.qa.Log;

public class CartCheckoutTest extends ExtentManager {

    public CartCheckoutTest() throws IOException {
        super();
    }

    CartCheckoutPage cart;

    // ================= SETUP =================
    @BeforeClass
    public void setup() throws Exception {

        Log.info("Starting test setup and initializing driver");

        begin();
        cart = new CartCheckoutPage(driver);

        Log.info("Navigating to login page");
        driver.get("https://automationexercise.com/login");

        Log.info("Entering login credentials");
        driver.findElement(By.name("email"))
                .sendKeys(prop.getProperty("email"));

        driver.findElement(By.name("password"))
                .sendKeys(prop.getProperty("password"));

        Log.info("Clicking login button");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]"))
                .click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(d -> d.getCurrentUrl().contains("automationexercise"));

        // Wait for page to be fully loaded with Products link visible
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("Products")));

        Log.info("Login successful and home page loaded");
    }

    // ================= CORE FLOW =================
    public void checkoutFlow(String msg) {

        Log.info("Starting checkout flow: " + msg);

        cart.clickViewCart();
        Log.info("Clicked view cart");

        cart.proceedToCheckout();
        Log.info("Proceeded to checkout");

        Assert.assertTrue(cart.isAddressDisplayed(), "Address not displayed");
        Log.info("Address verified on checkout page");

        cart.placeOrder(msg);
        Log.info("Order placed with message: " + msg);

        cart.enterPayment("Siddhi", "1234567812345678", "123", "12", "2030");
        Log.info("Entered payment details");

        cart.pay();
        Log.info("Payment submitted");

        Assert.assertTrue(cart.isOrderSuccess(), "Order success not found");
        Log.info("Order completed successfully");
    }

    // ================= TEST 1 =================
    @Test
    public void addProductUsingSearch() {

        Log.info("Test started: addProductUsingSearch");

        cart.goToProducts();
        Log.info("Navigated to Products page");

        cart.searchProduct("Jeans");
        Log.info("Searched product: Jeans");

        cart.addFirstProductFromList();
        Log.info("Added first product from search results");

        checkoutFlow("Search order");
    }

    // ================= TEST 2 =================
    @Test
    public void categoryFlow() {

        Log.info("Test started: categoryFlow");

        cart.goToProducts();
        cart.navigateWomenCategory();
        Log.info("Navigated to Women category");

        cart.addFirstProductFromList();
        Log.info("Added product from category");

        checkoutFlow("Category order");
    }

    // ================= TEST 3 =================
    @Test
    public void brandFlow() {

        Log.info("Test started: brandFlow");

        cart.goToProducts();
        cart.navigateToBrand("POLO");
        Log.info("Navigated to brand: POLO");

        cart.addFirstProductFromList();
        Log.info("Added product from brand");

        checkoutFlow("Brand order");
    }

    // ================= TEST 4 =================
    @Test
    public void verifyQuantity() {

        Log.info("Test started: verifyQuantity");

        cart.goToProducts();
        cart.searchProduct("Top");
        Log.info("Searched product: Top");

        cart.addFirstProductFromList();
        Log.info("Added product from search");

        checkoutFlow("Quantity order");
    }

    // ================= GUEST TEST =================
    @Test
    public void guestUserBlocked() {

        Log.info("Test started: guestUserBlocked");

        driver.manage().deleteAllCookies();
        driver.get("https://automationexercise.com/");
        Log.info("Cookies cleared and navigated to home page");

        cart.goToProducts();
        cart.searchProduct("Top");
        cart.addFirstProductFromList();

        Log.info("Attempting checkout as guest");

        cart.clickViewCart();
        cart.proceedToCheckout();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        boolean isLoginPageDisplayed = wait.until(d ->
                d.findElements(By.xpath("//u[text()='Register / Login']")).size() > 0
                && d.findElement(By.xpath("//u[text()='Register / Login']")).isDisplayed()
        );

        Log.info("Login page verification result: " + isLoginPageDisplayed);

        Assert.assertTrue(
                isLoginPageDisplayed,
                "Guest user was NOT redirected to Register/Login page"
        );
    }

    // ================= SCREENSHOT =================
    @AfterMethod
    public void screenshot(ITestResult result) throws IOException {

        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            Log.error("Test FAILED: " + result.getName() + " - capturing screenshot");
            ScreenshotUtility.captureScreenshot(driver, result.getName());
        } else {
            Log.info("Test passed: " + result.getName());
        }
    }

    // ================= CLEANUP =================
    @AfterClass(alwaysRun = true)
    public void tearDown() {

        Log.info("Closing browser and ending test suite");

        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
        	Log.error("Error while closing driver: " + e.getMessage());
        }
    }
}

