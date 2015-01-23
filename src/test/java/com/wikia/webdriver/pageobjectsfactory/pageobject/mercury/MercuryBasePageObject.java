package com.wikia.webdriver.pageobjectsfactory.pageobject.mercury;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.wikia.webdriver.common.contentpatterns.MercuryContent;
import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.mobile.MobileBasePageObject;

public class MercuryBasePageObject extends MobileBasePageObject{

	public MercuryBasePageObject(WebDriver driver) {
		super(driver);
	}

	public SpecialMercuryPageObject openSpecialMercury(String wikiURL) {
		getUrl(wikiURL + MercuryContent.MERCURY_SPECIAL_PAGE);
		PageObjectLogging.log("openSpecialMercury", MercuryContent.MERCURY_SPECIAL_PAGE+ " opened", true);
		return new SpecialMercuryPageObject(driver);
	}

	public MercuryArticlePageObject openMercuryArticleByName(String wikiURL, String articleName) {
		getUrl(wikiURL + URLsContent.WIKI_DIR + articleName);
		PageObjectLogging.log("openMercuryArticleByName", "Article" + articleName + " was opened", true);
		return new MercuryArticlePageObject(driver);
	}

	public void openMercuryWiki(String wikiName){
		String mercuryWiki = urlBuilder.getUrlForWiki(wikiName);
		getUrl(mercuryWiki);
	}

	public void tapOnElement(WebElement element) {
		JavascriptExecutor jsexec = (JavascriptExecutor)driver;
		jsexec.executeScript("arguments[0].click();", element);
	}

	public void doubleTapZoom(WebElement element) {
		Actions doubleTapZoom = new Actions(driver);
		doubleTapZoom.doubleClick(element).perform();
	}

	public void swipeLeft(WebElement element) {
		Actions swipeAction = new Actions(driver);
		swipeAction.dragAndDropBy(element, -100, 0).build().perform();
	}

	public void swipeRight(WebElement element) {
		Actions swipeAction = new Actions(driver);
		swipeAction.dragAndDropBy(element, +100, 0).build().perform();
	}
}
