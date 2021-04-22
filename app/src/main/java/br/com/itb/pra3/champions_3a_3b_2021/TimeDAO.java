package br.com.itb.pra3.champions_3a_3b_2021;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO {

    public int inserirTime(Time time){
        int retorno = 0;
        PreparedStatement pst;

        try{
            String inserir = "Insert Into Time (nome_time, pais_time, status) " +
                    "values (?, ?, ?);";

            // Criar objeto de preparação para inserção
            pst = Conexao.conectar().prepareStatement(inserir);
            // Passagem dos parâmetros para inserir na Tabela Time
            pst.setString(1, time.getNome());
            pst.setString(2, time.getPais());
            pst.setInt(3, time.getStatus());
            // Executar Insert
            pst.executeUpdate();
            // Sucesso
            retorno = 1;
        }catch (SQLException e){
            e.getMessage();
            retorno = 0;
        }

        return retorno;
    }

    public List<Time> pesquisarTime(){
        List<Time> retorno = new ArrayList<>();
        PreparedStatement pst;
        ResultSet res;

        try{
            String pesquisa = "Select nome_time, pais_time, status From Time;";

            pst = Conexao.conectar().prepareStatement(pesquisa);
            // Recebimento dos dados pesquisados
            res = pst.executeQuery();
            if(res != null){
                while(res.next()){
                    retorno.add(new Time(res.getString(1),
                            res.getString(2),
                            res.getInt(3)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

}
