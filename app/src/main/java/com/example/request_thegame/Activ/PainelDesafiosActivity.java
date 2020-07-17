package com.example.request_thegame.Activ;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.request_thegame.Frag.desafios.DesafioDoisFragment;
import com.example.request_thegame.Frag.desafios.DesafioQuatroFragment;
import com.example.request_thegame.Frag.desafios.DesafioTresFragment;
import com.example.request_thegame.Frag.desafios.DesafioUmFragment;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Interface.InterfacePainelDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PainelDesafiosActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, InterfacePainelDesafios {

    private BottomNavigationView bottomNavigationView;
    private Usuario usuario;
    private Desafio desafio;
    private ValueEventListener valueEventListener;
    private String[] idDesafio;
    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafios);
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        desafio = (Desafio) getIntent().getExtras().getSerializable("desafio");

        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setItemSelectedNav();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_primeiro_desafio:

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_painel_desafios,new DesafioUmFragment())
                        .commit();
                return true;

            case R.id.item_segundo_desafio:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_painel_desafios,new DesafioDoisFragment())
                            .commit();
                return true;

                case R.id.item_terceiro_desafio:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_painel_desafios,new DesafioTresFragment())
                            .commit();
                    return true;

            case R.id.item_quarto_desafio:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_painel_desafios,new DesafioQuatroFragment())
                        .commit();
                return true;

        }

            return false;
    }

    @Override
    public void setDesafio(Desafio desaf) {
        desafio = desaf;
    }

    @Override
    public Desafio getDesafio() {
        return desafio;
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    protected void onResume() {
        super.onResume();

        try{
            valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        desafio = snapshot.getValue(Desafio.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            idDesafio =usuario.getDesafio().split("/");

            reference.
                    child("Desafios")
                    .child(idDesafio[0])
                    .child(idDesafio[1])
                    .addValueEventListener(valueEventListener);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setItemSelectedNav();
    }

    private void setItemSelectedNav(){
        if( desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                && !desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_painel_desafios,new DesafioDoisFragment())
                    .commitAllowingStateLoss();
        }

        else if( desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")
                && !desafio.getStatusDesafios().getStatusDesafioTres().equals("Concluído")){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_painel_desafios,new DesafioTresFragment())
                    .commitAllowingStateLoss();
        }

        else if(desafio.getStatusDesafios().getStatusDesafioTres().equals("Concluído")
                && !desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Concluído")){
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_painel_desafios,new DesafioQuatroFragment())
                    .commitAllowingStateLoss();
        }

        else{
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_painel_desafios,new DesafioUmFragment())
                    .commitAllowingStateLoss();
        }
    }
}