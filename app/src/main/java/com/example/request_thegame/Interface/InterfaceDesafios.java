package com.example.request_thegame.Interface;

import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.desafio.InformacoesDesafio;
import com.example.request_thegame.Model.desafio.MensagemFinal;
import com.example.request_thegame.Model.desafio.PrimeiroDesafio;
import com.example.request_thegame.Model.desafio.QuartoDesafio;
import com.example.request_thegame.Model.desafio.SegundoDesafio;
import com.example.request_thegame.Model.desafio.StatusDesafio;
import com.example.request_thegame.Model.desafio.TerceiroDesafio;

public interface InterfaceDesafios {
    void setIdDesafio(String idDesafio);

    void setInformacoesDeasafio(InformacoesDesafio informacoesDeasafio);

    void setStatusDesafio(StatusDesafio statusDesafio);

    void setPrimeiroDesafio(PrimeiroDesafio primeiroDesafio);

    void setSegundoDesafio(SegundoDesafio segundoDesafio);

    void setTerceiroDesafio(TerceiroDesafio terceiroDesafio);

    void setQuartoDesafio(QuartoDesafio quartoDesafio);

    void setMensagemFinal(MensagemFinal mensagemFinal);

    Desafio getDesafio();


}
