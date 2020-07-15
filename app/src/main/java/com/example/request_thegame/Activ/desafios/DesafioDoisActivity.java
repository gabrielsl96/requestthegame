package com.example.request_thegame.Activ.desafios;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Model.Perguntas;
import com.example.request_thegame.R;
import com.google.firebase.database.DatabaseReference;

import java.util.Collections;
import java.util.List;

public class DesafioDoisActivity extends AppCompatActivity {
    private List<Perguntas> perguntas;
    private String idDesafio;
    private int quantidadePerguntas;
    private Button btnResposta;
    private int respostaCerta;
    private TextView titulo,cronometro;
    private Handler handler=new Handler();
    private RadioButton respostaUm,respostaDois,respostaTres,respostaQuatro;
    private int acertos = 0;
    private int erros=0;
    private int tempo=0;
    private static DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private String [] id;
    private AlertDialog.Builder resolução;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio_dois);
        perguntas = (List<Perguntas>) getIntent().getExtras().getSerializable("perguntas");
        tempo= (int) getIntent().getExtras().get("tempo");
        idDesafio= (String) getIntent().getExtras().get("idDes");
        id=idDesafio.split("/");

        titulo=findViewById(R.id.titulo_perguntas_dois);
        respostaUm=findViewById(R.id.respostaUm_dois);
        respostaDois=findViewById(R.id.respostaDois_dois);
        respostaTres=findViewById(R.id.respostaTres_dois);
        respostaQuatro=findViewById(R.id.respostaQuatro_dois);
        cronometro=findViewById(R.id.text_cronometro_dois);
        btnResposta=findViewById(R.id.btn_resposta_dois);

        cronometro.setText(String.valueOf(tempo));

        runnable = new Runnable() {
            @Override
            public void run() {
                if(tempo > 0){
                    cronometro.setText(String.valueOf(tempo));
                    tempo=tempo-1;
                    handler.postDelayed(runnable,1000);
                }

                else{
                    cronometro.setText(String.valueOf(tempo));
                    resolução
                            .setMessage("Infelizmente você não conseguiu realizar o desafio! Tente novamente")
                            .setCancelable(true)
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }
                            });
                    resolução.create().show();
                    handler.removeCallbacks(runnable);
                }
            }
        };

        runOnUiThread(runnable);



        resolução = new AlertDialog.Builder(DesafioDoisActivity.this);

        preparaPerguntas();
        carregarPergunta();

        btnResposta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!respostaUm.isChecked()
                        && !respostaDois.isChecked()
                        && !respostaTres.isChecked()
                        && !respostaQuatro.isChecked()){
                    Toast.makeText(DesafioDoisActivity.this,
                            "Escolha uma alternativa",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    correcaoResposta();
                    if (perguntas.size() >= 1) {
                        carregarPergunta();
                    } else {


                        if (acertos == quantidadePerguntas) {
                            handler.removeCallbacks(runnable);
                            reference.child("Desafios")
                                    .child(id[0])
                                    .child(id[1])
                                    .child("statusDesafios")
                                    .child("statusDesafio")
                                    .setValue("Concluído segundo desafio");

                            reference.child("Desafios")
                                    .child(id[0])
                                    .child(id[1])
                                    .child("statusDesafios")
                                    .child("statusDesafioDois")
                                    .setValue("Concluído");


                            resolução.setTitle("Parabéns!!!")
                                    .setMessage("Você conseguiu realizar a tarefa!!")
                                    .setCancelable(true)
                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            finish();
                                        }
                                    });
                            resolução.create().show();

                        } else {
                            handler.removeCallbacks(runnable);
                            resolução
                                    .setMessage("Infelizmente você não conseguiu realizar o desafio! Tente novamente!")
                                    .setCancelable(true)
                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            finish();

                                        }
                                    });
                            resolução.create().show();
                        }

                    }
                }
            }
        });

    }

    private void preparaPerguntas(){
        Collections.shuffle(perguntas);
        quantidadePerguntas = perguntas.size();
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