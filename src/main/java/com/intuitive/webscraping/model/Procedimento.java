package com.intuitive.webscraping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedimento {

    private String codigo;       // Código único do procedimento
    private String descricao;    // Descrição do procedimento
    private double valor;        // Valor associado ao procedimento
}