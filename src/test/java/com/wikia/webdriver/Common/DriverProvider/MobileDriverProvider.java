package com.wikia.webdriver.Common.DriverProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Bgona 'bognix' Knychala
 */
public class MobileDriverProvider {

	private final String remoteURL = "http://127.0.0.1:";
	private String remoteURLSuffix = "/wd/hub";
	private String chromeDriverDefaultPort = "9515";
	private String iOSDriverDefaultPort = "5555";
	private String port;
	private String browserName;
	private String version;

	public MobileDriverProvider MobileDriverProvider() {
		return this;
	}

	public WebDriver getMobileDriverInstance(String browserName, String version, String deviceID, String port) {
		this.browserName = browserName;
		this.version = version;
		DesiredCapabilities desiredCapabilities = null;
		WebDriver remoteWebDriver;
		URL url;
		if (browserName.equals("chrome")) {
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("androidPackage", "com.android.chrome");
			if (deviceID != null) {
				chromeOptions.put("androidDeviceSerial", deviceID);
			}
			desiredCapabilities = DesiredCapabilities.chrome();
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			remoteURLSuffix = "";
			if (port == null) {
				port = chromeDriverDefaultPort;
			}
		} else if (browserName.equals("safari")) {
			desiredCapabilities = DesiredCapabilities.iphone();
			port = iOSDriverDefaultPort;
		}
		try {
			url = new URL(remoteURL + port + remoteURLSuffix);
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
		remoteWebDriver = new RemoteWebDriver(url, desiredCapabilities);
		WebDriver augmentedDriver = new Augmenter().augment(remoteWebDriver);
		return augmentedDriver;
	}
}
