package com.example.request_thegame.Model.desafio;

import com.example.request_thegame.Model.Perguntas;

import java.io.Serializable;
import java.util.List;

public class PrimeiroDesafio implements Serializable{
    private List<Perguntas> perguntas;
    private int tempoParaRealizacao;
    private String dataInicio;
    private String horaInicio;

    public PrimeiroDesafio() {
    }

    public PrimeiroDesafio(List<Perguntas> perguntas, int tempoParaRealizacao, String dataInicio, String horaInicio) {
        this.perguntas = perguntas;
        this.tempoParaRealizacao = tempoParaRealizacao;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
    }

    public List<Perguntas> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Perguntas> perguntas) {
        this.perguntas = perguntas;
    }

    public int getTempoParaRealizacao() {
        return tempoParaRealizacao;
    }

    public void setTempoParaRealizacao(int tempoParaRealizacao) {
        this.tempoParaRealizacao = tempoParaRealizacao;
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