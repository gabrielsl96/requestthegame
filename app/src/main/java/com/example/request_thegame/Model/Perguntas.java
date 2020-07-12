package com.example.request_thegame.Model;

import java.util.ArrayList;
import java.util.List;

public class Perguntas {
    private String pergunta;
    private List<String> listRespostas=new ArrayList<>();
    private int respostaCerta;

    public Perguntas(String pergunta, String respotaUm,String respostaDois,String respostaTres,String respostaQuatro, int respostaCerta) {
        this.pergunta = pergunta;
        this.listRespostas.add(respotaUm);
        this.listRespostas.add(respostaDois);
        this.listRespostas.add(respostaTres);
        this.listRespostas.add(respostaQuatro);
        this.respostaCerta = respostaCerta;
    }
    public String getPergunta(){
        return this.pergunta;
    }

    public List<String> getRespostas(){
        return this.listRespostas;
    }
    public int getRespostaCerta(){
        return this.respostaCerta;
    }
}
