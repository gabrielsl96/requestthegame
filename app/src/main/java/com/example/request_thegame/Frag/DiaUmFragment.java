package com.example.request_thegame.Frag;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Helper.GeofenceBroadcastReceiver;
import com.example.request_thegame.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import static com.example.request_thegame.R.color.colorPrimaryDark;
import static com.example.request_thegame.R.color.notification_icon_bg_color;

public class DiaUmFragment extends Fragment implements OnMapReadyCallback {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GoogleMap gMap;
    private View rootView;
    private Button bt_comecar;
    private LatLng usuario, pontoDePartida;
    private GeofencingClient geofencingClient;
    private Geofence geofence;
    private PendingIntent geoPendingIntent;
    private final LatLng destinoFinal = new LatLng(-23.605005, -46.490343);
    private boolean statusInicio=false;

    public DiaUmFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        MapView mapView = (MapView) rootView.findViewById(R.id.map_diaUm);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(getContext());
        bt_comecar = (Button) rootView.findViewById(R.id.btn_comecarTrajeto);
        if(statusInicio){
            bt_comecar.setText("ABRIR ROTA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dia_um, container, false);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gMap.clear();
                usuario = new LatLng(location.getLatitude(), location.getLongitude());
                if(statusInicio){
                    PolylineOptions pl=new PolylineOptions().add(pontoDePartida,destinoFinal);
                    gMap.addPolyline(pl);
                    gMap.addMarker(new MarkerOptions().position(destinoFinal));
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            final LatLng usuario = new LatLng(location.getLatitude(), location.getLongitude());
            gMap.clear();
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usuario, 18));
            gMap.setMyLocationEnabled(true);
            gMap.setTrafficEnabled(true);
            bt_comecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (bt_comecar.getText().equals("VAMOS LÁ")) {
                        geofence= new Geofence.Builder().setRequestId("Escola")
                                .setCircularRegion(destinoFinal.latitude,destinoFinal.longitude,100)
                                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT)
                                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                                .build();
                        geofencingClient.addGeofences(getGeofencingRequest(),getGeoPendingIntent())
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Resultado","BOM");
                                    }
                                }).addOnFailureListener(getActivity(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Resultado","Ruim");
                            }
                        });
                        pontoDePartida=usuario;
                        LatLngBounds latLngBounds = new LatLngBounds(pontoDePartida, destinoFinal);
                        PolylineOptions pl = new PolylineOptions().add(usuario, destinoFinal);
                        gMap.addPolyline(pl);
                        Marker gMaker = gMap.addMarker(new MarkerOptions().position(destinoFinal));
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(), 17));
                        statusInicio=true;
                        bt_comecar.setText("ABRIR ROTA");

                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getUrl(usuario, destinoFinal, "walking")));
                        startActivity(intent);
                    }
                }
            });


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
                }
            }
        }
    }
    private static String getUrl(LatLng ori, LatLng dest, String travelmode) {
        String origin = "origin=" + String.valueOf(ori.latitude + "," + ori.longitude);
        String destination = "destination=" + String.valueOf(dest.latitude + "," + dest.longitude);
        String parameters = origin + "&" + destination + "&" + "dir_action=navigate";
        String url = "https://www.google.com/maps/dir/?api=1&" + parameters;

        return url;

    }
    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder geoRequest=new GeofencingRequest.Builder();
        geoRequest.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        geoRequest.addGeofence(geofence);
        return geoRequest.build();

    }
    private PendingIntent getGeoPendingIntent(){
        if(geoPendingIntent!=null){
            return geoPendingIntent;

        }
        Intent intent=new Intent(getContext(), GeofenceBroadcastReceiver.class);
        geoPendingIntent=PendingIntent.getBroadcast(getContext(),1000,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return geoPendingIntent;
    }
}