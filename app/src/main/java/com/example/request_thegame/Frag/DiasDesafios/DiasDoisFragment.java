package com.example.request_thegame.Frag.DiasDesafios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.request_thegame.Activ.DiaDoisQuizActivity;
import com.example.request_thegame.R;

public class DiasDoisFragment extends Fragment {
    private Button btn_quiz;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dias_dois, container, false);
        btn_quiz= view.findViewById(R.id.buttonQuiz);
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity fga=getActivity();
                startActivity(new Intent(fga, DiaDoisQuizActivity.class));
            }
        });
        return view;
    }
}