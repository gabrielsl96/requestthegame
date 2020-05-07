package com.example.request_thegame.Config;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;

public class ConfiguracaoFirebase {

    static private FirebaseAuth autenticacao;
    static private DatabaseReference referenceDatabase;
    static private DatabaseReference referenceGio;
    static private StorageReference referenceStorage;
    static private FirebaseUser user;

    public static FirebaseAuth getFirebaseAuth(){
        if (autenticacao==null){
            autenticacao=FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    public static DatabaseReference getReferenceDatabase() {
        if(referenceDatabase==null){
            referenceDatabase= FirebaseDatabase.getInstance().getReference();
        }
        return referenceDatabase;
    }

    public static StorageReference getReferenceStorage() {
        if(referenceStorage==null){
            referenceStorage= FirebaseStorage.getInstance().getReference();
        }
        return referenceStorage;
    }

    public static DatabaseReference getReferenceGio() {
        referenceGio=FirebaseDatabase.getInstance().getReference("path/to/geofire");
        return referenceGio;
    }

    public static FirebaseUser getFirebaseUser(){
        user = ConfiguracaoFirebase.getFirebaseAuth().getCurrentUser();
        return getFirebaseUser();
    }

    public static boolean atualizarFoto(Uri url){
        try {
            FirebaseUser user=getFirebaseUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.
                    Builder().
                    setPhotoUri(url).
                    build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Erro", "Erro ao atualizar a foto!");
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

