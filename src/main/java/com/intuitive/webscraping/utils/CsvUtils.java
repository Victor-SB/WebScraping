package com.intuitive.webscraping.utils;

import com.intuitive.webscraping.model.Procedimento;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtils {

    // Método para salvar uma lista de procedimentos em formato CSV
    public static void saveProcedimentosToCsv(List<Procedimento> procedimentos, String csvFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            // Escreve o cabeçalho do CSV
            writer.write("Código,Descrição,Valor\n");

            // Escreve os dados dos procedimentos
            for (Procedimento procedimento : procedimentos) {
                writer.write(procedimento.getCodigo() + "," +
                        procedimento.getDescricao() + "," +
                        procedimento.getValor() + "\n");
            }
            System.out.println("CSV gerado com sucesso em: " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
