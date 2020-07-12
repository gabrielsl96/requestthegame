package com.example.request_thegame.Model;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class Usuario {

    private String id, nome, email, senha, tipo;
    private Uri uri_foto;

    public Usuario() {

    }

    public Usuario(final String nome, final String email, final String senha, final String tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Usuario(final String id, final String nome, final String email, final String senha, final String tipo, final Uri uri_foto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.uri_foto = uri_foto;
    }

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

    @Exclude
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
