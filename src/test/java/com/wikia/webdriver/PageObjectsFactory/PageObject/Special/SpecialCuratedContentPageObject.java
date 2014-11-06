package com.wikia.webdriver.PageObjectsFactory.PageObject.Special;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.wikia.webdriver.Common.Logging.PageObjectLogging;

public class SpecialCuratedContentPageObject extends SpecialPageObject {
	
	@FindBy(css = "")
	private WebElement addSectionButton;
	@FindBy(css = "")
	private WebElement removeSectionButton;
	@FindBy(css = "")
	private WebElement addItemButton;
	@FindBy(css = "")
	private WebElement removeItemButton;
	@FindBy(css = "")
	private WebElement saveButton;
	@FindBy(css = "")
	private WebElement addImageButton;

	@FindBys(@FindBy(css = ""))
	private List<WebElement> sections;
	@FindBys(@FindBy(css = ""))
	private List<WebElement> items;
	
	public SpecialCuratedContentPageObject(WebDriver driver) {
		super(driver);
	}

	public void verifyTitlePresence() {
		// TODO Auto-generated method stub
		
	}

	public void verifyTutorialPresencee() {
		// TODO Auto-generated method stub
		
	}

	public void verifyButtonsPresencee() {
		// TODO Auto-generated method stub
		
	}

	public void verifyNoSection(String notExpected) {
		if (!sections.isEmpty()) {
			for (WebElement section: sections) {
				waitForTextToBeNotPresentInElementByElement(section, notExpected);
			}
		}
		PageObjectLogging.log("verifyNoSection", "text "+notExpected+ " not found in section", true);
	}

	public void verifySection(String expected) {
		for (WebElement section: sections) {
			waitForTextToBePresentInElementByElement(section, expected);
		}
		PageObjectLogging.log("verifySection", "text "+expected+ " found in section", true);
	}

	public void clickAddSection(String string) {
		waitForElementByElement(addSectionButton);
		addSectionButton.click();
	}

	public void clickRemoveSection(String string) {
		waitForElementByElement(removeSectionButton);
		removeSectionButton.click();
	}

	public void verifyItem(String expected) {
		for (WebElement item: items) {
			waitForTextToBeNotPresentInElementByElement(item, expected);
		}
		PageObjectLogging.log("verifyItem", "text "+expected+ " found in items", true);		// TODO Auto-generated method stub
	}

	public void verifyNoItem(String notExpected) {
		if (!items.isEmpty()) {
			for (WebElement item: items) {
				waitForTextToBeNotPresentInElementByElement(item, notExpected);
			}
		}
		PageObjectLogging.log("verifyNoItem", "text "+notExpected+ " not found in items", true);
	}

	public void clickAddItem(String string) {
		waitForElementByElement(addItemButton);
		addItemButton.click();
	}

	public void clickRemoveItem(String string) {
		waitForElementByElement(removeItemButton);
		removeItemButton.click();
	}

	public void verifyImage(String string) {
		// TODO Auto-generated method stub
		
	}

	public void verifyNoImage(String string) {
		// TODO Auto-generated method stub
		
	}

	public void addImage(String string) {
		// TODO Auto-generated method stub
		
	}

	public void removeImage(String string) {
		// TODO Auto-generated method stub
		
	}

}
