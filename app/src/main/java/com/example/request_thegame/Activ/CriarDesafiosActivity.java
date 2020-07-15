package com.example.request_thegame.Activ;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.request_thegame.Frag.criar_desafios.CriarInformacoesDesafioFragment;
import com.example.request_thegame.Interface.InterfaceDesafios;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.desafio.InformacoesDesafio;
import com.example.request_thegame.Model.desafio.MensagemFinal;
import com.example.request_thegame.Model.desafio.PrimeiroDesafio;
import com.example.request_thegame.Model.desafio.QuartoDesafio;
import com.example.request_thegame.Model.desafio.SegundoDesafio;
import com.example.request_thegame.Model.desafio.StatusDesafio;
import com.example.request_thegame.Model.desafio.TerceiroDesafio;
import com.example.request_thegame.R;

public class CriarDesafiosActivity extends AppCompatActivity implements InterfaceDesafios {

    private String idDesa;
    private InformacoesDesafio informaDesafio;
    private StatusDesafio staDesafio;
    private PrimeiroDesafio primDesafio;
    private SegundoDesafio segDesafio;
    private TerceiroDesafio terDesafio;
    private QuartoDesafio quarDesafio;
    private MensagemFinal mensFinal;
    private Desafio desafios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_desafios);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_criar, new CriarInformacoesDesafioFragment())
                .commit();
    }


    @Override
    public void setIdDesafio(String idDesafio) {
        idDesa=idDesafio;
    }

    @Override
    public void setInformacoesDeasafio(InformacoesDesafio informacoesDeasafio) { informaDesafio=informacoesDeasafio; }

    @Override
    public void setStatusDesafio(StatusDesafio statusDesafio) { staDesafio=statusDesafio;}

    @Override
    public void setPrimeiroDesafio(PrimeiroDesafio primeiroDesafio) {
        primDesafio=primeiroDesafio;
    }

    @Override
    public void setSegundoDesafio(SegundoDesafio segundoDesafio) {
        segDesafio=segundoDesafio;
    }

    @Override
    public void setTerceiroDesafio(TerceiroDesafio terceiroDesafio) {
        terDesafio=terceiroDesafio;
    }

    @Override
    public void setQuartoDesafio(QuartoDesafio quartoDesafio) {
        quarDesafio=quartoDesafio;
    }

    @Override
    public void setMensagemFinal(MensagemFinal mensagemFinal) {
        mensFinal=mensagemFinal;
    }

    @Override
    public Desafio getDesafio() {
        desafios=new Desafio(idDesa,informaDesafio,staDesafio,primDesafio,segDesafio,terDesafio,quarDesafio,mensFinal);
      return desafios;
    }

}