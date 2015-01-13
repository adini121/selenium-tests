package com.wikia.webdriver.PageObjectsFactory.PageObject.Special;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.wikia.webdriver.Common.Core.Assertion;
import com.wikia.webdriver.Common.Logging.PageObjectLogging;

public class SpecialCuratedContentPageObject extends SpecialPageObject {
	
	@FindBy(css = "#addSection")
	private WebElement addSectionButton;
	@FindBy(css = "#addItem")
	private WebElement addItemButton;
	@FindBy(css = "#save")
	private WebElement saveButton;
	@FindBy(css = ".section-input[value=\"Featured Content\"]")
	private WebElement featuredContentSection;
	@FindBy(css = "div.AdminDashboardGeneralHeader h1")
	private WebElement pageTitle;
	
	
	@FindBys(@FindBy(css = "li.section input"))
	private List<WebElement> sections;
	@FindBys(@FindBy(css = "li.item input.name"))
	private List<WebElement> itemLabels;
	@FindBys(@FindBy(css = "li.item input.item-input"))
	private List<WebElement> itemInputs;
	@FindBys(@FindBy(css = "ol.header li"))
	private List<WebElement> tutorialItems;

	String[] tutorial = {
			"Add wiki \"Items\" and rename them as you'd like them to"
			+ " appear in Curated Content with \"Curated Content Names\".",
			"Categories are supported in all sections. \"Featured\" section "
			+ "supports: articles, blogs, videos (youtube and ooyala)",
			"Does it make sense to group items under a game series installment? "
			+ "Add a \"Section\" and drag and drop wiki items under it.",
			" Section needs to have an image",
			"Feel free to delete or add more lines, and reorder them by dragging and dropping.",
			"Items without section or with empty section will be shown as last in Curated Content.",
			"When adding items label them (right field) and type names (left field) "
			+ "with following convention: article_name, Category:category_name, User blog:username/post_name, File:video_name"
		};

	String title = "Curated content";
	
	public SpecialCuratedContentPageObject(WebDriver driver) {
		super(driver);
	}

	public void verifyFeaturedContentPresence() {
		waitForElementByElement(featuredContentSection);
	}

	public void verifyTitlePresence() {
		waitForTextToBeNotPresentInElementByElement(pageTitle, title);
	}

	public void verifyTutorialPresence() {
		for(int i=0; i < tutorialItems.size(); i++) {
			String a = tutorialItems.get(i).getText();
			String b = tutorial[i];
			Assertion.assertEquals(tutorialItems.get(i).getText(), tutorial[i]);
		}
	}

	public void verifyButtonsPresencee() {
		waitForElementByElement(addSectionButton);
		waitForElementByElement(addItemButton);
		waitForElementByElement(saveButton);
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

	public void clickAddSection() {
		waitForElementByElement(addSectionButton);
		addSectionButton.click();
	}

	public void verifyItem(String expectedInput, String expectedLabel, int itemNumber) {
		String itemInput = itemInputs.get(itemNumber).getText();
		String itemLabel = itemLabels.get(itemNumber).getText();
		for (int i = 0; i < itemInputs.size(); i++) {
			Assertion.assertEquals(itemInput, expectedInput);
			Assertion.assertEquals(itemLabel, expectedLabel);
		}
		PageObjectLogging.log("verifyItem", "item "+expectedInput+ " with label "+expectedLabel+" found in items", true);		
	}

	public void verifyNoItem(String notExpectedInput) {
		if (!itemInputs.isEmpty()) {
			for (WebElement item: itemInputs) {
				Assertion.assertNotEquals(notExpectedInput, item.getText());
			}
		}
		PageObjectLogging.log("verifyNoItem", "item "+notExpectedInput+ " not found ", true);
	}

	public void clickAddItem() {
		waitForElementByElement(addItemButton);
		addItemButton.click();
	}

	public void typeSectionName(String sectionName, int sectionNumber) {
		WebElement section = sections.get(sectionNumber);
		section.sendKeys(sectionName);
	}

	public int getSectionCount() {
		return sections.size();
	}

	public void typeItemLabel(String itemLabel, int itemNumber) {
		WebElement item = itemLabels.get(itemNumber);
		item.sendKeys(itemLabel);
	}
	
	public void typeItemInput(String itemInput, int itemNumber) {
		WebElement item = itemInputs.get(itemNumber);
		item.sendKeys(itemInput);
	}

	public void clickSave() {
		waitForElementClickableByElement(saveButton);
		saveButton.click();
	}

	public void verifyItemLabel(String expectedLabel) {
		List<String> labels = null;
		for (int i = 0; i < itemLabels.size(); i++) {
			String label = itemLabels.get(i).getText();
			labels.add(label);
		}
		Assertion.assertNotNull(labels);
		Assertion.assertTrue(labels.contains(expectedLabel));
	}

	public int getItemsCount() {
		return itemInputs.size();
	}

}
