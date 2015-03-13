package com.wikia.webdriver.testcases.articlecrudtests;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.dataprovider.ArticleDataProvider;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.editmode.VisualEditModePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialCreatePagePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.visualeditor.VisualEditorPageObject;

import org.testng.annotations.Test;

/**
 * @author: Bogna 'bognix' Knychała
 */
public class ArticleCRUDAnonTests extends NewTestTemplate {

  @Test(
      groups = {"ArticleCRUDAnon", "ArticleCRUDAnon_001"}
  )
  public void ArticleCRUDAnon_001_addBySpecialPage() {
    WikiBasePageObject base = new WikiBasePageObject(driver);
    String articleContent = PageContent.ARTICLE_TEXT;
    String articleTitle = PageContent.ARTICLE_NAME_PREFIX + base.getTimeStamp();
    SpecialCreatePagePageObject specialCreatePage = base.openSpecialCreatePage(wikiURL);
    VisualEditModePageObject visualEditMode = specialCreatePage.populateTitleField(articleTitle);
    visualEditMode.addContent(articleContent);
    ArticlePageObject article = visualEditMode.submitArticle();
    article.verifyContent(articleContent);
    article.verifyArticleTitle(articleTitle);
  }

  @Test(
      groups = {"ArticleCRUDAnon", "ArticleCRUDAnon_002"}
  )
  public void ArticleCRUDAnon_002_addByURL() {
    WikiBasePageObject base = new WikiBasePageObject(driver);
    String articleContent = PageContent.ARTICLE_TEXT;
    String articleTitle = PageContent.ARTICLE_NAME_PREFIX + base.getTimeStamp();
    VisualEditModePageObject
        visualEditMode =
        base.navigateToArticleEditPageCK(wikiURL, articleTitle);
    visualEditMode.addContent(articleContent);
    ArticlePageObject article = visualEditMode.submitArticle();
    article.verifyContent(articleContent);
    article.verifyArticleTitle(articleTitle);
  }

  @Test(
      groups = {"ArticleCRUDAnon", "ArticleCRUDAnon_003"}
  )
  public void ArticleCRUDAnon_003_addDropdown() {
    WikiBasePageObject base = new WikiBasePageObject(driver);
    String articleContent = PageContent.ARTICLE_TEXT;
    String articleTitle = PageContent.ARTICLE_NAME_PREFIX + base.getTimeStamp();
    ArticlePageObject article = base.openRandomArticle(wikiURL);
    VisualEditorPageObject ve = article.createArticleInVEUsingDropdown(articleTitle);
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    article = ve.clickVEEditAndPublish(articleContent);
    article.verifyContent(articleContent);
    article.verifyArticleTitle(articleTitle);
  }
}
