package br.com.itb.pra3.champions_3a_3b_2021;

public class Time {

    private int _id;
    private String nome;
    private String pais;
    private int status;

    public Time(int _id, String nome, String pais, int status) {
        this._id = _id;
        this.nome = nome;
        this.pais = pais;
        this.status = status;
    }

    public Time(String nome, String pais, int status) {
        this.nome = nome;
        this.pais = pais;
        this.status = status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
