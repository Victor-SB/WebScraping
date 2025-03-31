package com.intuitive.webscraping.service;

import com.intuitive.webscraping.utils.WebDriverUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebScraperService {

    private static final Logger logger = LoggerFactory.getLogger(WebScraperService.class);

    private final WebDriverUtils webDriverUtils;

    @Value("${webscraper.xpath.pdfLinks}")
    private String pdfLinksXPath;

    /**
     * Extrai os links dos arquivos PDF de um site do governo.
     * @param urlGoverno URL da página onde os PDFs estão listados.
     * @return Lista de URLs dos PDFs encontrados.
     */
    public List<String> extractPdfLinks(String urlGoverno) {
        logger.info("🔍 Iniciando extração de PDFs em: {}", urlGoverno);
        WebDriver driver = webDriverUtils.createWebDriver();
        List<String> pdfLinks = new ArrayList<>();

        try {
            driver.get(urlGoverno);
            pdfLinks = obterLinksDosPdfs(driver);
        } catch (Exception e) {
            logger.error("❌ Erro ao extrair PDFs de {}: {}", urlGoverno, e.getMessage(), e);
        } finally {
            driver.quit();
        }

        logger.info("✅ Extração concluída! {} PDFs encontrados.", pdfLinks.size());
        return pdfLinks;
    }
    /**
     * Obtém os links dos PDFs a partir dos elementos encontrados no DOM.
     * @param driver WebDriver para interação com a página.
     * @return Lista de URLs dos PDFs.
     */
    private List<String> obterLinksDosPdfs(WebDriver driver) {
        List<String> pdfLinks = new ArrayList<>();
        try {
            List<WebElement> links = driver.findElements(By.xpath(pdfLinksXPath));
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                if (href != null && href.endsWith(".pdf")) {
                    pdfLinks.add(href);
                }
            }
            logger.info("📄 {} links de PDF encontrados na página.", pdfLinks.size());
        } catch (Exception e) {
            logger.error("⚠️ Erro ao buscar os links dos PDFs: {}", e.getMessage(), e);
        }
        return pdfLinks;
    }
}