package com.example.request_thegame.Frag.criar_desafios;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.request_thegame.Adapter.PerguntasAdapter;
import com.example.request_thegame.Helper.DatePickerFragment;
import com.example.request_thegame.Helper.TimePickerFragment;
import com.example.request_thegame.Interface.InterfaceDesafios;
import com.example.request_thegame.Model.Perguntas;
import com.example.request_thegame.Model.desafio.PrimeiroDesafio;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CriarPrimeiroDesafioFragment extends Fragment {
    private TextInputEditText inputTempo, inputDataInicio, inputHoraInicio, inputTituloPergunta, inputRespostaA, inputRespostaB, inputRespostaC, inputRespostaD;
    private RadioButton radioA, radioB, radioC, radioD;
    private Button btnAdicionar, btnProximo;
    private RecyclerView recyclerView;
    private PerguntasAdapter perguntasAdapter;
    private List<Perguntas> perguntas = new ArrayList<>();
    private InterfaceDesafios interfaceDesafios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_primeiro_desafio, container, false);

        //Setando componentes da tela
        inputTempo = view.findViewById(R.id.input_tempo_tarefa);
        inputDataInicio=view.findViewById(R.id.input_data_inicio);
        inputHoraInicio=view.findViewById(R.id.input_hora_inicio);
        inputTituloPergunta = view.findViewById(R.id.input_titulo_pergunta);
        inputRespostaA = view.findViewById(R.id.input_respostaA);
        inputRespostaB=view.findViewById(R.id.input_respostaB);
        inputRespostaC=view.findViewById(R.id.input_respostaC);
        inputRespostaD=view.findViewById(R.id.input_respostaD);
        radioA=view.findViewById(R.id.radio_A);
        radioB=view.findViewById(R.id.radio_B);
        radioC=view.findViewById(R.id.radio_C);
        radioD=view.findViewById(R.id.radio_D);
        btnAdicionar=view.findViewById(R.id.btn_adcionar);
        btnProximo=view.findViewById(R.id.btn_proximo);
        recyclerView =view.findViewById(R.id.recyclerViewPerguntas);

        //Setando o Layout e Adapter do RecylerView
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        perguntasAdapter = new PerguntasAdapter(perguntas);
        recyclerView.setAdapter(perguntasAdapter);
        perguntasAdapter.notifyDataSetChanged();


        //Abre o fragent para seleção da data de inicio do desafio
        inputDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment(inputDataInicio);
                dialogFragment.show(getChildFragmentManager(), "Data");
            }
        });

        //Abre o fragment para selação da hora inicio do desafio
        inputHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment(inputHoraInicio);
                dialogFragment.show(getChildFragmentManager(), "Tempo");
            }
        });

        //Setando botão de adicionar perguntas
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloPergunta = inputTituloPergunta.getText().toString();
                String respostaA = inputRespostaA.getText().toString();
                String respostaB = inputRespostaB.getText().toString();
                String respostaC = inputRespostaC.getText().toString();
                String respostaD = inputRespostaD.getText().toString();
                Integer respostaCerta=respostaCorreta();

                if(!tituloPergunta.isEmpty()){
                    if(!respostaA.isEmpty()){
                        if(!respostaB.isEmpty()){
                            if(!respostaC.isEmpty()){
                                if(!respostaD.isEmpty()){
                                    if(respostaCerta!=0){

                                        //Adicionando pergunta a lista
                                        List<String>respostas = new ArrayList<>();
                                        perguntas.add(new Perguntas(tituloPergunta,respostaA, respostaB, respostaC, respostaD,respostaCerta));
                                        perguntasAdapter.notifyDataSetChanged();
                                        limparCampos();
                                    }
                                    else{
                                        Toast.makeText(getContext(),
                                                "Assinale a resposta correta",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getContext(),
                                            "Preencha o campo 'Resposta D'",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getContext(),
                                        "Preencha o campo 'Resposta C'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(),
                                    "Preencha o campo 'Resposta B'",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),
                                "Preencha o campo 'Resposta A'",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),
                            "Preencha o campo Titulo da pergunta",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Setando botão próximo
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempo = inputTempo.getText().toString();
                String dataInicio=inputDataInicio.getText().toString();
                String horaInicio=inputHoraInicio.getText().toString();

                if(!tempo.isEmpty()){
                    if(!dataInicio.isEmpty()){
                        if(!horaInicio.isEmpty()){
                            if(perguntas.size()>0){

                                Integer tempoInt = Integer.valueOf(tempo);
                                PrimeiroDesafio primeiroDesafio = new PrimeiroDesafio(perguntas,tempoInt,dataInicio,horaInicio);
                                interfaceDesafios.setPrimeiroDesafio(primeiroDesafio);

                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container_criar, new CriarSegundoDesafioFragment())
                                        .addToBackStack("Primeiro desafio")
                                        .commit();
                            }
                            else{
                                Toast.makeText(getContext(),
                                        "Adicione ao menos uma pergunta para o desafio",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(),
                                    "Preencha o campo com a hora para inicio de desafio",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),
                                "Preencha a data de inicio do desafio",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),
                            "Preencha o campo com o tempo para realização do desafio",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private Integer respostaCorreta(){
        int respostaCerta=0;

        if(radioA.isChecked()){
            respostaCerta=1;
        }
        if(radioB.isChecked()){
            respostaCerta=2;
        }
        if(radioC.isChecked()){
            respostaCerta=3;
        }
        if(radioD.isChecked()){
            respostaCerta=4;
        }
        return respostaCerta;
    }

    private void limparCampos(){
        inputTituloPergunta.getText().clear();
        inputRespostaA.getText().clear();
        inputRespostaB.getText().clear();
        inputRespostaC.getText().clear();
        inputRespostaD.getText().clear();
        radioA.setChecked(false);
        radioB.setChecked(false);
        radioC.setChecked(false);
        radioD.setChecked(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            interfaceDesafios = (InterfaceDesafios) context;
        } catch (Exception e){
            Log.d("onAttach" , e.toString());
        }
    }
}