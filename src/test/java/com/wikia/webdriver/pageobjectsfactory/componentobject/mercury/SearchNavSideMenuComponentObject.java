package com.wikia.webdriver.pageobjectsfactory.componentobject.mercury;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.mercury.MercuryArticlePageObject;

import org.openqa.selenium.WebDriver;

import com.wikia.webdriver.pageobjectsfactory.pageobject.mercury.MercuryBasePageObject;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author: Rodrigo Gomez, Łukasz Nowak, Tomasz Napieralski
 */
public class SearchNavSideMenuComponentObject extends MercuryBasePageObject {

  @FindBy(css = ".ember-text-field")
  private WebElement searchInput;
  @FindBy(css = ".local-nav-menu > li")
  private WebElement firstLevelMenuPositions;
  @FindBy(css = ".local-nav-menu > title")
  private WebElement menuTitle;
  @FindBy(css = "li[class='needsclick']")
  private WebElement needsClickMenuPositions;
  @FindBy(css = ".cancel")
  private WebElement cancelSearchCaption;
  @FindBy(css = ".local-wikia-search > li > a")
  private WebElement searchResults;
  @FindBy(css = "li[class='back']")
  private WebElement menuBackButton;
  @FindBy(css = ".icon chevron")
  private WebElement needsClickChevrons;
  @FindBy(css = ".local-wikia-search a")
  private List<WebElement> searchSuggestions;
  @FindBy(css = ".local-wikia-search span")
  private WebElement searchAlerts;
  @FindBy(css = ".search")
  private WebElement searchView;
  @FindBy(css = "div[class='drawer']")
  private WebElement menuView;
  @FindBy(css = ".local-nav-menu > li > div")
  private List<WebElement> chevrons;
  @FindBy(css = ".local-nav-menu > li > a")
  private List<WebElement> noChevrons;
  @FindBy(css = ".back")
  private WebElement backChevron;
  @FindBy(css = ".overlay")
  private WebElement overlay;
  @FindBy(css = "a[href='/wiki/Special:Random']")
  private WebElement randomPage;
  @FindBy(css = ".local-wikia-search")
  private WebElement resultField;
  @FindBy(css = ".local-nav-menu")
  private WebElement menuField;

  public SearchNavSideMenuComponentObject(WebDriver driver) {
    super(driver);
  }

  public void clickSearchField() {
    waitForElementByElement(searchInput);
    searchInput.click();
    PageObjectLogging.log("clickSearchField", "Search field was clicked", true);
  }

  public MercuryBasePageObject clickSearchSuggestion(int index) {
    waitForElementByElement(searchSuggestions.get(index));
    searchSuggestions.get(index).click();
    return new MercuryBasePageObject(driver);
  }

  public void clickCancelButton() {
    waitForElementVisibleByElement(cancelSearchCaption);
    cancelSearchCaption.click();
  }

  public MercuryArticlePageObject clickRandomPage() {
    waitForElementVisibleByElement(randomPage);
    tapOnElement(randomPage);
    PageObjectLogging.log("clickRandomPage", "Random page button was clicked", true);
    return new MercuryArticlePageObject(driver);
  }

  public String getSearchResultHref(int searchPosition) {
    waitForElementVisibleByElement(searchSuggestions.get(searchPosition));
    return searchSuggestions.get(searchPosition).getAttribute("href");
  }

  public void typeInSearchField(String content, int length) {
    waitForElementVisibleByElement(searchInput);
    searchInput.sendKeys(content.substring(0, length));
    PageObjectLogging.log("typeInSearchField", "String >>" + content.substring(0, length)
                                               + "<< was typed in string field", true);
  }

  public void verifySearchSuggestionsWereVisible() {
    waitForElementByElement(searchSuggestions.get(0));
    Assertion.assertTrue(checkIfElementOnPage(searchSuggestions.get(0)));
    PageObjectLogging.log("verifySearchSuggestionsWereVisible", "Search suggestions were visible",
                          true, driver);
  }

  public void verifySearchView() {
    Assertion.assertTrue(checkIfElementOnPage(searchView));
    PageObjectLogging.log("verifySearchView", "Search view was displayed on full size", true,
                          driver);
  }

  public void verifyMenuView() {
    Assertion.assertTrue(checkIfElementOnPage(menuView));
    PageObjectLogging.log("verifyMenuView", "Menu view was visible", true, driver);
  }

  public void verifySearchNoResult() {
    Assertion.assertTrue(searchSuggestions.size() == 0, "There are results");
  }

  public void verifyOpeningArticleInNav(int anchorIndex) {
    waitForElementVisibleByElement(noChevrons.get(anchorIndex));
    noChevrons.get(anchorIndex).click();
    PageObjectLogging.log("verifyOpeningArticleInNav", "New article opened", true, driver);
  }

  public void verifyOpeningNextLevelInNav(int anchorIndex) {
    waitForElementVisibleByElement(chevrons.get(anchorIndex));
    chevrons.get(anchorIndex).click();
    waitForElementVisibleByElement(backChevron);
    PageObjectLogging.log("verifyOpeningNextLevelInNav", "Back button is visible", true, driver);
  }

  public void verifyBackLinkFunctionality(int anchorIndex) {
    waitForElementVisibleByElement(chevrons.get(anchorIndex));
    chevrons.get(anchorIndex).click();
    waitForElementVisibleByElement(backChevron);
    backChevron.click();
    PageObjectLogging.log("verifyBackLinkFunctionality", "Back button is working", true, driver);
  }

  public void verifyClosingNav() {
    waitForElementVisibleByElement(overlay);
    tapOnElement(overlay);
    PageObjectLogging.log("verifyClosingNav", "Nav menu is closed", true, driver);
  }

  public void verifyTextEllipsis(int anchorIndex) {
    waitForElementVisibleByElement(noChevrons.get(anchorIndex));
    waitForElementVisibleByElement(chevrons.get(anchorIndex));
    boolean noChevronsEllipsis =
        noChevrons.get(anchorIndex).getCssValue("text-overflow").contains("ellipsis");
    boolean chevronsEllipsis =
        chevrons.get(anchorIndex).getCssValue("text-overflow").contains("ellipsis");
    Assertion.assertTrue(noChevronsEllipsis && chevronsEllipsis,
                         "CSS sellector isn't set to ellipsis");
  }

  public void verifyClickOnSearchWillExpandWindow() {
    clickSearchField();
    Assertion.assertTrue(resultField.getCssValue("visibility").equals("visible"),
                         "Result field is now hidden");
    Assertion.assertFalse(menuField.getCssValue("visibility").equals("visible"),
                          "Menu field is now visible");
  }

  public void verifyRandomPageOnFirstLevel(int randomPagePos, int checkInCategory) {
    Assertion.assertTrue(noChevrons.get(randomPagePos).getText().toString().equals("Random Page"));
    chevrons.get(checkInCategory).click();
    if (noChevrons.size() > 0) {
      Assertion
          .assertFalse(noChevrons.get(randomPagePos).getText().toString().equals("Random Page"));
    }
  }
}
