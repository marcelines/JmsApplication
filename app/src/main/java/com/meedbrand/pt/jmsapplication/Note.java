package com.meedbrand.pt.jmsapplication;

/**
 * Created by João daSilva on 21/10/2015.
 */
public class Note {

    private int _id;
    private String empresa;
    private String nome;
    private String contacto;
    private String pais;
    private String texto;
    private int rating;
    private int vendedor;
    private int tipo;

    public Note() {

    }

    public Note(int _id, String empresa, String nome, String contacto,String pais, String texto,int rating, int vendedor, int tipo) {
        this._id = _id;
        this.empresa = empresa;
        this.nome = nome;
        this.contacto = contacto;
        this.pais = pais;
        this.texto = texto;
        this.rating = rating;
        this.vendedor = vendedor;
        this.tipo = tipo;
    }
    public Note(String empresa, String nome, String contacto,String pais, String texto, int rating, int vendedor, int tipo) {
        this.empresa = empresa;
        this.nome = nome;
        this.contacto = contacto;
        this.pais = pais;
        this.texto = texto;
        this.rating = rating;
        this.vendedor = vendedor;
        this.tipo = tipo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Cliente:{" + "ref='" + getEmpresa() + '\'' + '}';
    }

}

