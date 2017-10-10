package com.meedbrand.pt.jmsapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EditaNoteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText etEmpresa;
    private EditText etNome;
    private EditText etContacto;
    private EditText etPais;
    private EditText etTexto;
    private RatingBar rb_customColor;
    TextView tvRating;
    NavigationView navigationView;
    MenuItem catalogo;
    MenuItem notas;
    MenuItem video;
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    String rec, subject, textMessage;
    Spinner spinnerVend;
    Spinner spinnerTipo;
    Button btnGuardar;
    int valorRanking;
    int noteDbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_edita_note);
        etEmpresa =(EditText)findViewById(R.id.etEmpresa);
        etNome = (EditText)findViewById(R.id.etNome);
        etContacto = (EditText)findViewById(R.id.etContacto);
        etPais = (EditText)findViewById(R.id.etPais);
        etTexto = (EditText)findViewById(R.id.etTexto);
        context = this;
        tvRating =(TextView)findViewById(R.id.tvRating);
        rb_customColor = (RatingBar) findViewById(R.id.ratingBar5);
        rb_customColor.setRating(1.0f);
        valorRanking = 1;
        tvRating.setText(valorRanking+"/9");

        LayerDrawable stars = (LayerDrawable) rb_customColor
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#00355F"),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(Color.parseColor("#2f6E9A"),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(Color.parseColor("#a6a6a6"),
                PorterDuff.Mode.SRC_ATOP); // for empty stars

        rb_customColor.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                valorRanking= (int) rating;

                if (rating < 1.0f) {
                    valorRanking= 1;
                    ratingBar.setRating(1.0f);
                }

               //doRating(rating);

                tvRating.setText(valorRanking+"/9");
                //Toast.makeText(getApplicationContext(), "" + valorRanking, Toast.LENGTH_LONG).show();


            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerVend = (Spinner)findViewById(R.id.spinnerVendedor);
        spinnerTipo = (Spinner)findViewById(R.id.spinnerTipo);

        ArrayAdapter<CharSequence> vAdapter = ArrayAdapter.createFromResource(this,R.array.vendedores_array, R.layout.spinner_row);
        spinnerVend.setAdapter(vAdapter);
        ArrayAdapter<CharSequence> vAdapter2 = ArrayAdapter.createFromResource(this,R.array.contacto_array, R.layout.spinner_row);
        spinnerTipo.setAdapter(vAdapter2);
        /*spinnerVend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
        notas.setChecked(true);

        noteDbId = getIntent().getIntExtra("noteDbId", -1);

        if (noteDbId >= 0) {

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();



            Note note = databaseAccess.noteComId(noteDbId);
            etEmpresa.setText(note.getEmpresa());
            etNome.setText(note.getNome());
            etContacto.setText(note.getContacto());
            etPais.setText(note.getPais());
            etTexto.setText(note.getTexto());
            rb_customColor.setRating(note.getRating());
            spinnerVend.setSelection(note.getVendedor());
            spinnerTipo.setSelection(note.getTipo());


            databaseAccess.close();

        }
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteDbId >= 0) {
                    actualiza(noteDbId);
                } else {
                    insereNova();
                }
                finish();
            }
        });
        ImageButton btnEnviaMail = (ImageButton)findViewById(R.id.ibMail);
        btnEnviaMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkAvailable()) {
                    if(Global.idioma == "PT"){

                        Toast.makeText(getApplicationContext(), "Erro! Por favor verifique a sua ligação à internet.", Toast.LENGTH_LONG).show();
                    } else if (Global.idioma == "EN"){

                        Toast.makeText(getApplicationContext(), "Error! Check your internet connection.", Toast.LENGTH_LONG).show();
                    } else if (Global.idioma == "FR"){
                        Toast.makeText(getApplicationContext(), "Erreur! S'il vous plaît vérifier votre connexion Internet.", Toast.LENGTH_LONG).show();
                    }else if (Global.idioma == "IT"){
                        Toast.makeText(getApplicationContext(), "Errore! Controlla la tua connessione a Internet.", Toast.LENGTH_LONG).show();
                    }


                } else {
                    String nome = etNome.getText().toString();
                    String empresa = etEmpresa.getText().toString();
                    String contacto = etContacto.getText().toString();
                    int rating = (int) rb_customColor.getRating();
                    String pais = etPais.getText().toString();
                    String texto = etTexto.getText().toString();
                    String vendedor = spinnerVend.getSelectedItem().toString();
                    String tipo = spinnerTipo.getSelectedItem().toString();
                    String paragrafo = getResources().getString(R.string.break_string);

                    String emailEnvio = "mobiliariohospitalarjms@gmail.com";
                    rec = emailEnvio;
                    subject = "Contacto de " + nome + " - " + empresa;
                    textMessage = "País do contacto: " + pais + paragrafo + paragrafo + "Observações: "+ paragrafo + texto  + paragrafo + paragrafo + "Grau de importância: " + rating + " em 9" + paragrafo + paragrafo +"Contacto cliente: " + contacto + paragrafo + paragrafo + "Tipo: " + tipo +  paragrafo + paragrafo + "Nome do vendedor: " + vendedor;

                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    session = Session.getDefaultInstance(props, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("mobiliariohospitalarjms@gmail.com", "jmsmobiliario2015");
                        }
                    });

                    if (Global.idioma == "PT") {
                        pdialog = ProgressDialog.show(context, "", "A enviar email...", true);
                    } else if (Global.idioma == "EN") {
                        pdialog = ProgressDialog.show(context, "", "Sending email...", true);
                    } else if (Global.idioma == "FR") {
                        pdialog = ProgressDialog.show(context, "", "Envoi email...", true);
                    } else if (Global.idioma == "IT") {
                        pdialog = ProgressDialog.show(context, "", "Invio di email...", true);
                    }


                    RetreiveFeedTask task = new RetreiveFeedTask();
                    task.execute();


                }
            }
        });
    }

    public void insereNova() {

        String nome = etNome.getText().toString();

        String empresa = etEmpresa.getText().toString();

        String contacto = etContacto.getText().toString();

        String pais = etPais.getText().toString();

        String texto = etTexto.getText().toString();
        int rating = (int) rb_customColor.getRating();
        int vendedor = spinnerVend.getSelectedItemPosition();
        int tipo = spinnerTipo.getSelectedItemPosition();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        databaseAccess.insereNote(nome, empresa, contacto,pais, texto,rating, vendedor,tipo);

        databaseAccess.close();

    }
    public void actualiza(int id) {

        String nome = etNome.getText().toString();

        String empresa = etEmpresa.getText().toString();

        String contacto = etContacto.getText().toString();

        String pais = etPais.getText().toString();

        String texto = etTexto.getText().toString();

        int rating = (int) rb_customColor.getRating();

        int vendedor = spinnerVend.getSelectedItemPosition();
        int tipo = spinnerTipo.getSelectedItemPosition();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        databaseAccess.actualizaNote(id, empresa, nome, contacto,pais, texto,rating, vendedor,tipo);

        databaseAccess.close();

    }
    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("mobiliariohospitalarjms@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();

            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            //reciep.setText("");
            //msg.setText("");
            //sub.setText("");
            if (Global.idioma == "PT"){

                Toast.makeText(getApplicationContext(), "Contacto enviado", Toast.LENGTH_LONG).show();
            }else if(Global.idioma == "EN"){
                Toast.makeText(getApplicationContext(), "Contact sent", Toast.LENGTH_LONG).show();

            }else if (Global.idioma == "FR"){
                Toast.makeText(getApplicationContext(), "Contact envoyé", Toast.LENGTH_LONG).show();

            }else if (Global.idioma == "IT"){
                Toast.makeText(getApplicationContext(), "Contatto inviato", Toast.LENGTH_LONG).show();

            }
            if (noteDbId >= 0) {
                actualiza(noteDbId);
            } else {
                insereNova();
            }
            finish();;

        }
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
            etEmpresa.setHint("Insira o nome da empresa");
            etNome.setHint("Insira o nome do cliente");
            etContacto.setHint("Insira um contacto");
            etTexto.setHint("Insira um texto");
            etPais.setHint("Insira o país");
            btnGuardar.setText("Guardar");
            if(mPT.isChecked()) mPT.setChecked(false);
            else mPT.setChecked(true);

        } else if (Global.idioma =="EN"){

            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            etEmpresa.setHint("Enter the company name");
            etNome.setHint("Enter the customer's name");
            etContacto.setHint("Enter a contact");
            etPais.setHint("Enter a country");
            etTexto.setHint("Enter text");
            btnGuardar.setText("Save");
            if(mEN.isChecked()) mEN.setChecked(false);
            else mEN.setChecked(true);


        } else if (Global.idioma =="FR") {

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            etEmpresa.setHint("Entrez le nom de l'entreprise");
            etNome.setHint("Entrez le nom du client");
            etContacto.setHint("Entrez un contact");
            etPais.setHint("Entrez une pays");
            etTexto.setHint("Entrez le texte");
            btnGuardar.setText("Sauvegarder");
            if(mFR.isChecked()) mFR.setChecked(false);
            else mFR.setChecked(true);

        } else if (Global.idioma =="IT") {

            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            etEmpresa.setHint("Inserire il nome della società");
            etNome.setHint("Immettere il nome del cliente");
            etContacto.setHint("Inserisci un contatto");
            etTexto.setHint("Inserisci il testo");
            etPais.setHint("Inserisci il paese");
            btnGuardar.setText("Salva");
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
            etEmpresa.setHint("Insira o nome da empresa");
            etNome.setHint("Insira o nome do cliente");
            etContacto.setHint("Insira um contacto");
            etPais.setHint("Insira o país");
            etTexto.setHint("Insira um texto");
            btnGuardar.setText("Guardar");



            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);

            return true;
        } else if (id == R.id.nav_en){

            Global.idioma = "EN";

            catalogo.setTitle("Catalog");
            notas.setTitle("Contacts");
            video.setTitle("Video");
            etEmpresa.setHint("Enter the company name");
            etNome.setHint("Enter the customer's name");
            etPais.setHint("Enter a country");
            etContacto.setHint("Enter a contact");
            etTexto.setHint("Enter text");
            btnGuardar.setText("Save");



            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_fr){

            Global.idioma = "FR";

            catalogo.setTitle("Catalogue");
            notas.setTitle("Contacts");
            video.setTitle("Vidéo");
            etEmpresa.setHint("Entrez le nom de l'entreprise");
            etNome.setHint("Entrez le nom du client");
            etContacto.setHint("Entrez un contact");
            etPais.setHint("Entrez une pays");
            etTexto.setHint("Entrez le texte");
            btnGuardar.setText("Sauvegarder");


            if(item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
            return true;

        } else if (id == R.id.nav_it){

            Global.idioma = "IT";

            catalogo.setTitle("Catalogo");
            notas.setTitle("Contatti");
            video.setTitle("Video");
            etEmpresa.setHint("Inserire il nome della società");
            etNome.setHint("Immettere il nome del cliente");
            etContacto.setHint("Inserisci un contatto");
            etTexto.setHint("Inserisci il testo");
            etPais.setHint("Inserisci il paese");
            btnGuardar.setText("Salva");



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
            finish();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_Notas) {
            Intent i = new Intent(this, NotesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_video) {

            Intent i = new Intent(this, VideoActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
