package com.example.request_thegame.DAO;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.request_thegame.Frag.criar_desafios.TelaApresentaCodigoFragment;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.ProgressBarLoad;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class DesafioDAO {
    private static DatabaseReference databaseReference = ConfigFirebase.getDatabaseReference();
    private static FirebaseAuth firebaseAuth = ConfigFirebase.getFirebaseAuth();

    public void create(final Context context, final FragmentManager fragmentManager, final Desafio desafio, final ProgressBarLoad progressBarLoad){
        progressBarLoad.iniciar();
        final String idUsuario = firebaseAuth.getCurrentUser().getUid();
        final String codigoDesafio=desafio.getCodigoDesafio();

        databaseReference
                .child("Desafios")
                .child(idUsuario)
                .child(codigoDesafio)
                .setValue(desafio)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBarLoad.finalizar();

                    Bundle args = new Bundle();
                    args.putString("codigoDesafio", idUsuario+"/"+codigoDesafio);

                    Fragment telaApresentaçãoCodigo  = new TelaApresentaCodigoFragment();
                    telaApresentaçãoCodigo.setArguments(args);
                    fragmentManager.popBackStack();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container_criar,telaApresentaçãoCodigo)
                            .commit();

                }
                else{
                    progressBarLoad.finalizar();
                    String exception = "";

                    try {
                        throw task.getException();
                    }
                    catch (DatabaseException e){
                        exception = "Erro ao salvar:"+e;
                    }
                    catch (Exception e) {
                        exception="Erro ao salvar:"+e;
                    }

                    Toast.makeText(context,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void delete(final Context context, Desafio desafio){

        if(desafio.getStatusDesafios().getStatusDesafio().equals("Não Iniciado")) {
            databaseReference
                    .child("Desafios")
                    .child(firebaseAuth.getUid())
                    .child(desafio.getCodigoDesafio())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context,
                                    "Desafio removido com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Toast.makeText(context,
                    "Esse desafio já foi iniciado e não pode ser excluído!",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
