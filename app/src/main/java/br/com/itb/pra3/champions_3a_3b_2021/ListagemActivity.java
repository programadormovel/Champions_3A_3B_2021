package br.com.itb.pra3.champions_3a_3b_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListagemActivity extends AppCompatActivity {

    SQLiteDatabase banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        // Definição do caminho do banco
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();
        // Abrindo banco de dados local existente
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null);

        // Montar lista de times
        try{
            List<Time> times = new ArrayList<>();
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
            rvLista.setAdapter(adapter);

        }catch (Exception e){
            e.getMessage();
        }
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