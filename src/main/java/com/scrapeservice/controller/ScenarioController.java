package com.scrapeservice.controller;

import com.scrapeservice.model.Greeting;
import com.scrapeservice.page.pageobjects.impl.TravelHomePage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ScenarioController {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    TravelHomePage page;

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/auto")
    public void auto() {
        logger.info("Starting webdriver instance...");
        page.setWebDriver();
        logger.info("Navigating to page...");
        page.navigateToThisPage();
        logger.info("Initializing this page...");
        page.initializePage();
        logger.info("Stopping webdriver instance..");
        page.getWebDriver().quit();
    }

}
