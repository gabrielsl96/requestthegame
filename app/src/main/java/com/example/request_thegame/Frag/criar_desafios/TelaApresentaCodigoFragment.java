package com.example.request_thegame.Frag.criar_desafios;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.R;

public class TelaApresentaCodigoFragment extends Fragment {
    private TextView textCodigo, btn_copiar, btn_compartilhar;
    private Button btnFinalizar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_apresenta_codigo,container,false);

        textCodigo=view.findViewById(R.id.text_codigo_compartilhar);
        btn_copiar=view.findViewById(R.id.btn_text_copiar);
        btn_compartilhar=view.findViewById(R.id.btn_text_compartiljhar);
        btnFinalizar = view.findViewById(R.id.btn_finalizar_criar);

        Bundle bundle = getArguments();
        final String codigoCompartilhar = bundle.getString("codigoDesafio");
        textCodigo.setText(codigoCompartilhar);

        //Botão para copiar o código para área de transferência
        btn_copiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData clipData = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN,codigoCompartilhar);

                cm.setPrimaryClip(clipData);

                Toast.makeText(getContext(),
                        "Código copiado para área de transferência!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Botão para compartilhar via apps de terceiros
        btn_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Você está preparado(a) para o deasafio?\nVenha tentar a sorte em Request - The GAME.\nO seu código-chave é:\n\n"+codigoCompartilhar);

                sendIntent.setType("text/plain");

                Intent sharedIntent = Intent.createChooser(sendIntent, null);
                startActivity(sharedIntent);
            }
        });


        //Botão para finalizar o cadastro e voltar para a MainActivity
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}
