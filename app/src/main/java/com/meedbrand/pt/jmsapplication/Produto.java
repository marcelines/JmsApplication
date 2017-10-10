package com.meedbrand.pt.jmsapplication;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Márcio Pinto on 12/10/2015.
 */
public class Produto implements Serializable {

    private int _id;
    private String descPt;
    private String descEn;
    private String descFr;
    private String descIt;
    private String material;
    private String categoria;
    private String categoriaEN;
    private String categoriaFR;
    private String categoriaIT;
    private String ref;
    private String dimensoes;
    private String peso;
    private String linkImagem;
    private Bitmap imagens;
    private Bitmap thumb;

    public Produto() {

    }

    public Produto(int id, String descPt, String descEn, String descFr, String descIt, String material, String categoria, String categoriaEN, String categoriaFR, String categoriaIT, String ref, String dimensoes, String peso, String linkImagem ) {
        _id = id;
        this.descPt = descPt;
        this.descEn = descEn;
        this.descFr = descFr;
        this.descIt = descIt;
        this.material = material;
        this.categoria = categoria;
        this.categoriaEN = categoriaEN;
        this.categoriaFR = categoriaFR;
        this.categoriaIT = categoriaIT;
        this.ref = ref;
        this.dimensoes = dimensoes;
        this.peso = peso;
        this.linkImagem = linkImagem;

    }
    public Produto(String descPt, String descEn, String descFr, String descIt, String material, String categoria,String categoriaEN, String categoriaFR, String categoriaIT, String ref, String dimensoes, String peso) {
        this.descPt = descPt;
        this.descEn = descEn;
        this.descFr = descFr;
        this.descIt = descIt;
        this.material = material;
        this.categoria = categoria;
        this.categoriaEN = categoriaEN;
        this.categoriaFR = categoriaFR;
        this.categoriaIT = categoriaIT;
        this.ref = ref;
        this.dimensoes = dimensoes;
        this.peso = peso;

    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public int getId() {
        return _id;
    }

    public String getCategoriaEN() {
        return categoriaEN;
    }

    public void setCategoriaEN(String categoriaEN) {
        this.categoriaEN = categoriaEN;
    }

    public String getCategoriaFR() {
        return categoriaFR;
    }

    public void setCategoriaFR(String categoriaFR) {
        this.categoriaFR = categoriaFR;
    }

    public String getCategoriaIT() {
        return categoriaIT;
    }

    public void setCategoriaIT(String categoriaIT) {
        this.categoriaIT = categoriaIT;
    }

    public String getDescPt() {
        return descPt;
    }

    public void setDescPt(String descPt) {
        this.descPt = descPt;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescFr() {
        return descFr;
    }

    public void setDescFr(String descFr) {
        this.descFr = descFr;
    }

    public String getDescIt() {
        return descIt;
    }

    public void setDescIt(String descIt) {
        this.descIt = descIt;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Bitmap getImagens() {
        return imagens;
    }

    public void setImagens(Bitmap imagens) {
        this.imagens = imagens;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    public Produto(int id, String descPt, String descEn, String descFr, String descIt, String material, String categoria, String categoriaEN, String categoriaFR, String categoriaIT, String ref, String dimensoes, String peso, Bitmap imagens) {
        _id = id;
        this.descPt = descPt;
        this.descEn = descEn;
        this.descFr = descFr;
        this.descIt = descIt;
        this.material = material;
        this.categoria = categoria;
        this.categoriaEN = categoriaEN;
        this.categoriaFR = categoriaFR;
        this.categoriaIT = categoriaIT;
        this.ref = ref;
        this.dimensoes = dimensoes;
        this.peso = peso;
        this.imagens = imagens;



    }
    public Produto (int id, String ref,Bitmap thumb){
        _id = id;
        this.ref = ref;
        this.thumb = thumb;

    }

    @Override
    public String toString() {
        return "Produto{" + "ref='" + ref + '\'' + '}';
    }


}
