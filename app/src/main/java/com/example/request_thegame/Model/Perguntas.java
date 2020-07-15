package com.example.request_thegame.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Perguntas implements Serializable {

    private String pergunta;
    private List<String> respostas =new ArrayList<>();
    private int respostaCerta;

    public Perguntas() {
    }

    public Perguntas(String pergunta, String respotaUm, String respostaDois, String respostaTres, String respostaQuatro, int respostaCerta) {
        this.pergunta = pergunta;
        this.respostas.add(respotaUm);
        this.respostas.add(respostaDois);
        this.respostas.add(respostaTres);
        this.respostas.add(respostaQuatro);
        this.respostaCerta = respostaCerta;
    }
    public String getPergunta(){
        return this.pergunta;
    }

    public List<String> getRespostas(){
        return this.respostas;
    }
    public int getRespostaCerta(){
        return this.respostaCerta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }


    public void setRespostas(List<String> respostas) {
        this.respostas = respostas;
    }

    public void setRespostaCerta(int respostaCerta) {
        this.respostaCerta = respostaCerta;
    }
}