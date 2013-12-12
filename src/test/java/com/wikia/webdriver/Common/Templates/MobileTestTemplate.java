package com.wikia.webdriver.Common.Templates;

import com.wikia.webdriver.Common.ContentPatterns.URLsContent;
import com.wikia.webdriver.Common.DriverProvider.MobileDriverProvider;
import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Bogna 'bognix' Knychala
 */

public class MobileTestTemplate extends NewTestTemplateCore {

	protected WebDriver mobileDriver;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		prepareURLs();
	}

	@BeforeMethod(alwaysRun = true)
	public void start(Method method, Object[] data) {
		startBrowser();
		logOut();
	}

	@AfterMethod(alwaysRun = true)
	public void stop() {
		mobileDriver.quit();
	}

	@Override
	protected void logOut() {
		mobileDriver.get(wikiURL + URLsContent.logout);
	}

	@Override
	protected void stopBrowser() {
		if (mobileDriver != null) {
			mobileDriver.quit();
		}
	}

	@Override
	protected void startBrowser() {
		mobileDriver = new MobileDriverProvider().getMobileDriverInstance(
			config.getBrowser(), config.getBrowserVersion(),
			config.getDeviceID(), config.getPort()
		);
	}
}
