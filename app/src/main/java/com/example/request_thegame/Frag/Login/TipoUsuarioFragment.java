package com.example.request_thegame.Frag.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.request_thegame.R;

public class TipoUsuarioFragment extends Fragment {
    private Button btn_desafiante,btn_desafiado;
    private LoginDesafianteFragment desafianteFragment=new LoginDesafianteFragment();
    private LoginDesafiadoFragment desafiadoFragment=new LoginDesafiadoFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_tipo_usuario, container, false);
        btn_desafiado=view.findViewById(R.id.btn_desafiado);
        btn_desafiante=view.findViewById(R.id.btn_desafiante);

        btn_desafiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,desafianteFragment).addToBackStack("desafiante").commit();
            }
        });
        btn_desafiado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,desafiadoFragment).addToBackStack("desafiado").commit();
            }
        });

        return view;
    }
}
