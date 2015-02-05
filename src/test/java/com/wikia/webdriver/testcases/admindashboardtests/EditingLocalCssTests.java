package com.wikia.webdriver.testcases.admindashboardtests;

import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.core.LoginManager;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.editmode.VisualEditModePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialAdminDashboardPageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.wikipagemonobook.WikiArticleMonoBookPageObject;

import org.testng.annotations.Test;


/**
 * tests are prepared to test the following feature: https://wikia-inc.atlassian.net/browse/DAR-136
 *
 * @author wikia
 */
public class EditingLocalCssTests extends NewTestTemplate {

  Credentials credentials = config.getCredentials();

  @Test(groups = {"EditingLocalCss_001", "EditingLocalCss", "AdminDashboard"})
  public void EditingLocalCss_001_UserWithAdminRightsTriesToEditWikiaCss() {
    LoginManager.logInCookie(driver, credentials.userNameStaff, credentials.passwordStaff, wikiURL);
    ArticlePageObject
        article =
        new ArticlePageObject(driver).openArticleByName(wikiURL, URLsContent.MEDIAWIKI_CSS);
    VisualEditModePageObject edit = article.goToCurrentArticleEditPage();
    edit.verifyUrl(URLsContent.SPECIAL_CSS);
  }

  @Test(groups = {"EditingLocalCss_002", "EditingLocalCss", "AdminDashboard"})
  public void EditingLocalCss_002_UserWithoutAdminRightsHasNoEditOption() {
    ArticlePageObject
        article =
        new ArticlePageObject(driver).openArticleByName(wikiURL, URLsContent.MEDIAWIKI_CSS);
    article.verifyEditButtonNotPresent();
    article.goToCurrentArticleEditPage();
    article.verifyPermissionsErrorsPresent();
    LoginManager.logInCookie(driver, credentials.userName, credentials.password, wikiURL);
    article.openArticleByName(wikiURL, URLsContent.MEDIAWIKI_CSS);
    article.verifyEditButtonNotPresent();
    article.goToCurrentArticleEditPage();
    article.verifyPermissionsErrorsPresent();
  }

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-299
   */
  @Test(groups = {"EditingLocalCss_003", "EditingLocalCss", "AdminDashboard"})
  public void EditingLocalCss_003_MonobookUserWithAdminRightsEditsWikiaCss() {
    LoginManager
        .logInCookie(driver, credentials.userNameMonobook, credentials.passwordMonobook, wikiURL);
    WikiArticleMonoBookPageObject monobookArticle = new WikiArticleMonoBookPageObject(driver);
    monobookArticle.openArticleByName(wikiURL, URLsContent.MEDIAWIKI_CSS);
    monobookArticle.clickEdit();
    monobookArticle.verifyEditionArea();
  }

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-300
   */
  @Test(groups = {"EditingLocalCss_004", "EditingLocalCss", "AdminDashboard"})
  public void EditingLocalCss_004_MonobookUserWithAdminRightsOpensSpecialCss() {
    LoginManager
        .logInCookie(driver, credentials.userNameMonobook, credentials.passwordMonobook, wikiURL);
    WikiArticleMonoBookPageObject monobookArticle = new WikiArticleMonoBookPageObject(driver);
    monobookArticle.openSpecialCss(wikiURL);
    monobookArticle.verifyOasisOnly();
  }

  /**
   * https://wikia-inc.atlassian.net/browse/DAR-302
   */
  @Test(groups = {"EditingLocalCss_005", "EditingLocalCss", "AdminDashboard"})
  public void EditingLocalCss_005_UserWithAdminRightsTriesToAccesSpecialCssFromAdminDashboard() {
    LoginManager.logInCookie(driver, credentials.userNameStaff, credentials.passwordStaff, wikiURL);
    SpecialAdminDashboardPageObject
        adminDashboard =
        new SpecialAdminDashboardPageObject(driver).openSpecialAdminDashboard(
            wikiURL);
    adminDashboard.clickCssTool();
    adminDashboard.verifyUrl(URLsContent.SPECIAL_CSS);
  }
}
