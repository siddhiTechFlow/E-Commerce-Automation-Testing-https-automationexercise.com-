package org.utils.qa;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.lang.reflect.Method;

import org.base.qa.BaseTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class ExtentManager extends BaseTest {

    public ExtentManager() throws IOException {

        super();
    }

    public static ExtentReports report;

    public static ExtentTest test;

    String className;

    // ================= REPORT SETUP =================

    @BeforeSuite
    public void setupReport() {

        className =
                this.getClass().getSimpleName();

        String reportPath =
                System.getProperty("user.dir")
                + "/Reports/"
                + className
                + "Report.html";

        ExtentSparkReporter spark =
                new ExtentSparkReporter(reportPath);

        // DOCUMENT TITLE

        spark.config().setDocumentTitle(
                className + " Automation Report");

        // REPORT NAME

        spark.config().setReportName(
                className + " Execution Report");

        // THEME

        spark.config().setTheme(Theme.DARK);

        report = new ExtentReports();

        report.attachReporter(spark);

        // SYSTEM INFO

        report.setSystemInfo(
                "Tester",
                "Siddhi More");

        report.setSystemInfo(
                "Module",
                className);

        report.setSystemInfo(
                "Browser",
                prop.getProperty("browser"));

        report.setSystemInfo(
                "Environment",
                "QA");
    }

    // ================= CREATE TEST =================

    @BeforeMethod
    public void createTest(Method method) {

        test = report.createTest(
                method.getName());

        test.log(
                Status.INFO,
                "Test Started : "
                + method.getName());
    }

    // ================= CAPTURE RESULT =================

    @AfterMethod
    public void captureResult(
            ITestResult result)
            throws IOException {

        if (result.getStatus()
                == ITestResult.FAILURE) {

            String screenshotPath =
                    ScreenshotUtility
                    .captureScreenshot(
                            driver,
                            result.getName());

            test.fail(result.getThrowable());

            test.addScreenCaptureFromPath(
                    screenshotPath);
        }

        else if (result.getStatus()
                == ITestResult.SUCCESS) {

            test.pass("Test Passed");
        }

        else {

            test.skip("Test Skipped");
        }
    }

    // ================= CLOSE BROWSER =================

    @AfterClass
    public void closeBrowser() {

    	if (driver != null) {
    	    try {
    	        driver.quit();
    	    } catch (Exception ignored) {}
    	}
    }

    // ================= FLUSH REPORT =================

    @AfterSuite
    public void flushReport() {

        report.flush();
    }
}