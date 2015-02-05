package com.wikia.webdriver.common.core;

import com.wikia.webdriver.common.logging.PageObjectLogging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ludwik on 2015-02-05.
 */
public class LoginManager {

  private static final String
      LOGGED_IN_USER_SELECTOR_VENUS = ".AccountNavigation a[href*=%userName%]";
  private static final String LOGGED_IN_USER_SELECTOR_MONOBOOK = "#pt-userpage a[href*=%userName%]";

  public static void logInCookie(WebDriver driver, String userName, String password, String wikiURL) {
    try {
      CookieStore cookieStore = new BasicCookieStore();

      HttpClient httpclient = HttpClientBuilder.create()
          .setConnectionBackoffStrategy(new DefaultBackoffStrategy())
          .setDefaultCookieStore(cookieStore)
          .disableAutomaticRetries()
          .build();

      HttpPost loginTokenPost = new HttpPost(wikiURL + "wikia.php");
      List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

      postParameters.add(new BasicNameValuePair("controller", "UserLoginSpecial"));
      postParameters.add(new BasicNameValuePair("format", "json"));
      postParameters.add(new BasicNameValuePair("method", "retrieveLoginToken"));

      loginTokenPost.setEntity(new UrlEncodedFormEntity(postParameters, StandardCharsets.UTF_8));

      HttpResponse response = httpclient.execute(loginTokenPost);
      HttpEntity entity = response.getEntity();

      JSONObject loginTokenResponseValue = new JSONObject(EntityUtils.toString(entity));

      PageObjectLogging.log("LOGIN HEADERS: ", response.toString(), true);
      PageObjectLogging.log("LOGIN RESPONSE: ", loginTokenResponseValue.toString(), true);

      String token = loginTokenResponseValue.getString("loginToken");

      postParameters.clear();
      postParameters.add(new BasicNameValuePair("username", userName));
      postParameters.add(new BasicNameValuePair("password", password));
      postParameters.add(new BasicNameValuePair("loginToken", token));

      HttpPost userLoginPost = new HttpPost(wikiURL + "wiki/Special:UserLogin");
      userLoginPost.setEntity(new UrlEncodedFormEntity(postParameters, StandardCharsets.UTF_8));

      response = httpclient.execute(userLoginPost);

      PageObjectLogging.log("LOGIN HEADERS: ", response.toString(), true);

      for (org.apache.http.cookie.Cookie cookie : cookieStore.getCookies()) {
        driver.manage().addCookie(
            new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(),
                       cookie.getExpiryDate(), false));
      }

      driver.get(wikiURL);

      verifyUserLoggedIn(driver, userName);

      PageObjectLogging.log("loginCookie", "user was logged in by cookie", true, driver);
    } catch (TimeoutException e) {
      PageObjectLogging.log("loginCookie", "page timeout after login by cookie", true);
    } catch (UnsupportedEncodingException e) {
      PageObjectLogging.log("logInCookie", "UnsupportedEncodingException", false);
    } catch (ClientProtocolException e) {
      PageObjectLogging.log("logInCookie", "ClientProtocolException", false);
    } catch (ParseException e) {
      PageObjectLogging.log("logInCookie", e.getMessage(), false);
    } catch (IOException e) {
      PageObjectLogging.log("logInCookie", e.getMessage(), false);
    } catch (JSONException e) {
      PageObjectLogging.log("Failed to parse response value", e.getMessage(), false);
    }
  }

  private static void verifyUserLoggedIn(WebDriver driver, String userName) {
    if (driver.findElement(By.tagName("body")).getAttribute("class").contains("skin-monobook")) {
      driver.findElement(
          By.cssSelector(LOGGED_IN_USER_SELECTOR_MONOBOOK.replace("%userName%", userName.replace(
              " ", "_"))));
    } else {
      //Venus
      driver.findElement(
          By.cssSelector(
              LOGGED_IN_USER_SELECTOR_VENUS
                  .replace("%userName%", userName)));
    }
    PageObjectLogging.log(
        "verifyUserLoggedIn",
        "user " + userName + " logged in",
        true
    );
  }
}
