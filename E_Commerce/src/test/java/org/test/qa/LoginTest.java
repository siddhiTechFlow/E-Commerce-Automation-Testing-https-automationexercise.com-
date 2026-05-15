package org.test.qa;

import java.io.IOException;

import org.pages.qa.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.utils.qa.ExcelUtility;
import org.utils.qa.ExtentManager;
import org.utils.qa.Log;

public class LoginTest extends ExtentManager {

	LoginPage lp;

	public LoginTest() throws IOException {

		super();
	}

	@BeforeClass
	public void setup() throws IOException {

		begin();

		lp = new LoginPage();

		Log.info("Browser launched successfully");
	}

	@Test(dataProvider = "loginData")
	public void loginTest(String testCaseId, String email, String password, String scenario, String expectedResult,
			String testType) {

		test = report.createTest(testCaseId);
		
		Log.info("================================================");

		Log.info("TEST CASE STARTED : " + testCaseId);

		Log.info("SCENARIO : " + scenario);

		Log.info("TEST TYPE : " + testType);

		try {

			// ENTER KEY LOGIN TEST CASE
			if (testCaseId.equalsIgnoreCase("TC_013")) {

				lp.loginWithEnterKey(email, password);

			} else {

				lp.login(email, password);
			}

			// INTENTIONAL BUG TEST CASE
			if (testCaseId.equalsIgnoreCase("TC_014")) {

				Assert.fail("Forgot Password feature not implemented");
			}

			// SUCCESS TEST CASES
			if (expectedResult.equalsIgnoreCase("success")) {

				Assert.assertTrue(lp.isLogoutBtnDisplayed(), "FAILED: Login failed for " + testCaseId);

				lp.logout();
			}

			// VALIDATION TEST CASES
			else if(expectedResult.equalsIgnoreCase("validation")) {

			    String validationMessage = "";

			    // EMPTY PASSWORD
			    if(password.isEmpty()) {

			        validationMessage =
			                lp.getPasswordValidationMessage();
			    }

			    // EMPTY EMAIL
			    else if(email.isEmpty()) {

			        validationMessage =
			                lp.getEmailValidationMessage();
			    }

			    System.out.println(validationMessage);

			    Assert.assertTrue(validationMessage.length() > 0,
			            "FAILED: Validation message not displayed for " + testCaseId);
			}

			// ERROR TEST CASES
			else {

			    Assert.assertTrue(lp.isErrorMessageDisplayed(),
			            "FAILED: Error message not displayed for " + testCaseId);
			}
			// EXECUTES ONLY IF ASSERTIONS PASS
			Log.info("TEST PASSED : " + testCaseId);
		} catch (AssertionError e) {

			Log.error("TEST FAILED : " + testCaseId);

			Log.error("ASSERTION FAILED : " + e.getMessage());

			throw e;
		}

		catch (Exception e) {

			Log.error("TEST FAILED : " + testCaseId);

			Log.error("EXCEPTION OCCURRED : " + e.getClass().getSimpleName());

			Log.error("REASON : " + e.getMessage());

			throw e;
		}

		Log.info("================================================");
	}

	@DataProvider(name = "loginData")
	public Object[][] getExcelData() throws Exception {

		ExcelUtility excel = new ExcelUtility();

		 return excel.getExcelData("LoginTestData.xlsx", "LoginData");
	}

}

