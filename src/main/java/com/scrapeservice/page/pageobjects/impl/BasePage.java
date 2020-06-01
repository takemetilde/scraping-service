package com.scrapeservice.page.pageobjects.impl;

import com.scrapeservice.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Component
public class BasePage {

    private static final int WAIT_TIMEOUT = 10;

    @Autowired
    public WebDriverConfig config;

    public WebDriver webDriver;

    private URI uri;

    public static void handledSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setWebDriver() {
        webDriver = config.getWebDriver();
    }

    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    protected WebElement findElement(By selector) {
        return findElement(ExpectedConditions.presenceOfElementLocated(selector));
    }

    protected WebElement findElement(WebElement selector) {
        return findElement(ExpectedConditions.visibilityOf(selector));
    }

    private WebElement findElement(ExpectedCondition<WebElement> expectedCondition) {
        return getWebDriverWait().until(expectedCondition);
    }

    protected WebDriverWait getWebDriverWait() {
        return new WebDriverWait(webDriver, WAIT_TIMEOUT);
    }

    public void navigateToPage(URI url) {
        navigateToPage(url.toString());
    }

    public void navigateToPage(String url) {
        webDriver.get(url);
        getWebDriverWait().until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete';"));
    }

    public Boolean isElementDisplayed(By selector) {
        return findElement(selector).isDisplayed();
    }

    public String getElementText(By selector) {
        return StringUtils.trimWhitespace(findElement(selector).getText());
    }

    public Boolean isClickable(WebElement element) {
        try {
            findElement(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectButtonByText(WebElement button, String buttonText) {
        assertTrue("Button is not clickable: " + button.getTagName(), isClickable(button));
        assertEquals("Button text is incorrect:" + buttonText, buttonText, button.getAttribute("value"));
        button.click();
    }

    public void selectItemInDropDown(WebElement dropDownElement, String itemToSelect) {
        Select select = new Select(dropDownElement);
        List<WebElement> allOptions = select.getOptions();
        List<WebElement> match =
                allOptions.stream().filter(o -> o.getText().equals(itemToSelect)).collect(
                        Collectors.toList());
        assertFalse(String.format("There was no %s option in the dropdown.", itemToSelect), match.isEmpty());
        assertEquals("There was more than one option to select in the dropdown.", match.size(), 1);
        match.get(0).click();
    }

}
