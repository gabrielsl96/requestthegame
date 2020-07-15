package com.example.request_thegame.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.request_thegame.Services.ServiceListenerDesafio;

public class BroadcastListenerDesafio extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
            Intent pushIntent = new Intent(context, ServiceListenerDesafio.class);
            context.startService(pushIntent);
        }
    }
}
