package com.intuitive.webscraping.utils;

import com.intuitive.webscraping.model.Procedimento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtils {

    private static final Logger logger = LoggerFactory.getLogger(CsvUtils.class);
    private static final String CSV_HEADER = "Código,Descrição,Valor";

    private CsvUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void saveProcedimentosToCsv(List<Procedimento> procedimentos, String csvFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Procedimento procedimento : procedimentos) {
                writer.write(formatProcedimentoAsCsv(procedimento));
                writer.newLine();
            }

            logger.info("CSV gerado com sucesso em: {}", csvFilePath);
        } catch (IOException e) {
            logger.error("Erro ao salvar o CSV: {}", e.getMessage(), e);
        }
    }

    private static String formatProcedimentoAsCsv(Procedimento procedimento) {
        return new StringBuilder()
                .append(procedimento.getCodigo()).append(",")
                .append(procedimento.getDescricao()).append(",")
                .append(procedimento.getValor())
                .toString();
    }
}