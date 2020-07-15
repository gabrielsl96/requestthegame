package com.example.request_thegame.Helper;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TextInputEditText inputHora;

    public TimePickerFragment(TextInputEditText inputHora) {
        this.inputHora = inputHora;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c =Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this,hour,minute,true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String hora;
        String minuto;

        if(hourOfDay<10){
            hora = "0"+hourOfDay;
        }
        else{
            hora = String.valueOf(hourOfDay);
        }

        if(minute<10){
            minuto = "0"+minute;
        }
        else{
            minuto = String.valueOf(minute);
        }

        String horaEscolhida = hora+":"+minuto;
        inputHora.setText(horaEscolhida);

    }
}