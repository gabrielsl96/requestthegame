package com.example.request_thegame.Frag.criar_desafios;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Interface.InterfaceDesafios;
import com.example.request_thegame.Model.desafio.MensagemFinal;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

public class CriarMensagemFinalFragment extends Fragment {
    private TextInputEditText primeiraParte, segundaParte, terceiraParte, quartaParte;
    private Switch switchTipoMensagem;
    private Button btnFinalizar;
    private InterfaceDesafios interfaceDesafios;
    private String tipoMensagem=" ";
    private DatabaseReference reference= ConfigFirebase.getDatabaseReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mensagem_final, container, false);

        primeiraParte=view.findViewById(R.id.input_parteUm);
        segundaParte=view.findViewById(R.id.input_parteDois);
        terceiraParte=view.findViewById(R.id.input_parteTres);
        quartaParte=view.findViewById(R.id.input_parteQuatro);
        switchTipoMensagem = view.findViewById(R.id.switch_tipo_mensagem);
        btnFinalizar=view.findViewById(R.id.btn_proximo);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String primeiroTrecho=primeiraParte.getText().toString();
                String segundoTrecho=segundaParte.getText().toString();
                String terceiroTrecho=terceiraParte.getText().toString();
                String quartoTrecho=quartaParte.getText().toString();


                if(!primeiroTrecho.isEmpty()){
                    if(!segundoTrecho.isEmpty()){
                        if(!terceiroTrecho.isEmpty()){
                            if(!quartoTrecho.isEmpty()){

                                if(switchTipoMensagem.isChecked()){
                                    tipoMensagem="Código Morse";
                                }
                                else {
                                    tipoMensagem="Português";
                                }


                                MensagemFinal mensagemFinal = new MensagemFinal(primeiroTrecho,segundoTrecho,terceiroTrecho,quartoTrecho,tipoMensagem);
                                interfaceDesafios.setMensagemFinal(mensagemFinal);


                                getParentFragmentManager().popBackStack();
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container_criar,new CriarCodigoDesafioFragment())
                                        .addToBackStack("Mesnsagem")
                                        .commit();






                            }
                            else {
                                Toast.makeText(getContext(),
                                        "Preencha a quarta parte da mensagem",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getContext(),
                                    "Preencha a terceira parte da mensagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(),
                                "Preencha a segunda parte da mensagem",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(),
                            "Preencha a primeira parte da mensagem",
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