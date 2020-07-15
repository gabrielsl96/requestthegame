package com.example.request_thegame.Frag.login_cadastro;

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
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.ProgressBarLoad;
import com.example.request_thegame.Interface.InterfaceCadastroUsuario;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CodigoDesafiadoFragment extends Fragment {

    private TextInputEditText input_codigo;
    private Button btn_codigo;
    private DatabaseReference reference;
    private Dialog dialog;
    private InterfaceCadastroUsuario interfaceCadastroUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_codigo_desafiado, container, false);

        //Iniciando os componentes na tela
        input_codigo=view.findViewById(R.id.input_codigo);
        btn_codigo=view.findViewById(R.id.btn_codigo);

        //Setando o botão
        btn_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo=input_codigo.getText().toString();
                if (!codigo.isEmpty()) {


                    confereCodigo(codigo);



                }

                else{
                    Toast.makeText(getContext(),
                            "Preencha o campo código 'Código'",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void confereCodigo(final String inputCodigo){

        String[] codigoDesafio = inputCodigo.split("/");

        final ProgressBarLoad progressBarLoad = new ProgressBarLoad(getContext(),dialog);
        progressBarLoad.iniciar();
        reference = ConfigFirebase.getDatabaseReference();
        reference.child("Desafios")
                .child(codigoDesafio[0])
                .child(codigoDesafio[1])
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            progressBarLoad.finalizar();

                            interfaceCadastroUsuario.setDesafios(inputCodigo);
                            getParentFragmentManager().popBackStack();
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container_login,new CaptarFotoFragment())
                                    .commit();
                        }
                        else{
                            Toast.makeText(getContext(),
                                    "Código inválido! Tente novamente ou contate o desafiante!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(),
                                databaseError.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            interfaceCadastroUsuario = (InterfaceCadastroUsuario) context;
        }
        catch (Exception e){
            Log.d("onAttach",e.toString());
        }
    }
}