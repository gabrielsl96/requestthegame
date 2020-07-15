package com.example.request_thegame.Model.desafio;

import java.io.Serializable;

public class MensagemFinal implements Serializable{

    private String primeiraParte;
    private String segundaParte;
    private String terceiraParte;
    private String quartaParte;
    private String tipoMensagem;

    public MensagemFinal() {
    }

    public MensagemFinal(String primeiraParte, String segundaParte, String terceiraParte, String quartaParte, String tipoMensagem) {
        this.primeiraParte = primeiraParte;
        this.segundaParte = segundaParte;
        this.terceiraParte = terceiraParte;
        this.quartaParte = quartaParte;
        this.tipoMensagem=tipoMensagem;
    }

    public String getPrimeiraParte() {
        return primeiraParte;
    }

    public String getSegundaParte() {
        return segundaParte;
    }

    public String getTerceiraParte() {
        return terceiraParte;
    }

    public String getQuartaParte() {
        return quartaParte;
    }

    public String getTipoMensagem() {
        return tipoMensagem;
    }
}