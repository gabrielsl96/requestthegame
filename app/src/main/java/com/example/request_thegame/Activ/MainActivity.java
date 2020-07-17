package com.example.request_thegame.Activ;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.request_thegame.Adapter.DesafiosAdapter;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.ProgressBarLoad;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.example.request_thegame.Services.ServiceListenerDesafio;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private TextView nomeUsuario;
    private CircleImageView imageUsuario;
    private RecyclerView recyclerViewDesafios;
    private Button btnCriarDesafio;
    private List<Desafio> desafiosList = new ArrayList<>();
    private Usuario usuario;
    private ProgressBarLoad load;
    private Dialog dialog;
    private DesafiosAdapter adapter;
    private static DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private static FirebaseAuth auth = ConfigFirebase.getFirebaseAuth();
    private ValueEventListener valueEventListener, valueEventListenerUsuario;
    private Toolbar toolbar;
    private Runnable runnable;
    private Handler handler;
    private LinearLayoutManager layoutManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        intent = new Intent(MainActivity.this,ServiceListenerDesafio.class);


        //Setando os componentes da tela
        nomeUsuario = findViewById(R.id.text_nomeUsuario);
        imageUsuario = findViewById(R.id.img_usuario);
        recyclerViewDesafios=findViewById(R.id.recyclerview_desafios);
        btnCriarDesafio=findViewById(R.id.btn_criarDesafio);
        toolbar = findViewById(R.id.tootlbar_main);

        load = new ProgressBarLoad(this,dialog);
        load.iniciar();


        runnable = new Runnable() {
            @Override
            public void run() {
                if(desafiosList.size()!=0){

                    if(usuario.getTipoUsuario().equals("Desafiado")) {
                        load.finalizar();
                        startService(intent);
                        handler.removeCallbacks(runnable);
                    }
                    else if(usuario.getTipoUsuario().equals("Desafiante")){
                        handler.removeCallbacks(runnable);
                    }
                }
                else{
                    handler.postDelayed(runnable,1000);
                }
            }
        };
        handler=new Handler();
        runOnUiThread(runnable);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Configura o recycler dos desafios
        layoutManager = new LinearLayoutManager(MainActivity.this);





        //Botão para abrir activity de criar desafios
        btnCriarDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CriarDesafiosActivity.class));
            }
        });

    }

    private void carregarDadosUsuario(){


        valueEventListenerUsuario=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                usuario = dataSnapshot.getValue(Usuario.class);


                if(usuario!=null){
                    usuario.setIdUsuario(auth.getUid());
                    atualizarDadosUsuarioNaTela(usuario);
                    carregarDesafios(usuario);
                }

                else{
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this,SplashScreenActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,
                        databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        };

        reference.child("Usuários")
                .child(auth.getCurrentUser().getUid())
                .addValueEventListener(valueEventListenerUsuario);
    }

    private void atualizarDadosUsuarioNaTela(Usuario usuario){
        StorageReference reference = ConfigFirebase.getStorageReference();

        String[] partesNome = usuario.getNomeUsuario().split(" ");
        nomeUsuario.setText("Olá, "+partesNome[0]+"!");

        reference
                .child("Fotos/Perfil/"+usuario.getIdUsuario()+"/foto-usuario.jpg")
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Glide.with(MainActivity.this)
                                .load(uri)
                                .into(imageUsuario);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Erro ao carregar imagem!",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void carregarDesafios(final Usuario usuario){


        if (usuario.getTipoUsuario().equals("Desafiante")) {

            btnCriarDesafio.setVisibility(View.VISIBLE);

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    desafiosList.clear();
                    load.finalizar();

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Desafio desafio = postSnapShot.getValue(Desafio.class);

                        desafiosList.add(desafio);

                        recyclerViewDesafios.setLayoutManager(layoutManager);

                        //Configura o RecyclerView dos desafios
                        adapter = new DesafiosAdapter(MainActivity.this,desafiosList, usuario);
                        recyclerViewDesafios.setAdapter(adapter);
                        adapter.notifyDataSetChanged();



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    load.finalizar();
                    Toast.makeText(MainActivity.this,
                            databaseError.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            };
            reference.child("Desafios")
                    .child(usuario.getIdUsuario())
                    .addValueEventListener(valueEventListener);

        }

        if (usuario.getTipoUsuario().equals("Desafiado")) {

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    desafiosList.clear();
                    Desafio desa = dataSnapshot.getValue(Desafio.class);

                    desafiosList.add(desa);

                    recyclerViewDesafios.setLayoutManager(layoutManager);

                    //Carrega os desafios no RecyclerView
                    adapter = new DesafiosAdapter(MainActivity.this,desafiosList, usuario);
                    recyclerViewDesafios.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    load.finalizar();
                    Toast.makeText(MainActivity.this,
                            databaseError.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            };

            String desafio = usuario.getDesafio();

            String[] dados = desafio.split("/");

            reference.child("Desafios")
                    .child(dados[0])
                    .child(dados[1])
                    .addValueEventListener(valueEventListener);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal_activity,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.sair:
                stopService(intent);
                auth.signOut();
                startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(valueEventListener);
        reference.removeEventListener(valueEventListenerUsuario);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarDadosUsuario();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(usuario.getTipoUsuario().equals("Desafiado")) {
            stopService(intent);
            startService(intent);
        }
    }
}