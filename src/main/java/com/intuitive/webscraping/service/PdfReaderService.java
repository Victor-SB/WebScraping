package com.intuitive.webscraping.service;

import com.intuitive.webscraping.model.Procedimento;
import com.intuitive.webscraping.utils.CsvUtils;
import com.intuitive.webscraping.utils.PdfUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PdfReaderService {

    public void readPdf() {
        String downloadDir = "pdfs-baixados"; // Mesmo caminho do WebScraping

        try {
            File anexo1 = new File(downloadDir + "/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
            if (!anexo1.exists()) {
                throw new IOException("Arquivo Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf não encontrado em " + downloadDir);
            }

            // Extração dos dados do PDF
            List<Procedimento> procedimentos = PdfUtils.extractProcedimentos(anexo1);
            String csvFilePath = downloadDir + "/Rol_de_Procedimentos.csv";

            // Salvando os dados extraídos no formato CSV
            CsvUtils.saveProcedimentosToCsv(procedimentos, csvFilePath);

            // Compactando o CSV
            CompressFileService zipService = new CompressFileService();
            zipService.compactarCSV(csvFilePath, downloadDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
