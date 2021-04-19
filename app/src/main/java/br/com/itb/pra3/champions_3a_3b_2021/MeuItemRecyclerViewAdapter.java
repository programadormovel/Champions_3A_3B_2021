package br.com.itb.pra3.champions_3a_3b_2021;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MeuItemRecyclerViewAdapter extends
        RecyclerView.Adapter<MeuItemRecyclerViewAdapter.ViewHolder> {

    private List<Time> mValores;
    private Context mContext;
    private SQLiteDatabase mBanco;

    public MeuItemRecyclerViewAdapter(List<Time> mValores, Context context, SQLiteDatabase banco) {
        this.mValores = mValores;
        this.mContext = context;
        this.mBanco = banco;
    }

    @NonNull
    @Override
    public MeuItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listagem_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MeuItemRecyclerViewAdapter.ViewHolder holder,
            final int position) {
        holder.mTime = mValores.get(position);

        holder.mNome.setText(mValores.get(position).getNome());

        holder.mPais.setText(mValores.get(position).getPais());

        holder.mStatus.setText(String.valueOf(mValores.get(position).getStatus()));

        // Tornar cada item da lista clicável
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trocar para a janela de Alteração
                Intent it = new Intent(mContext, AlterarActivity.class);
                // Passagem de dados de uma janela para outra
                it.putExtra("_id", mValores.get(position).get_id());
                it.putExtra("nome_time", mValores.get(position).getNome());
                it.putExtra("pais_time", mValores.get(position).getPais());
                it.putExtra("status", mValores.get(position).getStatus());
                mContext.startActivity(it);
            }
        });

        holder.mBotaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - CRIAR ALERTA PARA EXCLUIR E PROGRAMAR REFRESH

                // Informar argumentos
                String[] args = {String.valueOf(mValores.get(position).get_id())};

                // Implementar exclusão do item solicitado
                long resultado = mBanco.delete("Time", "_id = ?", args);

                if(resultado > 0)
                    Snackbar.make(v, "TIME EXCLUÍDO", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "ERRO AO EXCLUIR", Snackbar.LENGTH_LONG).show();

                // Solicitar exclusão
                //Snackbar.make(v, "Excluir solicitado", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public AppCompatTextView mNome;
        public AppCompatTextView mPais;
        public AppCompatTextView mStatus;
        public Time mTime;
        public FloatingActionButton mBotaoExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mNome = itemView.findViewById(R.id.item_nome_time);
            mPais = itemView.findViewById(R.id.item_pais_time);
            mStatus = itemView.findViewById(R.id.item_status_time);
            mBotaoExcluir = itemView.findViewById(R.id.btn_excluir);
        }
    }
}
