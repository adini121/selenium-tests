package com.wikia.webdriver.pageobjectsfactory.componentobject.photo;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.BasePageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PhotoAddComponentObject extends BasePageObject {

  @FindBy(css = "#ImageQuery")
  private WebElement searchField;
  @FindBy(css = "#ImageUploadFind [value='Find']")
  private WebElement findButton;
  @FindBy(css = "#ImageUploadProgress2")
  private WebElement searchProgressThrobber;
  @FindBy(css = "#WMU_source_1")
  private WebElement flickButton;
  @FindBy(css = "#WMU_source_0")
  private WebElement thisWikiButton;
  @FindBy(css = "#ImageUploadFile")
  private WebElement chooseFileInput;
  @FindBy(css = "#ImageUploadForm input:nth-child(2)")
  private WebElement uploadButton;
  @FindBy(css = "tr.ImageUploadFindImages td a")
  private List<WebElement> addThisPhotoList;

  private static final String IMAGE_UPLOAD_HEADLINE_CSS = "#ImageUploadHeadline";
  @FindBy(css = IMAGE_UPLOAD_HEADLINE_CSS)
  private WebElement imageUploadHeadline;

  private String photoName;

  public PhotoAddComponentObject(WebDriver driver) {
    super(driver);
  }

  public void verifyAddPhotoModal() {
    waitForElementByElement(searchField);
    waitForElementByElement(findButton);
  }

  public void typeSearchQuery(String photoName) {
    waitForElementByElement(searchField);
    searchField.sendKeys(photoName);
    PageObjectLogging.log("typeSearchQuery", photoName + " searching", true);
  }

  public void clickFind() {
    waitForElementByElement(findButton);
    String oldHeadline = imageUploadHeadline.getText();
    scrollAndClick(findButton);
    waitForTextNotPresentInElementByElementLocatedBy(By.cssSelector(IMAGE_UPLOAD_HEADLINE_CSS),
                                                     oldHeadline);
    PageObjectLogging.log("clickSearch", "search button clicked", true);
  }

  public PhotoOptionsComponentObject clickAddThisPhoto(int photoNumber) {
    waitForElementByElement(addThisPhotoList.get(photoNumber));
    photoName =
        addThisPhotoList.get(photoNumber).findElement(By.cssSelector("img"))
            .getAttribute("data-image-name");
    scrollAndClick(addThisPhotoList.get(photoNumber));
    PageObjectLogging.log("clickAddPhoto", "add photo button clicked", true);
    return new PhotoOptionsComponentObject(driver);
  }

  public PhotoOptionsComponentObject clickAddThisPhoto(String fileName) {
    WebElement photo = getElementByValue(addThisPhotoList, "title", fileName);
    photoName = photo.findElement(By.cssSelector("img")).getAttribute("data-image-name");
    scrollAndClick(photo);
    PageObjectLogging.log("clickAddPhoto", "add photo button clicked", true);
    return new PhotoOptionsComponentObject(driver);
  }

  public String getPhotoName() {
    return photoName.replace(' ', '_');
  }

  /**
   * Adding photo with given @photoName and @photoNumber
   */
  public PhotoOptionsComponentObject addPhotoFromWiki(String photoName, int photoNumber) {
    typeSearchQuery(photoName);
    clickFind();
    clickAddThisPhoto(photoNumber);
    return new PhotoOptionsComponentObject(driver);
  }

  public PhotoOptionsComponentObject addPhotoFromWiki(String photoName) {
    typeSearchQuery(photoName);
    clickFind();
    clickAddThisPhoto(photoName);
    return new PhotoOptionsComponentObject(driver);
  }

  public void clickThisWiki() {
    thisWikiButton.click();
    PageObjectLogging.log("clickThisWiki", "this wiki button clicked", true);
  }

  public void clickFlickr() {
    flickButton.click();
    PageObjectLogging.log("clickFlickr", "flickr button clicked", true);
  }

  public void chooseFileToUpload(String file) {
    chooseFileInput.sendKeys(
        getAbsolutePathForFile(PageContent.RESOURCES_PATH + file)
    );
    PageObjectLogging.log("selectFileToUpload", "select file " + file + " to upload it", true);
  }

  public void clickUpload() {
    uploadButton.click();
    PageObjectLogging.log("clickUpload", "click on upload button", true);
  }
}