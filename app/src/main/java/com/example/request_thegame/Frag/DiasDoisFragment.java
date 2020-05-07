package com.example.request_thegame.Frag;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Frag.DiaDois.DiaDoisChildFragment;
import com.example.request_thegame.Model.DiasDesafios;
import com.example.request_thegame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DiasDoisFragment extends Fragment {
    private ViewPager viewPager;
    private DiaDoisChildFragment diaDoisChild = new DiaDoisChildFragment();
    private String statusDiaDois;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dias_dois,container,false);
    }

}
