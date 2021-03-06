package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.dataprovider.ads.AdsDataProvider;
import com.wikia.webdriver.common.templates.TemplateDontLogout;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsOoyalaObject;

import org.testng.annotations.Test;

import java.awt.*;

/**
 * @author Dmytro Rets
 * @ownership AdEngineering
 */
public class TestOoyalaAds extends TemplateDontLogout {

  private final static Color GREEN = new Color(0, 214, 0);
  private final static Color BLUE = new Color(0, 13, 255);
  private final static int AD_DURATION_SEC = 30;
  private final static int VIDEO_DURATION_SEC = 30;

  @Test(
      dataProviderClass = AdsDataProvider.class,
      groups = {"TestOoyalaAds_GeoEdgeFree"},
      dataProvider = "ooyalaAds"
  )
  public void TestOoyalaAds_GeoEdgeFree(String wikiName, String article) {
    String testedPage = urlBuilder.getUrlForPath(wikiName, article);
    AdsOoyalaObject wikiPage = new AdsOoyalaObject(driver, testedPage);
    wikiPage.verifyLightboxAd(BLUE, AD_DURATION_SEC);
    wikiPage.verifyLightboxVideo(GREEN, VIDEO_DURATION_SEC);
  }
}
