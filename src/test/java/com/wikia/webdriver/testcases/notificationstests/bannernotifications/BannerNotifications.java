package com.wikia.webdriver.testcases.notificationstests.bannernotifications;

import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.HomePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qaga on 2015-05-05.
 */
public class BannerNotifications extends NewTestTemplate {

    @Test
    //TODO: add forcing wiki language to avoid appearing wiki in your lang notifications
    public void closeBannerNotifications() {

        ArticlePageObject home = new ArticlePageObject(driver);
        home.openMainPage(wikiURL);
        driver.navigate().refresh();
//        new WebDriverWait(driver, 20)
//                .until(ExpectedConditions
//                        .not(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".banner-notification"))));
        List<String> bannerNotificationsTypes = new ArrayList<String>();
        bannerNotificationsTypes.add("notify");
        bannerNotificationsTypes.add("warn");
        bannerNotificationsTypes.add("error");
        bannerNotificationsTypes.add("confirm");

        for (String type : bannerNotificationsTypes) {
            ((JavascriptExecutor) driver).executeScript("new BannerNotification('wiadomosc', '" + type + "').show();");
        }
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.cssSelector(".banner-notification")));
        for (String type : bannerNotificationsTypes) {
            WebElement closeButton = driver.findElement(By.cssSelector(".banner-notification."+type+" .close"));
            closeButton.click();

        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
