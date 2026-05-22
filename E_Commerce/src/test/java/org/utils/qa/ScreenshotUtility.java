package org.utils.qa;

import java.io.File;
import java.io.IOException;
import org.base.qa.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class ScreenshotUtility extends BaseTest {

	public ScreenshotUtility() throws IOException {
		super();
	}

	public static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {

		// CREATE FOLDER PATH

		String screenshotDir = System.getProperty("user.dir") + "/Screenshots/";

		// CREATE FOLDER IF NOT EXISTS

		File directory = new File(screenshotDir);

		if (!directory.exists()) {

			directory.mkdirs();
		}

		// SCREENSHOT FILE

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String destination = screenshotDir + screenshotName + ".png";

		File destFile = new File(destination);

		Files.copy(src, destFile);

		return destination;
	}
}


