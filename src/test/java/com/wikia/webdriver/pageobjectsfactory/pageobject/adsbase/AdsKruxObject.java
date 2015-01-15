package com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase;

import com.google.common.base.Joiner;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.CommonExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Rets
 * @ownership AdEngineering
 */
public class AdsKruxObject extends AdsBaseObject {
	private static final String KRUX_CONTROL_TAG_URL_PREFIX = "http://cdn.krxd.net/controltag?confid=";
	@FindBy(css = "script[src^=\"" + KRUX_CONTROL_TAG_URL_PREFIX + "\"]")
	private WebElement kruxControlTag;

	public AdsKruxObject(WebDriver driver) {
		super(driver);
	}

	public AdsKruxObject(WebDriver driver, String page) {
		super(driver, page);
	}

	/**
	 * Test whether the Krux control tag is called with the proper site ID
	 *
	 * @param kruxSiteId the expected Krux site ID
	 */
	public AdsKruxObject verifyKruxControlTag(String kruxSiteId) {
		String expectedUrl = KRUX_CONTROL_TAG_URL_PREFIX + kruxSiteId;
		Assertion.assertEquals(expectedUrl, kruxControlTag.getAttribute("src"));
		return this;
	}

	/**
	 * Test whether the Krux user id is not empty and added to GPT calls
	 */
	public AdsKruxObject verifyKruxUserParam() {
		waitForKrux();
		String kruxUser = (String) ((JavascriptExecutor) driver).executeScript("return Krux.user;");
		Assertion.assertStringNotEmpty(kruxUser);
		Assertion.assertTrue(isGptParamPresent("u", kruxUser));
		return this;
	}

	private String getKruxSegment(){
		List segments = (ArrayList) ((JavascriptExecutor) driver).executeScript("return Krux.segments;");
		return Joiner.on("\t").join(segments);
	}

	public AdsKruxObject verifyKruxSegment(String segId) {
		waitForKrux();
		Assertion.assertStringContains(segId, getKruxSegment());
		return this;
	}

	public AdsKruxObject verifyNoKruxSegment(String segId) {
		waitForKrux();
		Assertion.assertStringNotContains(segId, getKruxSegment());
		return this;
	}

	public AdsKruxObject waitForKrux() {
		wait.until(CommonExpectedConditions.scriptReturnsTrue("return !!window.Krux"));
		return this;
	}

	public AdsKruxObject goTo(String wikiName, String article) {
		String testPage = urlBuilder.getUrlForPath(wikiName, article);
		getUrl(testPage, true);
		return this;
	}

	public AdsKruxObject refresh(){
		refreshPage();
		return this;
	}
}
