package com.example.request_thegame.Frag.desafios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Activ.desafios.DesafioDoisActivity;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.TradutorCodigoMorse;
import com.example.request_thegame.Interface.InterfacePainelDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.Model.desafio.SegundoDesafio;
import com.example.request_thegame.R;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class DesafioDoisFragment extends Fragment {
    private InterfacePainelDesafios painelDesafios;
    private Desafio desafio;
    private Usuario usuario;
    private View view;
    private TextView textTempo,textPerguntas;
    private Button btnComecar;
    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        desafio=painelDesafios.getDesafio();
        usuario=painelDesafios.getUsuario();

        if(desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído") &&
        desafio.getStatusDesafios().getStatusDesafioDois().equals("Indisponível")){
            view = inflater.inflate(R.layout.view_desafio_bloqueado,container,false);
        }

        else if(desafio.getStatusDesafios().getStatusDesafioDois().equals("Indisponível")){
            view = inflater.inflate(R.layout.view_desafio_bloqueado_anterior,container,false);

        }

        else if(desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")){
            view = inflater.inflate(R.layout.fragment_concluido,container,false);

            TextView textMensagemFinal = view.findViewById(R.id.text_mensagem_final);
            TextView textTituloConcluido = view.findViewById(R.id.text_titulo_concluido);

            textTituloConcluido.setText("Segundo desafio concluído");

            if (desafio.getMensagemFinal().getTipoMensagem().equals("Código Morse")) {
                textMensagemFinal.setText(TradutorCodigoMorse.getCodigoMorse(desafio.getMensagemFinal().getSegundaParte()));
            } else {
                textMensagemFinal.setText(desafio.getMensagemFinal().getSegundaParte());
            }
        }

        else{
            view = inflater.inflate(R.layout.fragment_desafio_dois,container,false);

            final SegundoDesafio segundoDesafio = desafio.getSegundoDesafio();

            textTempo=view.findViewById(R.id.text_tempo_dois);
            textPerguntas=view.findViewById(R.id.text_perguntas_dois);
            btnComecar=view.findViewById(R.id.btn_vamos_dois);

            textTempo.setText(String.valueOf(segundoDesafio.getTempoParaRealizacao()));
            textPerguntas.setText(String.valueOf(segundoDesafio.getPerguntas().size()));

            btnComecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] idDesafio = usuario.getDesafio().split("/");
                    reference.child("Desafios").child(idDesafio[0]).child(idDesafio[1]).child("statusDesafios").child("statusDesafio").setValue("Executando segundo desafio");
                    Intent intent = new Intent(getContext(), DesafioDoisActivity.class);
                    intent.putExtra("perguntas", (Serializable) segundoDesafio.getPerguntas());
                    intent.putExtra("idDes",usuario.getDesafio());
                    intent.putExtra("tempo",desafio.getSegundoDesafio().getTempoParaRealizacao());
                    startActivity(intent);
                }
            });

        }


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            painelDesafios=(InterfacePainelDesafios)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}