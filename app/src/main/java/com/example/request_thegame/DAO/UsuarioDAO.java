package com.example.request_thegame.DAO;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.request_thegame.Config.ConfiguracaoFirebase;
import com.example.request_thegame.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

public class UsuarioDAO {

    private FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAuth();
    private FirebaseUser user=getCurrentUser();
    private DatabaseReference dtReference=ConfiguracaoFirebase.getReferenceDatabase();


    public static FirebaseUser getCurrentUser(){
        FirebaseAuth usuario= ConfiguracaoFirebase.getFirebaseAuth();
        return usuario.getCurrentUser();
    }

    public void criarUsuario(final Usuario u, final Context c){
            auth.createUserWithEmailAndPassword(
                    u.getEmail(),
                    u.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        atualizarNomeUsuario(String.valueOf(u.getNome()));
                        u.setId(auth.getUid());
                        updateUserDatabase(u);
                    }
                    else{
                        String excecao="";
                        try{
                            throw task.getException();
                        } catch(FirebaseAuthUserCollisionException e){
                            excecao="Já existe um usuário com esse endereço de email";
                        }catch(FirebaseAuthWeakPasswordException e){
                            excecao = "Digite uma senha com no mínimo 6 caracteres";
                        }catch(Exception e){
                            excecao="Erro ao cadastrar:"+e.getMessage();
                        }

                        Toast.makeText(c,
                                excecao,
                                Toast.LENGTH_LONG).show();
                    }
                }

            });

    }

    private boolean atualizarNomeUsuario(String nome){

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

    private boolean atualizarFotoUsuario(Uri uri){
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

    private boolean updateUserDatabase(Usuario u){
        try {
            dtReference.child("Usuarios")
                    .child(u.getId())
                    .setValue(u);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
