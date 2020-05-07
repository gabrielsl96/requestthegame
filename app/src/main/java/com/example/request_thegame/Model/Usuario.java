package com.example.request_thegame.Model;

import android.net.Uri;

public class Usuario {

    private String id, nome, email, senha, tipo;
    private Uri uri_foto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Uri getUri_foto() {
        return uri_foto;
    }

    public void setUri_foto(Uri uri_foto) {
        this.uri_foto = uri_foto;
    }
}
