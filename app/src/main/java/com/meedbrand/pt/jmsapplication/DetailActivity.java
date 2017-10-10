package com.meedbrand.pt.jmsapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements Serializable,NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView rvCores;
    private RecyclerView.Adapter mAdapterCores;
    private ArrayList<Cor> listaCores = null;
    //private ArrayList<Produto> listaExp = null;

    MenuItem catalogo;
    MenuItem notas;
    MenuItem video;

    String descPt;
    String descEn;
    String descFr;
    String descIt;
    String material;
    String categoria;
    String categoriaEN;
    String categoriaFR;
    String categoriaIT;
    String ref;
    String dimensoes;
    String peso;
    Bitmap img;
    String linkImagem;
    AssetManager aMan;


    TextView tvDesc;
    TextView tvMaterial;
    TextView tvCategoria;
    TextView tvRef;
    TextView tvDimensoes;
    TextView tvPeso;
    TouchImageView ivProduto;

    TextView lbDesc;
    TextView lbMaterial;
    TextView lbCategoria;
    TextView lbRef;
    TextView lbDimensoes;
    TextView lbPeso;
    TextView tvBarra;
    Bitmap bmPrint;
    Button btnPdf;
    Button btnAnterior;
    Button btnSeguinte;
    SeekBar seekBarImagens;
    DatabaseAccess databaseAccess;
    NavigationView navigationView;
    int index;
    Produto p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        btnPdf = (Button)findViewById(R.id.btnPdf);

        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkAvailable()) {

                    if (Global.idioma == "PT") {

                        Toast.makeText(getApplicationContext(), "Erro! Por favor verifique a sua ligação à internet.", Toast.LENGTH_LONG).show();
                    } else if (Global.idioma == "EN") {

                        Toast.makeText(getApplicationContext(), "Error! Check your internet connection.", Toast.LENGTH_LONG).show();
                    } else if (Global.idioma == "FR") {
                        Toast.makeText(getApplicationContext(), "Erreur! S'il vous plaît vérifier votre connexion Internet.", Toast.LENGTH_LONG).show();
                    } else if (Global.idioma == "IT") {
                        Toast.makeText(getApplicationContext(), "Errore! Controlla la tua connessione a Internet.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    bmPrint = v1.getDrawingCache();
                    saveBitmap(bmPrint);
                }


            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        catalogo = navigationView.getMenu().findItem(R.id.nav_catalogo);
        notas = navigationView.getMenu().findItem(R.id.nav_Notas);
        video = navigationView.getMenu().findItem(R.id.nav_video);
        seekBarImagens =(SeekBar)findViewById(R.id.seekBarImagens);
        seekBarImagens.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.rgb(6, 53, 95), PorterDuff.Mode.MULTIPLY));

        //seekBarImagens.getIndeterminateDrawable().setColorFilter(new LightingColorFilter(Color.rgb(6, 53, 95), Color.rgb(6,53,95)));


        catalogo.setChecked(true);



        Global.idProduto = getIntent().getIntExtra("produto", 0);

        // Toast.makeText(getApplicationContext(),"REF: = " + novaRef,Toast.LENGTH_LONG).show();
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        p = databaseAccess.selectProdutoDetalhe(this);
        listaCores = databaseAccess.selectCores();
        databaseAccess.close();



        descPt = p.getDescPt();
        descEn = p.getDescEn();
        descFr = p.getDescFr();
        descIt = p.getDescIt();
        material = p.getMaterial();
        categoria = p.getCategoria();
        categoriaEN = p.getCategoriaEN();
        categoriaFR = p.getCategoriaFR();
        categoriaIT = p.getCategoriaIT();
        ref = p.getRef();
        dimensoes = p.getDimensoes();
        peso = p.getPeso();
        img = p.getImagens();

        tvDesc = (TextView) findViewById(R.id.tvDescProduto);
        tvMaterial = (TextView) findViewById(R.id.tvMaterialProduto);
        tvCategoria = (TextView) findViewById(R.id.tvCategoriaProduto);
        tvRef = (TextView) findViewById(R.id.tvRefProduto);
        tvDimensoes = (TextView) findViewById(R.id.tvDimensoesProduto);
        tvPeso = (TextView) findViewById(R.id.tvPesoProduto);
        ivProduto =(TouchImageView)findViewById(R.id.ivProduto);
        lbDesc = (TextView)findViewById(R.id.lbDesc);
        lbMaterial = (TextView)findViewById(R.id.lbMaterial);
        lbCategoria = (TextView)findViewById(R.id.lbCategoria);
        lbRef = (TextView)findViewById(R.id.lbRef);
        lbDimensoes = (TextView)findViewById(R.id.lbDimensoes);
        lbPeso = (TextView)findViewById(R.id.lbPeso);
        tvBarra = (TextView)findViewById(R.id.tvBarra);

        tvDesc.setText(descPt);
        tvMaterial.setText(material);
        tvCategoria.setText(categoria);
        tvRef.setText(ref);
        tvDimensoes.setText(dimensoes);
        tvPeso.setText(peso);
        ivProduto.setImageBitmap(img);

        verificaIdioma();

        boolean  reforganizador = ref.startsWith("03");
        boolean  refAc = ref.startsWith("AC");
        boolean  refAd = ref.startsWith("AD");
        boolean  refAp = ref.startsWith("AP");
        boolean  refAr = ref.startsWith("AR");
        boolean  refBi = ref.startsWith("BI");
        boolean  refCd = ref.startsWith("CD");
        boolean  refCm = ref.startsWith("CM");
        boolean  refCr = ref.startsWith("CR");
        boolean  refDv = ref.startsWith("DV");
        boolean  refEs = ref.startsWith("ES");
        boolean  refFi = ref.startsWith("FI");
        boolean  refMa = ref.startsWith("MA");
        boolean  refMc = ref.startsWith("MC");
        boolean  refMe = ref.startsWith("ME");
        boolean  refNe = ref.startsWith("NE");
        boolean  refVe = ref.startsWith("VE");
        aMan = getApplicationContext().getAssets();
        if (reforganizador){
            seekBarImagens.setVisibility(View.INVISIBLE);
            tvBarra.setVisibility(View.INVISIBLE);

        } else if (refAc){

            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosAC = databaseAccess.selectProdutosComecadosPorAC();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosAC.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosAC.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosAC.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } else if(refAd){

            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosAD = databaseAccess.selectProdutosComecadosPorAD();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosAD.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosAD.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosAD.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }
        else if(refAp){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosAP = databaseAccess.selectProdutosComecadosPorAP();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosAP.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosAP.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosAP.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        } else if(refAr){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosAR = databaseAccess.selectProdutosComecadosPorAR();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosAR.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosAR.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosAR.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refBi){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosBI = databaseAccess.selectProdutosComecadosPorBI();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosBI.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosBI.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosBI.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refCd){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosCD = databaseAccess.selectProdutosComecadosPorCD();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosCD.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosCD.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosCD.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refCm){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosCM = databaseAccess.selectProdutosComecadosPorCM();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosCM.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosCM.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosCM.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refCr){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosCR = databaseAccess.selectProdutosComecadosPorCR();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosCR.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosCR.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosCR.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refDv){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosDV = databaseAccess.selectProdutosComecadosPorDV();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosDV.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosDV.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosDV.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refEs){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosES = databaseAccess.selectProdutosComecadosPorES();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosES.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosES.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosES.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refFi){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosFI = databaseAccess.selectProdutosComecadosPorFI();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosFI.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosFI.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosFI.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refMa){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosMA = databaseAccess.selectProdutosComecadosPorMA();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosMA.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosMA.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosMA.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refMc){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosMC = databaseAccess.selectProdutosComecadosPorMC();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosMC.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosMC.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosMC.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refMe){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosME = databaseAccess.selectProdutosComecadosPorME();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosME.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosME.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosME.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if(refNe){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosNE = databaseAccess.selectProdutosComecadosPorNE();
            databaseAccess.close();
            seekBarImagens.setMax(Global.produtosNE.size());
            seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    for (int i = 0; i < Global.produtosNE.size(); ++i) {

                        if (progress == i) {

                            Produto l = Global.produtosNE.get(i);
                            p = l;
                            descPt = p.getDescPt();
                            descEn = p.getDescEn();
                            descFr = p.getDescFr();
                            descIt = p.getDescIt();
                            material = p.getMaterial();
                            categoria = p.getCategoria();
                            categoriaEN = p.getCategoriaEN();
                            categoriaFR = p.getCategoriaFR();
                            categoriaIT = p.getCategoriaIT();
                            ref = p.getRef();
                            dimensoes = p.getDimensoes();
                            peso = p.getPeso();
                            tvDesc.setText(descPt);
                            tvMaterial.setText(material);
                            tvCategoria.setText(categoria);
                            tvRef.setText(ref);
                            tvDimensoes.setText(dimensoes);
                            tvPeso.setText(peso);
                            linkImagem = p.getLinkImagem();
                            Bitmap imagem = null;
                            try {
                                imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ivProduto.setImageBitmap(imagem);
                            verificaIdioma();
                        }

                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }else if (refVe){
            databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            Global.produtosVE = databaseAccess.selectProdutosComecadosPorVE();
            databaseAccess.close();
                seekBarImagens.setMax(Global.produtosVE.size());
                seekBarImagens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        for (int i = 0; i < Global.produtosVE.size(); ++i) {

                            if (progress == i) {

                                Produto l = Global.produtosVE.get(i);
                                p = l;
                                descPt = p.getDescPt();
                                descEn = p.getDescEn();
                                descFr = p.getDescFr();
                                descIt = p.getDescIt();
                                material = p.getMaterial();
                                categoria = p.getCategoria();
                                categoriaEN = p.getCategoriaEN();
                                categoriaFR = p.getCategoriaFR();
                                categoriaIT = p.getCategoriaIT();
                                ref = p.getRef();
                                dimensoes = p.getDimensoes();
                                peso = p.getPeso();
                                tvDesc.setText(descPt);
                                tvMaterial.setText(material);
                                tvCategoria.setText(categoria);
                                tvRef.setText(ref);
                                tvDimensoes.setText(dimensoes);
                                tvPeso.setText(peso);
                                linkImagem = p.getLinkImagem();
                                Bitmap imagem = null;
                                try {
                                    imagem = BitmapFactory.decodeStream(aMan.open(linkImagem));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ivProduto.setImageBitmap(imagem);
                                verificaIdioma();
                            }

                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


        }



        LinearLayoutManager layoutManagerCores = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCores = (RecyclerView) findViewById(R.id.rvCores);
        rvCores.setLayoutManager(layoutManagerCores);
        mAdapterCores = new CorAdapter(this, listaCores);
        rvCores.setAdapter(mAdapterCores);
        rvCores.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCores, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cor c = listaCores.get(position);
                String cor = c.getCor();
                String corEn = c.getCorEn();
                String corFr = c.getCorFr();
                String corIt = c.getCorIt();
                if (Global.idioma == "PT"){
                    Snackbar.make (view,cor, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //Toast.makeText(getApplicationContext(), cor, Toast.LENGTH_LONG).show();
                }else if (Global.idioma == "EN"){

                    Snackbar.make (view,corEn, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //Toast.makeText(getApplicationContext(), corEn, Toast.LENGTH_LONG).show();
                }else if(Global.idioma == "FR"){
                    Snackbar.make (view,corFr, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //Toast.makeText(getApplicationContext(), corFr, Toast.LENGTH_LONG).show();
                }else if(Global.idioma == "IT"){
                    Snackbar.make (view,corIt, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                   // Toast.makeText(getApplicationContext(), corIt, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(getApplicationContext(),"LongClick" + position+1,Toast.LENGTH_LONG).show();
            }
        }));

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem mPT = menu.findItem(R.id.nav_pt);
        MenuItem mEN = menu.findItem(R.id.nav_en);
        MenuItem mFR = menu.findItem(R.id.nav_fr);
        MenuItem mIT = menu.findItem(R.id.nav_it);
        if (Global.idioma == "PT"){

            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            if(mPT.isChecked()) mPT.setChecked(false);
            else mPT.setChecked(true);

        } else if (Global.idioma =="EN"){

            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            if(mEN.isChecked()) mEN.setChecked(false);
            else mEN.setChecked(true);


        } else if (Global.idioma =="FR") {

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            if(mFR.isChecked()) mFR.setChecked(false);
            else mFR.setChecked(true);

        } else if (Global.idioma =="IT") {

            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            if(mIT.isChecked()) mIT.setChecked(false);
            else mIT.setChecked(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_pt) {

            Global.idioma = "PT";
            tvDesc.setText(descPt);
            tvCategoria.setText(categoria);
            lbDesc.setText("Descrição");
            lbMaterial.setText("Material");
            lbCategoria.setText("Categoria");
            lbRef.setText("Referência");
            lbDimensoes.setText("Dimensões");
            lbPeso.setText("Peso");
            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            btnPdf.setText("Enviar imagem");
            rvCores.setAdapter(mAdapterCores);
            tvBarra.setText("Deslize para visualizar produtos relacionados");


            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            return true;
        } else if (id == R.id.nav_en){

            Global.idioma = "EN";
            tvDesc.setText(descEn);
            tvCategoria.setText(categoriaEN);
            lbDesc.setText("Description");
            lbMaterial.setText("Material");
            lbCategoria.setText("Category");
            lbRef.setText("Reference");
            lbDimensoes.setText("Dimensions");
            lbPeso.setText("Weight");
            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            btnPdf.setText("Send image");
            rvCores.setAdapter(mAdapterCores);
            tvBarra.setText("Scroll to view related products");


            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_fr){

            Global.idioma = "FR";
            tvDesc.setText(descFr);
            tvCategoria.setText(categoriaFR);
            lbDesc.setText("Description");
            lbMaterial.setText("Matériel");
            lbCategoria.setText("Catégorie");
            lbRef.setText("Reference");
            lbDimensoes.setText("Dimensions");
            lbPeso.setText("Poids");
            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            btnPdf.setText("Soumettre image");
            rvCores.setAdapter(mAdapterCores);
            tvBarra.setText("Faites défiler pour voir les produits");

            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_it){

            Global.idioma = "IT";
            tvDesc.setText(descIt);
            tvCategoria.setText(categoriaIT);
            lbDesc.setText("Descrizione");
            lbMaterial.setText("Materiale");
            lbCategoria.setText("Categoria");
            lbRef.setText("Referenza");
            lbDimensoes.setText("Dimensioni");
            lbPeso.setText("Peso");
            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            btnPdf.setText("Carica foto");
            rvCores.setAdapter(mAdapterCores);
            tvBarra.setText("Scorrere per visualizzare i prodotti");

            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_catalogo) {
            Intent i= new Intent(this, MainActivity.class);
            /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            startActivity(i);
        } else if (id == R.id.nav_Notas) {

            Intent i = new Intent(this, NotesActivity.class);
            /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            startActivity(i);

        } else if (id == R.id.nav_video) {

            Intent i = new Intent(this, VideoActivity.class);
           /* i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void verificaIdioma (){

        if (Global.idioma == "PT"){

            tvDesc.setText(descPt);
            tvCategoria.setText(categoria);
            lbDesc.setText("Descrição");
            lbMaterial.setText("Material");
            lbCategoria.setText("Categoria");
            lbRef.setText("Referência");
            lbDimensoes.setText("Dimensões");
            lbPeso.setText("Peso");
            btnPdf.setText("Enviar imagem");
            tvBarra.setText("Deslize para visualizar produtos relacionados");

        } else if (Global.idioma =="EN"){

            tvDesc.setText(descEn);
            tvCategoria.setText(categoriaEN);
            lbDesc.setText("Description");
            lbMaterial.setText("Material");
            lbCategoria.setText("Category");
            lbRef.setText("Reference");
            lbDimensoes.setText("Dimensions");
            lbPeso.setText("Weight");
            btnPdf.setText("Send image");
            tvBarra.setText("Scroll to view related products");

        } else if (Global.idioma =="FR"){

            tvDesc.setText(descFr);
            tvCategoria.setText(categoriaFR);
            lbDesc.setText("Description");
            lbMaterial.setText("Matériel");
            lbCategoria.setText("Catégorie");
            lbRef.setText("Reference");
            lbDimensoes.setText("Dimensions");
            lbPeso.setText("Poids");
            btnPdf.setText("Soumettre image");
            tvBarra.setText("Faites défiler pour voir les produits");

        } else if (Global.idioma =="IT"){

            tvDesc.setText(descIt);
            tvCategoria.setText(categoriaIT);
            lbDesc.setText("Descrizione");
            lbMaterial.setText("Materiale");
            lbCategoria.setText("Categoria");
            lbRef.setText("Referenza");
            lbDimensoes.setText("Dimensioni");
            lbPeso.setText("Peso");
            btnPdf.setText("Carica foto");
            tvBarra.setText("Scorrere per visualizzare i prodotti");
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "Pictures/screenshot.png";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            sendMail(filePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    public void sendMail(String path) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Ref: " + ref);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}





