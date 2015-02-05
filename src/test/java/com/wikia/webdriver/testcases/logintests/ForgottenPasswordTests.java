package com.wikia.webdriver.testcases.logintests;

import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.componentobject.dropdowncomponentobject.DropDownComponentObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.login.SpecialUserLoginPageObject;

import org.testng.annotations.Test;


/**
 * @author Bogna 'bognix' Knychala
 * @author Karol 'kkarolk' Kujawiak
 */
public class ForgottenPasswordTests extends NewTestTemplate {

  Credentials credentials = config.getCredentials();

  @Test(groups = {"ForgottenPassword_001", "ForgottenPassword"})
  public void ForgottenPassword_001_dropdown() {
    String userName = credentials.userNameForgottenPassword;

    WikiBasePageObject base = new WikiBasePageObject(driver);
    base.openWikiPage(wikiURL);
    DropDownComponentObject dropdown = new DropDownComponentObject(driver);
    dropdown.openDropDown();
    dropdown.remindPassword(userName, credentials.apiToken);

    dropdown.verifyMessageAboutNewPassword(userName);
    String
        newPassword =
        dropdown.receiveMailWithNewPassowrd(credentials.email, credentials.emailPassword);
    dropdown.openDropDown();
    dropdown.logIn(userName, newPassword);
    SpecialUserLoginPageObject login = new SpecialUserLoginPageObject(driver);
    newPassword = login.setNewPassword();
    login.verifyUserLoggedIn(userName);

    login.logOut(driver);
    dropdown.openDropDown();
    dropdown.logIn(userName, newPassword);
    dropdown.verifyUserLoggedIn(userName);
  }

  @Test(
      groups = {"ForgottenPassword_002", "ForgottenPassword"}
  )
  public void ForgottenPassword_002_specialPage() {
    String userName = credentials.userNameForgottenPassword2;

    SpecialUserLoginPageObject loginPage = new SpecialUserLoginPageObject(driver).open(wikiURL);
    loginPage.remindPassword(userName, credentials.apiToken);
    loginPage.verifyMessageAboutNewPassword(userName);
    String
        newPassword =
        loginPage.receiveMailWithNewPassowrd(credentials.email, credentials.emailPassword);
    loginPage.login(userName, newPassword);
    newPassword = loginPage.setNewPassword();
    loginPage.verifyUserLoggedIn(userName);

    loginPage.logOut(wikiURL);
    loginPage.open(wikiURL);
    loginPage.login(userName, newPassword);
    loginPage.verifyUserLoggedIn(userName);
  }
}
