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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.Model.desafio.TerceiroDesafio;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

public class DesafioTresActivity extends AppCompatActivity {

    private TextView textCharada;
    private TextInputEditText inputCharada;
    private Button btnResponder;
    private TerceiroDesafio terceiroDesafio;
    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private LocationManager locationManager;
    private Location location;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio_tres);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        textCharada = findViewById(R.id.text_charada_tres);
        inputCharada = findViewById(R.id.input_charada_tres);
        btnResponder = findViewById(R.id.btn_responder_tres);

        terceiroDesafio = (TerceiroDesafio) getIntent().getExtras().get("desafio");
        usuario = (Usuario) getIntent().getExtras().get("usuario");

        textCharada.setText(terceiroDesafio.getCharada());

        if (ActivityCompat.checkSelfPermission(DesafioTresActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(DesafioTresActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DesafioTresActivity.this,new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},69);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }


        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resposta = inputCharada.getText().toString();
                String respostaDesafio = terceiroDesafio.getResposta().toLowerCase();

                if (location != null) {
                    if (!resposta.isEmpty()) {
                        if (resposta.toLowerCase().equals(respostaDesafio)) {
                            final String[] idDesafio = usuario.getDesafio().split("/");
                            reference.
                                    child("Desafios")
                                    .child(idDesafio[0])
                                    .child(idDesafio[1])
                                    .child("statusDesafios")
                                    .child("statusDesafio")
                                    .setValue("Concluído terceiro desafio")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            reference.
                                                    child("Desafios")
                                                    .child(idDesafio[0])
                                                    .child(idDesafio[1])
                                                    .child("statusDesafios")
                                                    .child("statusDesafioTres")
                                                    .setValue("Concluído")
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(DesafioTresActivity.this,
                                                                    "Vamos nessa!",
                                                                    Toast.LENGTH_SHORT).show();

                                                            String origem = location.getLatitude() + "," + location.getLongitude();

                                                            final String uri = "https://www.google.com/maps/dir/?api=1&origin="
                                                                    + origem
                                                                    + "&destination="
                                                                    + terceiroDesafio.getLocalizacao()
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
                                                            Toast.makeText(DesafioTresActivity.this,
                                                                    e.getMessage(),
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });


                        } else {
                            Toast.makeText(DesafioTresActivity.this,
                                    "Resposta errada",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DesafioTresActivity.this,
                                "Preencha o campo resposta",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DesafioTresActivity.this,
                            "Você precisa dar permissão para acessar a localização do seue aparaelho!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

}