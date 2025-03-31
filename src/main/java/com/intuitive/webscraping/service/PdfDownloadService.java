package com.intuitive.webscraping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class PdfDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(PdfDownloadService.class);
    private static final int BUFFER_SIZE = 1024;

    public void downloadPDF(String fileURL, String savePath) {
        if (!validarURL(fileURL)) {
            logger.error("❌ URL inválida: {}", fileURL);
            return;
        }

        criarDiretorioSeNecessario(savePath);

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileURL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {

            byte[] dataBuffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, BUFFER_SIZE)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            logger.info("✅ PDF baixado com sucesso: {}", savePath);
        } catch (IOException e) {
            logger.error("❌ Erro ao baixar o PDF de {}", fileURL, e);
        }
    }

    private boolean validarURL(String fileURL) {
        try {
            new URL(fileURL);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Garante que o diretório de destino exista antes de salvar o arquivo.
     */
    private void criarDiretorioSeNecessario(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (created) {
                logger.info("📂 Diretório criado: {}", parentDir.getAbsolutePath());
            }
        }
    }
}