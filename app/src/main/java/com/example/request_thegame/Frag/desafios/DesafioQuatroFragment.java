package com.example.request_thegame.Frag.desafios;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Activ.desafios.DesafioQuatroActivity;
import com.example.request_thegame.Activ.desafios.DesafioTresActivity;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.TradutorCodigoMorse;
import com.example.request_thegame.Interface.InterfacePainelDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;


public class DesafioQuatroFragment extends Fragment {

    private Desafio desafio;
    private Usuario usuario;
    private InterfacePainelDesafios painelDesafios;
    private View view;
    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        desafio=painelDesafios.getDesafio();
        usuario=painelDesafios.getUsuario();

        if(desafio.getStatusDesafios().getStatusDesafioTres().equals("Concluído") &&
                desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Indisponível")){
            view = inflater.inflate(R.layout.view_desafio_bloqueado,container,false);
        }

        else if(desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Indisponível")){
            view = inflater.inflate(R.layout.view_desafio_bloqueado_anterior,container,false);

        }

        else if(desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Executando")){
            view = inflater.inflate(R.layout.view_executando,container,false);
        }

        else if(desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Concluído")){
            view = inflater.inflate(R.layout.fragment_concluido,container,false);

            TextView textMensagemFinal = view.findViewById(R.id.text_mensagem_final);
            TextView textTituloConcluido = view.findViewById(R.id.text_titulo_concluido);

            textTituloConcluido.setText("Quarto desafio concluído");

            if (desafio.getMensagemFinal().getTipoMensagem().equals("Código Morse")) {
                textMensagemFinal.setText(TradutorCodigoMorse.getCodigoMorse(desafio.getMensagemFinal().getQuartaParte()));
            } else {
                textMensagemFinal.setText(desafio.getMensagemFinal().getQuartaParte());
            }
        }

        else{
            view = inflater.inflate(R.layout.fragment_desafio_quatro,container,false);
            Button button = view.findViewById(R.id.btn_tres);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},69);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                                    String[] idDesafio = usuario.getDesafio().split("/");
                                    reference.child("Desafios").child(idDesafio[0]).child(idDesafio[1]).child("statusDesafios").child("statusDesafio").setValue("Executando quarto desafio");
                                    Intent intent = new Intent(getContext(), DesafioQuatroActivity.class);
                                    intent.putExtra("desafio", desafio.getQuartoDesafio());
                                    intent.putExtra("usuario",usuario);
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