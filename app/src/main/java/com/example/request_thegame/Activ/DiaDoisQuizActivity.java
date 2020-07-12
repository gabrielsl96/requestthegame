package com.example.request_thegame.Activ;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.request_thegame.Model.Perguntas;
import com.example.request_thegame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiaDoisQuizActivity extends AppCompatActivity {
    private TextView titulo;
    private List<Perguntas> perguntas;
    private RadioButton respostaUm,respostaDois,respostaTres,respostaQuatro;
    private RadioGroup grupoRespostas;
    private Button btn_resposta;
    private int quantidadePerguntas;
    private int respostaCerta;
    private int acertos=0;
    private int erros=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dia_dois_quiz);
        titulo=findViewById(R.id.titulo_perguntas);
        respostaUm=findViewById(R.id.respostaUm);
        respostaDois=findViewById(R.id.respostaDois);
        respostaTres=findViewById(R.id.respostaTres);
        respostaQuatro=findViewById(R.id.respostaQuatro);
        btn_resposta=findViewById(R.id.btn_resposta);

        downloadPerguntas();
        carregarPergunta();

        btn_resposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correcaoResposta();
                if(perguntas.size()>=1){
                    carregarPergunta();
                }
                else{
                    AlertDialog.Builder resolução = new AlertDialog.Builder(DiaDoisQuizActivity.this);

                    if(acertos==quantidadePerguntas){
                        resolução.setTitle("Parabéns!!!")
                                .setMessage("Você conseguiu realizar a tarefa!!")
                                .setCancelable(true)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        finish();
                                    }
                                });

                    }
                    else{
                        resolução.setMessage("Infelizmente você não conseguiu realaziar o desafio!!!");
                    }
                    resolução.create().show();
                }
            }
        });


    }

    private void downloadPerguntas(){
        perguntas= new ArrayList<Perguntas>();
        perguntas.add(new Perguntas("Uma", "um","dois","três","quatro",1));
        perguntas.add(new Perguntas("Duas", "um","dois","três","quatro",2));
        perguntas.add(new Perguntas("Três", "um","dois","três","quatro",3));
        perguntas.add(new Perguntas("Quatro", "um","dois","três","quatro",4));
        Collections.shuffle(perguntas);
        quantidadePerguntas=perguntas.size();
    }
    private void carregarPergunta(){
        titulo.setText(perguntas.get(0).getPergunta());
        respostaUm.setText(perguntas.get(0).getRespostas().get(0));
        respostaDois.setText(perguntas.get(0).getRespostas().get(1));
        respostaTres.setText(perguntas.get(0).getRespostas().get(2));
        respostaQuatro.setText(perguntas.get(0).getRespostas().get(3));
        respostaCerta=perguntas.get(0).getRespostaCerta();
        perguntas.remove(0);
    }
    private void correcaoResposta(){
        int respostaUsuario= 0;

        if(respostaUm.isChecked()){
            respostaUsuario=1;
        }
        else if(respostaDois.isChecked()){
            respostaUsuario=2;
        }
        else if(respostaTres.isChecked()){
            respostaUsuario=3;
        }
        else if(respostaQuatro.isChecked()){
            respostaUsuario=4;
        }

        if(respostaUsuario==respostaCerta){
            acertos++;
        }
        else{
            erros++;
        }
    }
}
