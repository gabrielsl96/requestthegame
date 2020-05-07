package com.example.request_thegame.Activ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Model.DiasDesafios;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    private int value_progressaBar1 = 0, value_progressaBar2 = 0, value_progressaBar3 = 0, value_progressaBar4 = 0, value_progressaBar5 = 0;
    private Button btn;
    private TextInputEditText text, text2;
    private ValueEventListener eventListener = null;
    private DatabaseReference reference=ConfiguracaoFirebase.getReferenceDatabase().child("DiasDesafios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        progressBar1 = findViewById(R.id.progressBar_1);
        progressBar2 = findViewById(R.id.progressBar_2);
        progressBar3 = findViewById(R.id.progressBar_3);
        progressBar4 = findViewById(R.id.progressBar_4);
        progressBar5 = findViewById(R.id.progressBar_5);


        progressBarLoad();
    }

    private void progressBarLoad(){
       final Timer t=new Timer();
       final TimerTask timer=new TimerTask() {
           @Override
           public void run() {

               if(value_progressaBar1<100){
                   value_progressaBar1++;
                   progressBar1.setProgress(value_progressaBar1);
               }
               else if(value_progressaBar1>=100&&value_progressaBar2<100){
                   value_progressaBar2++;
                   progressBar2.setProgress(value_progressaBar2);
               }
               else if(value_progressaBar1>=100&&value_progressaBar2>=100&&value_progressaBar3<100){
                   value_progressaBar3++;
                   progressBar3.setProgress(value_progressaBar3);
               }
               else if(value_progressaBar1>=100&&value_progressaBar2>=100&&value_progressaBar3>=100&&value_progressaBar4<100){
                   value_progressaBar4++;
                   progressBar4.setProgress(value_progressaBar4);
               }
               else if(value_progressaBar1>=100&&value_progressaBar2>=100&&value_progressaBar3>=100&&value_progressaBar4>=100&&value_progressaBar5<100){
                   value_progressaBar5++;
                   progressBar5.setProgress(value_progressaBar5);
               }
               else{
                   t.cancel();
               }
           }
       };
       t.schedule(timer,0,5);
    }

    public void abrirDesafios(View view){

        eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DiasDesafios dias=dataSnapshot.getValue(DiasDesafios.class);

                if(dias.getIniciou().equals("n")){
                    startActivity(new Intent(MainActivity.this,PrimeiroAcesso.class));
                    reference.removeEventListener(eventListener);

                }
                else{
                    startActivity(new Intent(MainActivity.this,DesafiosActivity.class));
                    reference.removeEventListener(eventListener);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(eventListener);



    }
}
