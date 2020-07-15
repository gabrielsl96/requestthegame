package com.example.request_thegame.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.request_thegame.Model.Perguntas;
import com.example.request_thegame.R;

import java.util.List;


public class PerguntasAdapter extends RecyclerView.Adapter<PerguntasAdapter.PerguntasViewHolder> {

    List<Perguntas> perguntas;

    public PerguntasAdapter(List<Perguntas> perguntas) {
        this.perguntas = perguntas;
    }

    public class PerguntasViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        public PerguntasViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewPeguntas);
            textView=itemView.findViewById(R.id.pergunta);
        }
    }


    @NonNull
    @Override
    public PerguntasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_perguntas,parent,false);
        PerguntasViewHolder perguntasViewHolder =new PerguntasViewHolder(v);
        return perguntasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PerguntasViewHolder holder, int position) {
        holder.textView.setText("'"+perguntas.get(position).getPergunta()+"'");
    }

    @Override
    public int getItemCount() {
        return perguntas.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}