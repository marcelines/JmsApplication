package com.meedbrand.pt.jmsapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotesActivity extends AppCompatActivity implements Serializable,NavigationView.OnNavigationItemSelectedListener {



    Context context = null;


    private static final int NEW_FRIEND = -1;
    private static final int MENU_CONTEXTO_ITEM_EDITAR = 0;
    private static final int MENU_CONTEXTO_ITEM_APAGAR = 1;
    Menu mOptionsMenu;
    NavigationView navigationView;
    MenuItem catalogo;
    MenuItem notas;
    MenuItem video;
    private ListView lvListaClientes;
    ListNoteAdapter adapter;
    private ArrayList<Note> notes = null;
    ImageButton ibNovo;
    ImageButton ibApagar;
    TextView tvNovo;
    TextView tvApapar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_notes);
        context = this;
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
        tvNovo = (TextView)findViewById(R.id.tvNovo);
        tvNovo = (TextView)findViewById(R.id.tvNovo);
        tvApapar = (TextView)findViewById(R.id.tvApagar);

        notas.setChecked(true);

        poeNotasNaLista();


        if(notes.isEmpty()){

            Toast.makeText(this, "Não existem notas adicionadas!", Toast.LENGTH_SHORT).show();
        } else {

            lvListaClientes.setAdapter(adapter);

            lvListaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Note note = (Note) parent.getItemAtPosition(position);

                    gotoDetail(note.get_id());
                }
            });

        }

        ibNovo = (ImageButton)findViewById(R.id.ibNovo);
        ibNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetail(NEW_FRIEND);

            }
        });
        registerForContextMenu(lvListaClientes);
        ibApagar =(ImageButton)findViewById(R.id.ibApagar);
        ibApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notes.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Contacto apagado!", Toast.LENGTH_SHORT).show();
                } else {

                   vamosApagarTudo();

                }
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

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem mPT = menu.findItem(R.id.nav_pt);
        MenuItem mEN = menu.findItem(R.id.nav_en);
        MenuItem mFR = menu.findItem(R.id.nav_fr);
        MenuItem mIT = menu.findItem(R.id.nav_it);
        if (Global.idioma == "PT"){

            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            tvNovo.setText("Novo contacto");
            tvApapar.setText("Apagar tudo");

            if(mPT.isChecked()) mPT.setChecked(false);
            else mPT.setChecked(true);

        } else if (Global.idioma =="EN"){

            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            tvNovo.setText("New contact");
            tvApapar.setText("Erase all");
            if(mEN.isChecked()) mEN.setChecked(false);
            else mEN.setChecked(true);



        } else if (Global.idioma =="FR") {

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            tvNovo.setText("Nouveau contact");
            tvApapar.setText("Supprimer tout");
            if(mFR.isChecked()) mFR.setChecked(false);
            else mFR.setChecked(true);

        } else if (Global.idioma =="IT") {

            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            tvNovo.setText("Nuovo contatto");
            tvApapar.setText("Elimina tutto");
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

            catalogo.setTitle("Catálogo");
            notas.setTitle("Contactos");
            video.setTitle("Vídeo");
            tvNovo.setText("Novo contacto");
            tvApapar.setText("Apagar tudo");


            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            return true;
        } else if (id == R.id.nav_en){

            Global.idioma = "EN";

            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            tvNovo.setText("New contact");
            tvApapar.setText("Erase all");



            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_fr){

            Global.idioma = "FR";

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            tvNovo.setText("Nouveau contact");
            tvApapar.setText("Supprimer tout");


            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_it){

            Global.idioma = "IT";

            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            tvNovo.setText("Nuovo contatto");
            tvApapar.setText("Elimina tutto");


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

            Intent i = new Intent(this, MainActivity.class);
           /* i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            startActivity(i);
        } else if (id == R.id.nav_Notas) {

        } else if (id == R.id.nav_video) {

            Intent i = new Intent(this, VideoActivity.class);
            /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        poeNotasNaLista();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void poeNotasNaLista() {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        notes = databaseAccess.selectNotes();
        databaseAccess.close();

        adapter = new ListNoteAdapter(this, notes);
        lvListaClientes = (ListView)findViewById(R.id.lvNotes);
        lvListaClientes.setAdapter(adapter);
    }
    public void gotoDetail(int noteDbId) {

        Intent i = new Intent(this, EditaNoteActivity.class);

        if (noteDbId != NEW_FRIEND) {
            i.putExtra("noteDbId", noteDbId);
        }
        //finish();
        startActivity(i);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Menu");
        menu.setHeaderIcon(android.R.drawable.ic_menu_info_details);
        if(Global.idioma == "PT"){
            menu.add(0, MENU_CONTEXTO_ITEM_EDITAR, 0, "Editar");
            menu.add(0, MENU_CONTEXTO_ITEM_APAGAR, 0, "Apagar");
        }else if(Global.idioma == "EN") {

            menu.add(0, MENU_CONTEXTO_ITEM_EDITAR, 0, "Edit");
            menu.add(0, MENU_CONTEXTO_ITEM_APAGAR, 0, "Delete");

        } else if (Global.idioma == "FR"){

            menu.add(0, MENU_CONTEXTO_ITEM_EDITAR, 0, "Modifier");
            menu.add(0, MENU_CONTEXTO_ITEM_APAGAR, 0, "Supprimer");

        }else if(Global.idioma == "IT") {

            menu.add(0, MENU_CONTEXTO_ITEM_EDITAR, 0, "Modifica");
            menu.add(0, MENU_CONTEXTO_ITEM_APAGAR, 0, "Cancellare");
        }


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;

        int id = notes.get(position).get_id();

        if (item.getItemId() == MENU_CONTEXTO_ITEM_EDITAR) {

            gotoDetail(id);

        } else if (item.getItemId() == MENU_CONTEXTO_ITEM_APAGAR) {

            vamosApagar(id);

        }

        return super.onContextItemSelected(item);
    }

    public void vamosApagar(final int id) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        if( Global.idioma == "PT"){
            builder.setTitle("Aviso!");
            builder.setMessage("Deseja apagar o contacto?");

            builder.setNegativeButton("Não", null);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Contacto apagado.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaAmigo(id);

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });
        }else if (Global.idioma == "EN"){
            builder.setTitle("Warning!");
            builder.setMessage("Do you wish to delete the contact?");

            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Contact deleted.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaAmigo(id);

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });
        } else if (Global.idioma == "FR"){
            builder.setTitle("Avertissement!");
            builder.setMessage("Voulez-vous supprimer le contact?");

            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Contact dehors.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaAmigo(id);

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });
        } else if (Global.idioma == "IT"){
            builder.setTitle("Avvertimento!");
            builder.setMessage("Vuoi eliminare il contatto?");

            builder.setNegativeButton("Non", null);
            builder.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Contatto cancellati.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaAmigo(id);

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });

        }


        android.app.AlertDialog alerta = builder.create();
        alerta.show();
    }
    public void vamosApagarTudo() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        if(Global.idioma == "PT"){
            builder.setTitle("Aviso!");
            builder.setMessage("Deseja apagar todos os contactos?");

            builder.setNegativeButton("Não", null);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Todos os contactos foram apagados.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaTudo();

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });
        }else if (Global.idioma == "EN"){
            builder.setTitle("Warning!");
            builder.setMessage("Do you want to delete all contacts?");

            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "All contacts were deleted.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaTudo();

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });

        }else if (Global.idioma =="FR"){
            builder.setTitle("Avertissement!");
            builder.setMessage("Voulez-vous supprimer tous les contacts ??");

            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Tous les contacts ont été supprimés.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaTudo();

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });

        }else if (Global.idioma =="IT"){
            builder.setTitle("Avvertimento!!");
            builder.setMessage("Vuoi eliminare tutti i contatti?");

            builder.setNegativeButton("Non", null);
            builder.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(NotesActivity.this);
                    databaseAccess.open();

                    Toast.makeText(getApplicationContext(), "Tutti i contatti sono stati eliminati.", Toast.LENGTH_SHORT).show();
                    databaseAccess.apagaTudo();

                    databaseAccess.close();

                    poeNotasNaLista();

                }
            });

        }


        android.app.AlertDialog alerta = builder.create();
        alerta.show();
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
    protected void onRestart() {
        super.onRestart();
        //verificaIdioma();
        notas.setChecked(true);
    }


}
