package org.pages.qa;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class CartCheckoutPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;

    public CartCheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    // ================= ADS =================
    public void killAds() {
        try {
            js.executeScript(
                "document.querySelectorAll('iframe').forEach(e => e.remove());" +
                "document.querySelector('#google_vignette')?.remove();"
            );
        } catch (Exception ignored) {}
    }

    // ================= LOCATORS (IMPROVED) =================
    By productsBtn = By.xpath("//a[contains(@href,'products')]");
    By searchBox = By.id("search_product");
    By searchBtn = By.id("submit_search");

    // FIX: more stable product container
    By productCards = By.xpath("//div[contains(@class,'product-image-wrapper')]");

    // FIX: scoped button inside product card (IMPORTANT)
    By addToCartInsideCard = By.xpath(".//a[contains(text(),'Add to cart')]");

    By viewCartBtn = By.xpath("//u[text()='View Cart']");
    By checkoutBtn = By.xpath("//a[contains(text(),'Proceed To Checkout')]");

    By addressBox = By.id("address_delivery");
    By commentBox = By.name("message");
    By placeOrderBtn = By.xpath("//a[contains(text(),'Place Order')]");

    By successMsg = By.xpath("//*[contains(text(),'Congratulations')]");

    // ================= NAV =================
    public void goToProducts() {
        killAds();
        driver.get("https://automationexercise.com/products");
        wait.until(ExpectedConditions.urlContains("products"));
    }

    // ================= SEARCH =================
    public void searchProduct(String product) {
        killAds();

        WebElement sb = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        sb.clear();
        sb.sendKeys(product);

        driver.findElement(searchBtn).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
    }

    // ================= FIXED ADD TO CART =================
    public void addFirstProductFromList() {

        killAds();

        List<WebElement> cards = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(productCards)
        );

        WebElement first = cards.get(0);

        js.executeScript("arguments[0].scrollIntoView(true);", first);

        actions.moveToElement(first).pause(Duration.ofMillis(800)).perform();

        // FIX: search button INSIDE first product card (NOT global list)
        WebElement btn = first.findElement(addToCartInsideCard);

        wait.until(ExpectedConditions.elementToBeClickable(btn));

        js.executeScript("arguments[0].click();", btn);
    }

    // ================= CATEGORY (FIXED CLICK FLOW) =================
    public void navigateWomenCategory() {

        killAds();

        driver.get("https://automationexercise.com/products");

        WebElement women = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='#Women']")
                )
        );

        js.executeScript("arguments[0].click();", women);

        WebElement dress = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Dress')]")
                )
        );

        js.executeScript("arguments[0].click();", dress);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
    }

    // ================= BRAND FIXED =================
    public void navigateToBrand(String brand) {

        killAds();

        driver.get("https://automationexercise.com/products");

        // FIX: scroll to brands section first
        WebElement brandSection = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//h2[contains(text(),'Brands')]")
                )
        );

        js.executeScript("arguments[0].scrollIntoView(true);", brandSection);

        By brandLocator = By.xpath("//a[contains(@href,'brand') and contains(text(),'" + brand + "')]");

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(brandLocator));

        js.executeScript("arguments[0].click();", el);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
    }

    // ================= CART =================
    public void clickViewCart() {
        killAds();
        wait.until(ExpectedConditions.elementToBeClickable(viewCartBtn)).click();
    }

    public void proceedToCheckout() {
        killAds();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        js.executeScript("arguments[0].click();", el);
    }

    public boolean isAddressDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(addressBox)
        ).isDisplayed();
    }

    // ================= ORDER =================
    public void placeOrder(String msg) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentBox))
                .sendKeys(msg);

        driver.findElement(placeOrderBtn).click();
    }

    public void enterPayment(String n, String c, String cv, String m, String y) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name_on_card")));

        driver.findElement(By.name("name_on_card")).sendKeys(n);
        driver.findElement(By.name("card_number")).sendKeys(c);
        driver.findElement(By.name("cvc")).sendKeys(cv);
        driver.findElement(By.name("expiry_month")).sendKeys(m);
        driver.findElement(By.name("expiry_year")).sendKeys(y);
    }

    public void pay() {
        driver.findElement(By.id("submit")).click();
    }

    public boolean isOrderSuccess() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMsg)
        ).isDisplayed();
    }
}