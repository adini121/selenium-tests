package com.wikia.webdriver.TestCases.Mobile;

import com.wikia.webdriver.Common.Properties.Credentials;
import com.wikia.webdriver.Common.Templates.MobileTestTemplate;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Mobile.MobileBasePageObject;
import org.testng.annotations.Test;

/**
 *
 * @author Bogna 'bognix' Knychala
 */
public class HackathonLoginTest extends MobileTestTemplate {

	Credentials credentials = config.getCredentials();

	@Test(
		groups={"HackathonTest", "mobile"}
	)
	public void hackathonLoginTest() {
		MobileBasePageObject mobile = new MobileBasePageObject(mobileDriver);
		mobile.openHome(wikiURL);
		mobile.openRandomPage();
		String url = mobileDriver.getCurrentUrl();
		mobile.login(credentials.userName, credentials.password);
		mobile.verifyURLcontains(url);
	}
}
