package com.scrapeservice;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class WebDriverConfig {

    public enum BROWSER_VERSION {
        AUTO("auto");

        public final String value;

        BROWSER_VERSION(String value) {
            this.value = value;
        }
    }

    enum BROWSER_TYPE {
        BROWSERSTACK("BrowserStack");

        public final String value;

        BROWSER_TYPE(String value) {
            this.value = value;
        }
    }

    @Value("${ui.webdriver.browser.type}")
    String browserType;

    @Value("${ui.webdriver.remote}")
    String remote;

    @Value("${ui.webdriver.browser.version}")
    String browserVersion;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    public WebDriver getWebDriver() {
        logger.info(String.format("Setting up %s browser with version: %s", browserType, browserVersion));
        switch (browserType) {
            case BrowserType.CHROME:
                chromeDriverSetup();
                return new ChromeDriver();
            case BrowserType.FIREFOX:
                firefoxDriverSetup();
                return new FirefoxDriver();
            default:
                Assert.fail("No webdriver instance created.");
                return null;
        }
    }

    private void chromeDriverSetup() {
        if (browserVersion.equals(BROWSER_VERSION.AUTO)) {
            WebDriverManager.chromedriver().setup();
        } else {
            WebDriverManager.chromedriver().version(browserVersion).setup();
        }
    }

    private void firefoxDriverSetup() {
        if (browserVersion.equals(BROWSER_VERSION.AUTO)) {
            WebDriverManager.firefoxdriver().setup();
        } else {
            WebDriverManager.firefoxdriver().version(browserVersion).setup();
        }
    }

    private void browserStackSetup() {
        if (browserVersion.equals(BROWSER_TYPE.BROWSERSTACK)) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
        }
    }

}
