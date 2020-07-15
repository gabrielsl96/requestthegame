package com.example.request_thegame.Broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.request_thegame.R;

public class BroadcastListenerDesafioClosed extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Status", "Verificando");

        NotificationManager manager = context.getSystemService(NotificationManager.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        NotificationChannel channel = new NotificationChannel("GABRIEL","GAB",NotificationManager.IMPORTANCE_DEFAULT);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "GABRIEL")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Deu certo!")
                .setContentText("SOY FUEDA!!!")
                .setAutoCancel(true);

        manager.createNotificationChannel(channel);

        manager.notify(0,builder.build());


    }
}
