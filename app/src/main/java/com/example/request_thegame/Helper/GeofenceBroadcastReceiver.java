package com.example.request_thegame.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "Deu ruim no Broadcast");
            return;
        }
        int geofanceTransition=geofencingEvent.getGeofenceTransition();

        if(geofanceTransition!= Geofence.GEOFENCE_TRANSITION_ENTER){
           Log.i("Localização","Está fora!");
        }
    }
}
