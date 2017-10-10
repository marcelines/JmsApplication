package com.meedbrand.pt.jmsapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    VideoView videoView;
    MenuItem catalogo;
    MenuItem notas;
    MenuItem video;
    NavigationView navigationView;
    Menu mOptionsMenu;
    //RelativeLayout rlVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_video);

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
        navigationView.getMenu().findItem(R.id.nav_video).setChecked(true);
        catalogo = navigationView.getMenu().findItem(R.id.nav_catalogo);
        notas = navigationView.getMenu().findItem(R.id.nav_Notas);
        video = navigationView.getMenu().findItem(R.id.nav_video);


        videoView = (VideoView) findViewById(R.id.vvVideo);
        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);

        Uri video = Uri.parse("android.resource://com.meedbrand.pt.jmsapplication/raw/jms_video");

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.requestFocus();
                videoView.start();
            }

        });


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
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
            }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                //item.findItem(R.id.nav_catalogo).setChecked(true);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                if (id == R.id.nav_catalogo) {
                    /*finish();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);*/
                    Intent i = new Intent(this, MainActivity.class);
                   /* i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                    startActivity(i);

                } else if (id == R.id.nav_Notas) {

                    Intent i = new Intent(this, NotesActivity.class);
                    /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                    startActivity(i);

                } else if (id == R.id.nav_video) {

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_pt) {

            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            Global.idioma = "PT";

            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            return true;
        } else if (id == R.id.nav_en){

            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            Global.idioma = "EN";

            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_fr){



            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            Global.idioma = "FR";
            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_it){


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
    @Override
    protected void onRestart() {
        super.onRestart();
        //verificaIdioma();
        video.setChecked(true);
    }
}
