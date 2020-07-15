package com.example.request_thegame.Model.desafio;

import java.io.Serializable;

public class TerceiroDesafio implements Serializable{

    private String horaInicio;
    private String dataInicio;
    private String charada;
    private String resposta;
    private String localizacao;

    public TerceiroDesafio() {
    }

    public TerceiroDesafio(String dataInicio, String horaInicio, String charada, String resposta, String localizacao) {
        this.horaInicio = horaInicio;
        this.dataInicio = dataInicio;
        this.charada = charada;
        this.resposta = resposta;
        this.localizacao = localizacao;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getCharada() {
        return charada;
    }

    public void setCharada(String charada) {
        this.charada = charada;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}