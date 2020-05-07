package com.example.request_thegame.DAO;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioDAO {

    private FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
    private FirebaseUser user=getCurrentUser();


    public static FirebaseUser getCurrentUser(){
        FirebaseAuth usuario= ConfiguracaoFirebase.getFirebaseAuth();
        return usuario.getCurrentUser();
    }

    public void criarUsuario(final Usuario u){
        auth.createUserWithEmailAndPassword(
                u.getEmail(),
                u.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    atualizarNomeUsuario(String.valueOf(u.getNome()));
                }
            }
        });
    }

    public boolean atualizarNomeUsuario(String nome){

        try{

            UserProfileChangeRequest profile =new UserProfileChangeRequest.Builder().
                    setDisplayName(nome)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Erro","Erro ao atualizar nome de Usuário!");
                    }
                }
            });
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public boolean atualizarFotoUsuario(Uri uri){
        try{
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().
                    setPhotoUri(uri)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("Sucesso", "Sucesso ao atualizar imagem do usuário!");
                    }
                }
            });
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
