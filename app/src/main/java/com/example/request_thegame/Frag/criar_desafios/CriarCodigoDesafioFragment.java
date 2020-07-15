package com.example.request_thegame.Frag.criar_desafios;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.DAO.DesafioDAO;
import com.example.request_thegame.Helper.ProgressBarLoad;
import com.example.request_thegame.Interface.InterfaceDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.desafio.StatusDesafio;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;

public class CriarCodigoDesafioFragment extends Fragment {
    private TextInputEditText inputCodigoChave;
    private Button btnFinalizar;
    private InterfaceDesafios interfaceDesafios;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_codigo_desafio,container,false);

        inputCodigoChave=view.findViewById(R.id.input_codigo_chave);
        btnFinalizar=view.findViewById(R.id.btn_finalizar);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoChave = inputCodigoChave.getText().toString();

                if(!codigoChave.isEmpty()){

                    //Configura o status do desafio para padrão
                    StatusDesafio statusDesafio = new StatusDesafio("Não Iniciado","Indisponível","Indisponível","Indisponível","Indisponível");

                    //Passa os dados para a interface na Activity
                    interfaceDesafios.setStatusDesafio(statusDesafio);
                    interfaceDesafios.setIdDesafio(codigoChave.toUpperCase());


                    //Via interface, devolve os dados passados no fragments anteriores em objeto Desafio
                    Desafio desafio = interfaceDesafios.getDesafio();

                    //Salva o desafio no Firebase
                    DesafioDAO dao = new DesafioDAO();
                    ProgressBarLoad progressBarLoad = new ProgressBarLoad(getContext(),dialog);
                    dao.create(getContext(), getParentFragmentManager(),desafio,progressBarLoad);

                }
                else{
                    Toast.makeText(getContext(),
                            "Preencha o campo com o código antes de prosseguir",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            interfaceDesafios = (InterfaceDesafios) context;
        }
        catch(Exception e){
            Log.d("onAttach", e.toString());
        }
    }
}
