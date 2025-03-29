package com.intuitive.webscraping.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class CompressFileService {

    public void zipFiles(File[] files, String zipFilePath) {
        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(new FileOutputStream(zipFilePath))) {
            for (File file : files) {
                if (!file.isFile()) continue;

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipArchiveEntry zipEntry = new ZipArchiveEntry(file.getName());
                    zipOut.putArchiveEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, len);
                    }

                    zipOut.closeArchiveEntry();
                }
            }
            System.out.println("🗜️ Compactação concluída!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao compactar arquivos.");
            e.printStackTrace();
        }
    }

    // Método para compactar um arquivo CSV específico
    public void compactarCSV(String csvFilePath, String downloadDir) {
        try (FileInputStream fis = new FileInputStream(csvFilePath);
             FileOutputStream fos = new FileOutputStream(downloadDir + "/Teste_VictorHugo.zip");
             ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(fos)) {

            // Cria uma entrada no ZIP para o arquivo CSV
            ZipArchiveEntry zipEntry = new ZipArchiveEntry(new File(csvFilePath).getName());
            zipOut.putArchiveEntry(zipEntry);

            // Escreve o conteúdo do CSV no arquivo ZIP
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }

            zipOut.closeArchiveEntry();  // Fecha a entrada do arquivo CSV

            zipOut.finish();  // Finaliza o arquivo ZIP

            System.out.println("CSV compactado e salvo em: " + downloadDir + "/Teste_VictorHugo.zip");
        } catch (IOException e) {
            System.out.println("❌ Erro ao compactar CSV.");
            e.printStackTrace();
        }
    }
}
