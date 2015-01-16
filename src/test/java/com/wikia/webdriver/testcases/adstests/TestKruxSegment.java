package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.dataprovider.ads.AdsDataProvider;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsKruxObject;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Dmytro Rets
 * @ownership AdEngineering
 */
public class TestKruxSegment extends NewTestTemplate {
	private static final String DEFAULT_KRUX_SEGMENT_ID = "l7lznzoty";

	@Test(
		dataProviderClass = AdsDataProvider.class,
		dataProvider = "mainWikiPages",
		groups = {"KruxSegmentDesktop_GeoEdgeFree", "KruxSegmentMobile_GeoEdgeFree", "Ads"}
	)
	public void TestDefaultKruxSegment(String wikiName, String article) {
		new AdsKruxObject(driver)
			.goTo(urlBuilder.getUrlForPath(wikiName, article))
			.refresh().refresh().refresh()
			.verifyKruxSegment(DEFAULT_KRUX_SEGMENT_ID);
	}

	@Test(
		dataProviderClass = AdsDataProvider.class,
		dataProvider = "kruxSegment",
		groups = {"KruxSegmentDesktop_GeoEdgeFree", "Ads"}
	)
	public void TestCustomKruxSegment(List<String> pages, String segment, String segmentMissing) {
		AdsKruxObject adsKruxObject = new AdsKruxObject(driver);
		for (String page : pages) {
			adsKruxObject.goTo(page).waitForKrux();
		}
		if (segment != null) {
			adsKruxObject.verifyKruxSegment(segment);
		}
		if (segmentMissing != null) {
			adsKruxObject.verifyNoKruxSegment(segmentMissing);
		}
	}
}
