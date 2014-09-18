package com.wikia.webdriver.PageObjectsFactory.ComponentObject.VisualEditorDialogs;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.wikia.webdriver.Common.ContentPatterns.PageContent;
import com.wikia.webdriver.Common.Logging.PageObjectLogging;
import com.wikia.webdriver.PageObjectsFactory.PageObject.VisualEditor.VisualEditorPageObject;

public class VisualEditorAddMediaDialog extends VisualEditorDialog {

	@FindBy(css=".oo-ui-textInputWidget-decorated>input")
	private WebElement searchInput;
	@FindBy(css=".oo-ui-pageLayout-active .ve-ui-wikiaUploadButtonWidget")
	private WebElement clickToUploadArea;
	@FindBy(css=".oo-ui-pageLayout-active .oo-ui-labeledElement-label")
	private WebElement midUploadButton;
	@FindBy(css=".oo-ui-window-foot .oo-ui-labeledElement-label")
	private WebElement addMediaButton;
	@FindBy(css=".ve-ui-wikiaMediaQueryWidget-uploadWrapper .oo-ui-labeledElement-label")
	private WebElement topUploadButton;
	@FindBy(css=".secondary .oo-ui-labeledElement-label")
	private WebElement removeThisItemButton;
	@FindBy(css=".video.oo-ui-pageLayout-active .video-thumbnail")
	private WebElement externalVideoThumbnail;
	@FindBy(css=".oo-ui-window-body")
	private WebElement mediaDialogBody;
	@FindBy(css=".oo-ui-bookletLayout .ve-ui-wikiaUploadButtonWidget input")
	private WebElement fileUploadInput;
	@FindBy(css=".oo-ui-fieldsetLayout input")
	private WebElement fileNameInput;

	private By mediaResultsWidgetBy = By.cssSelector(".ve-ui-wikiaMediaResultsWidget");
	private By mediaResultsBy = By.cssSelector(".ve-ui-wikiaMediaResultsWidget ul li");
	private By mediaAddIconBy = By.cssSelector(".oo-ui-icon-unchecked");
	private By mediaTitlesBy = By.cssSelector(".ve-ui-wikiaMediaResultsWidget ul li>.oo-ui-labeledElement-label");

	public VisualEditorAddMediaDialog(WebDriver driver) {
		super(driver);
	}

	private void typeInSearchTextField(String input) {
		waitForElementByElement(searchInput);
		searchInput.sendKeys(input);
	}

	private void clickAddMediaButton() {
		waitForElementVisibleByElement(addMediaButton);
		waitForElementClickableByElement(addMediaButton);
		addMediaButton.click();
	}

	public VisualEditorPageObject addMediaByURL(String url) {
		switchToIFrame();
		typeInSearchTextField(url);
		waitForElementVisibleByElement(topUploadButton);
		waitForElementClickableByElement(topUploadButton);
		clickAddMediaButton();
		switchOutOfIFrame();
		return new VisualEditorPageObject(driver);
	}

	public VisualEditorAddMediaDialog searchMedia(String searchText) {
		switchToIFrame();
		typeInSearchTextField(searchText);
		switchOutOfIFrame();
		return new VisualEditorAddMediaDialog(driver);
	}

	public VisualEditorPageObject addExistingMedia(int number) {
		switchToIFrame();
		WebElement mediaResultsWidget = mediaDialogBody.findElement(mediaResultsWidgetBy);
		waitForElementVisibleByElement(mediaResultsWidget);
		List<WebElement> mediaResults = mediaResultsWidget.findElements(mediaResultsBy);
		for (int i = 0; i<number; i++) {
			WebElement mediaAddIcon = mediaResults.get(i).findElement(mediaAddIconBy);
			mediaAddIcon.click();
		}
		clickAddMediaButton();
		switchOutOfIFrame();
		return new VisualEditorPageObject(driver);
	}

	public VisualEditorPageObject uploadImage(String fileName) {
		switchToIFrame();
		selectFileToUpload(fileName);
		waitForElementVisibleByElement(topUploadButton);
		clickAddMediaButton();
		switchOutOfIFrame();
		return new VisualEditorPageObject(driver);
	}

	public VisualEditorPageObject uploadImageWithFileName(String fileName, String newFileName) {
		switchToIFrame();
		selectFileToUpload(fileName);
		waitForElementVisibleByElement(topUploadButton);
		typeNewFileName(newFileName);
		clickAddMediaButton();
		switchOutOfIFrame();
		return new VisualEditorPageObject(driver);
	}

	private void typeNewFileName(String newFileName) {
		waitForElementByElement(fileNameInput);
		if (Boolean.parseBoolean(fileNameInput.getAttribute("readonly"))) {
			throw new NoSuchElementException("File name input is read only! This file already exist on this wiki");
		}
		fileNameInput.clear();
		fileNameInput.sendKeys(newFileName);
		waitForValueToBePresentInElementsAttributeByElement(fileNameInput, "value", newFileName);
	}

	public VisualEditorPageObject previewExistingMediaByIndex(int index) {
		switchToIFrame();
		WebElement mediaResultsWidget = mediaDialogBody.findElement(mediaResultsWidgetBy);
		waitForElementVisibleByElement(mediaResultsWidget);
		WebElement targetMedia = mediaResultsWidget.findElements(mediaTitlesBy).get(index);
		targetMedia.click();
		switchOutOfIFrame();
		return new VisualEditorPageObject(driver);
	}

	private void selectFileToUpload(String fileName) {
		fileUploadInput.sendKeys(
			getAbsolutePathForFile(PageContent.resourcesPath + fileName)
		);
		PageObjectLogging.log(
			"selectFileToUpload",
			"file " + fileName + " added to upload",
			true
		);
	}

	public VisualEditorPageObject previewExistingMediaByTitle(String title) {
		switchToIFrame();
		WebElement media = findMediaByTitle(title);
		media.click();
		PageObjectLogging.log("previewExistingMediaByTitle", "Media clicked", true);
		switchOutOfIFrame();
		return new VisualEditorPageObject(driver);
	}

	private WebElement findMediaByTitle(String title) {
		WebElement elementFound = null;
		WebElement mediaResultsWidget = mediaDialogBody.findElement(mediaResultsWidgetBy);
		waitForElementVisibleByElement(mediaResultsWidget);
		List<WebElement> mediaTitles = mediaResultsWidget.findElements(mediaTitlesBy);
		int i = 0;
		boolean found = false;
		while(i<mediaTitles.size() && found == false) {
			String mediaTitle = mediaTitles.get(i).getAttribute("title");
			if (mediaTitle.equals(title)) {
				found = true;
				elementFound = mediaTitles.get(i);
				PageObjectLogging.log("findMediaByTitle", title + " found from media dialog", true);
			}
			i++;
		}
		if (found == false) {
			throw new NoSuchElementException("Media with the title: " + title + " is not found");
		}
		return elementFound;
	}
}
