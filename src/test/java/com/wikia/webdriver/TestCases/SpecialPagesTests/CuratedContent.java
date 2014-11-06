package com.wikia.webdriver.TestCases.SpecialPagesTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wikia.webdriver.Common.ContentPatterns.CssEditorContent;
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
		specialCC.verifyTitlePresence();
		specialCC.verifyTutorialPresencee();
		specialCC.verifyButtonsPresencee();
	}
	
	@Test(groups = {"CuratedContent_002", "CuratedContent"})
	public void CuratedContent_002_add_sections() {
		specialCC.verifyNoSection("sectionA");
		specialCC.verifyNoSection("sectionB");
		specialCC.verifyNoSection("sectionC");
		
		specialCC.addSection("sectionA");
		specialCC.addSection("sectionB");
		specialCC.addSection("sectionC");
		
		specialCC.verifySection("sectionA");
		specialCC.verifySection("sectionB");
		specialCC.verifySection("sectionC");
	}

	@Test(groups = {"CuratedContent_003", "CuratedContent"})
	public void CuratedContent_003_remove_sections() {
		specialCC.verifySection("sectionA");
		specialCC.verifySection("sectionB");
		specialCC.verifySection("sectionC");
		
		specialCC.removeSection("sectionA");
		specialCC.removeSection("sectionB");
		specialCC.removeSection("sectionC");
		
		specialCC.verifyNoSection("sectionA");
		specialCC.verifyNoSection("sectionB");
		specialCC.verifyNoSection("sectionC");
	}

	@Test(groups = {"CuratedContent_004", "CuratedContent"})
	public void CuratedContent_004_add_items() {
		specialCC.verifyNoItem("itemA");
		specialCC.verifyNoItem("itemB");
		specialCC.verifyNoItem("itemC");

		specialCC.addItem("itemA");
		specialCC.addItem("itemB");
		specialCC.addItem("itemC");
		
		specialCC.verifyItem("itemA");
		specialCC.verifyItem("itemB");
		specialCC.verifyItem("itemC");
	}

	@Test(groups = {"CuratedContent_005", "CuratedContent"})
	public void CuratedContent_005_remove_items() {
		specialCC.verifyItem("itemA");
		specialCC.verifyItem("itemB");
		specialCC.verifyItem("itemC");

		specialCC.removeItem("itemA");
		specialCC.removeItem("itemB");
		specialCC.removeItem("itemC");
	
		specialCC.verifyNoItem("itemA");
		specialCC.verifyNoItem("itemB");
		specialCC.verifyNoItem("itemC");
	}
	
	@Test(groups = {"CuratedContent_006", "CuratedContent"})
	public void CuratedContent_006_add_images() {
		specialCC.verifyNoImage("imageA");
		specialCC.verifyNoImage("imageB");
		specialCC.verifyNoImage("imageC");
		
		specialCC.addImage("imageA");
		specialCC.addImage("imageB");
		specialCC.addImage("imageC");

		specialCC.verifyImage("imageA");
		specialCC.verifyImage("imageB");
		specialCC.verifyImage("imageC");
		
	}
	
	@Test(groups = {"CuratedContent_007", "CuratedContent"})
	public void CuratedContent_007_remove_images() {
		specialCC.verifyImage("imageA");
		specialCC.verifyImage("imageB");
		specialCC.verifyImage("imageC");

		specialCC.removeImage("imageA");
		specialCC.removeImage("imageB");
		specialCC.removeImage("imageC");
		
		specialCC.verifyNoImage("imageA");
		specialCC.verifyNoImage("imageB");
		specialCC.verifyNoImage("imageC");
		
	}



	
}
