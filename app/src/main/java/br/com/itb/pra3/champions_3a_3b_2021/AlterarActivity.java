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

public class AlterarActivity extends AppCompatActivity {

    String nomeTime, paisTime;
    int _id, status;
    AppCompatEditText edtNome, edtPais, edtStatus;
    AppCompatButton botaoAlterar;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        //Recebendo dados da outra janela
        Intent intent = getIntent();
        nomeTime = intent.getStringExtra("nome_time");
        paisTime = intent.getStringExtra("pais_time");
        _id = intent.getIntExtra("_id", 0);
        status = intent.getIntExtra("status", -1);

        // Vinculos estabelecidos
        edtNome = findViewById(R.id.nome_time_alterar);
        edtPais = findViewById(R.id.pais_time_alterar);
        edtStatus = findViewById(R.id.status_time_alterar);
        botaoAlterar = findViewById(R.id.btn_alterar);

        edtNome.setText(nomeTime);
        edtPais.setText(paisTime);
        edtStatus.setText(String.valueOf(status));

        // Abrir o banco de dados local
        String caminho = getBaseContext().getDatabasePath("champions.db").getPath();

        db = SQLiteDatabase.openOrCreateDatabase(caminho, null);

        // Alterar os dados do banco de dados local
        botaoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Carregar cursor de valores
                ContentValues valores = new ContentValues();
                valores.put("nome_time", edtNome.getText().toString());
                valores.put("pais_time", edtPais.getText().toString());
                valores.put("status", edtStatus.getText().toString());

                // Informar o argumento do critério
                String[] args = {String.valueOf(_id)};

                // Acionar a alteração da tabela Time, que esta no banco de dados local
                long resultado = db.update("Time", valores, "_id = ?", args);

                if(resultado > 0)
                    Snackbar.make(v, "TIME ALTERADO", Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "ERRO AO ALTERAR", Snackbar.LENGTH_LONG).show();
            }
        });




    }
}