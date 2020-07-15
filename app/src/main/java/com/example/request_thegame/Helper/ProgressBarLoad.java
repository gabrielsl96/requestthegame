package com.example.request_thegame.Helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.request_thegame.R;

public class ProgressBarLoad {

    private Dialog mDialog;
    private Context context;

    public ProgressBarLoad(Context context,Dialog mDialog) {
        this.mDialog = mDialog;
        this.context = context;
    }

    public void iniciar(){
        mDialog = new Dialog(context);
        //vamos remover o titulo da Dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //vamos carregar o xml personalizado
        mDialog.setContentView(R.layout.dialog_progressbar);
        //DEixamos transparente
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // não permitimos fechar esta dialog
        mDialog.setCancelable(false);
        //temos a instancia do ProgressBar!
        final ProgressBar progressBar = ProgressBar.class.cast(mDialog.findViewById(R.id.progressbar_load));

        mDialog.show();

    }

    public void finalizar(){
        mDialog.dismiss();
    }
}
