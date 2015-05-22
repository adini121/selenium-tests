package com.wikia.webdriver.testcases.adstests.mobileadstests;

import com.wikia.webdriver.common.core.geoedge.GeoEdgeProxy;
import com.wikia.webdriver.common.core.urlbuilder.UrlBuilder;
import com.wikia.webdriver.common.dataprovider.mobile.MobileAdsDataProvider;
import com.wikia.webdriver.common.templates.mobile.MobileTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.mobile.MobileAdsBaseObject;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

/**
 * Bogna 'bognix' Knychala
 *
 * @ownership AdEngineering
 */
public class TestLeaderboardPresenceOnMobile extends MobileTestTemplate {

  private String testedPage;

  @Factory(
      dataProviderClass = MobileAdsDataProvider.class,
      dataProvider = "articlesWithTopLeaderboard"
  )
  public TestLeaderboardPresenceOnMobile(String wikiName, String article) {
    urlBuilder = new UrlBuilder(config.getEnv(), config.getBrowser());
    testedPage = urlBuilder.getUrlForPath(wikiName, article);
  }

  @GeoEdgeProxy(country = "JP")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_JP", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_JP() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "VE")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_VE", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_VE() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "AU")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_AU", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_AU() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "NZ")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_NZ", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_NZ() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "DE")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_DE", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_DE() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "GB")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_GB", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_GB() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "LT")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_LT", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_LT() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "TW")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_TW", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_TW() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "CA")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_CA", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_CA() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_GEF", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_GEF() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }

  @GeoEdgeProxy(country = "NO")
  @Test(
      groups = {"MobileAds", "TopLeaderboardPresenceTest_NO", "MonitoringTestsMobile"}
  )
  public void TopLeaderboardPresenceTest_NO() {
    new MobileAdsBaseObject(driver, testedPage).verifyMobileTopLeaderboard();
  }
}
