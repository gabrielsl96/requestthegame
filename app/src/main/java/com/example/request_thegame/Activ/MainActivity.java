package com.example.request_thegame.Activ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth=ConfiguracaoFirebase.getFirebaseAuth();
    private DatabaseReference reference=ConfiguracaoFirebase.getReferenceDatabase().child("Usuarios");
    private TextView textoSaudacao;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        textoSaudacao=findViewById(R.id.texto_saudacao);
        carregarInformacoesUsuario();
    }

        public void carregarInformacoesUsuario(){
        String id=auth.getUid();
        reference.child(id);
        valueEventListener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario=dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MainActivity.this,
                        databaseError.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}
