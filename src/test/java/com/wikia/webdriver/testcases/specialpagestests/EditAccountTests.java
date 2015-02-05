package com.wikia.webdriver.testcases.specialpagestests;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.LoginManager;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.editaccount.EditAccountPage;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.login.SpecialUserLoginPageObject;

import org.testng.annotations.Test;

/**
 * @author Karol 'kkarolk' Kujawiak <p/> 1. Close user account, 2. Verify user account closed, 3.
 *         Reopen user account, 4. Verify user account reopened
 */
public class EditAccountTests extends NewTestTemplate {

  Credentials credentials = config.getCredentials();

  @Test(groups = "EditAccountTest")
  public void EditAccount_001_closeAccount() {
    LoginManager.logInCookie(driver, credentials.userNameStaff, credentials.passwordStaff, wikiURL);
    EditAccountPage
        editAccount = new EditAccountPage(driver).openAccountManagementFor(
        credentials.userNameClosedAccount);
    editAccount.closeAccount(PageContent.CAPTION);
    editAccount.verifyAccountClosedMessage();
  }

  @Test(groups = "EditAccountTest", dependsOnMethods = "EditAccount_001_closeAccount")
  public void EditAccount_002_verifyAccountClosed() {
    SpecialUserLoginPageObject login = new SpecialUserLoginPageObject(driver).open(wikiURL);
    login.login(credentials.userNameClosedAccount, credentials.passwordClosedAccount);
    login.verifyClosedAccountMessage();
  }

  @Test(groups = "EditAccountTest", dependsOnMethods = "EditAccount_002_verifyAccountClosed")
  public void EditAccount_003_reopenAccount() {
    LoginManager.logInCookie(driver, credentials.userNameStaff, credentials.passwordStaff, wikiURL);
    EditAccountPage
        editAccount = new EditAccountPage(driver).openAccountManagementFor(
        credentials.userNameClosedAccount);
    editAccount.reopenAccount(credentials.passwordClosedAccount);
    editAccount.verifyAccountReopenedMessage();
  }

  @Test(groups = {"EditAccountTest",
                  "EditAccountTest_001"}, dependsOnMethods = "EditAccount_003_reopenAccount")
  public void EditAccount_004_verifyAccountReopened() {
    SpecialUserLoginPageObject login = new SpecialUserLoginPageObject(driver).open(wikiURL);
    login.loginAndVerify(credentials.userNameClosedAccount, credentials.passwordClosedAccount,
                         wikiURL);
  }
}
