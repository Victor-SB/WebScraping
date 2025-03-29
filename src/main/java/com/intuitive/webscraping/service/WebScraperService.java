package com.intuitive.webscraping.service;

import com.intuitive.webscraping.utils.WebDriverUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebScraperService {

    private final WebDriverUtils webDriverUtils;

    public List<String> extractPdfLinks(String urlGoverno) {
        WebDriver driver = webDriverUtils.createWebDriver();
        List<String> pdfLinks = new ArrayList<>();

        try {
            driver.get(urlGoverno);
            System.out.println("🔍 Acessando o site...");
            // Localiza os elementos que contêm os links dos PDFs
            List<WebElement> links = driver.findElements(
                    By.xpath("//*[@id='cfec435d-6921-461f-b85a-b425bc3cb4a5']/div/ol/li[position()<=2]/a[1]"));

            for (WebElement link : links) {
                pdfLinks.add(link.getAttribute("href"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return pdfLinks;
    }
}
