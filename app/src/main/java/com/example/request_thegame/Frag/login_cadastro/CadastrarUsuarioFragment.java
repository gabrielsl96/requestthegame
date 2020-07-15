package com.example.request_thegame.Frag.login_cadastro;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Helper.ProcessosFirebase;
import com.example.request_thegame.Interface.InterfaceCadastroUsuario;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;

public class CadastrarUsuarioFragment extends Fragment {

    private TextInputEditText input_nome, input_email, input_senha, input_confirmaSenha;
    private Button btn_cadastrar;
    private InterfaceCadastroUsuario interfaceCadastro;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastrar_usuario, container, false);

        //Iniciar os componentes na tela
        input_nome=view.findViewById(R.id.input_nome);
        input_email=view.findViewById(R.id.input_email);
        input_senha=view.findViewById(R.id.input_senha);
        input_confirmaSenha=view.findViewById(R.id.input_confirmacaoSenha);
        btn_cadastrar=view.findViewById(R.id.btn_cadastar);

        //Setar o botão de cadastro
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome=input_nome.getText().toString();
                String email=input_email.getText().toString();
                String senha=input_senha.getText().toString();
                String confirmacaoSenha=input_confirmaSenha.getText().toString();

                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty()){
                            if(!confirmacaoSenha.isEmpty()){
                                if(senha.equals(confirmacaoSenha)){
                                    interfaceCadastro.setDadosUsuario(nome,email,senha);
                                    Usuario usuario = interfaceCadastro.getUsuario();
                                    ProcessosFirebase firebase = new ProcessosFirebase();
                                    firebase.cadastrarUsuario(getContext(),getActivity(),usuario);

                                }
                                else{
                                    Toast.makeText(getContext(),
                                            "Senhas diferentes",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getContext(),
                                        "Preencha o campo Confirmação Senha",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(),
                                    "Preencha o campo Senha",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),
                                "Preencha o campo Email",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),
                            "Preencha o campo Nome",
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
            interfaceCadastro = (InterfaceCadastroUsuario) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}