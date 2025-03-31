package com.intuitive.webscraping.utils;

import com.intuitive.webscraping.model.Procedimento;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfUtils {

    private static final Logger logger = LogManager.getLogger(PdfUtils.class);

    private PdfUtils() {
        // Evitar instância da classe
    }

    // Método para extrair os procedimentos do PDF
    public static List<Procedimento> extractProcedimentos(File pdfFile) {
        if (pdfFile == null || !pdfFile.exists()) {
            logger.error("Arquivo PDF inválido ou não encontrado: {}", pdfFile);
            return new ArrayList<>();
        }

        List<Procedimento> procedimentos = new ArrayList<>();

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();

            // Extrai todo o texto do PDF
            String text = stripper.getText(document);

            // Processa o texto extraído para identificar os procedimentos
            procedimentos = processarTextoParaProcedimentos(text);
        } catch (IOException e) {
            logger.error("Erro ao carregar ou processar o PDF: {}", pdfFile, e);
        }

        return procedimentos;
    }

    // Método auxiliar para processar o texto extraído do PDF e converter em uma lista de procedimentos
    private static List<Procedimento> processarTextoParaProcedimentos(String text) {
        List<Procedimento> procedimentos = new ArrayList<>();

        // Lógica de processamento do texto (exemplo de extração de dados)
        String[] lines = text.split("\n");

        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length == 3) {
                try {
                    String codigo = parts[0].trim();
                    String descricao = parts[1].trim();
                    double valor = Double.parseDouble(parts[2].trim());

                    procedimentos.add(new Procedimento(codigo, descricao, valor));
                } catch (NumberFormatException e) {
                    logger.warn("Falha ao processar a linha, valor inválido: {}", line);
                }
            }
        }

        return procedimentos;
    }
}