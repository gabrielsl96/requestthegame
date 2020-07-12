package com.example.request_thegame.Activ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Frag.Login.TipoUsuarioFragment;
import com.example.request_thegame.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUsuarioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new TipoUsuarioFragment()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        verificarUsuario();
    }

    private void verificarUsuario(){
        FirebaseAuth auth= ConfiguracaoFirebase.getFirebaseAuth();

        if(auth==null){
            startActivity(new Intent(LoginUsuarioActivity.this,MainActivity.class));
        }
    }
}
