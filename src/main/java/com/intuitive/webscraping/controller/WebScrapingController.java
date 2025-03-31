package com.intuitive.webscraping.controller;

import com.intuitive.webscraping.service.CompressFileService;
import com.intuitive.webscraping.service.PdfDownloadService;
import com.intuitive.webscraping.service.PdfReaderService;
import com.intuitive.webscraping.service.WebScraperService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebScrapingController {

    private static final Logger logger = LoggerFactory.getLogger(WebScrapingController.class);

    @Value("${governo-url}")
    private String urlGoverno;

    private final WebScraperService webScraperService;
    private final PdfDownloadService pdfDownloadService;
    private final CompressFileService compressFileService;
    private final PdfReaderService pdfReaderService;

    @PostConstruct
    public void init() throws IOException {
        logger.info("Iniciando Web Scraping para URL: {}", urlGoverno);

        List<String> pdfUrls = webScraperService.extractPdfLinks(urlGoverno);
        if (pdfUrls.isEmpty()) {
            logger.warn("❌ Nenhum PDF encontrado.");
            return;
        }

        File downloadDir = new File("pdfs-baixados");
        criarDiretorio(downloadDir);

        baixarPdfs(pdfUrls, downloadDir);

        File zipFile = new File("pdfs-baixados/Anexos.zip");
        compressFileService.zipFiles(downloadDir.listFiles(), zipFile.getAbsolutePath());
        logger.info("Arquivos compactados em: {}", zipFile.getAbsolutePath());

        pdfReaderService.readPdf();
    }

    private void criarDiretorio(File directory) {
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (created) {
                logger.info("Diretório criado: {}", directory.getAbsolutePath());
            } else {
                logger.error("❌ Falha ao criar diretório: {}", directory.getAbsolutePath());
            }
        }
    }

    private void baixarPdfs(List<String> pdfUrls, File downloadDir) {
        for (String pdfUrl : pdfUrls) {
            String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);
            String filePath = downloadDir.getAbsolutePath() + File.separator + fileName;

            try {
                pdfDownloadService.downloadPDF(pdfUrl, filePath);
                logger.info("✅ Download concluído: {}", filePath);
            } catch (Exception e) {
                logger.error("❌ Falha no download do PDF: {}", pdfUrl, e);
            }
        }
    }
}