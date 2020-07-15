package com.example.request_thegame.Model.desafio;

import java.io.Serializable;

public class StatusDesafio implements Serializable{

    private String statusDesafio;
    private String statusDesafioUm;
    private String statusDesafioDois;
    private String statusDesafioTres;
    private String statusDesafioQuatro;

    public StatusDesafio() {
    }

    public StatusDesafio(String statusDesafio, String statusDesafioUm, String statusDesafioDois, String statusDesafioTres, String statusDesafioQuatro) {
        this.statusDesafio = statusDesafio;
        this.statusDesafioUm = statusDesafioUm;
        this.statusDesafioDois = statusDesafioDois;
        this.statusDesafioTres = statusDesafioTres;
        this.statusDesafioQuatro = statusDesafioQuatro;
    }

    public String getStatusDesafio() {
        return statusDesafio;
    }

    public void setStatusDesafio(String statusDesafio) {
        this.statusDesafio = statusDesafio;
    }

    public String getStatusDesafioUm() {
        return statusDesafioUm;
    }

    public void setStatusDesafioUm(String statusDesafioUm) {
        this.statusDesafioUm = statusDesafioUm;
    }

    public String getStatusDesafioDois() {
        return statusDesafioDois;
    }

    public void setStatusDesafioDois(String statusDesafioDois) {
        this.statusDesafioDois = statusDesafioDois;
    }

    public String getStatusDesafioTres() {
        return statusDesafioTres;
    }

    public void setStatusDesafioTres(String statusDesafioTres) {
        this.statusDesafioTres = statusDesafioTres;
    }

    public String getStatusDesafioQuatro() {
        return statusDesafioQuatro;
    }

    public void setStatusDesafioQuatro(String statusDesafioQuatro) {
        this.statusDesafioQuatro = statusDesafioQuatro;
    }
}
