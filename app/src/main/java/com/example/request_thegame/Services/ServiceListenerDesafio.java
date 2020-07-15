package com.example.request_thegame.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ServiceListenerDesafio extends Service {

    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAuth();
    private String[] idDesafio;
    private Desafio desafio;
    private boolean status;
    private static String CHANNEL_ID;
    private String[] dataDesafio,dataPrimeiro,dataSegundo,dataTerceiro,dataQuarto;
    private String[] horaDesafio,horaPrimeiro,horaSegundo,horaTerceiro,horaQuarto;
    private Calendar instanteAtual,inicioDesafio,inicioPrimeiro,inicioSegundo,inicioTerceiro,inicioQuarto;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {



                    reference
                            .child("Usuários")
                            .child(auth.getUid())
                            .child("desafio")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    idDesafio = snapshot.getValue().toString().split("/");
                                    reference
                                            .child("Desafios")
                                            .child(idDesafio[0])
                                            .child(idDesafio[1])
                                            .addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    desafio = snapshot.getValue(Desafio.class);

                                                    Log.i("Serviço:", "Desafio baixado");

                                                    Timer timer = new Timer();

                                                    TimerTask timerTask = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            Log.i("Serviço:", "Conferindo");
                                                            if(desafio!=null) {

                                                                instanteAtual = Calendar.getInstance();
                                                                inicioDesafio = Calendar.getInstance();
                                                                inicioPrimeiro = Calendar.getInstance();
                                                                inicioSegundo = Calendar.getInstance();
                                                                inicioTerceiro = Calendar.getInstance();
                                                                inicioQuarto = Calendar.getInstance();

                                                                dataDesafio = desafio.getInformacoesDesafio().getDataInicio().split("/");
                                                                dataPrimeiro = desafio.getPrimeiroDesafio().getDataInicio().split("/");
                                                                dataSegundo = desafio.getSegundoDesafio().getDataInicio().split("/");
                                                                dataTerceiro = desafio.getTerceiroDesafio().getDataInicio().split("/");
                                                                dataQuarto = desafio.getQuartoDesafio().getDataInicio().split("/");

                                                                horaDesafio = desafio.getInformacoesDesafio().getHoraInicio().split(":");
                                                                horaPrimeiro = desafio.getPrimeiroDesafio().getHoraInicio().split(":");
                                                                horaSegundo = desafio.getSegundoDesafio().getHoraInicio().split(":");
                                                                horaTerceiro = desafio.getTerceiroDesafio().getHoraInicio().split(":");
                                                                horaQuarto = desafio.getQuartoDesafio().getHoraInicio().split(":");


                                                                inicioDesafio.set(Integer.parseInt(dataDesafio[2]),
                                                                        Integer.parseInt(dataDesafio[1]),
                                                                        Integer.parseInt(dataDesafio[0]),
                                                                        Integer.parseInt(horaDesafio[0]),
                                                                        Integer.parseInt(horaDesafio[1]));

                                                                inicioPrimeiro.set(Integer.parseInt(dataPrimeiro[2]),
                                                                        Integer.parseInt(dataPrimeiro[1]),
                                                                        Integer.parseInt(dataPrimeiro[0]),
                                                                        Integer.parseInt(horaPrimeiro[0]),
                                                                        Integer.parseInt(horaPrimeiro[1]));

                                                                inicioSegundo.set(Integer.parseInt(dataSegundo[2]),
                                                                        Integer.parseInt(dataSegundo[1]),
                                                                        Integer.parseInt(dataSegundo[0]),
                                                                        Integer.parseInt(horaSegundo[0]),
                                                                        Integer.parseInt(horaSegundo[1]));

                                                                inicioTerceiro.set(Integer.parseInt(dataTerceiro[2]),
                                                                        Integer.parseInt(dataTerceiro[1]),
                                                                        Integer.parseInt(dataTerceiro[0]),
                                                                        Integer.parseInt(horaTerceiro[0]),
                                                                        Integer.parseInt(horaTerceiro[1]));

                                                                inicioQuarto.set(Integer.parseInt(dataQuarto[2]),
                                                                        Integer.parseInt(dataQuarto[1]),
                                                                        Integer.parseInt(dataQuarto[0]),
                                                                        Integer.parseInt(horaQuarto[0]),
                                                                        Integer.parseInt(horaQuarto[1]));

                                                                CHANNEL_ID = getString(R.string.common_google_play_services_notification_channel_name);

                                                                if (desafio.getStatusDesafios().getStatusDesafioUm().equals("Indisponível")
                                                                        && inicioPrimeiro.after(instanteAtual)) {
                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioUm")
                                                                            .setValue("Disponível");

                                                                } else if (desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioDois().equals("Indisponível")
                                                                        && inicioSegundo.after(instanteAtual)) {

                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioDois")
                                                                            .setValue("Disponível");
                                                                } else if (desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioTres().equals("Indisponível")
                                                                        && inicioTerceiro.after(instanteAtual)) {
                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioTres")
                                                                            .setValue("Disponível");
                                                                } else if (desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioTres().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Indisponível")
                                                                        && inicioQuarto.after(instanteAtual)) {

                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioQuatro")
                                                                            .setValue("Disponível");
                                                                }

                                                            }
                                                        }
                                                    };
                                                    timer.scheduleAtFixedRate(timerTask,1000,1000);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
