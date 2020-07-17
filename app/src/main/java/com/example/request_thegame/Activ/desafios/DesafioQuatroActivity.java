package com.example.request_thegame.Activ.desafios;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.Model.desafio.QuartoDesafio;
import com.example.request_thegame.Model.desafio.TerceiroDesafio;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

public class DesafioQuatroActivity extends AppCompatActivity {

    private TextView textCharada;
    private TextInputEditText inputCharada;
    private Button btnResponder;
    private QuartoDesafio quartoDesafio;
    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private LocationManager locationManager;
    private Location location;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio_quatro);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        textCharada = findViewById(R.id.text_charada_quatro);
        inputCharada=findViewById(R.id.input_charada_quatro);
        btnResponder=findViewById(R.id.btn_responder_quatro);

        quartoDesafio = (QuartoDesafio) getIntent().getExtras().get("desafio");
        usuario = (Usuario) getIntent().getExtras().get("usuario");

        textCharada.setText(quartoDesafio.getCharada());

        if (ActivityCompat.checkSelfPermission(DesafioQuatroActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DesafioQuatroActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DesafioQuatroActivity.this,new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},69);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }


        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resposta = inputCharada.getText().toString();
                String respostaDesafio = quartoDesafio.getResposta().toLowerCase();

                if(location!=null) {
                    if (!resposta.isEmpty()) {
                        if (resposta.toLowerCase().equals(respostaDesafio)) {

                            final String[] idDesafio = usuario.getDesafio().split("/");

                            reference.
                                    child("Desafios")
                                    .child(idDesafio[0])
                                    .child(idDesafio[1])
                                    .child("statusDesafios")
                                    .child("statusDesafio")
                                    .setValue("Request concluído!!")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            reference.
                                                    child("Desafios")
                                                    .child(idDesafio[0])
                                                    .child(idDesafio[1])
                                                    .child("statusDesafios")
                                                    .child("statusDesafioQuatro")
                                                    .setValue("Concluído")
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(DesafioQuatroActivity.this,
                                                                    "Vamos nessa!",
                                                                    Toast.LENGTH_SHORT).show();


                                                            String origem = location.getLatitude() + "," + location.getLongitude();

                                                            final String uri = "https://www.google.com/maps/dir/?api=1&origin="
                                                                    + origem
                                                                    + "&destination="
                                                                    + quartoDesafio.getLocalizacao()
                                                                    + "&travelmode=walking&dir_action=navigate";

                                                            Intent abriGoogleMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                                            abriGoogleMaps.setPackage("com.google.android.apps.maps");
                                                            startActivity(abriGoogleMaps);
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(DesafioQuatroActivity.this,
                                                                    e.getMessage(),
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });


                        } else {
                            Toast.makeText(DesafioQuatroActivity.this,
                                    "Resposta errada",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DesafioQuatroActivity.this,
                                "Preencha o campo resposta",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(DesafioQuatroActivity.this,
                            "Você precisa dar permissão para acessar a localização do seue aparaelho!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        });

    }
}