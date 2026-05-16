package org.pages.qa;

import java.io.IOException;
import java.time.Duration;

import org.base.qa.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BaseTest {

    WebDriverWait wait;
    Actions actions;

    public HomePage() throws IOException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        actions = new Actions(driver);
    }

   
    private void waitForPageReady() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    // ================= USER VERIFY =================
    public boolean verifyLoggedInUser() {
        waitForPageReady();

        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'Logged in as')]")
        )).isDisplayed();
    }

    // ================= NAVIGATION =================
    public void clickProducts() {
        waitForPageReady();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Products"))).click();
    }

    public void clickCart() {
        waitForPageReady();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Cart"))).click();
    }

    public void clickLogout() {
        waitForPageReady();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Logout"))).click();
    }

    // ================= CATEGORY =================
    public void clickCategory(String value) {

        waitForPageReady();

        WebElement categoryMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(text(),'Category')]")
                )
        );

        actions.moveToElement(categoryMenu).perform();

        By categoryLocator = By.xpath("//a[contains(text(),'" + value + "')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryLocator));
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
    }

    // ================= BRAND =================
    public void clickBrand(String value) {

        waitForPageReady();

        WebElement brandMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(text(),'Brands')]")
                )
        );

        actions.moveToElement(brandMenu).perform();

        By brandLocator = By.xpath("//a[contains(text(),'" + value + "')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(brandLocator));
        wait.until(ExpectedConditions.elementToBeClickable(brandLocator)).click();
    }

    // ================= CART =================
    public void clickAddToCart() {
        waitForPageReady();

        By addToCart = By.xpath("//a[normalize-space()='Add to cart']");
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public void clickViewProduct() {
        waitForPageReady();

        By viewProduct = By.xpath("//a[normalize-space()='View Product']");
        wait.until(ExpectedConditions.elementToBeClickable(viewProduct)).click();
    }
}