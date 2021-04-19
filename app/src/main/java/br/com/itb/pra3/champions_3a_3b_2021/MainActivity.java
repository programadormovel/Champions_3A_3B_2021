package br.com.itb.pra3.champions_3a_3b_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    // Declarar objetos java
    AppCompatEditText edtNome, edtPais;
    AppCompatButton botao;
    SQLiteDatabase banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Caminho do banco a partir da API 23
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        // Criar banco de dados local (SQLite)
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null, null);

        // Criar tabela no banco de dados local
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS [Time] (");
        query.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append("[nome_time] VARCHAR(256) NOT NULL, ");
        query.append("[pais_time] VARCHAR(256) NOT NULL, ");
        query.append("[status] INTEGER NOT NULL);");
        banco.execSQL(query.toString());

        // Capturar dados da tabela
        edtNome = findViewById(R.id.nome_time);
        edtPais = findViewById(R.id.pais_time);
        botao = findViewById(R.id.btn_salvar);

        // Acionar o botão
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inserir dados na tabela do banco de dados local
                ContentValues valores = new ContentValues();
                valores.put("nome_time", edtNome.getText().toString());
                valores.put("pais_time", edtPais.getText().toString());
                valores.put("status", 1);

                long resultado = banco.insert("Time", "_id", valores);

                if(resultado > 0)
                    Snackbar.make(v, "SUCESSO", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "ERRO AO INSERIR", Snackbar.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btn_pesquisar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Chamar a tela de listagem
                Intent it = new Intent(MainActivity.this, ListagemActivity.class);
                startActivity(it);
            }
        });

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

        // Caminho do banco a partir da API 23
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        // Criar banco de dados local (SQLite)
        banco = SQLiteDatabase.openOrCreateDatabase(caminho, null, null);
    }
}