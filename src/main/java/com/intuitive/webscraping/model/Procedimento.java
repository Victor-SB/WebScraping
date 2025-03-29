package com.intuitive.webscraping.model;

public class Procedimento {

    private String codigo;  // Código único do procedimento
    private String descricao;  // Descrição do procedimento
    private double valor;  // Valor associado ao procedimento

    // Construtor
    public Procedimento(String codigo, String descricao, double valor) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}