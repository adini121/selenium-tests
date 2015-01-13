package com.wikia.webdriver.TestCases.SpecialPagesTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wikia.webdriver.Common.ContentPatterns.CssEditorContent;
import com.wikia.webdriver.Common.DataProvider.CreateNewWikiDataProvider;
import com.wikia.webdriver.Common.Properties.Credentials;
import com.wikia.webdriver.Common.Templates.NewTestTemplate;
import com.wikia.webdriver.PageObjectsFactory.PageObject.WikiBasePageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Special.SpecialCssPageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Special.SpecialCuratedContentPageObject;

/**
 * http://wikia-inc.atlassian.net/browse/DAT-2160
 */
public class CuratedContent extends NewTestTemplate {

	Credentials credentials = config.getCredentials();

	SpecialCuratedContentPageObject specialCC;
	private String testedPage;

	@BeforeMethod(alwaysRun = true)
	public void CssChrome_loginAndOpenSpecialCSS() {
		WikiBasePageObject wiki = new WikiBasePageObject(driver);
		wiki.logInCookie(credentials.userNameStaff, credentials.passwordStaff, wikiURL);
		specialCC = wiki.openSpecialCuratedContent(wikiURL);
		testedPage = specialCC.getCurrentUrl();
	}

	@Test(groups = {"CuratedContent_001", "CuratedContent"})
	public void CuratedContent_001_zero_state() {
		specialCC.verifyFeaturedContentPresence();
		specialCC.verifyTitlePresence();
		specialCC.verifyTutorialPresence();
		specialCC.verifyButtonsPresencee();
	}
}
