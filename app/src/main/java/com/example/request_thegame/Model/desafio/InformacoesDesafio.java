package com.example.request_thegame.Model.desafio;

import java.io.Serializable;

public class InformacoesDesafio implements Serializable {

    private String nomeDesafiado;
    private String dataInicio;
    private String horaInicio;

    public InformacoesDesafio(){}

    public InformacoesDesafio(String nomeDesafiado, String dataInicio, String horaInicio) {
        this.nomeDesafiado = nomeDesafiado;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
    }

    public String getNomeDesafiado() {
        return nomeDesafiado;
    }

    public void setNomeDesafiado(String nomeDesafiado) {
        this.nomeDesafiado = nomeDesafiado;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
}
