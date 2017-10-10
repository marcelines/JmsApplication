package com.meedbrand.pt.jmsapplication;

import android.graphics.Bitmap;

/**
 * Created by Márcio Pinto on 15/10/2015.
 */
public class Cor {

    private int _id;
    private String cor;
    private String corEn;
    private String corFr;
    private String corIt;
    private String ref;
    private Bitmap imagens;
    private Bitmap thumb;

    public Cor() {

    }

    public Cor(String cor, String ref, Bitmap imagens, Bitmap thumb ) {

        this.cor = cor;
        this.ref = ref;
        this.imagens = imagens;
        this.thumb = thumb;
    }

    public int getId() {
        return _id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCorEn() {
        return corEn;
    }

    public void setCorEn(String corEn) {
        this.corEn = corEn;
    }

    public String getCorFr() {
        return corFr;
    }

    public void setCorFr(String corFr) {
        this.corFr = corFr;
    }

    public String getCorIt() {
        return corIt;
    }

    public void setCorIt(String corIt) {
        this.corIt = corIt;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public Cor(int id, String cor, String corEn, String corFr, String corIt, String ref, Bitmap imagens, Bitmap thumb) {
        _id = id;
        this.cor = cor;
        this.corEn = corEn;
        this.corFr = corFr;
        this.corIt = corIt;
        this.ref = ref;
        this.imagens = imagens;
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "Cor{" + "ref='" + ref + '\'' + '}';
    }


}
