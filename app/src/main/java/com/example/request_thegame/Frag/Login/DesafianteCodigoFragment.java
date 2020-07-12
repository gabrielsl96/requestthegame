package com.example.request_thegame.Frag.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;

public class DesafianteCodigoFragment extends Fragment {
    private TextInputEditText codigo;
    private Button btn_acessar;

    public DesafianteCodigoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_desafiante_codigo, container, false);
        btn_acessar=view.findViewById(R.id.btn_codigo);
        codigo=view.findViewById(R.id.codigo_desafiado);

        btn_acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String codigoDesafio= codigo.getText().toString();

            }
        });

        return view;
    }
}
