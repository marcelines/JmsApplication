package com.meedbrand.pt.jmsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by Márcio Pinto on 08/10/2015.
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;


    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */

    public Bitmap getImage(int i){

        String qu = "select imagens  from produto where _id=" + i ;
        Cursor cur = database.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public ArrayList<Produto> selectProdutosHospitalar(Context context) {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where categoria = 'Hospitalar' ORDER BY ref ASC", null);
        cursor.moveToFirst();

        AssetManager aMan = context.getAssets();

        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String ref = cursor.getString(10);
            String thumbUri = cursor.getString(14);
            //Uri uri = Uri.parse(thumbUri);
           // Bitmap thumb = MediaStore.Images.Media.getBitmap()
            Bitmap thumb = null;
            try {
                thumb = BitmapFactory.decodeStream(aMan.open(thumbUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Produto p = new Produto(_id, ref, thumb);
            produto.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }

    public ArrayList<Produto> selectProdutosClinico(Context context) {

        Cursor cursor = database.rawQuery("SELECT * FROM produto where categoria = 'Clínico' ORDER BY ref ASC", null);
        cursor.moveToFirst();

        AssetManager aMan = context.getAssets();

        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String ref = cursor.getString(10);
            String thumbUri = cursor.getString(14);
            //Uri uri = Uri.parse(thumbUri);
            // Bitmap thumb = MediaStore.Images.Media.getBitmap()
            Bitmap thumb = null;
            try {
                thumb = BitmapFactory.decodeStream(aMan.open(thumbUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Produto p = new Produto(_id, ref, thumb);
            produto.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosFisioterapia(Context context) {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where categoria = 'Fisioterapia' ORDER BY ref ASC", null);
        cursor.moveToFirst();
        AssetManager aMan = context.getAssets();
        ArrayList<Produto> produto = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String ref = cursor.getString(10);
            String thumbUri = cursor.getString(14);
            //byte[] thumbByte = cursor.getBlob(14);
            //Bitmap thumb = BitmapFactory.decodeByteArray(thumbByte, 0, thumbByte.length);
            Bitmap thumb = null;
            try {
                thumb = BitmapFactory.decodeStream(aMan.open(thumbUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Produto p = new Produto(_id, ref, thumb);
            produto.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosGeriatrico(Context context) {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where categoria = 'Geriátrico' ORDER BY ref ASC", null);
        cursor.moveToFirst();
        AssetManager aMan = context.getAssets();
        ArrayList<Produto> produto = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String ref = cursor.getString(10);
            String thumbUri = cursor.getString(14);

            Bitmap thumb = null;
            try {
                thumb = BitmapFactory.decodeStream(aMan.open(thumbUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Produto p = new Produto(_id, ref, thumb);
            produto.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosAjudas(Context context) {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where categoria = 'Ajudas Técnicas' ORDER BY ref ASC", null);
        cursor.moveToFirst();
        AssetManager aMan = context.getAssets();
        ArrayList<Produto> produto = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String ref = cursor.getString(10);
            String thumbUri = cursor.getString(14);
            //byte[] thumbByte = cursor.getBlob(14);
            //Bitmap thumb = BitmapFactory.decodeByteArray(thumbByte, 0, thumbByte.length);
            Bitmap thumb = null;
            try {
                thumb = BitmapFactory.decodeStream(aMan.open(thumbUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Produto p = new Produto(_id, ref, thumb);
            produto.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosDiversos(Context context) {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where categoria = 'Diversos' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        AssetManager aMan = context.getAssets();
        ArrayList<Produto> produto = new ArrayList<>();

        Global.mListDiversos = new ArrayList();


        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String ref = cursor.getString(10);
            String thumbUri = cursor.getString(14);
            //byte[] thumbByte = cursor.getBlob(14);
            //Bitmap thumb = BitmapFactory.decodeByteArray(thumbByte, 0, thumbByte.length);
            Bitmap thumb = null;
            try {
                thumb = BitmapFactory.decodeStream(aMan.open(thumbUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Produto p = new Produto(_id, ref, thumb);
            produto.add(p);
            Global.mListDiversos.add(_id);
            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public Produto selectProdutoDetalhe(Context context) {
        Cursor cursor = database.rawQuery("select * from produto where _id= '" + Global.idProduto + "'", null);
        AssetManager aMan = context.getAssets();
        cursor.moveToFirst();

        int _id = cursor.getInt(0);
        String desc_pt = cursor.getString(1);
        String desc_en = cursor.getString(2);
        String desc_fr = cursor.getString(3);
        String desc_it = cursor.getString(4);
        String material = cursor.getString(5);
        String categoria = cursor.getString(6);
        String categoriaEn = cursor.getString(7);
        String categoriaFr = cursor.getString(8);
        String categoriaIt = cursor.getString(9);
        String ref = cursor.getString(10);
        String dimensoes = cursor.getString(11);
        String peso = cursor.getString(12);
        String imgUri = cursor.getString(13);
        //Uri uri = Uri.parse(thumbUri);
        // Bitmap thumb = MediaStore.Images.Media.getBitmap()
        Bitmap imagem = null;
        try {
            imagem = BitmapFactory.decodeStream(aMan.open(imgUri));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imagem);

        return p;
    }
    public ArrayList<Cor> selectCores() {

        Cursor cursor = database.rawQuery("SELECT * FROM cores", null);
        cursor.moveToFirst();
        ArrayList<Cor> cores = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String cor = cursor.getString(1);
            String corEn = cursor.getString(2);
            String corFr = cursor.getString(3);
            String corIt = cursor.getString(4);
            String ref = cursor.getString(5);
            byte[] imgByte = cursor.getBlob(6);
            Bitmap imagens = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            byte[] thumbByte = cursor.getBlob(7);
            Bitmap thumb = BitmapFactory.decodeByteArray(thumbByte, 0, thumbByte.length);

            Cor c = new Cor(_id, cor, corEn, corFr, corIt, ref, imagens, thumb);
            cores.add(c);
            cursor.moveToNext();
        }
        return cores;
    }
    public ArrayList<Note> selectNotes() {
        Cursor cursor = database.rawQuery("SELECT * FROM notas", null);
        cursor.moveToFirst();
        ArrayList<Note> note = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String empresa = cursor.getString(1);
            String nome = cursor.getString(2);
            String contacto = cursor.getString(3);
            String texto = cursor.getString(4);
            int vendedor = cursor.getInt(5);
            int rating = cursor.getInt(6);
            String pais = cursor.getString(7);
            int tipo = cursor.getInt(8);

            Note n = new Note(_id, empresa, nome, contacto,pais,texto,rating,vendedor,tipo);
            note.add(n);

            cursor.moveToNext();
        }
        return note;
    }

    public long insereNote(String empresa, String nome, String contacto, String pais, String texto,int rating, int vendedor,int tipo) {

        ContentValues values = new ContentValues();
        values.put("empresa", empresa);
        values.put("nome", nome);
        values.put("contacto", contacto);
        values.put("pais", pais);
        values.put("texto", texto);
        values.put("rating", rating);
        values.put("vendedor", vendedor);
        values.put("tipo", tipo);


        long insertId = database.insert("notas", null, values);

        return insertId;
    }
    public void apagaAmigo(int id) {

        database.delete("notas", "_id = " + id, null);

    }
    public void apagaTudo() {

        database.delete("notas",null,null);

    }
    public Note noteComId(int id) {

        String queryNotas = "SELECT * FROM notas WHERE _id = " + id;

        Cursor cursor = database.rawQuery(queryNotas,null);

        cursor.moveToFirst();

        Note note = new Note();

        note.set_id(cursor.getInt(0));
        note.setEmpresa(cursor.getString(1));
        note.setNome(cursor.getString(2));
        note.setContacto(cursor.getString(3));
        note.setTexto(cursor.getString(4));
        note.setVendedor(cursor.getInt(5));
        note.setRating(cursor.getInt(6));
        note.setPais(cursor.getString(7));
        note.setTipo(cursor.getInt(8));

        cursor.close();

        return note;
    }
    public void actualizaNote(int id, String empresa, String nome, String contacto, String pais, String texto,int rating, int vendedor,int tipo) {

        ContentValues values = new ContentValues();
        values.put("empresa", empresa);
        values.put("nome", nome);
        values.put("contacto", contacto);
        values.put("pais", pais);
        values.put("texto", texto);
        values.put("rating", rating);
        values.put("vendedor", vendedor);
        values.put("tipo", tipo);

        database.update("notas", values, "_id = " + id, null);
    }
    public ArrayList<Produto> selectProdutosComecadosPorAC() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'AC%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorAD() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'AD%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);


            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorAP() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'AP%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorAR() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'AR%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorBI() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'BI%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorCD() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'CD%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorCM() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'CM%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorCR() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'CR%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorDV() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'DV%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorES() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'ES%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorFI() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'FI%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorMA() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'MA%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorMC() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'MC%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorME() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'ME%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorNE() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'NE%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
    public ArrayList<Produto> selectProdutosComecadosPorVE() {
        Cursor cursor = database.rawQuery("SELECT * FROM produto where ref like 'VE%' ORDER BY ref ASC ", null);
        cursor.moveToFirst();
        ArrayList<Produto> produto = new ArrayList<>();

        while (!cursor.isAfterLast()) {

            int _id = cursor.getInt(0);
            String desc_pt = cursor.getString(1);
            String desc_en = cursor.getString(2);
            String desc_fr = cursor.getString(3);
            String desc_it = cursor.getString(4);
            String material = cursor.getString(5);
            String categoria = cursor.getString(6);
            String categoriaEn = cursor.getString(7);
            String categoriaFr = cursor.getString(8);
            String categoriaIt = cursor.getString(9);
            String ref = cursor.getString(10);
            String dimensoes = cursor.getString(11);
            String peso = cursor.getString(12);
            String imgUri = cursor.getString(13);

            Produto p = new Produto(_id, desc_pt, desc_en, desc_fr, desc_it, material, categoria,categoriaEn,categoriaFr,categoriaIt, ref, dimensoes, peso, imgUri);

            produto.add(p);

            cursor.moveToNext();
        }
        cursor.close();
        return produto;
    }
}