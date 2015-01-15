package com.wikia.webdriver.testcases.adstests;

import com.wikia.webdriver.common.dataprovider.ads.AdsDataProvider;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.adsbase.AdsKruxObject;
import org.testng.annotations.Test;

/**
 * @author Dmytro Rets
 * @ownership AdEngineering
 */
public class TestKruxSegment extends NewTestTemplate {
	private static final String DEFAULT_KRUX_SEGMENT_ID = "l7lznzoty";
	private static final String CUSTOM_KRUX_SEGMENT_ID = "o7x6qbgru";
	private static final String GENERAL_WIKI = "glee";
	private static final String GENERAL_ARTICLE = "Glee_TV_Show_Wiki";

	@Test(
		dataProviderClass = AdsDataProvider.class,
		dataProvider = "mainWikiPages",
		groups = {"KruxSegmentDesktop_GeoEdgeFree", "KruxSegmentMobile_GeoEdgeFree", "Ads"}
	)
	public void TestDefaultKruxSegment(String wikiName, String article) {
		new AdsKruxObject(driver)
			.goTo(wikiName, article)
			.refresh().refresh().refresh()
			.verifyKruxSegment(DEFAULT_KRUX_SEGMENT_ID);
	}

	@Test(
		dataProviderClass = AdsDataProvider.class,
		dataProvider = "kruxArticles",
		groups = {"KruxSegmentDesktop_GeoEdgeFree", "Ads"}
	)
	public void TestCustomKruxSegmentOneWiki(String wikiName, String article1, String article2, String article3) {
		new AdsKruxObject(driver)
			.goTo(wikiName, article1).waitForKrux()
			.goTo(wikiName, article2).waitForKrux()
			.goTo(wikiName, article3).waitForKrux()
			.verifyKruxSegment(CUSTOM_KRUX_SEGMENT_ID);
	}

	@Test(
		dataProviderClass = AdsDataProvider.class,
		dataProvider = "kruxArticles",
		groups = {"KruxSegmentDesktop_GeoEdgeFree", "Ads"}
	)
	public void TestCustomKruxSegmentTwoWikis(String wikiName, String article1, String article2, String article3) {
		new AdsKruxObject(driver)
			.goTo(GENERAL_WIKI, GENERAL_ARTICLE).waitForKrux()
			.goTo(wikiName, article1).waitForKrux()
			.goTo(wikiName, article2).waitForKrux()
			.goTo(wikiName, article3).waitForKrux()
			.verifyKruxSegment(CUSTOM_KRUX_SEGMENT_ID);
	}

	@Test(
		dataProviderClass = AdsDataProvider.class,
		dataProvider = "kruxArticles",
		groups = {"KruxSegmentDesktop_GeoEdgeFree", "Ads"}
	)
	public void TestNoCustomKruxSegment() {
		new AdsKruxObject(driver)
			.goTo(GENERAL_WIKI, GENERAL_ARTICLE).waitForKrux()
			.refresh().refresh().refresh()
			.verifyNoKruxSegment(CUSTOM_KRUX_SEGMENT_ID);
	}
}
