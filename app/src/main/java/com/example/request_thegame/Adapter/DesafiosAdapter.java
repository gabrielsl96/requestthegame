package com.example.request_thegame.Adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.request_thegame.Activ.PainelDesafiosActivity;
import com.example.request_thegame.DAO.DesafioDAO;
import com.example.request_thegame.Model.Desafio;
import com.example.request_thegame.Model.Usuario;
import com.example.request_thegame.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DesafiosAdapter extends RecyclerView.Adapter<DesafiosAdapter.MyViewHolder> {

    List<Desafio> desafios;
    Usuario usuario;
    Context context;

    public DesafiosAdapter(Context context, List<Desafio> desafios, Usuario usuario) {
        this.desafios = desafios;
        this.usuario=usuario;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardview;
        TextView idDesafio, nomeDesafiado, statusDesafio;
        ProgressBar progressBarDesafioUm, progressBarDesafioDois, progressBarDesafioTres, progressBarDesafioQuatro ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview = itemView.findViewById(R.id.cardview_desafios);
            idDesafio=itemView.findViewById(R.id.text_id_desafio);
            nomeDesafiado=itemView.findViewById(R.id.text_nome_desafiado);
            statusDesafio=itemView.findViewById(R.id.text_status_desafio);
            progressBarDesafioUm=itemView.findViewById(R.id.progressbar1);
            progressBarDesafioDois=itemView.findViewById(R.id.progressbar2);
            progressBarDesafioTres=itemView.findViewById(R.id.progressbar3);
            progressBarDesafioQuatro=itemView.findViewById(R.id.progressbar4);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardview_desafios, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.idDesafio.setText(desafios.get(position).getCodigoDesafio());
        holder.nomeDesafiado.setText(desafios.get(position).getInformacoesDesafio().getNomeDesafiado());
        holder.statusDesafio.setText(desafios.get(position).getStatusDesafios().getStatusDesafio());


        String textoStatus = holder.statusDesafio.getText().toString();


        //Qundo clicado
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuario.getTipoUsuario().equals("Desafiado")) {

                    String[] data = desafios.get(position).getInformacoesDesafio().getDataInicio().split("/");
                    String[] hora = desafios.get(position).getInformacoesDesafio().getHoraInicio().split(":");

                    Calendar dataInicio = Calendar.getInstance();

                    dataInicio.set(Integer.parseInt(data[0]),
                            Integer.parseInt(data[1]),
                            Integer.parseInt(data[2]),
                            Integer.parseInt(hora[0]),
                            Integer.parseInt(hora[1]));

                    Calendar dataAutal = Calendar.getInstance();

                    Date inicio = dataInicio.getTime();
                    Date dataAtual = dataAutal.getTime();

                    if (inicio.before(dataAtual)) {

                        Intent in = new Intent(holder.itemView.getContext(), PainelDesafiosActivity.class);
                        in.putExtra("usuario", usuario);
                        in.putExtra("desafio",desafios.get(0));

                        holder
                                .itemView
                                .getContext()
                                .startActivity(in);
                    }
                    else{
                        Toast.makeText(context,
                                "Desafio ainda não começou! Aguarde!",
                                Toast.LENGTH_SHORT).show();
                    }

                }

                else if(usuario.getTipoUsuario().equals("Desafiante")){
                    PopupMenu popupMenu = new PopupMenu(context,holder.itemView);
                    popupMenu.inflate(R.menu.menu_cardview_desafio);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()){
                                case R.id.excluir:
                                    DesafioDAO dao = new DesafioDAO();
                                    dao.delete(context,desafios.get(position));

                                    break;

                                case R.id.copiar:
                                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                                    ClipData clipData = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN,usuario.getIdUsuario()+"/"+desafios.get(position).getCodigoDesafio());

                                    cm.setPrimaryClip(clipData);

                                    Toast.makeText(context,
                                            "Código copiado para área de transferência!",
                                            Toast.LENGTH_SHORT).show();

                                    break;

                                case R.id.compartilhar:
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Você está preparado(a) para o deasafio?\nVenha tentar a sorte em Request - The GAME.\nO seu código-chave é:\n\n"+usuario.getIdUsuario()+"/"+desafios.get(position).getCodigoDesafio());

                                    sendIntent.setType("text/plain");

                                    Intent sharedIntent = Intent.createChooser(sendIntent, null);
                                    context.startActivity(sharedIntent);

                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            }
        });


        if(textoStatus.equals("Executando primeiro desafio")){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.YELLOW);
        }

        else if(textoStatus.equals("Concluído primeiro desafio") ||
                textoStatus.equals("Desafio dois disponível!") ){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

        }

        else if(textoStatus.equals("Executando segundo desafio")){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.YELLOW);
        }

        else if(textoStatus.equals("Concluído segundo desafio")||
                textoStatus.equals("Desafio três disponível!")){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.GREEN);
        }

        else if(textoStatus.equals("Executando terceiro desafio")){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioTres.setProgress(100,false);
            holder.progressBarDesafioTres.getProgressDrawable().setTint(Color.YELLOW);
        }

        else if(textoStatus.equals("Concluído terceiro desafio")||
                textoStatus.equals("Desafio quatro disponível!")){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioTres.setProgress(100,false);
            holder.progressBarDesafioTres.getProgressDrawable().setTint(Color.GREEN);
        }

        else if(textoStatus.equals("Executando quarto desafio")){
            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioTres.setProgress(100,false);
            holder.progressBarDesafioTres.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioQuatro.setProgress(100,false);
            holder.progressBarDesafioQuatro.getProgressDrawable().setTint(Color.YELLOW);
        }

        else if(textoStatus.equals("Desafios concluídos! Lendo mensagem")){

            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioTres.setProgress(100,false);
            holder.progressBarDesafioTres.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioQuatro.setProgress(100,false);
            holder.progressBarDesafioQuatro.getProgressDrawable().setTint(Color.GREEN);
        }

        else if(textoStatus.equals("Request concluído!!")){

            holder.progressBarDesafioUm.setProgress(100,false);
            holder.progressBarDesafioUm.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioDois.setProgress(100,false);
            holder.progressBarDesafioDois.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioTres.setProgress(100,false);
            holder.progressBarDesafioTres.getProgressDrawable().setTint(Color.GREEN);

            holder.progressBarDesafioQuatro.setProgress(100,false);
            holder.progressBarDesafioQuatro.getProgressDrawable().setTint(Color.GREEN);

            holder.statusDesafio.setTextColor(Color.GREEN);
        }


    }

    @Override
    public int getItemCount() {
        return desafios.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
