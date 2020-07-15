package com.example.request_thegame.Frag.criar_desafios;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Helper.DatePickerFragment;
import com.example.request_thegame.Helper.TimePickerFragment;
import com.example.request_thegame.Interface.InterfaceDesafios;
import com.example.request_thegame.Model.desafio.TerceiroDesafio;
import com.example.request_thegame.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CriarTerceiroDesafioFragment extends Fragment implements OnMapReadyCallback {

    private TextInputEditText inputDataInicio, inputHoraInicio, inputCharada, inputResposta, inputEndereco;
    private GoogleMap map;
    private Geocoder geocoder;
    private ArrayAdapter<Address> adapter;
    private List<Address> addresses = new ArrayList<>();
    private Button btnPesquisar, btnProximo;
    private String localAlvo;
    private LocationManager locationManager;
    private InterfaceDesafios interfaceDesafios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terceiro_desafio, container, false);

        //Setando os componentes da tela
        inputDataInicio = view.findViewById(R.id.input_data_inicio);
        inputHoraInicio = view.findViewById(R.id.input_hora_inicio);
        inputCharada = view.findViewById(R.id.input_charada);
        inputResposta = view.findViewById(R.id.input_resposta);
        inputEndereco = view.findViewById(R.id.input_endereco);
        btnPesquisar = view.findViewById(R.id.btn_pesquisar);
        btnProximo = view.findViewById(R.id.btn_proximo);

        //Abre o fragent para seleção da data de inicio do desafio
        inputDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment(inputDataInicio);
                dialogFragment.show(getChildFragmentManager(), "Data");
            }
        });

        //Abre o fragment para selação da hora inicio do desafio
        inputHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment(inputHoraInicio);
                dialogFragment.show(getChildFragmentManager(), "Tempo");
            }
        });

        //Botão para pesquisar endereço e preencher o campo
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endereco = inputEndereco.getText().toString();
                geocoder = new Geocoder(getContext());

                try {
                    //Busca o endereco indicado pelo usuario desafiante
                    addresses = geocoder.getFromLocationName(endereco, 1);

                    if (addresses.size() > 0) {
                        //Se deu certo
                        Address address = addresses.get(0);
                        inputEndereco.setText(address.getAddressLine(0));

                        //Salva as coordenadas do endereço em uma String
                        localAlvo = address.getLatitude() + "," + address.getLongitude();

                        //Define e atualiza o mapa
                        LatLng position = new LatLng(address.getLatitude(), address.getLongitude());
                        map.clear();
                        map.addMarker(new MarkerOptions().position(position));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18));
                    } else if (addresses.size() == 0) {
                        //Se deu errado
                        inputEndereco.getText().clear();
                        Toast.makeText(getContext(),
                                "Local não encontrado! Atenção a grafia ou especifique melhor o endereço ou nome do local",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Botão para ir para a próxima fragment
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dataInicio = inputDataInicio.getText().toString();
                String horaInicio = inputHoraInicio.getText().toString();
                String charada = inputCharada.getText().toString();
                String resposta = inputResposta.getText().toString();
                String endereco = inputEndereco.getText().toString();


                if (!dataInicio.isEmpty()) {
                    if (!horaInicio.isEmpty()) {
                        if (!charada.isEmpty()) {
                            if (!resposta.isEmpty()) {
                                if (!endereco.isEmpty()) {
                                    if(!localAlvo.isEmpty()) {

                                        TerceiroDesafio terceiroDesafio = new TerceiroDesafio(dataInicio, horaInicio, charada, resposta, localAlvo);
                                        interfaceDesafios.setTerceiroDesafio(terceiroDesafio);

                                        //Ir para a página de cadastro do próximo desafio
                                        getParentFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.container_criar, new CriarQuartoDesafioFragment())
                                                .addToBackStack("Terceiro desafio")
                                                .commit();
                                    }
                                    else{
                                        Toast.makeText(getContext(),
                                                "Antes de prosseguir, pesquise o endereço",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    
                                } else {
                                    Toast.makeText(getContext(),
                                            "Preencha o campo com o endereço",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getContext(),
                                        "Preencha o campo 'Resposta'",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(),
                                    "Preencha o campO 'Charada'",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(),
                                "Preencha o campo com a hora de início do desafio",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Preencha o campo com a data de início do desafio",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Setando o Fragment do mapa
        MapView mapView = getView().findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            Location localUsuario = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            LatLng latLng = new LatLng(localUsuario.getLatitude(), localUsuario.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            interfaceDesafios = (InterfaceDesafios) context;
        } catch (Exception e) {
            Log.d("onAttach", e.toString());
        }
    }

}
