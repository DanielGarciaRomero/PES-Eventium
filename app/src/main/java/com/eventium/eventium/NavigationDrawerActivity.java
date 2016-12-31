package com.eventium.eventium;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.eventium.eventium.TabFragments.TabThreeFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;
import java.util.TimeZone;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    public static String PACKAGE_NAME;
    public static Context contexto;
    public static String token;
    public static Boolean filtrar;
    public static String direccionFiltraje;

    public static Bitmap userimage;
    public static String usersaldo;

    public static ArrayList<Evento> events;
    public static ArrayList<Calendario> calendarios;
    public static ArrayList<Calendario> calenSponsors;

    TextView nav_usersaldo;
    ImageView nav_userimage;

    public static Boolean change_image;
    public static Boolean change_saldo;

    public static String event_id;
    public static String myUsername;
    public static Integer myUserID;

    TextView textFechaIni;
    TextView textFechaFi;
    TextView textHoraIni;
    TextView textHoraFi;
    TextView textFiltrar;
    TextView textCancelar;
    int seletedPicker; // 0 -> fechaIni, 1 -> fechaFi, 2-> horaIni, 3-> horaFi
    String fechaIni, fechaFi, horaIni, horaFi;
    CheckBox checkbox;
    EditText etciudad, etprecioMin, etprecioMax;
    String categoriasMarcadas, ciudad, precioMin, precioMax;
    View viewFilter;
    NavigationView navigationView;
    RatingBar minRatingBar, maxRatingBar;
    EditText etcity;
    TextView textFiltrar2, textCancelar2;

    public void fromAnyWhereToVerEventos() {
        navigationView.getMenu().getItem(0).setChecked(true);
        Fragment fragment = new EventosFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_principal, fragment)
                .commit();
        getSupportActionBar().setTitle("Eventium");
    }

    public void fromCalendarioToMostrarEvento(String item) {
        navigationView.getMenu().getItem(0).setChecked(true);
        Fragment fragment = new MostrarEventoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("event", item);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Eventium");
    }

    public void fromAsistentesToMostrarUsuario(String item) {
        navigationView.getMenu().getItem(0).setChecked(true);
        Fragment fragment;
        if(!item.equals(myUsername)) fragment = new PerfilFragment();
        else fragment = new MiPerfilFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", item);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Eventium");
    }

    public void fromMostrarToEditarEvento(String item) {
        Fragment fragment = new EditarEventoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("event", item);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Editar evento");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        change_image = false;
        change_saldo = false;
        filtrar = false;

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

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);

        if (MainActivity.token != null) token = MainActivity.token;
        else token = RegistroActivity.token;

        HTTPMethods httpMethods = new HTTPMethods(4);
        httpMethods.setToken_user(token);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        UsernameSponsor us = httpMethods.getUsernameSponsor();
        String aux2 = us.getUsername();
        //String aux = httpMethods.getResultado();
        //String aux2 = aux.substring(14, aux.length() - 2);

        HTTPMethods httpMethods1 = new HTTPMethods(1);
        httpMethods1.setUsername(aux2);
        httpMethods1.ejecutarHttpAsyncTask();
        while (!httpMethods1.getFinished());
        Usuario user = httpMethods1.getUser();

        myUserID = Integer.parseInt(user.getId());
        myUsername = user.getUsername();

        TextView nav_username = (TextView) hView.findViewById(R.id.textViewNaviDrawer1);
        nav_username.setText(user.getUsername());
        TextView nav_useremail = (TextView) hView.findViewById(R.id.textViewNaviDrawer2);
        nav_useremail.setText(user.getMail());
        nav_usersaldo = (TextView) hView.findViewById(R.id.textViewNaviDrawer3);
        nav_usersaldo.setText("Saldo : " + user.getSaldo() +  " €");
        nav_userimage = (ImageView) hView.findViewById(R.id.imageViewNaviDrawer);
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //Toast.makeText(this, "Dia seleccionado: " + dayOfMonth + "/" + (monthOfYear+1) + "/" + year, Toast.LENGTH_SHORT).show();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, monthOfYear);
        String s = new SimpleDateFormat("E d MMM yyyy").format(cal.getTime());
        if (seletedPicker == 0) {
            textFechaIni.setText(s);
            int mes = monthOfYear+1;
            fechaIni = year + "/";
            if (mes < 10) fechaIni += "0" + mes + "/";
            else fechaIni += mes + "/";
            if (dayOfMonth < 10) fechaIni += "0" + dayOfMonth;
            else fechaIni += dayOfMonth;
            //System.out.println(fechaIni);
        }
        else if (seletedPicker == 1) {
            textFechaFi.setText(s);
            int mes = monthOfYear+1;
            fechaFi = year + "/";
            if (mes < 10) fechaFi += "0" + mes + "/";
            else fechaFi += mes + "/";
            if (dayOfMonth < 10) fechaFi += "0" + dayOfMonth;
            else fechaFi += dayOfMonth;
            //System.out.println(fechaFi);
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        //Toast.makeText(this, "Hora seleccionada: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        if (seletedPicker == 2) {
            if (hourOfDay < 10) horaIni = "0" + hourOfDay + ":";
            else horaIni = hourOfDay + ":";
            if (minute < 10) horaIni += "0" + minute;
            else horaIni += minute;
            textHoraIni.setText(horaIni);
        }
        else if (seletedPicker == 3) {
            if (hourOfDay < 10) horaFi = "0" + hourOfDay + ":";
            else horaFi = hourOfDay + ":";
            if (minute < 10) horaFi += "0" + minute;
            else horaFi += minute;
            textHoraFi.setText(horaFi);
        }
    }

    public void createFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        viewFilter = inflater.inflate(R.layout.dialog_filtrar, null);
        builder.setView(viewFilter);
        final AlertDialog dialog = builder.create();

        textFechaIni = (TextView) viewFilter.findViewById(R.id.filtrar_fechaIni_text);
        textFechaFi = (TextView) viewFilter.findViewById(R.id.filtrar_fechaFi_text);
        fechaIni = "dd/mm/aaaa";
        fechaFi = "dd/mm/aaaa";
        textFechaIni.setText(fechaIni);
        textFechaFi.setText(fechaFi);

        textFechaIni.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                NavigationDrawerActivity.this,
                                c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH)
                        );
                        seletedPicker = 0;
                        dpd.show(getFragmentManager(), "DatePickerDialog");
                    }
                }
        );

        textFechaFi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                NavigationDrawerActivity.this,
                                c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH)
                        );
                        seletedPicker = 1;
                        dpd.show(getFragmentManager(), "DatePickerDialog");
                    }
                }
        );

        textHoraIni = (TextView) viewFilter.findViewById(R.id.filtrar_horaIni_text);
        textHoraFi = (TextView) viewFilter.findViewById(R.id.filtrar_horaFi_text);
        horaIni = "hh:mm";
        horaFi = "hh:mm";
        textHoraIni.setText(horaIni);
        textHoraFi.setText(horaFi);

        textHoraIni.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        TimePickerDialog dpd = TimePickerDialog.newInstance(
                                NavigationDrawerActivity.this,
                                c.get(Calendar.HOUR),
                                c.get(Calendar.MINUTE),
                                true);
                        seletedPicker = 2;
                        dpd.show(getFragmentManager(), "Timepickerdialog");
                    }
                }
        );

        textHoraFi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        TimePickerDialog dpd = TimePickerDialog.newInstance(
                                NavigationDrawerActivity.this,
                                c.get(Calendar.HOUR),
                                c.get(Calendar.MINUTE),
                                true);
                        seletedPicker = 3;
                        dpd.show(getFragmentManager(), "Timepickerdialog");
                    }
                }
        );

        etciudad = (EditText) viewFilter.findViewById(R.id.filtrar_et_ciudad);
        etciudad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        etprecioMin = (EditText) viewFilter.findViewById(R.id.filtrar_et_precioMin);
        etprecioMin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        etprecioMax = (EditText) viewFilter.findViewById(R.id.filtrar_et_precioMax);
        etprecioMax.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        textFiltrar = (TextView) viewFilter.findViewById(R.id.filtrar_tv_filtrar);
        textCancelar = (TextView) viewFilter.findViewById(R.id.filtrar_tv_cancelar);
        textFiltrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filtrar = true;

                        direccionFiltraje = "http://10.4.41.168:5000/events?";
                        boolean first = true;

                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Art);
                        categoriasMarcadas = "";
                        if (checkbox.isChecked()) categoriasMarcadas = "0,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Auto);
                        if (checkbox.isChecked()) categoriasMarcadas += "1,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Cine);
                        if (checkbox.isChecked()) categoriasMarcadas += "2,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Depor);
                        if (checkbox.isChecked()) categoriasMarcadas += "3,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Gastro);
                        if (checkbox.isChecked()) categoriasMarcadas += "4,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Liter);
                        if (checkbox.isChecked()) categoriasMarcadas += "5,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Moda);
                        if (checkbox.isChecked()) categoriasMarcadas += "6,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Music);
                        if (checkbox.isChecked()) categoriasMarcadas += "7,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Otros);
                        if (checkbox.isChecked()) categoriasMarcadas += "8,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Poli);
                        if (checkbox.isChecked()) categoriasMarcadas += "9,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Teatral);
                        if (checkbox.isChecked()) categoriasMarcadas += "10,";
                        checkbox = (CheckBox) viewFilter.findViewById(R.id.filtrar_checkBox_Tecno);
                        if (checkbox.isChecked()) categoriasMarcadas += "11";
                        int length = categoriasMarcadas.length();
                        if (length > 0) {
                            char c = categoriasMarcadas.charAt(length - 1);
                            if (c == ',')
                                categoriasMarcadas = categoriasMarcadas.substring(0, length - 1);
                            if (first) {
                                direccionFiltraje += "categoria=" + categoriasMarcadas;
                                first = false;
                            } else direccionFiltraje += "&categoria=" + categoriasMarcadas;
                        }

                        ciudad = etciudad.getText().toString();
                        precioMin = etprecioMin.getText().toString();
                        precioMax = etprecioMax.getText().toString();

                        if (!ciudad.equals("")) {
                            if (first) {
                                direccionFiltraje += "ciudad=" + ciudad;
                                first = false;
                            } else direccionFiltraje += "&ciudad=" + ciudad;
                        }

                        if (!precioMin.equals("")) {
                            if (first) {
                                direccionFiltraje += "precioMin=" + precioMin;
                                first = false;
                            } else direccionFiltraje += "&precioMin=" + precioMin;
                        }

                        if (!precioMax.equals("")){
                            if (first) {
                                direccionFiltraje += "precioMax=" + precioMax;
                                first = false;
                            } else direccionFiltraje += "&precioMax=" + precioMax;
                        }

                        if (!fechaIni.equals("dd/mm/aaaa")){
                            if (first) {
                                direccionFiltraje += "fecha_ini=" + fechaIni;
                                first = false;
                            } else direccionFiltraje += "&fecha_ini=" + fechaIni;
                        }

                        if (!fechaFi.equals("dd/mm/aaaa")) {
                            if (first) {
                                direccionFiltraje += "fecha_fin=" + fechaFi;
                                first = false;
                            } else direccionFiltraje += "&fecha_fin=" + fechaFi;
                        }

                        if (!horaIni.equals("hh:mm")) {
                            if (first) {
                                direccionFiltraje += "hora_ini=" + horaIni;
                                first = false;
                            } else direccionFiltraje += "&hora_ini=" + horaIni;
                        }

                        if (!horaFi.equals("hh:mm")){
                            if (first) {
                                direccionFiltraje += "hora_fin=" + horaFi;
                                first = false;
                            } else direccionFiltraje += "&hora_fin=" + horaFi;
                        }

                        dialog.dismiss();

                        EventosFragment.tabLayout.getTabAt(2).select();
                        ViewPager vp = EventosFragment.viewPager;
                        TabThreeFragment frag3 = (TabThreeFragment) vp.getAdapter().instantiateItem(vp, 2);
                        frag3.mostrarEventosFiltrados();
                    }

                }
        );
        textCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        dialog.show();
    }

    public void createUserFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        viewFilter = inflater.inflate(R.layout.dialog_filtrar_users, null);
        builder.setView(viewFilter);
        final AlertDialog dialog = builder.create();

        minRatingBar = (RatingBar) viewFilter.findViewById(R.id.minRatingBar);
        maxRatingBar = (RatingBar) viewFilter.findViewById(R.id.maxRatingBar);
        etcity = (EditText) viewFilter.findViewById(R.id.filtrar_et_city);
        etcity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        textFiltrar2 = (TextView) viewFilter.findViewById(R.id.filtrar_tv_filtrar2);
        textCancelar2 = (TextView) viewFilter.findViewById(R.id.filtrar_tv_cancelar2);

        textFiltrar2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int minRatingValue = (int) minRatingBar.getRating();
                        int maxRatingValue = (int) maxRatingBar.getRating();
                        System.out.println("minRatingValue = " + minRatingValue);
                        System.out.println("maxRatingValue = " + maxRatingValue);
                        System.out.println("ciudad = " + etcity.getText().toString());
                        dialog.dismiss();
                    }
                }
        );

        textCancelar2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            createFilterDialog();
        }
        if (item.getItemId() == R.id.action_filter2) {
            //Toast.makeText(getBaseContext(), "Has clicado en filtrar usuarios", Toast.LENGTH_LONG).show();
            createUserFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}
