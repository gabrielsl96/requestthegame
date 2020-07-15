package com.example.request_thegame.Frag.desafios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Activ.desafios.DesafioUmActivity;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.TradutorCodigoMorse;
import com.example.request_thegame.Interface.InterfacePainelDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;


public class DesafioUmFragment extends Fragment {

    private InterfacePainelDesafios painelDesafios;
    private TextView textTempo, textPerguntas;
    private Button btnComecar;
    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




            Log.d("Status","Abriu");
           final Desafio desafio = painelDesafios.getDesafio();
           final Usuario usuario = painelDesafios.getUsuario();

            if (desafio.getStatusDesafios().getStatusDesafioUm().equals("Disponível")) {
                view = inflater.inflate(R.layout.fragment_desafio_um, container, false);

                textTempo = view.findViewById(R.id.text_tempo_um);
                textPerguntas = view.findViewById(R.id.text_perguntas_um);
                btnComecar = view.findViewById(R.id.btn_vamos_dois);

                textTempo.setText(String.valueOf(desafio.getPrimeiroDesafio().getTempoParaRealizacao()));
                textPerguntas.setText(String.valueOf(desafio.getPrimeiroDesafio().getPerguntas().size()));

                btnComecar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] idDesafio = usuario.getDesafio().split("/");
                        reference.child("Desafios").child(idDesafio[0]).child(idDesafio[1]).child("statusDesafios").child("statusDesafio").setValue("Executando primeiro desafio");
                        Intent intent = new Intent(getContext(), DesafioUmActivity.class);
                        intent.putExtra("perguntas", (Serializable) desafio.getPrimeiroDesafio().getPerguntas());
                        intent.putExtra("idDes", usuario.getDesafio());
                        intent.putExtra("tempo", desafio.getPrimeiroDesafio().getTempoParaRealizacao());
                        startActivity(intent);
                    }
                });
            } else if (desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")) {
                view = inflater.inflate(R.layout.fragment_concluido, container, false);

                TextView textMensagemFinal = view.findViewById(R.id.text_mensagem_final);
                TextView textTituloConcluido = view.findViewById(R.id.text_titulo_concluido);

                textTituloConcluido.setText("Primeiro desafio concluído");
                if (desafio.getMensagemFinal().getTipoMensagem().equals("Código Morse")) {
                    textMensagemFinal.setText(TradutorCodigoMorse.getCodigoMorse(desafio.getMensagemFinal().getPrimeiraParte()));
                } else {
                    textMensagemFinal.setText(desafio.getMensagemFinal().getPrimeiraParte());
                }
            } else {
                view = inflater.inflate(R.layout.view_desafio_bloqueado, container, false);
            }


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            painelDesafios=(InterfacePainelDesafios)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}