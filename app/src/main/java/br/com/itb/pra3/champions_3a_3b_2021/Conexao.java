package br.com.itb.pra3.champions_3a_3b_2021;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conn;

    public static Connection conectar(){
        try{
            // Preparar política de conexão com o banco de dados externo
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            // Verificar se o driver de conexão foi importado
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // Realizar a conexão com o banco de dados externo SQL Server
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.167;" +
                    "databaseName=CHAMPIONS;user=sa;password=123456;");
        }catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }

        return conn;
    }

    public static void desconectar(){
        try{
            conn.close();
        }catch (SQLException e){
            e.getMessage();
        }
    }
}
