package br.com.itb.pra3.champions_3a_3b_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    SQLiteDatabase banco;
    FloatingActionButton botaoExportar;
    List<Time> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        // Vincular objetos da janela
        botaoExportar = findViewById(R.id.btn_exportar);

        // Definição do caminho do banco
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();
        // Abrindo banco de dados local existente
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null);

        // Montar lista de times
        try{
            times = new ArrayList<>();
            MeuItemRecyclerViewAdapter adapter;
            RecyclerView rvLista = findViewById(R.id.lista_time);
            rvLista.setLayoutManager(new LinearLayoutManager(getBaseContext()));

            // capturar dados do banco de dados local
            String[] colunas = {"_id, nome_time, pais_time, status"};
            // SELECT * FROM TIME
            Cursor cursor = banco.query("Time", colunas, null,
                    null, null, null, null);
            if(cursor != null){
                // capturar dados do cursor
                while(cursor.moveToNext()){
                    times.add(new Time(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3)));
                }

            }else{
                Snackbar.make(rvLista, "DADOS NÃO ENCONTRADOS NA TABELA TIME",
                        Snackbar.LENGTH_LONG).show();
            }

            // carregar RecyclerView com os dados capturados na lista
            adapter = new MeuItemRecyclerViewAdapter(times, ListagemActivity.this, banco);
            adapter.notifyDataSetChanged();
            rvLista.setAdapter(adapter);

        }catch (Exception e){
            e.getMessage();
        }

        // Preparando botão para aguardar o clique
        botaoExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exportar dados da listagem para o SQL Server
                TimeDAO timeDAO = new TimeDAO();

                for(Time timeAtual: times){
                    // Inserindo time da lista no SQL Server
                    int resultado = timeDAO.inserirTime(timeAtual);

                    if(resultado==0){
                        Snackbar.make(v, "Falha na inserção do time " + timeAtual.getNome() + "!",
                                Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Fechar o banco de dados
        banco.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Definição do caminho do banco
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();
        // Abrindo banco de dados local existente
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null);
    }
}