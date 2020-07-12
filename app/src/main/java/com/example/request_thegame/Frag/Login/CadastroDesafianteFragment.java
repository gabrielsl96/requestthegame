package com.example.request_thegame.Frag.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.DAO.UsuarioDAO;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.UserProfileChangeRequest;

public class CadastroDesafianteFragment extends Fragment {
    private FirebaseAuth auth= ConfiguracaoFirebase.getFirebaseAuth();
    private TextInputEditText nome, email, senha, confirmacaoSenha;
    private Button btnCadastrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_desafiante, container, false);

        //Setando os componentes
        nome=view.findViewById(R.id.textinput_nome);
        email=view.findViewById(R.id.textinput_email);
        senha=view.findViewById(R.id.textinput_senha);
        confirmacaoSenha=view.findViewById(R.id.textinput_confirmasenha);
        btnCadastrar=view.findViewById(R.id.btn_cadastrar);

        //Setando o lstener do botão de cadastro
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nomeUsuario=nome.getText().toString();
                String emailUsuario = email.getText().toString();
                String senhaUsuario=senha.getText().toString();
                String confirmacaoSenhaUsuario=confirmacaoSenha.getText().toString();

                if(!nomeUsuario.isEmpty()){
                    if(!emailUsuario.isEmpty()){
                        if(!senhaUsuario.isEmpty()){
                            if(!confirmacaoSenhaUsuario.isEmpty()){
                                if(senhaUsuario.equals(confirmacaoSenhaUsuario)){
                                    Usuario usuario = new Usuario(nomeUsuario,emailUsuario,senhaUsuario,"desafiante");
                                    UsuarioDAO usuarioDAO=new UsuarioDAO();
                                    usuarioDAO.criarUsuario(usuario,getContext());
                                }
                                else{
                                    Toast.makeText(getContext(),
                                            "Senhas diferentes",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getContext(),
                                        "Preencha o campo confirmação senha",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(),
                                    "Preencha o campo senha",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),
                                "Preencha o campo email",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),
                            "Preencha o campo nome",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    return view;
    }
}
