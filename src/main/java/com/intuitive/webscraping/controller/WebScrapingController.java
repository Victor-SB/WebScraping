package com.intuitive.webscraping.controller;

import com.intuitive.webscraping.service.CompressFileService;
import com.intuitive.webscraping.service.PdfDownloadService;
import com.intuitive.webscraping.service.PdfReaderService;
import com.intuitive.webscraping.service.WebScraperService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebScrapingController {

    @Value("${governo-url}")
    private String urlGoverno;

    private final WebScraperService webScraperService;

    private final PdfDownloadService pdfDownloadService;

    private final CompressFileService compressFileService;

    private final PdfReaderService pdfReaderService;

    @PostConstruct
    public void init() {
//        List<String> pdfUrls = webScraperService.extractPdfLinks(urlGoverno);
//
//        if (pdfUrls.isEmpty()) {
//            System.out.println("❌ Nenhum PDF encontrado.");
//            return;
//        }
//        File downloadDir = new File("pdfs-baixados");
//        if (!downloadDir.exists())
//            downloadDir.mkdir();
//
//        for (String pdfUrl : pdfUrls) {
//            String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);
//            pdfDownloadService.downloadPDF(pdfUrl, "pdfs-baixados/" + fileName);
//        }
//
//        compressFileService.zipFiles(downloadDir.listFiles(), "pdfs-baixados/Anexos.zip");

        pdfReaderService.readPdf();
    }
}
