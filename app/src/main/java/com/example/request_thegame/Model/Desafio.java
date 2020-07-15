package com.example.request_thegame.Model;

import com.example.request_thegame.Model.desafio.InformacoesDesafio;
import com.example.request_thegame.Model.desafio.MensagemFinal;
import com.example.request_thegame.Model.desafio.PrimeiroDesafio;
import com.example.request_thegame.Model.desafio.QuartoDesafio;
import com.example.request_thegame.Model.desafio.SegundoDesafio;
import com.example.request_thegame.Model.desafio.StatusDesafio;
import com.example.request_thegame.Model.desafio.TerceiroDesafio;

import java.io.Serializable;

public class Desafio implements Serializable {

    private String codigoDesafio;
    private InformacoesDesafio informacoesDesafio;
    private StatusDesafio statusDesafios;
    private PrimeiroDesafio primeiroDesafio;
    private SegundoDesafio segundoDesafio;
    private TerceiroDesafio terceiroDesafio;
    private QuartoDesafio quartoDesafio;
    private MensagemFinal mensagemFinal;

    public Desafio() {
    }

    public Desafio(String codigoDesafio, InformacoesDesafio informacoesDesafio, StatusDesafio statusDesafios, PrimeiroDesafio primeiroDesafio, SegundoDesafio segundoDesafio, TerceiroDesafio terceiroDesafio, QuartoDesafio quartoDesafio, MensagemFinal mensagemFinal) {
        this.codigoDesafio = codigoDesafio;
        this.informacoesDesafio = informacoesDesafio;
        this.statusDesafios = statusDesafios;
        this.primeiroDesafio = primeiroDesafio;
        this.segundoDesafio = segundoDesafio;
        this.terceiroDesafio = terceiroDesafio;
        this.quartoDesafio = quartoDesafio;
        this.mensagemFinal = mensagemFinal;
    }

    public String getCodigoDesafio() {
        return codigoDesafio;
    }

    public void setCodigoDesafio(String codigoDesafio) {
        this.codigoDesafio = codigoDesafio;
    }

    public InformacoesDesafio getInformacoesDesafio() {
        return informacoesDesafio;
    }

    public void setInformacoesDesafio(InformacoesDesafio informacoesDesafio) {
        this.informacoesDesafio = informacoesDesafio;
    }

    public StatusDesafio getStatusDesafios() {
        return statusDesafios;
    }

    public void setStatusDesafios(StatusDesafio statusDesafios) {
        this.statusDesafios = statusDesafios;
    }

    public PrimeiroDesafio getPrimeiroDesafio() {
        return primeiroDesafio;
    }

    public void setPrimeiroDesafio(PrimeiroDesafio primeiroDesafio) {
        this.primeiroDesafio = primeiroDesafio;
    }

    public SegundoDesafio getSegundoDesafio() {
        return segundoDesafio;
    }

    public void setSegundoDesafio(SegundoDesafio segundoDesafio) {
        this.segundoDesafio = segundoDesafio;
    }

    public TerceiroDesafio getTerceiroDesafio() {
        return terceiroDesafio;
    }

    public void setTerceiroDesafio(TerceiroDesafio terceiroDesafio) {
        this.terceiroDesafio = terceiroDesafio;
    }

    public QuartoDesafio getQuartoDesafio() {
        return quartoDesafio;
    }

    public void setQuartoDesafio(QuartoDesafio quartoDesafio) {
        this.quartoDesafio = quartoDesafio;
    }

    public MensagemFinal getMensagemFinal() {
        return mensagemFinal;
    }

    public void setMensagemFinal(MensagemFinal mensagemFinal) {
        this.mensagemFinal = mensagemFinal;
    }
}
