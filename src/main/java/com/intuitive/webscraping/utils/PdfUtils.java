package com.intuitive.webscraping.utils;

import com.intuitive.webscraping.model.Procedimento;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfUtils {

    // Método para extrair os procedimentos do PDF
    public static List<Procedimento> extractProcedimentos(File pdfFile) throws IOException {
        List<Procedimento> procedimentos = new ArrayList<>();

        // Carrega o documento PDF
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();

        // Extrai todo o texto do PDF
        String text = stripper.getText(document);
        document.close();

        // Processa o texto extraído para identificar os procedimentos
        procedimentos = processarTextoParaProcedimentos(text);

        return procedimentos;
    }

    // Método auxiliar para processar o texto extraído do PDF e converter em uma lista de procedimentos
    private static List<Procedimento> processarTextoParaProcedimentos(String text) {
        List<Procedimento> procedimentos = new ArrayList<>();

        // Lógica de processamento do texto (exemplo de extração de dados)
        // Esse código depende do formato do PDF e da tabela a ser extraída

        // Exemplo fictício de como os dados poderiam ser extraídos:
        // Aqui, você faria a lógica necessária para extrair os dados da tabela
        // e criar objetos Procedimento. Vamos supor que o formato da tabela seja:
        // "Código | Descrição | Valor"

        String[] lines = text.split("\n");

        for (String line : lines) {
            // Suponhamos que cada linha tenha o formato: Código, Descrição, Valor
            String[] parts = line.split("\\|");
            if (parts.length == 3) {
                String codigo = parts[0].trim();
                String descricao = parts[1].trim();
                double valor = Double.parseDouble(parts[2].trim());

                procedimentos.add(new Procedimento(codigo, descricao, valor));
            }
        }

        return procedimentos;
    }
}
