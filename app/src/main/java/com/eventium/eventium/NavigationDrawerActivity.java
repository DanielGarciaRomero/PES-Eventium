package com.eventium.eventium;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public static String PACKAGE_NAME;
    public static Context contexto;
    public static String token;

    public static Bitmap userimage;
    public static String usersaldo;

    public static ArrayList<Evento> events;

    TextView nav_usersaldo;
    ImageView nav_userimage;

    public static Boolean change_image;
    public static Boolean change_saldo;

    TextView textFechaIni;
    TextView textFechaFi;
    TextView textHoraIni;
    TextView textHoraFi;
    TextView textFiltrar;
    TextView textCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        change_image = false;
        change_saldo = false;

        PACKAGE_NAME = getApplicationContext().getPackageName();
        contexto = getBaseContext();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
                    public void onDrawerOpened(View drawerView){
                        super.onDrawerOpened(drawerView);
                        if (change_image) {
                            RoundImage roundedImage = new RoundImage(userimage);
                            nav_userimage.setImageDrawable(roundedImage);
                        }
                        if (change_saldo) {
                            nav_usersaldo.setText("Saldo : " + usersaldo +  " €");
                        }
                    }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);

        if (MainActivity.token != null) token = MainActivity.token;
        else token = RegistroActivity.token;

        HTTPMethods httpMethods = new HTTPMethods(4);
        httpMethods.setToken_user(token);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        String aux = httpMethods.getResultado();
        String aux2 = aux.substring(14, aux.length() - 2);

        HTTPMethods httpMethods1 = new HTTPMethods(1);
        httpMethods1.setUsername(aux2);
        httpMethods1.ejecutarHttpAsyncTask();
        while (!httpMethods1.getFinished());
        Usuario user = httpMethods1.getUser();

        TextView nav_username = (TextView) hView.findViewById(R.id.textViewNaviDrawer1);
        nav_username.setText(user.getUsername());
        TextView nav_useremail = (TextView) hView.findViewById(R.id.textViewNaviDrawer2);
        nav_useremail.setText(user.getMail());
        nav_usersaldo = (TextView) hView.findViewById(R.id.textViewNaviDrawer3);
        nav_usersaldo.setText("Saldo : " + user.getSaldo() +  " €");
        nav_userimage = (ImageView) hView.findViewById(R.id.imageViewNaviDrawer);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.raw.zuckerberg);
        String nav_userpic = user.getPic();
        byte[] decodedImage = Base64.decode(nav_userpic, Base64.DEFAULT);
        Bitmap base64BitmapImage = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        RoundImage roundedImage = new RoundImage(base64BitmapImage);
        nav_userimage.setImageDrawable(roundedImage);

        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new EventosFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_principal, fragment)
                .commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean FragmentTransaction = false;
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new EventosFragment();
            FragmentTransaction = true;
        } else if (id == R.id.nav_evento) {
            fragment = new CrearEventoFragment();
            FragmentTransaction = true;
            //Toast.makeText(getBaseContext(), "Has clicado en Crear evento", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_usuarios) {
            fragment = new UsuariosFragment();
            FragmentTransaction = true;
            //Toast.makeText(getBaseContext(), "Has clicado en Usuarios", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_perfil) {
            fragment = new MiPerfilFragment();
            FragmentTransaction = true;
            //Toast.makeText(getBaseContext(), "Has clicado en Mi perfil", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_calendario) {
            fragment = new CalendarioFragment();
            FragmentTransaction = true;
            //Toast.makeText(getBaseContext(), "Has clicado en Mi calendario", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_saldo) {
            fragment = new SaldoFragment();
            FragmentTransaction = true;
            //Toast.makeText(getBaseContext(), "Has clicado en Ingresar saldo", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_ajustes) {
            /*
            fragment = new AjustesFragment();
            FragmentTransaction = true;
            */
            Toast.makeText(getBaseContext(), "Has clicado en Ajustes", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {
            //fragment = new GaleriaFragment();
            //FragmentTransaction = true;
            MainActivity.token = null;
            RegistroActivity.token = null;
            NavigationDrawerActivity.this.startActivity(new Intent(NavigationDrawerActivity.this, MainActivity.class));
            //Toast.makeText(getBaseContext(), "Has clicado en Logout", Toast.LENGTH_LONG).show();
        }

        if (FragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor_principal, fragment)
                    .commit();

            item.setChecked(true);
            if (item.getTitle().toString().equals("HOME")) getSupportActionBar().setTitle("Eventium");
            else getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_filtrar, null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();

        textFechaIni = (TextView) v.findViewById(R.id.filtrar_fechaIni_text);
        textFechaFi = (TextView) v.findViewById(R.id.filtrar_fechaFi_text);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E MMM d yyyy");
        textFechaIni.setText(format.format(c.getTime()));
        textFechaFi.setText(format.format(c.getTime()));

        textHoraIni = (TextView) v.findViewById(R.id.filtrar_horaIni_text);
        textHoraFi = (TextView) v.findViewById(R.id.filtrar_horaFi_text);
        format = new SimpleDateFormat("HH:mm a");
        textHoraIni.setText(format.format(c.getTime()));
        textHoraFi.setText(format.format(c.getTime()));

        textFechaIni.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        NO FUNCIONA DE MOMENTO
                        final Calendar c = Calendar.getInstance();
                        DatePickerDialog dpd = new DatePickerDialog(getBaseContext(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        textFecha.setText(dayOfMonth + "-"
                                                + (monthOfYear + 1) + "-" + year);

                                    }
                                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                        dpd.show();
                        */
                    }
                }
        );

        textFiltrar = (TextView) v.findViewById(R.id.filtrar_tv_filtrar);
        textCancelar = (TextView) v.findViewById(R.id.filtrar_tv_cancelar);
        textFiltrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ...
                        dialog.dismiss();
                    }
                }
        );
        textCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ...
                        dialog.dismiss();
                    }
                }
        );

        /*
        Button filtrar = (Button) v.findViewById(R.id.filtrar_filtrar);
        Button cancelar = (Button) v.findViewById(R.id.filtrar_cancelar);

        filtrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ...
                        dialog.dismiss();
                    }
                }
        );

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ...
                        dialog.dismiss();
                    }
                }
        );
        */

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            //Toast.makeText(getBaseContext(), "Has clicado en filtrar eventos", Toast.LENGTH_LONG).show();

            createLoginDialogo();

        }
        if (item.getItemId() == R.id.action_filter2) {
            Toast.makeText(getBaseContext(), "Has clicado en filtrar usuarios", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
