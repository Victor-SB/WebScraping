package com.intuitive.webscraping.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;

@Service
public class CompressFileService {

    private static final Logger logger = LoggerFactory.getLogger(CompressFileService.class);

    public void zipFiles(File[] files, String zipFilePath) {
        if (files == null || files.length == 0) {
            logger.warn("Nenhum arquivo disponível para compactação.");
            return;
        }

        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(new FileOutputStream(zipFilePath))) {
            for (File file : files) {
                if (file.isFile()) {
                    adicionarArquivoAoZip(file, zipOut);
                }
            }
            logger.info("🗜️ Compactação concluída: {}", zipFilePath);
        } catch (IOException e) {
            logger.error("❌ Erro ao compactar arquivos em: {}", zipFilePath, e);
        }
    }

    public void compactarCSV(String csvFilePath, String downloadDir) {
        Path zipFilePath = Path.of(downloadDir, "Teste_VictorHugo.zip");

        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(new FileOutputStream(zipFilePath.toFile()));
             FileInputStream fis = new FileInputStream(csvFilePath)) {

            ZipArchiveEntry zipEntry = new ZipArchiveEntry(new File(csvFilePath).getName());
            zipOut.putArchiveEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }

            zipOut.closeArchiveEntry();
            logger.info("📂 CSV compactado em: {}", zipFilePath);
        } catch (IOException e) {
            logger.error("❌ Erro ao compactar CSV em: {}", zipFilePath, e);
        }
    }

    private void adicionarArquivoAoZip(File file, ZipArchiveOutputStream zipOut) {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipArchiveEntry zipEntry = new ZipArchiveEntry(file.getName());
            zipOut.putArchiveEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }

            zipOut.closeArchiveEntry();
            logger.info("✅ Arquivo adicionado ao ZIP: {}", file.getName());
        } catch (IOException e) {
            logger.error("❌ Erro ao adicionar arquivo {} ao ZIP", file.getName(), e);
        }
    }
}