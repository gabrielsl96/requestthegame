package com.example.request_thegame.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Seconds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ServiceListenerDesafio extends Service {

    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAuth();
    private String[] idDesafio;
    private Desafio desafio;
    private String CHANNEL_ID = "gabrielServiceListener";
    private NotificationManager notificationManager;
    private Context context;
    private Timer timer;
    private Notification.Builder builder;
    private Date inicioDesafioUm, inicioDesafioDois, inicioDesafioTres, inicioDesafioQuatro;
    private Calendar  calUm,calDois,calTres,calQuatro;
    private SimpleDateFormat simpleDateFormat;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        context = getBaseContext();
        createNotificationChannel();

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
                                                    if(timer!=null){
                                                        timer.cancel();
                                                        Log.d("Timer", "Parou!");
                                                    }

                                                    Log.i("Serviço:", "Desafio baixado");

                                                    simpleDateFormat = new SimpleDateFormat("dd/MM/yyyyHH:mm");
                                                    try {
                                                        inicioDesafioUm=simpleDateFormat.parse(desafio.getPrimeiroDesafio().getDataInicio()+desafio.getPrimeiroDesafio().getHoraInicio());
                                                        inicioDesafioDois=simpleDateFormat.parse(desafio.getSegundoDesafio().getDataInicio()+desafio.getSegundoDesafio().getHoraInicio());
                                                        inicioDesafioTres=simpleDateFormat.parse(desafio.getTerceiroDesafio().getDataInicio()+desafio.getTerceiroDesafio().getHoraInicio());
                                                        inicioDesafioQuatro=simpleDateFormat.parse(desafio.getQuartoDesafio().getDataInicio()+desafio.getQuartoDesafio().getHoraInicio());


                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                    timer = new Timer();

                                                    TimerTask timerTask = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            Log.i("Serviço:", "Conferindo");

                                                            if(desafio!=null) {

                                                                Calendar calendar = Calendar.getInstance();
                                                                Date instante = calendar.getTime();

                                                                if (inicioDesafioUm.before(instante)
                                                                && desafio.getStatusDesafios().getStatusDesafioUm().equals("Indisponível")) {

                                                                    builder = new Notification.Builder(context, CHANNEL_ID)
                                                                            .setSmallIcon(R.drawable.joystick)
                                                                            .setContentTitle("Desafio um")
                                                                            .setContentText("Desbloqueado!")
                                                                            .setAutoCancel(true);
                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioUm")
                                                                            .setValue("Disponível").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                           notificationManager.notify(0,builder.build());
                                                                        }
                                                                    });

                                                                }

                                                                else if (inicioDesafioDois.before(instante) &&
                                                                        desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioDois().equals("Indisponível")) {

                                                                    builder = new Notification.Builder(context, CHANNEL_ID)
                                                                            .setSmallIcon(R.drawable.joystick)
                                                                            .setContentTitle("Desafio Dois")
                                                                            .setContentText("Desbloqueado!")
                                                                            .setAutoCancel(true);

                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioDois")
                                                                            .setValue("Disponível")
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    notificationManager.notify(0,builder.build());
                                                                                }
                                                                            });


                                                                }

                                                                else if (inicioDesafioTres.before(instante)
                                                                && desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioTres().equals("Indisponível")) {

                                                                    builder = new Notification.Builder(context, CHANNEL_ID)
                                                                            .setSmallIcon(R.drawable.joystick)
                                                                            .setContentTitle("Desafio Três")
                                                                            .setContentText("Desbloqueado!")
                                                                            .setAutoCancel(true);

                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioTres")
                                                                            .setValue("Disponível")
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    notificationManager.notify(0,builder.build());
                                                                                }
                                                                            });

                                                                }

                                                                else if (inicioDesafioQuatro.before(instante)
                                                                && desafio.getStatusDesafios().getStatusDesafioUm().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioDois().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioTres().equals("Concluído")
                                                                        && desafio.getStatusDesafios().getStatusDesafioQuatro().equals("Indisponível")) {

                                                                    builder = new Notification.Builder(context, CHANNEL_ID)
                                                                            .setSmallIcon(R.drawable.joystick)
                                                                            .setContentTitle("Desafio Quatro")
                                                                            .setContentText("Desbloqueado!")
                                                                            .setAutoCancel(true);

                                                                    reference
                                                                            .child("Desafios")
                                                                            .child(idDesafio[0])
                                                                            .child(idDesafio[1])
                                                                            .child("statusDesafios")
                                                                            .child("statusDesafioQuatro")
                                                                            .setValue("Disponível")
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    notificationManager.notify(0,builder.build());
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        }
                                                    };
                                                    timer.scheduleAtFixedRate(timerTask,0,3000);
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
        timer.cancel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ListenerDesafios";
            String description = "Esse é o canal do Service Listener";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
