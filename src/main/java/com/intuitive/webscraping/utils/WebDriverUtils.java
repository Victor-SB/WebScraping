package com.intuitive.webscraping.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class WebDriverUtils {


    public WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage",
                "--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.addArguments("window-size=800,600");
        options.addArguments(
                "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) Applewebkit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

        return new ChromeDriver(options);
    }
}
