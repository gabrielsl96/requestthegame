package com.example.request_thegame.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.request_thegame.Activ.MainActivity;
import com.example.request_thegame.DAO.UsuarioDAO;
import com.example.request_thegame.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

public class ProcessosFirebase {

    private DatabaseReference reference = ConfigFirebase.getDatabaseReference();
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAuth();
    private Dialog dialog;
    private ProgressBarLoad load;

    public void login(final Context context, final Activity activity, String email, String senha){
        load = new ProgressBarLoad(context,dialog);
        load.iniciar();

        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            load.finalizar();
                            activity.startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        } else {
                            load.finalizar();

                            String exception ="";

                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidUserException e){
                                exception ="Erro: Usuário e/ou senha inválido(s)!";
                            } catch (Exception e) {
                                exception ="Erro:"+e.getMessage();
                            }

                            Toast.makeText(context,
                                    exception,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void cadastrarUsuario(final Context context, final Activity activity, final Usuario usuario){

        auth.createUserWithEmailAndPassword(
                usuario.getEmailUsuario(),
                usuario.getSenhaUsuario()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    usuarioDAO.create(context,activity,usuario);

                }

                else{
                    load.finalizar();
                    String exception ="";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        exception = "ERRO AO CADASTRAR: Senha fraca ou com menos de seis caracteres";
                    } catch (FirebaseAuthUserCollisionException e){
                        exception = "ERRO AO CADASTRAR: Email já cadastrado";
                    }
                    catch (FirebaseAuthException e){
                        exception = e.getMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
