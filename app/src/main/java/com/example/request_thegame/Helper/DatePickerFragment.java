package com.example.request_thegame.Helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextInputEditText data;

    public DatePickerFragment(TextInputEditText data){
        this.data=data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //Setando a data atual como referência
        Calendar c= Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int dayOfMonth= c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year, month, dayOfMonth);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dia;
        String mes;
        String ano;

        if(dayOfMonth<10){
            dia = "0"+dayOfMonth;
        }
        else{
            dia = String.valueOf(dayOfMonth);
        }

        if(month<10){
            month+=1;
            mes="0"+month;
        }
        else {
            mes=String.valueOf(month);
        }

        ano = String.valueOf(year);


        String date = dia+"/"+mes+"/"+ano;
        data.setText(date);
    }


}