package com.eventium.eventium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public static String PACKAGE_NAME;
    public static Context contexto;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        contexto = getBaseContext();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        TextView nav_usersaldo = (TextView) hView.findViewById(R.id.textViewNaviDrawer3);
        nav_usersaldo.setText("Saldo : " + user.getSaldo() +  " €");
        ImageView nav_userimage = (ImageView) hView.findViewById(R.id.imageViewNaviDrawer);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            Toast.makeText(getBaseContext(), "Has clicado en filtrar eventos", Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.action_filter2) {
            Toast.makeText(getBaseContext(), "Has clicado en filtrar usuarios", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
