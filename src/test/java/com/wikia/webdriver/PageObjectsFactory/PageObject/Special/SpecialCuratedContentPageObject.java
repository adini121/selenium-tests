package com.wikia.webdriver.PageObjectsFactory.PageObject.Special;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class SpecialCuratedContentPageObject extends SpecialPageObject {
	
	@FindBy(css = "")
	private WebElement addSectionButton;
	@FindBy(css = "")
	private WebElement addItemButton;
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

	public void verifyNoSection(String string) {
		// TODO Auto-generated method stub
		
	}

	public void verifySection(String string) {
		// TODO Auto-generated method stub
		
	}

	public void addSection(String string) {
		// TODO Auto-generated method stub
		
	}

	public void removeSection(String string) {
		// TODO Auto-generated method stub
		
	}

	public void verifyItem(String string) {
		// TODO Auto-generated method stub
		
	}

	public void verifyNoItem(String string) {
		// TODO Auto-generated method stub
		
	}

	public void addItem(String string) {
		// TODO Auto-generated method stub
		
	}

	public void removeItem(String string) {
		// TODO Auto-generated method stub
		
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
