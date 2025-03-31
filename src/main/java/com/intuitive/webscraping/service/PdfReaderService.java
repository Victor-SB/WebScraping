package com.intuitive.webscraping.service;

import com.intuitive.webscraping.model.Procedimento;
import com.intuitive.webscraping.utils.CsvUtils;
import com.intuitive.webscraping.utils.PdfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class PdfReaderService {

    private static final Logger logger = LoggerFactory.getLogger(PdfReaderService.class);
    private static final String DOWNLOAD_DIR = "pdfs-baixados";
    private static final String PDF_FILE_NAME = "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
    private static final String CSV_FILE_NAME = "Rol_de_Procedimentos.csv";
    private static final String ZIP_FILE_NAME = "Procedimentos.zip";

    private final CompressFileService compressFileService;

    public PdfReaderService(CompressFileService compressFileService) {
        this.compressFileService = compressFileService;
    }

    /**
     * Lê o PDF, extrai os procedimentos, salva em CSV e compacta.
     */
    public void readPdf() throws IOException {
        File pdfFile = new File(DOWNLOAD_DIR, PDF_FILE_NAME);
        if (!validarArquivo(pdfFile)) return;

        // Extração dos dados do PDF
        List<Procedimento> procedimentos = PdfUtils.extractProcedimentos(pdfFile);
        Path csvFilePath = Path.of(DOWNLOAD_DIR, CSV_FILE_NAME);

        // Salvando os dados extraídos no formato CSV
        CsvUtils.saveProcedimentosToCsv(procedimentos, csvFilePath.toString());

        // Compactando o CSV
        compactarCsv(csvFilePath);
    }

    /**
     * Verifica se o arquivo PDF existe antes de processar.
     * @param file Arquivo a ser validado.
     * @return true se o arquivo existir, false caso contrário.
     */
    private boolean validarArquivo(File file) {
        if (!file.exists()) {
            logger.error("❌ Arquivo não encontrado: {}", file.getAbsolutePath());
            return false;
        }
        return true;
    }

    /**
     * Compacta o arquivo CSV gerado.
     * @param csvFilePath Caminho do arquivo CSV.
     */
    private void compactarCsv(Path csvFilePath) {
        try {
            compressFileService.compactarCSV(csvFilePath.toString(), DOWNLOAD_DIR);
            logger.info("📂 CSV compactado com sucesso: {}", Path.of(DOWNLOAD_DIR, ZIP_FILE_NAME));
        } catch (Exception e) {
            logger.error("❌ Erro ao compactar o CSV", e);
        }
    }
}