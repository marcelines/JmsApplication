package com.meedbrand.pt.jmsapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rvProdutosHospitalar;
    private RecyclerView rvProdutosClinico;
    private RecyclerView rvProdutosFisio;
    private RecyclerView rvProdutosGeriatico;
    private RecyclerView rvAjudas;
    private RecyclerView rvDiversos;
    // private RecyclerView rvCores;
    ProgressDialog pdialog = null;
    Context context = null;

    private RecyclerView.Adapter mAdapterHospitalar;
    private RecyclerView.Adapter mAdapterClinico;
    private RecyclerView.Adapter mAdapterFisio;
    private RecyclerView.Adapter mAdapterGeriatico;
    private RecyclerView.Adapter mAdapterAjudas;
    private RecyclerView.Adapter mAdapterDiversos;
    // private RecyclerView.Adapter mAdapterCores;

    private ArrayList<Produto> listaProdutosHospitalar = null;
    private ArrayList<Produto> listaProdutosClinico = null;
    private ArrayList<Produto> listaProdutosFisio = null;
    private ArrayList<Produto> listaProdutosGeriatico = null;
    private ArrayList<Produto> listaProdutosAjudas = null;
    private ArrayList<Produto> listaProdutosDiversos = null;


    private ArrayList<Produto> filteredProdutoHospitalar = null;
    private ArrayList<Produto> filteredProdutoClinico = null;
    private ArrayList<Produto> filteredProdutoFisio = null;
    private ArrayList<Produto> filteredProdutoGeriatrico = null;
    private ArrayList<Produto> filteredProdutoAjudas = null;
    private ArrayList<Produto> filteredProdutoDiversos = null;
    // private ArrayList<Produto> listaCores = null;

    private ProgressBar firstBar = null;
    private Menu mOptionsMenu;
    NavigationView navigationView;
    MenuItem catalogo;
    MenuItem notas;
    MenuItem video;

    private Thread mSplashThread;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);

        RelativeLayout rlPesquisa = (RelativeLayout)findViewById(R.id.rlTopoPesquisa);


        rlPesquisa.bringToFront();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        catalogo = navigationView.getMenu().findItem(R.id.nav_catalogo);
        notas = navigationView.getMenu().findItem(R.id.nav_Notas);
        video = navigationView.getMenu().findItem(R.id.nav_video);

        catalogo.setChecked(true);

       /* mProgress = new ProgressDialog((this));
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setMessage("A carregar catálogo...");
        mProgress.show();
        ProgressThread thread = new ProgressThread();
        thread.start();*/



        //pdialog = ProgressDialog.show(this, "", "Carregando...", true);


        inicio();

        //String playerToast = Global.mListDiversos.toString();

        //Toast.makeText(getBaseContext(), playerToast, Toast.LENGTH_SHORT).show();

        final EditText etPesquisa = (EditText)findViewById(R.id.etPesquisa);
        etPesquisa.setText("");

        etPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filteredProdutoHospitalar = new ArrayList<Produto>();
                for (int i = 0; i<listaProdutosHospitalar.size(); i++) {

                    Produto produto = listaProdutosHospitalar.get(i);

                    String texto = "";

                        texto = produto.getRef();

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProdutoHospitalar.add(produto);
                    }
                }
                mAdapterHospitalar = new ProdutoAdapter(MainActivity.this, filteredProdutoHospitalar);
                rvProdutosHospitalar.setAdapter(mAdapterHospitalar);

                filteredProdutoClinico = new ArrayList<Produto>();
                for (int i = 0; i<listaProdutosClinico.size(); i++) {

                    Produto produto = listaProdutosClinico.get(i);

                    String texto = "";

                    texto = produto.getRef();

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProdutoClinico.add(produto);
                    }
                }
                mAdapterClinico = new ProdutoAdapter(MainActivity.this, filteredProdutoClinico);
                rvProdutosClinico.setAdapter(mAdapterClinico);

                filteredProdutoFisio = new ArrayList<Produto>();
                for (int i = 0; i<listaProdutosFisio.size(); i++) {

                    Produto produto = listaProdutosFisio.get(i);

                    String texto = "";

                    texto = produto.getRef();

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProdutoFisio.add(produto);
                    }
                }
                mAdapterFisio = new ProdutoAdapter(MainActivity.this, filteredProdutoFisio);
                rvProdutosFisio.setAdapter(mAdapterFisio);

                filteredProdutoGeriatrico = new ArrayList<Produto>();
                for (int i = 0; i<listaProdutosGeriatico.size(); i++) {

                    Produto produto = listaProdutosGeriatico.get(i);

                    String texto = "";

                    texto = produto.getRef();

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProdutoGeriatrico.add(produto);
                    }
                }
                mAdapterGeriatico = new ProdutoAdapter(MainActivity.this, filteredProdutoGeriatrico);
                rvProdutosGeriatico.setAdapter(mAdapterGeriatico);

                filteredProdutoAjudas = new ArrayList<Produto>();
                for (int i = 0; i<listaProdutosAjudas.size(); i++) {

                    Produto produto = listaProdutosAjudas.get(i);

                    String texto = "";

                    texto = produto.getRef();

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProdutoAjudas.add(produto);
                    }
                }
                mAdapterAjudas = new ProdutoAdapter(MainActivity.this, filteredProdutoAjudas);
                rvAjudas.setAdapter(mAdapterAjudas);


                filteredProdutoDiversos = new ArrayList<Produto>();
                for (int i = 0; i<listaProdutosDiversos.size(); i++) {

                    Produto produto = listaProdutosDiversos.get(i);

                    String texto = "";

                    texto = produto.getRef();

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredProdutoDiversos.add(produto);
                    }
                }
                mAdapterDiversos = new ProdutoAdapter(MainActivity.this, filteredProdutoDiversos);
                rvDiversos.setAdapter(mAdapterDiversos);

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void detalhesHospitalar(int pos) {

        Intent i = new Intent(this, DetailActivity.class);
        Produto p;
        if(filteredProdutoHospitalar == null){

            p = listaProdutosHospitalar.get(pos);

        } else {

            p = filteredProdutoHospitalar.get(pos);
        }

        int id= p.getId();

        i.putExtra("produto",id);

        // i.putExtra("produto", new Gson().toJson(listaProdutosHospitalar.get(pos)));
        startActivity(i);
    }
    private void detalhesClinico(int pos) {

        Intent i = new Intent(this, DetailActivity.class);
        Produto p;
        if(filteredProdutoClinico == null){

            p = listaProdutosClinico.get(pos);

        } else {

            p = filteredProdutoClinico.get(pos);
        }
        int id= p.getId();
        i.putExtra("produto",id);
        // i.putExtra("produto", new Gson().toJson(listaProdutosHospitalar.get(pos)));
        startActivity(i);
    }
    private void detalhesFisioterapia(int pos) {

        Intent i = new Intent(this, DetailActivity.class);
        Produto p;
        if(filteredProdutoFisio == null){

            p = listaProdutosFisio.get(pos);

        } else {

            p = filteredProdutoFisio.get(pos);
        }
        int id= p.getId();
        i.putExtra("produto",id);
        // i.putExtra("produto", new Gson().toJson(listaProdutosHospitalar.get(pos)));
        startActivity(i);
    }
    private void detalhesGeriatrico(int pos) {

        Intent i = new Intent(this, DetailActivity.class);
        Produto p;
        if(filteredProdutoGeriatrico == null){

            p = listaProdutosGeriatico.get(pos);

        } else {

            p = filteredProdutoGeriatrico.get(pos);
        }
        int id= p.getId();
        i.putExtra("produto",id);
        // i.putExtra("produto", new Gson().toJson(listaProdutosHospitalar.get(pos)));
        startActivity(i);
    }
    private void detalhesAjudas(int pos) {

        Intent i = new Intent(this, DetailActivity.class);
        Produto p;
        if(filteredProdutoAjudas == null){

            p = listaProdutosAjudas.get(pos);

        } else {

            p = filteredProdutoAjudas.get(pos);
        }
        int id= p.getId();
        i.putExtra("produto",id);
        // i.putExtra("produto", new Gson().toJson(listaProdutosHospitalar.get(pos)));
        startActivity(i);
    }
    private void detalhesDiversos(int pos) {

        Intent i = new Intent(this, DetailActivity.class);
        Produto p;
        if(filteredProdutoDiversos == null){

            p = listaProdutosDiversos.get(pos);

        } else {

            p = filteredProdutoDiversos.get(pos);
        }
        int id= p.getId();
        i.putExtra("produto", id);
        // i.putExtra("produto", new Gson().toJson(listaProdutosHospitalar.get(pos)));
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        new AlertDialog.Builder(this)
                .setTitle("Aviso")
                .setMessage("Deseja sair da aplicação?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.this.finish();
                        System.exit(0);

                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem mPT = menu.findItem(R.id.nav_pt);
        MenuItem mEN = menu.findItem(R.id.nav_en);
        MenuItem mFR = menu.findItem(R.id.nav_fr);
        MenuItem mIT = menu.findItem(R.id.nav_it);
        ImageView etHospitalar = (ImageView)findViewById(R.id.etHospitalar);
        ImageView etClinico = (ImageView)findViewById(R.id.etClinico);
        ImageView etFisio = (ImageView)findViewById(R.id.etFisio);
        ImageView etGeriatrico = (ImageView)findViewById(R.id.etGeriatico);
        ImageView etAjudas = (ImageView)findViewById(R.id.etAjudas);
        ImageView etDiversos = (ImageView)findViewById(R.id.etDiversos);

        if (Global.idioma == "PT"){

            etHospitalar.setImageResource(R.drawable.et_hospitalar);
            etClinico.setImageResource(R.drawable.et_clinico);
            etFisio.setImageResource(R.drawable.et_fisio);
            etGeriatrico.setImageResource(R.drawable.et_geriatico);
            etAjudas.setImageResource(R.drawable.et_ajudas);
            etDiversos.setImageResource(R.drawable.et_diversos);
            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            if(mPT.isChecked()) mPT.setChecked(false);
            else mPT.setChecked(true);

        } else if (Global.idioma =="EN"){

            etHospitalar.setImageResource(R.drawable.et_hospitalar_en);
            etClinico.setImageResource(R.drawable.et_clinico_en);
            etFisio.setImageResource(R.drawable.et_fisio_en);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_en);
            etAjudas.setImageResource(R.drawable.et_ajudas_en);
            etDiversos.setImageResource(R.drawable.et_diversos_en);
            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            if(mEN.isChecked()) mEN.setChecked(false);
            else mEN.setChecked(true);


        } else if (Global.idioma =="FR") {

            etHospitalar.setImageResource(R.drawable.et_hospitalar_fr);
            etClinico.setImageResource(R.drawable.et_clinico_fr);
            etFisio.setImageResource(R.drawable.et_fisio_fr);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_fr);
            etAjudas.setImageResource(R.drawable.et_ajudas_fr);
            etDiversos.setImageResource(R.drawable.et_diversos_fr);
            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            if(mFR.isChecked()) mFR.setChecked(false);
            else mFR.setChecked(true);

        } else if (Global.idioma =="IT") {

            etHospitalar.setImageResource(R.drawable.et_hospitalar_it);
            etClinico.setImageResource(R.drawable.et_clinico_it);
            etFisio.setImageResource(R.drawable.et_fisio_it);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_it);
            etAjudas.setImageResource(R.drawable.et_ajudas_it);
            etDiversos.setImageResource(R.drawable.et_diversos_it);
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

        ImageView etHospitalar = (ImageView)findViewById(R.id.etHospitalar);
        ImageView etClinico = (ImageView)findViewById(R.id.etClinico);
        ImageView etFisio = (ImageView)findViewById(R.id.etFisio);
        ImageView etGeriatrico = (ImageView)findViewById(R.id.etGeriatico);
        ImageView etAjudas = (ImageView)findViewById(R.id.etAjudas);
        ImageView etDiversos = (ImageView)findViewById(R.id.etDiversos);


        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_pt) {
            etHospitalar.setImageResource(R.drawable.et_hospitalar);
            etClinico.setImageResource(R.drawable.et_clinico);
            etFisio.setImageResource(R.drawable.et_fisio);
            etGeriatrico.setImageResource(R.drawable.et_geriatico);
            etAjudas.setImageResource(R.drawable.et_ajudas);
            etDiversos.setImageResource(R.drawable.et_diversos);
            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            Global.idioma = "PT";

            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            return true;
        } else if (id == R.id.nav_en){
            etHospitalar.setImageResource(R.drawable.et_hospitalar_en);
            etClinico.setImageResource(R.drawable.et_clinico_en);
            etFisio.setImageResource(R.drawable.et_fisio_en);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_en);
            etAjudas.setImageResource(R.drawable.et_ajudas_en);
            etDiversos.setImageResource(R.drawable.et_diversos_en);
            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            Global.idioma = "EN";

            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_fr){

            etHospitalar.setImageResource(R.drawable.et_hospitalar_fr);
            etClinico.setImageResource(R.drawable.et_clinico_fr);
            etFisio.setImageResource(R.drawable.et_fisio_fr);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_fr);
            etAjudas.setImageResource(R.drawable.et_ajudas_fr);
            etDiversos.setImageResource(R.drawable.et_diversos_fr);

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            Global.idioma = "FR";
            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_it){

            etHospitalar.setImageResource(R.drawable.et_hospitalar_it);
            etClinico.setImageResource(R.drawable.et_clinico_it);
            etFisio.setImageResource(R.drawable.et_fisio_it);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_it);
            etAjudas.setImageResource(R.drawable.et_ajudas_it);
            etDiversos.setImageResource(R.drawable.et_diversos_it);
            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            Global.idioma = "IT";
            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //item.findItem(R.id.nav_catalogo).setChecked(true);
        //if(item.isChecked()) item.setChecked(false);
        //else item.setChecked(true);

        if (id == R.id.nav_catalogo) {
            //Toast.makeText(getApplicationContext(),"Ir para Catálogo" ,Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_Notas) {

            Intent i = new Intent(this, NotesActivity.class);
           /* i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        MenuItem mPT = mOptionsMenu.findItem(R.id.nav_pt);
        MenuItem mEN = mOptionsMenu.findItem(R.id.nav_en);
        MenuItem mFR = mOptionsMenu.findItem(R.id.nav_fr);
        MenuItem mIT = mOptionsMenu.findItem(R.id.nav_it);
        ImageView etHospitalar = (ImageView)findViewById(R.id.etHospitalar);
        ImageView etClinico = (ImageView)findViewById(R.id.etClinico);
        ImageView etFisio = (ImageView)findViewById(R.id.etFisio);
        ImageView etGeriatrico = (ImageView)findViewById(R.id.etGeriatico);
        ImageView etAjudas = (ImageView)findViewById(R.id.etAjudas);
        ImageView etDiversos = (ImageView)findViewById(R.id.etDiversos);

        if (Global.idioma == "PT"){

            etHospitalar.setImageResource(R.drawable.et_hospitalar);
            etClinico.setImageResource(R.drawable.et_clinico);
            etFisio.setImageResource(R.drawable.et_fisio);
            etGeriatrico.setImageResource(R.drawable.et_geriatico);
            etAjudas.setImageResource(R.drawable.et_ajudas);
            etDiversos.setImageResource(R.drawable.et_diversos);
            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            if(mPT.isChecked()) mPT.setChecked(false);
            else mPT.setChecked(true);

        } else if (Global.idioma =="EN"){

            etHospitalar.setImageResource(R.drawable.et_hospitalar_en);
            etClinico.setImageResource(R.drawable.et_clinico_en);
            etFisio.setImageResource(R.drawable.et_fisio_en);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_en);
            etAjudas.setImageResource(R.drawable.et_ajudas_en);
            etDiversos.setImageResource(R.drawable.et_diversos_en);
            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            if(mEN.isChecked()) mEN.setChecked(false);
            else mEN.setChecked(true);


        } else if (Global.idioma =="FR"){

            etHospitalar.setImageResource(R.drawable.et_hospitalar_fr);
            etClinico.setImageResource(R.drawable.et_clinico_fr);
            etFisio.setImageResource(R.drawable.et_fisio_fr);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_fr);
            etAjudas.setImageResource(R.drawable.et_ajudas_fr);
            etDiversos.setImageResource(R.drawable.et_diversos_fr);

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            if(mFR.isChecked()) mFR.setChecked(false);
            else mFR.setChecked(true);


        } else if (Global.idioma =="IT"){

            etHospitalar.setImageResource(R.drawable.et_hospitalar_it);
            etClinico.setImageResource(R.drawable.et_clinico_it);
            etFisio.setImageResource(R.drawable.et_fisio_it);
            etGeriatrico.setImageResource(R.drawable.et_geriatico_it);
            etAjudas.setImageResource(R.drawable.et_ajudas_it);
            etDiversos.setImageResource(R.drawable.et_diversos_it);
            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            if(mIT.isChecked()) mIT.setChecked(false);
            else mIT.setChecked(true);
        }
    }
    private void inicio(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        listaProdutosHospitalar = databaseAccess.selectProdutosHospitalar(this);
        listaProdutosClinico = databaseAccess.selectProdutosClinico(this);
        listaProdutosFisio = databaseAccess.selectProdutosFisioterapia(this);
        listaProdutosGeriatico = databaseAccess.selectProdutosGeriatrico(this);
        listaProdutosAjudas = databaseAccess.selectProdutosAjudas(this);
        listaProdutosDiversos = databaseAccess.selectProdutosDiversos(this);


        databaseAccess.close();

        LinearLayoutManager layoutManagerHospitalar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProdutosHospitalar = (RecyclerView) findViewById(R.id.rvHospitalar);
        rvProdutosHospitalar.setLayoutManager(layoutManagerHospitalar);
        mAdapterHospitalar = new ProdutoAdapter(this, listaProdutosHospitalar);
        rvProdutosHospitalar.setAdapter(mAdapterHospitalar);
        rvProdutosHospitalar.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvProdutosHospitalar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //Toast.makeText(getApplicationContext(),"OnClick" + position,Toast.LENGTH_LONG).show();
                detalhesHospitalar(position);
            }

            @Override
            public void onLongClick(View view, int position) {


                //Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_LONG).show();


            }
        }));


        LinearLayoutManager layoutManagerClinico = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProdutosClinico = (RecyclerView) findViewById(R.id.rvClinico);
        rvProdutosClinico.setLayoutManager(layoutManagerClinico);
        mAdapterClinico = new ProdutoAdapter(this, listaProdutosClinico);
        rvProdutosClinico.setAdapter(mAdapterClinico);
        rvProdutosClinico.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvProdutosClinico, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                detalhesClinico(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        LinearLayoutManager layoutManagerFisio = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProdutosFisio = (RecyclerView) findViewById(R.id.rvFisio);
        rvProdutosFisio.setLayoutManager(layoutManagerFisio);
        mAdapterFisio = new ProdutoAdapter(this, listaProdutosFisio);
        rvProdutosFisio.setAdapter(mAdapterFisio);
        rvProdutosFisio.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvProdutosFisio, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                detalhesFisioterapia(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        LinearLayoutManager layoutManagerGeriatico = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProdutosGeriatico = (RecyclerView) findViewById(R.id.rvGeriatico);
        rvProdutosGeriatico.setLayoutManager(layoutManagerGeriatico);
        mAdapterGeriatico = new ProdutoAdapter(this, listaProdutosGeriatico);
        rvProdutosGeriatico.setAdapter(mAdapterGeriatico);
        rvProdutosGeriatico.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvProdutosGeriatico, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                detalhesGeriatrico(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        LinearLayoutManager layoutManagerAjudas = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAjudas = (RecyclerView) findViewById(R.id.rvAjudas);
        rvAjudas.setLayoutManager(layoutManagerAjudas);
        mAdapterAjudas = new ProdutoAdapter(this, listaProdutosAjudas);
        rvAjudas.setAdapter(mAdapterAjudas);
        rvAjudas.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvAjudas, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                detalhesAjudas(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        LinearLayoutManager layoutManagerDiversos = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvDiversos = (RecyclerView) findViewById(R.id.rvDiversos);
        rvDiversos.setLayoutManager(layoutManagerDiversos);
        mAdapterDiversos = new ProdutoAdapter(this, listaProdutosDiversos);
        rvDiversos.setAdapter(mAdapterDiversos);
        rvDiversos.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvDiversos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                detalhesDiversos(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       verificaIdioma();
        catalogo.setChecked(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        //pdialog.dismiss();
        //mProgress.dismiss();
        super.onPostCreate(savedInstanceState);
    }
    private class ProgressThread extends Thread{

        public void run(){

           inicio();
        }
    }

}
