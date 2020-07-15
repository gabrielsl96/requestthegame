package com.example.request_thegame.DAO;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.request_thegame.Activ.MainActivity;
import com.example.request_thegame.Helper.ConfigFirebase;
import com.example.request_thegame.Helper.ProgressBarLoad;
import com.example.request_thegame.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UsuarioDAO {
    private static DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private static FirebaseAuth auth = ConfigFirebase.getFirebaseAuth();
    private static StorageReference storage = ConfigFirebase.getStorageReference();
    private Dialog dialog;

    public void create(final Context context, final Activity activity, final Usuario usuario){
        final ProgressBarLoad load = new ProgressBarLoad(context,dialog);
        load.iniciar();


        usuario.setIdUsuario(auth.getCurrentUser().getUid());
        reference
                .child("Usuários")
                .child(auth.getUid())
                .setValue(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        salvarAtualizarImagemUsuario(context,activity,usuario.getFoto(),storage,auth,load);
                        atualizarStatusDesafio(usuario);
                    }
                });

    }

    public static Usuario reload(final Context context, final Usuario usuario){
        final FirebaseAuth auth=ConfigFirebase.getFirebaseAuth();
        DatabaseReference reference=ConfigFirebase.getDatabaseReference();



        return usuario;
    }

    private boolean salvarAtualizarImagemUsuario(final Context context, final Activity activity, Bitmap imagemUsuario, StorageReference storage, final FirebaseAuth auth, final ProgressBarLoad load){

        String idUsuario = auth.getCurrentUser().getUid();

        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagemUsuario.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] dataImagem = baos.toByteArray();

            //Pega a intancia do Storage Firebase e salva o arquivo
           final StorageReference reference= storage.child("Fotos")
                    .child("Perfil")
                    .child(idUsuario)
                    .child("foto-usuario.jpg");

            final UploadTask uploadTask = reference.putBytes(dataImagem);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        load.finalizar();
                        context.startActivity(new Intent(context, MainActivity.class));
                        activity.finish();
                    }

                    else{
                        load.finalizar();
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void atualizarStatusDesafio(Usuario usuario){

        if(usuario.getDesafio()!=null) {

            String [] desafio = usuario.getDesafio().split("/");

            reference
                    .child("Desafios")
                    .child(desafio[0])
                    .child(desafio[1])
                    .child("statusDesafios")
                    .child("statusDesafio")
                    .setValue("Primeiro acesso");
        }

    }
}
