package com.scrapeservice.page.pageobjects.impl;

import com.scrapeservice.page.PageInitialization;
import com.scrapeservice.page.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@PageObject
public class TravelHomePage extends BasePage implements PageInitialization {

    private String url = "http://www.google.com";

    @FindBy(name = "fromPort")
    private WebElement departureCityDropDown;

    @FindBy(name = "toPort")
    private WebElement destinationCityDropDown;

    @FindBy(className = "btn-primary")
    private WebElement findFlightsButton;

    @Override
    public void initializePage() {
        PageFactory.initElements(webDriver, this);
    }

    public void navigateToThisPage() {
        navigateToPage(this.url);
    }

    public WebElement getDepartureCityDropDown() {
        return departureCityDropDown;
    }

    public WebElement getDestinationCityDropDown() {
        return destinationCityDropDown;
    }

    public WebElement getFindFlightsButton() {
        return findFlightsButton;
    }

}
