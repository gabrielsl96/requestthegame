package com.example.request_thegame.Activ;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.request_thegame.Frag.login_cadastro.LoginUsuarioFragment;
import com.example.request_thegame.Interface.InterfaceCadastroUsuario;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;

public class LoginActivity extends AppCompatActivity implements InterfaceCadastroUsuario {

    private Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_login,new LoginUsuarioFragment()).addToBackStack("Tipo usuario").commit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    @Override
    public void setFotoUsuario(Bitmap fotoUsuario) {
        usuario.setFoto(fotoUsuario);
    }

    @Override
    public void setTipoUsuario(String tipoUsuario) {
        usuario.setTipoUsuario(tipoUsuario);
    }

    @Override
    public void setDadosUsuario(String nome, String email, String senha) {
        usuario.setNomeUsuario(nome);
        usuario.setEmailUsuario(email);
        usuario.setSenhaUsuario(senha);
    }

    @Override
    public void setDesafios(String codigoDesafio){
        usuario.setDesafios(codigoDesafio);
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }
}