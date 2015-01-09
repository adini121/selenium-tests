package com.wikia.webdriver.testcases.visualeditor;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.dataprovider.VisualEditorDataProvider;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplateBeforeClass;
import com.wikia.webdriver.pageobjectsfactory.componentobject.visualeditordialogs.VisualEditorInsertGalleryDialog;
import com.wikia.webdriver.pageobjectsfactory.componentobject.visualeditordialogs.VisualEditorSaveChangesDialog;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.visualeditor.VisualEditorPageObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Robert 'Rochan' Chan
 * @ownership Contribution
 *
 */

public class VEPerfTests extends NewTestTemplateBeforeClass {

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
		groups = {"Perf"},
		dataProviderClass=VisualEditorDataProvider.class,
		dataProvider="getPerfWikis"
	)
	public void VEPerfTests_001_sampleArticles(String wiki, String devbox1, String devbox2) {
		String devboxURL1 = urlBuilder.getUrlForWiki(wiki, devbox1);
		String devboxURL2 = urlBuilder.getUrlForWiki(wiki, devbox2);

		article = article.openRandomArticle(devboxURL1);
		String articleName = article.getArticleName();
		devOneElapsedTimes.add(getVELoadTime());
		article = article.openArticleByName(devboxURL2, articleName);
		devTwoElapsedTimes.add(getVELoadTime());
	}

	private long getVELoadTime() {
		VisualEditorPageObject ve = article.openVEModeWithMainEditButton();
		PageObjectLogging.log("Site ", "" + ve.getCurrentUrl(), true);
		long startTime = System.nanoTime();
		ve.verifyVEToolBarPresent();
		ve.verifyEditorSurfacePresent();
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		PageObjectLogging.log("Execution time", "" + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS), true);
		return elapsedTime;
	}

	@AfterClass
	public void printTimes() {

		long totalDevOneMS = 0;
		long totalDevTwoMS = 0;

		for (int i = 0; i < devOneElapsedTimes.size(); i++) {
			long devOneMS = TimeUnit.MILLISECONDS.convert(devOneElapsedTimes.get(i), TimeUnit.NANOSECONDS);
			long devTwoMS = TimeUnit.MILLISECONDS.convert(devTwoElapsedTimes.get(i), TimeUnit.NANOSECONDS);
			totalDevOneMS += devOneMS;
			totalDevTwoMS += devTwoMS;
			PageObjectLogging.log("Execution time ", "Dev1 " + devOneMS + " Dev2 " + devTwoMS, true);
		}

		PageObjectLogging.log("Total Execution time ", "Dev1 " + totalDevOneMS + " Dev2 " + totalDevTwoMS, true);
		PageObjectLogging.log("Average Execution time ", "Dev1 " + totalDevOneMS/devOneElapsedTimes.size() +
			" Dev2 " + totalDevTwoMS/devOneElapsedTimes.size(), true);
	}
}
