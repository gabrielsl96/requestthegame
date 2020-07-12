package com.example.request_thegame.Frag.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.request_thegame.Activ.LoginUsuarioActivity;
import com.example.request_thegame.Activ.MainActivity;
import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;

public class LoginDesafianteFragment extends Fragment {
    private CadastroDesafianteFragment cadastroDesafianteFragment=new CadastroDesafianteFragment();
    private TextInputEditText email,senha;
    private Button btnAcessar;
    private TextView btnCodigo;
    private FirebaseAuth auth=ConfiguracaoFirebase.getFirebaseAuth();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_desafiante, container, false);

        email=view.findViewById(R.id.inputText_email);
        senha=view.findViewById(R.id.inputText_senha);
        btnAcessar=view.findViewById(R.id.button);
        btnCodigo=view.findViewById(R.id.textView_button);

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUsuario=email.getText().toString();
                String senhaUsuario=senha.getText().toString();

                if(!emailUsuario.isEmpty()){
                    if(!senhaUsuario.isEmpty()){
                        auth.signInWithEmailAndPassword(
                                emailUsuario,
                                senhaUsuario
                        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                startActivity(new Intent(getActivity(),MainActivity.class));
                                getActivity().finish();
                                }
                                else{
                                    String excecao = "";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        excecao = "E-mail inválido!";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        excecao = "Senha inválida!";
                                    } catch (Exception e) {
                                        excecao = "Erro ao efetuar login: " + e.getMessage();
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(getContext(),
                                            excecao,
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(),
                                "Você precisa preencher o campo senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(),
                            "Você precisa preencher o campo email!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,cadastroDesafianteFragment).addToBackStack("Codigo desafiante").commit();
            }
        });
        return view;
    }

}


