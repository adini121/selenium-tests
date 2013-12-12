package com.wikia.webdriver.Common.DriverProvider;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Bgona 'bognix' Knychala
 */
public class MobileDriverProvider {

	private final String remoteURL = "http://127.0.0.1:";
	private final String remoteURLSuffix = "/wd/hub";
	private String port;
	private String browserName;
	private String version;

	public MobileDriverProvider MobileDriverProvider() {
		return this;
	}

	public WebDriver getMobileDriverInstance(String browserName, String version, String deviceID, String port) {
		this.browserName = browserName;
		this.version = version;
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		WebDriver remoteWebDriver = null;
		URL url;
		if (browserName.equals("chrome")) {
			desiredCapabilities.setCapability("app", "chrome");
			desiredCapabilities.setCapability("device", "Android");
			if (!deviceID.isEmpty()) {
				desiredCapabilities.setCapability("androidDeviceSerial", deviceID);
			}
			try {
				url = new URL(remoteURL + port + remoteURLSuffix);
			} catch (MalformedURLException ex) {
				throw new RuntimeException(ex);
			}
			remoteWebDriver = new RemoteWebDriver(url, desiredCapabilities);
		} else if (browserName.equals("safari")) {
			desiredCapabilities.setCapability("app", "safari");
		}
		WebDriver augmentedDriver = new Augmenter().augment(remoteWebDriver);
		return augmentedDriver;
	}
}
