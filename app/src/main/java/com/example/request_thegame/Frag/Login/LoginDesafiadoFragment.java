package com.example.request_thegame.Frag.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginDesafiadoFragment extends Fragment {
    private DesafianteCodigoFragment desafianteCodigoFragment=new DesafianteCodigoFragment();
    private TextInputEditText email,senha;
    private Button btnAcessar;
    private TextView btnCodigo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_desafiado, container, false);
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
                getParentFragmentManager().beginTransaction().replace(R.id.container,desafianteCodigoFragment).addToBackStack("Codigo desafiante").commit();
            }
        });
        return view;
    }
}
