package org.test.qa;

import java.io.IOException;
import org.utils.qa.Log;
import org.pages.qa.HomePage;
import org.pages.qa.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.utils.qa.ExcelUtility;
import org.utils.qa.ExtentManager;

public class HomePageTest extends ExtentManager {
	
	 LoginPage lp;
	    HomePage hp;

	    public HomePageTest() throws IOException {
	        super();
	    }

	    @BeforeClass
	    public void setUp() throws IOException {

	        begin();

	        lp = new LoginPage();
	        hp = new HomePage();

	        lp.login("siddhi24104@gmail.com", "123456");

	        Log.info("LOGIN SUCCESSFUL");
	    }

	    @Test(dataProvider = "homeData")
	    public void homePageTest(String tcID,
	                             String action,
	                             String value,
	                             String expectedResult,
	                             String testType) throws IOException {

	        test = report.createTest(tcID);

	        Log.info("================================================");
	        Log.info("TEST CASE STARTED : " + tcID);
	        Log.info("ACTION : " + action);

	        try {

	            switch (action.toLowerCase()) {

	                case "verifyuser":
	                    Assert.assertTrue(hp.verifyLoggedInUser());
	                    break;

	                case "products":
	                    hp.clickProducts();
	                    Assert.assertTrue(driver.getCurrentUrl().contains("products"));
	                    break;

	                case "cart":
	                    hp.clickCart();
	                    Assert.assertTrue(driver.getCurrentUrl().contains("view_cart"));
	                    break;

	                case "logout":
	                    hp.clickLogout();
	                    Assert.assertTrue(driver.getCurrentUrl().contains("login"));
	                    break;

	                case "category":
	                    hp.clickCategory(value);
	                    Assert.assertTrue(true);
	                    break;

	                case "brand":
	                    hp.clickBrand(value);
	                    Assert.assertTrue(driver.getCurrentUrl().contains("brand_products"));
	                    break;

	                case "addtocart":
	                    hp.clickAddToCart();
	                    Assert.assertTrue(true);
	                    break;

	                case "viewproduct":
	                    hp.clickViewProduct();
	                    Assert.assertTrue(driver.getCurrentUrl().contains("product_details"));
	                    break;

	                default:
	                    Assert.fail("Invalid action: " + action);
	            }

	            Log.info("TEST PASSED : " + tcID);

	        } catch (Exception e) {

	            Log.error("TEST FAILED : " + tcID);

	            Log.error(e.getMessage());

	            Assert.fail(e.getMessage());
	        }

	        Log.info("================================================");
	    }

	    @DataProvider(name = "homeData")
	    public Object[][] getHomeData() throws Exception {
	        ExcelUtility excel = new ExcelUtility();
	        return excel.getExcelData("HomePageTestData.xlsx", "HomePage");
	    }

	   
	}


