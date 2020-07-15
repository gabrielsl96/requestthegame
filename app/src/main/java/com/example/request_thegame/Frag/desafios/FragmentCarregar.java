package com.example.request_thegame.Frag.desafios;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Frag.desafios.Bloqueios.BloqueadoFragment;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.ProgressBarLoad;
import com.example.request_thegame.Interface.InterfacePainelDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FragmentCarregar extends Fragment {
    private DatabaseReference reference= ConfigFirebase.getDatabaseReference();
    private InterfacePainelDesafios painelDesafios;
    private Desafio desafio;
    private String[] idDesafio;
    private Dialog dialog;
    private Usuario usuario;
    private ProgressBarLoad load;
    private ValueEventListener valueEventListener;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        return null;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    @Override
    public void onStop() {
        super.onStop();
        reference.removeEventListener(valueEventListener);
    }
}