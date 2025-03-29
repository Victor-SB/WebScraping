package com.intuitive.webscraping.service;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Component
public class PdfDownloadService {

    public void downloadPDF(String fileURL, String savePath) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            System.out.println("✅ Baixado: " + savePath);
        } catch (IOException e) {
            System.out.println("❌ Erro ao baixar " + fileURL);
            e.printStackTrace();
        }
    }
}
