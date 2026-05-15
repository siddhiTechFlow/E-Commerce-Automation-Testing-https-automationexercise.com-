package org.test.qa;

import java.io.IOException;

import org.pages.qa.LoginPage;
import org.pages.qa.ProductSearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.utils.qa.ExcelUtility;
import org.utils.qa.ExtentManager;
import org.utils.qa.Log;

public class ProductSearchTest extends ExtentManager {

	LoginPage lp;

	ProductSearchPage productPage;

	public ProductSearchTest() throws IOException {

		super();
	}

	@BeforeClass
	public void setupPage() {

		Log.info("========================================");
		Log.info("Starting Product Search Test Suite");

		begin();

		productPage = new ProductSearchPage(driver);

		Log.info("Opening Website");

		productPage.login("siddhi24104@gmail.com", "123456");

		Log.info("Login Successful");

		productPage.closeAdIfPresent();

		Log.info("Ad Popup Checked");

		productPage.goToProductsPage();

		Log.info("Navigated To Products Page");

		Log.info("========================================");
	}

	// ================= TC001 =================

	@Test(priority = 1)
	public void verifySearchBoxVisible() {

		Log.info("Executing TC001 : verifySearchBoxVisible");

		Assert.assertTrue(productPage.isSearchBoxDisplayed());

		Log.info("Search Box Is Displayed");
	}

	// ================= TC002 =================

	@Test(priority = 2)
	public void verifySearchButtonVisible() {

		Log.info("Executing TC002 : verifySearchButtonVisible");

		Assert.assertTrue(productPage.isSearchButtonDisplayed());

		Log.info("Search Button Is Displayed");
	}

	// ================= TC003 =================

	@Test(priority = 3)
	public void searchValidProduct() {

		Log.info("Executing TC003 : searchValidProduct");

		Log.info("Searching Product : Dress");

		productPage.searchProduct("Dress");

		Log.info("Fetching Search Result Count");

		Assert.assertTrue(productPage.getSearchResultCount() > 0);

		Log.info("Valid Product Search Successful");
	}

	// ================= TC004 =================

	@Test(priority = 4)
	public void verifySearchResultKeyword() {

		Log.info("Executing TC004 : verifySearchResultKeyword");

		Log.info("Searching Product : Jeans");

		productPage.searchProduct("Jeans");

		Assert.assertTrue(productPage.verifyKeywordPresent("Jeans"));

		Log.info("Keyword Verified Successfully");
	}

	// ================= TC005 =================

	@Test(priority = 5)
	public void invalidSearch() {

		Log.info("Executing TC005 : invalidSearch");

		Log.info("Searching Invalid Product : xyz123");

		productPage.searchProduct("xyz123");

		Assert.assertEquals(productPage.getSearchResultCount(), 0);

		Log.info("Invalid Search Verified Successfully");
	}

	// ================= TC006 =================

	@Test(priority = 6)
	public void openProductDetailPage() {

		Log.info("Executing TC006 : openProductDetailPage");

		Log.info("Searching Product : Top");

		productPage.searchProduct("Top");

		Log.info("Opening Product Detail Page");

		productPage.clickViewProduct(0);

		String currentUrl = productPage.getCurrentUrl();

		Assert.assertTrue(currentUrl.contains("product_details"));

		Log.info("Product Detail Page Opened Successfully");
	}

	// ================= DATA PROVIDER =================

	@DataProvider(name = "searchData")
	public Object[][] getData() throws Exception {

		Log.info("Fetching Excel Test Data");

		ExcelUtility excel = new ExcelUtility();

		return excel.getExcelData("ProductSearchData.xlsx", "SearchData");
	}

	// ================= DATA DRIVEN =================

	@Test(dataProvider = "searchData")
	public void searchUsingExcel(String keyword, String expectedMin, String shouldHave) {

		Log.info("========================================");

		Log.info("Executing Data Driven Test");

		Log.info("Keyword : " + keyword);

		Log.info("Expected Min Result : " + expectedMin);

		Log.info("Should Have Result : " + shouldHave);

		productPage.searchProduct(keyword);

		int actual = productPage.getSearchResultCount();

		Log.info("Actual Result Count : " + actual);

		boolean expected = Boolean.parseBoolean(shouldHave);

		if (expected) {

			Assert.assertTrue(actual >= (int) Double.parseDouble(expectedMin));

			Log.info("Expected Product Found");
		}

		else {

			Assert.assertEquals(actual, 0);

			Log.info("No Product Found As Expected");
		}

		Log.info("Data Driven Test Completed");

		Log.info("========================================");
	}
}