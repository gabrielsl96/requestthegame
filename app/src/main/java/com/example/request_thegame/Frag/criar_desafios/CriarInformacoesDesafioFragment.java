package com.example.request_thegame.Frag.criar_desafios;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Helper.DatePickerFragment;
import com.example.request_thegame.Helper.TimePickerFragment;
import com.example.request_thegame.Interface.InterfaceDesafios;
import com.example.request_thegame.Model.desafio.InformacoesDesafio;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;

public class CriarInformacoesDesafioFragment extends Fragment {

    private TextInputEditText inputDesafiado, inputData, inputHora;
    private Button btn_proximo;
    private InterfaceDesafios interfaceDesafios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_criar_informacoes_desafio, container, false);

        //Setando os componentes da tela
        inputDesafiado =view.findViewById(R.id.input_nome_desafiado);
        inputData =view.findViewById(R.id.input_data_inicio);
        inputHora =view.findViewById(R.id.input_hora_inicio);
        btn_proximo=view.findViewById(R.id.btn_proximo);

        //Abrir fragment para seleção da data
        inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new DatePickerFragment(inputData);
                dialogFragment.show(getChildFragmentManager(),"data");

            }
        });

        //Abrir fragment para seleção da hora
        inputHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment= new TimePickerFragment(inputHora);
                dialogFragment.show(getChildFragmentManager(),"hora");

            }
        });

        //Botão para o próximo fragment
        btn_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeDesafiado = inputDesafiado.getText().toString();
                String dataInicio = inputData.getText().toString();
                String horaInicio=inputHora.getText().toString();


                if(!nomeDesafiado.isEmpty()){
                    if(!dataInicio.isEmpty()){
                        if(!horaInicio.isEmpty()){

                            InformacoesDesafio informacoesDesafio = new InformacoesDesafio(nomeDesafiado,dataInicio,horaInicio);
                            interfaceDesafios.setInformacoesDeasafio(informacoesDesafio);

                             getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container_criar,new CriarPrimeiroDesafioFragment())
                                    .addToBackStack("Cadastro desafiado")
                                    .commit();
                        }
                        else {
                            Toast.makeText(getContext(),
                                    "Preencha o campo com a hora de início do desafio",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(),
                                "Preencha o campo com a data de início do desafio",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(),
                            "Preencha o campo com o nome do Desafiado",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
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