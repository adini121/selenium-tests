package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.dataprovider.ads.AdsDataProvider;
import com.wikia.webdriver.common.templates.TemplateDontLogout;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsBaseObject;

import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Sergey Naumov
 * @link https://www.google.com/dfp/5441#delivery/LineItemDetail/lineItemId=115974612
 * @ownership AdEngineering
 */
public class TestDfpParamsPresent extends TemplateDontLogout {

  @Test(
      dataProviderClass = AdsDataProvider.class,
      dataProvider = "dfpParams",
      groups = {"TestDfpParamsPresent_GeoEdgeFree", "Ads"}
  )
  public void TestDfpParamsPresent_GeoEdgeFree(
      String wikiName, String article, String adUnit, String slot, String lineItemId,
      String creativeId, List<String> pageParams, List<String> slotParams
  ) {
    String testedPage = urlBuilder.getUrlForPath(wikiName, article);
    AdsBaseObject ads = new AdsBaseObject(driver, testedPage);
    ads.verifyGptIframe(adUnit, slot, "gpt");
    ads.verifyGptParams(slot, pageParams, slotParams);
    ads.verifyGptAdInSlot(slot, lineItemId, creativeId);

  }
}
