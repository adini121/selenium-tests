package com.wikia.webdriver.common.core.configuration;


import com.wikia.webdriver.common.core.Global;

import java.io.File;

/**
 * @author Bogna 'bognix' Knychała
 */
public class POMConfiguration extends AbstractConfiguration {

    private String browser;
    private String env;
    private String wikiName;
    private String captchaPath;
    private String credentialsFilePath;
    private String enableScreenshots;

    public POMConfiguration() {
        browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "FF"; //Set default value to Firefox;
        }

        env = System.getProperty("env");
        if (env == null || env.isEmpty()) {
            env = "prod"; //Set default value to production
        }

        wikiName = System.getProperty("wiki-name");
        if (wikiName == null || wikiName.isEmpty()) {
            wikiName = "mediawiki119"; //Set default value to mediawiki119
        }

        wikiName = System.getProperty("wiki-name");
        if (wikiName == null || wikiName.isEmpty()) {
            wikiName = "mediawiki119"; //Set default value to mediawiki119
        }

        enableScreenshots = System.getProperty("enable-screenshots");
        if (wikiName == null || wikiName.isEmpty()) {
            enableScreenshots = "true"; //Set default value to true
        }

        Global.ENABLE_SCREENSHOTS = System.getProperty("enable-screenshots");


        credentialsFilePath = System.getProperty("config");
        captchaPath = System.getProperty("captcha");
    }

    @Override
    public String getBrowser() {
        return this.browser;
    }

    @Override
    public String getEnv() {
        return this.env;
    }

    @Override
    public String getWikiName() {
        return this.wikiName;
    }

    @Override
    public File getCaptchaFile() {
        return new File(captchaPath);
    }

    @Override
    public String getPlatformVersion() {
        return System.getProperty("platform-version");
    }

    @Override
    public String getPlatform() {
        return System.getProperty("platform");
    }

    @Override
    public String getDeviceId() {
        return System.getProperty("device-id");
    }

    @Override
    public String getMobileConfig() {
        return System.getProperty("mobile-config");
    }

    @Override
    public String getCredentialsFilePath() {
        return this.credentialsFilePath;
    }

    @Override
    public String getQS() {
        return System.getProperty("qs");
    }

    @Override
    public String getAppiumIp() {
        return System.getProperty("appium-ip");
    }

    @Override
    public String getDeviceName() {
        return System.getProperty("device-name");
    }

//    @Override
//    public Boolean isScreenshotEnabled() {
//        if (enableScreenshots.equalsIgnoreCase("true")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
