package com.wikia.webdriver.pageobjectsfactory.componentobject.mercury;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.imageutilities.ImageComparison;
import com.wikia.webdriver.common.core.imageutilities.Shooter;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.pageobjectsfactory.pageobject.mercury.MercuryBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.mercury.PerformTouchAction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

/**
 * @author: Rodrigo Gomez, Łukasz Nowak, Tomasz Napieralski
 */

public class LightBoxMercuryComponentObject extends MercuryBasePageObject {

  @FindBy(css = ".lightbox-close-wrapper")
  private WebElement closeLightboxButton;
  @FindBy(css = ".lightbox-header-title")
  private WebElement imagesCounter;
  @FindBy(css = ".current")
  private WebElement currentImage;
  @FindBy(css = ".media-lightbox")
  private WebElement lightboxWrapper;
  @FindBy(css = ".lightbox-content-inner")
  private WebElement lightboxInner;
  @FindBy(css = ".lightbox-content")
  private WebElement lightboxContent;
  @FindBy(css = ".page-wrapper")
  private WebElement pageWrapper;
  @FindBy(css = ".lightbox-header")
  private WebElement lightboxHeader;
  @FindBy(css = ".lightbox-footer")
  private WebElement lightboxFooter;

  public static final String EDGE_LEFT = "left";
  public static final String EDGE_RIGHT = "right";

  public static final String ZOOM_METHOD_GESTURE = "gesture";
  public static final String ZOOM_METHOD_TAP = "tap";

  public static final String DIRECTION_LEFT = "left";
  public static final String DIRECTION_RIGHT = "right";
  public static final String DIRECTION_UP = "up";
  public static final String DIRECTION_DOWN = "down";

  public LightBoxMercuryComponentObject(WebDriver driver) {
    super(driver);
  }

  public MercuryBasePageObject clickCloseButton() {
    waitForElementVisibleByElement(closeLightboxButton);
    closeLightboxButton.click();
    return new MercuryBasePageObject(driver);
  }

  public String getCurrentImagePath() {
    return currentImage.getAttribute("src");
  }

  public void verifyCurrentImageIsVisible() {
    waitForElementVisibleByElement(currentImage);
    Assertion.assertTrue(checkIfElementOnPage(currentImage));
  }

  public void swipeImageLeft() {
    swipeLeft(lightboxContent);
    PageObjectLogging.log("swipeImageLeft", "Swipe left was simulated", true, driver);
  }

  public void swipeImageRight() {
    swipeRight(lightboxContent);
    PageObjectLogging.log("swipeImageRight", "Swipe right was simulated", true, driver);
  }

  public void testGestures() {
    doubleTapZoom(lightboxWrapper);
    doubleTapZoom(lightboxInner);
    doubleTapZoom(lightboxContent);
    doubleTapZoom(pageWrapper);
    PageObjectLogging
        .log("testGestures", "Double tap zoom was correctly simulated 4 times", true, driver);
  }

  public void verifyImageWasChanged(String imageOnePath, String imageTwoPath) {
    Assertion.assertFalse(imageOnePath.equals(imageTwoPath), "Image in lightbox was changed");
  }

  public void verifyLightboxClosed() {
    Assertion.assertFalse(checkIfElementOnPage(currentImage));
  }

  public String currentImageSrcPath() {
    return currentImage.getAttribute("src");
  }

  public void verifySwiping(PerformTouchAction touchAction, String direction, int attempts) {
    String currentImageSrc = getCurrentImagePath();
    String swipeDirection = direction;
    String nextImageSrc;
    boolean imageChanged = false;
    int startX = 70;
    int endX = 20;
    int duration = 300;
    int waitAfter = 5000;
    int centerY = 50;

    if ("right".equals(swipeDirection)) {
      int temp = startX;
      startX = endX;
      endX = temp;
    } else {
      swipeDirection = "left";
    }
    for (int i = 0; i < attempts; ++i) {
      touchAction.swipeFromPointToPoint(startX, centerY, endX, centerY, duration, waitAfter);
      nextImageSrc = getCurrentImagePath();
      if (!nextImageSrc.contains(currentImageSrc)) {
        imageChanged = true;
        break;
      }
    }
    if (imageChanged) {
      PageObjectLogging.log("verifySwiping", "Swiping to " + swipeDirection + " works", true);
    } else {
      PageObjectLogging
          .log("verifySwiping", "Swiping to " + swipeDirection + " doesn't work", false);
    }
  }

  private boolean verifyChangesInUI(boolean lastDisplayVisibility) {
    boolean lightboxHeaderDisplay = true;
    boolean lightboxFooterDisplay = true;
    if (lightboxHeader.getCssValue("display").contains("none")) {
      lightboxHeaderDisplay = false;
    }
    if (lightboxFooter.getCssValue("display").contains("none")) {
      lightboxFooterDisplay = false;
    }
    if (lightboxHeaderDisplay == lastDisplayVisibility) {
      PageObjectLogging
          .log("verifyChangesInUI", "Lightbox header visibility didn't changed", false);
    } else {
      PageObjectLogging.log("verifyChangesInUI", "Lightbox header visibility changed", true);
    }
    if (lightboxFooterDisplay == lastDisplayVisibility) {
      PageObjectLogging
          .log("verifyChangesInUI", "Lightbox footer visibility didn't changed", false);
    } else {
      PageObjectLogging.log("verifyChangesInUI", "Lightbox footer visibility changed", true);
    }
    return !lastDisplayVisibility;
  }

  public void verifyVisibilityUI(PerformTouchAction touchAction) {
    String LightboxHeaderDisplayValue = lightboxHeader.getCssValue("display");
    String LightboxFooterDisplayValue = lightboxFooter.getCssValue("display");
    int duration = 500;
    int waitAfter = 5000;
    boolean lastDisplayVisibility = true;
    if (LightboxHeaderDisplayValue.contains(LightboxFooterDisplayValue)) {
      if ("none".equals(LightboxHeaderDisplayValue)) {
        PageObjectLogging
            .log("verifyVisibilityUI", "Lightbox header and footer are not visible", true);
        lastDisplayVisibility = false;
      } else {
        PageObjectLogging.log("verifyVisibilityUI", "Lightbox header and footer are visible", true);
        lastDisplayVisibility = true;
      }
    } else {
      PageObjectLogging.log("verifyVisibilityUI",
                            "Visibility of lightbox header is different than lightbox footer",
                            false);
    }
    touchAction.tapOnPointXY(50, 50, duration, waitAfter);
    lastDisplayVisibility = verifyChangesInUI(lastDisplayVisibility);
    touchAction.tapOnPointXY(50, 50, duration, waitAfter);
    lastDisplayVisibility = verifyChangesInUI(lastDisplayVisibility);
  }

  public void verifyTappingOnImageEdge(PerformTouchAction touchAction, String edge) {
    String currentImageSrc = getCurrentImagePath();
    String imgEdge = edge;
    String nextImageSrc;
    int pointX = 25;
    int duration = 500;
    int waitAfter = 5000;
    if ("right".equals(imgEdge)) {
      pointX = 75;
    } else {
      imgEdge = "left";
    }
    touchAction.tapOnPointXY(pointX, 50, duration, waitAfter);
    nextImageSrc = getCurrentImagePath();
    if (!nextImageSrc.contains(currentImageSrc)) {
      PageObjectLogging
          .log("verifyTappingOnImageEdge", "Tapping on " + imgEdge + " edge works", true);
    } else {
      PageObjectLogging
          .log("verifyTappingOnImageEdge", "Tapping on " + imgEdge + " edge doesn't work", false);
    }
  }

  public void verifyZoomingByGesture(PerformTouchAction touchAction, String zoomMethod) {
    Shooter shooter = new Shooter();
    ImageComparison ic = new ImageComparison();
    File beforeZooming = shooter.capturePage(driver);
    switch (zoomMethod) {
      case "gesture":
        touchAction.zoomInOutPointXY(50, 50, 50, 100, PerformTouchAction.ZOOM_WAY_IN, 3000);
        break;
      case "tap":
        touchAction.tapOnPointXY(50, 50, 140, 0);
        touchAction.tapOnPointXY(50, 50, 140, 3000);
        break;
      default:
        break;
    }
    File afterZoomIn = shooter.capturePage(driver);
    if (ic.areFilesTheSame(beforeZooming, afterZoomIn)) {
      PageObjectLogging
          .log("verifyZoomingByGesture", "Zoom in by " + zoomMethod + " doesn't work", false);
    } else {
      PageObjectLogging.log("verifyZoomingByGesture", "Zoom in by " + zoomMethod + " works", true);
    }
    switch (zoomMethod) {
      case "gesture":
        touchAction.zoomInOutPointXY(50, 50, 50, 140, PerformTouchAction.ZOOM_WAY_OUT, 3000);
        break;
      case "tap":
        touchAction.tapOnPointXY(50, 50, 140, 0);
        touchAction.tapOnPointXY(50, 50, 140, 3000);
        break;
      default:
        break;
    }
    File afterZoomOut = shooter.capturePage(driver);
    if (ic.areFilesTheSame(beforeZooming, afterZoomOut)) {
      PageObjectLogging.log("verifyZoomingByGesture", "Zoom out by " + zoomMethod + " works", true);
    } else {
      PageObjectLogging
          .log("verifyZoomingByGesture", "Zoom out by " + zoomMethod + " doesn't work", false);
    }
  }

  public void verifyMovingImageAfterZoomingToDirection(PerformTouchAction touchAction,
                                                       String direction) {
    Shooter shooter = new Shooter();
    ImageComparison ic = new ImageComparison();
    File beforeZooming = shooter.capturePage(driver);
    touchAction.tapOnPointXY(50, 50, 140, 0);
    touchAction.tapOnPointXY(50, 50, 140, 2000);
    File afterZooming = shooter.capturePage(driver);
    if (ic.areFilesTheSame(beforeZooming, afterZooming)) {
      PageObjectLogging.log("verifyZoomingByGesture", "Zoom in doesn't work", false);
    } else {
      PageObjectLogging.log("verifyZoomingByGesture", "Zoom in works", true);
    }
    touchAction.swipeFromCenterToDirection(direction, 200, 200, 2000);
    File afterMoving = shooter.capturePage(driver);
    if (ic.areFilesTheSame(afterZooming, afterMoving)) {
      PageObjectLogging
          .log("verifyZoomingByGesture", "Move to " + direction + " doesn't work", false);
    } else {
      PageObjectLogging.log("verifyZoomingByGesture", "Move to " + direction + " works", true);
    }
  }

}

