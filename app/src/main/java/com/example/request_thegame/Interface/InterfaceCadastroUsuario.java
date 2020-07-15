package com.example.request_thegame.Interface;

import android.graphics.Bitmap;

import com.example.request_thegame.Model.Usuario;

public interface InterfaceCadastroUsuario {

    void setFotoUsuario(Bitmap Foto);

    void setTipoUsuario(String tipoUsuario);

    void setDadosUsuario(String nome, String email, String senha);

    void setDesafios(String codigoDesafio);

    Usuario getUsuario();


}

