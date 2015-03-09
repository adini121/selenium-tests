package com.wikia.webdriver.testcases.visualeditor;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.dataprovider.VisualEditorDataProvider;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.visualeditor.VisualEditorPageObject;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Robert 'Rochan' Chan
 * @ownership Contribution
 */

public class VEPerfTests extends NewTestTemplate {

  Credentials credentials = config.getCredentials();
  ArticlePageObject article;
  private List<Long> devOneElapsedTimes = new ArrayList<>();
  private List<Long> devTwoElapsedTimes = new ArrayList<>();

  @BeforeMethod(alwaysRun = true)
  public void setup_VEPreferred() {
    wikiURL = urlBuilder.getUrlForWiki(URLsContent.VE_ENABLED_WIKI);
    article = new ArticlePageObject(driver);
    article.logInCookie(credentials.userNameVEPreferred, credentials.passwordVEPreferred, wikiURL);
  }

  @Test(
      groups = {"Perf", "PerfNoCache"},
      dataProviderClass = VisualEditorDataProvider.class,
      dataProvider = "getPerfWikis",
      invocationCount = 10
  )
  public void VEPerfTests_001_noCache(String wiki) {
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    article = article.openRandomArticle(wikiURL);
    getVELoadTimes();
  }

  @Test(
      groups = {"PerfProd", "PerfCached"},
      dataProviderClass = VisualEditorDataProvider.class,
      dataProvider = "getPerfWikis",
      invocationCount = 50
  )
  public void VEPerfTests_002_cached(String wiki) {
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    article = article.openRandomArticle(wikiURL);
    VisualEditorPageObject ve = article.openVEModeWithMainEditButton();
    ve.verifyVEToolBarPresent();
    ve.clickCancelButton();
    devOneElapsedTimes.add(getVELoadTime());
  }

  @Test(
      groups = {"Perf", "PerfEmptyArticle"},
      dataProviderClass = VisualEditorDataProvider.class,
      dataProvider = "getPerfWikis",
                invocationCount = 10
  )
  public void VEPerfTests_003_emptyArticles(String wiki) {
    String articleName = PageContent.ARTICLE_NAME_PREFIX + article.getTimeStamp();
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    article = article.openArticleByName(wikiURL, articleName);
    getVELoadTimes();
  }

  @Test(
      groups = {"Perf", "PerfTemplateArticle"},
      dataProviderClass = VisualEditorDataProvider.class,
      dataProvider = "getPerfWikis",
      invocationCount = 50
  )
  public void VEPerfTests_004_templateArticle(String wiki, String devbox1, String devbox2) {
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    //TODO find an article with templates
    String articleName = PageContent.ARTICLE_NAME_PREFIX + article.getTimeStamp();
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    article = article.openArticleByName(wikiURL, articleName);
    devOneElapsedTimes.add(getVELoadTime());
  }

  @Test(
      groups = {"Perf", "PerfGalleryArticle"},
      dataProviderClass = VisualEditorDataProvider.class,
      dataProvider = "getPerfWikis",
      invocationCount = 50
  )
  public void VEPerfTests_005_galleryArticle(String wiki, String devbox1, String devbox2) {
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    //TODO find an article with galleries
    String articleName = PageContent.ARTICLE_NAME_PREFIX + article.getTimeStamp();
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    article = article.openArticleByName(wikiURL, articleName);
    devOneElapsedTimes.add(getVELoadTime());
  }

  @Test(
      groups = {"Perf", "PerfTableArticle"},
      dataProviderClass = VisualEditorDataProvider.class,
      dataProvider = "getPerfWikis",
      invocationCount = 50
  )
  public void VEPerfTests_006_tableArticle(String wiki, String devbox1, String devbox2) {
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    //TODO find an article with tables
    String articleName = PageContent.ARTICLE_NAME_PREFIX + article.getTimeStamp();
    wikiURL = urlBuilder.getUrlForWiki(wiki);
    article = article.openArticleByName(wikiURL, articleName);
    devOneElapsedTimes.add(getVELoadTime());
  }

  private long getVELoadTime() {
    VisualEditorPageObject ve = article.openVEModeWithMainEditButton();
    String url = ve.getCurrentUrl();
    PageObjectLogging.log("Site ", "" + ve.getCurrentUrl(), true);
    long startTime = System.nanoTime();
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    PageObjectLogging
        .logPerf(url, "" + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS), true);
    return elapsedTime;
  }

  private long getVELoadTimes() {
    VisualEditorPageObject ve = article.openVEModeWithMainEditButton();
    String url = ve.getCurrentUrl();
    PageObjectLogging.log("Site ", "" + ve.getCurrentUrl(), true);
    long startTime = System.nanoTime();
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    ve.clickCancelButton();
    ve.openVEModeWithMainEditButton();
    PageObjectLogging.log("Site ", "" + ve.getCurrentUrl(), true);
    long cachedStartTime = System.nanoTime();
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    long cachedEndTime = System.nanoTime();
    long cachedElapsedTime = cachedEndTime - cachedStartTime;
    PageObjectLogging.logPerf(url,
                              "" + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)
                              + "," + TimeUnit.MILLISECONDS
                                  .convert(cachedElapsedTime, TimeUnit.NANOSECONDS), true);
    return elapsedTime;
  }

  @AfterGroups
      (groups = {"Perf", "PerfProd"})
  public void printTimes() {

    long totalDevOneMS = 0;
    long totalDevTwoMS = 0;

    for (int i = 0; i < devOneElapsedTimes.size(); i++) {
      long
          devOneMS =
          TimeUnit.MILLISECONDS.convert(devOneElapsedTimes.get(i), TimeUnit.NANOSECONDS);
      long
          devTwoMS =
          TimeUnit.MILLISECONDS.convert(devTwoElapsedTimes.get(i), TimeUnit.NANOSECONDS);
      totalDevOneMS += devOneMS;
      totalDevTwoMS += devTwoMS;
      PageObjectLogging.log("Execution time (ms) ", "Dev1 " + devOneMS + " Dev2 " + devTwoMS, true);
    }

    PageObjectLogging
        .logPerf("Total Execution time (ms) ", "Dev1 " + totalDevOneMS + " Dev2 " + totalDevTwoMS,
                 true);
    PageObjectLogging.logPerf("Average Execution time (ms) ",
                              "Dev1 " + totalDevOneMS / devOneElapsedTimes.size() +
                              " Dev2 " + totalDevTwoMS / devOneElapsedTimes.size(), true);
  }

  @AfterGroups
      (groups = {"PerfProd"})
  public void printTimesProd() {

    long totalDevOneMS = 0;

    for (int i = 0; i < devOneElapsedTimes.size(); i++) {
      long
          devOneMS =
          TimeUnit.MILLISECONDS.convert(devOneElapsedTimes.get(i), TimeUnit.NANOSECONDS);
      totalDevOneMS += devOneMS;
      PageObjectLogging.log("Execution time (ms) ", "Prod " + devOneMS, true);
    }

    PageObjectLogging.logPerf("Total Execution time (ms) ", "Prod " + totalDevOneMS, true);
    PageObjectLogging.logPerf("Average Execution time (ms) ",
                              "Prod " + totalDevOneMS / devOneElapsedTimes.size(), true);
  }
}
