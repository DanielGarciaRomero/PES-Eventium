package com.eventium.eventium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Abel on 11/11/2016.
 */

public class MiPerfilFragment extends Fragment  {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    String path;

    TextView name;
    TextView mail;
    TextView tematicas;
    TextView city;
    //TextView direction;
    TextView siguiendo;
    TextView seguidores;
    TextView eventosAsistidos;
    TextView eventosOrganizados;
    //TextView opiniones;
    ImageView verified;
    ImageButton fotoMiPerfil;
    RatingBar reputacion;
    String idUsuario;
    String categorias;
    EditText editText_user_ciudad, editText_contrasena_actual, editText_contrasena_nueva;
    TextView textView_contrasena_actual, textView_contrasena_nueva;
    Usuario user;
    String encodedString;
    Boolean nuevaFoto;
    Bitmap bm;
    ArrayList<Evento> eventos;
    ArrayList<Evento> eventos2;

    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_miperfil, container, false);
        NavigationDrawerActivity.minimizarApp = 1;
        nuevaFoto = false;
        eventos = new ArrayList<>();
        eventos2 = new ArrayList<>();
        name = (TextView)view.findViewById(R.id.username);
        city = (TextView)view.findViewById(R.id.ciudadtext);
        mail = (TextView)view.findViewById(R.id.emailtext);
        verified = (ImageView)view.findViewById(R.id.verified);
        siguiendo = (TextView) view.findViewById(R.id.siguiendo);
        seguidores = (TextView) view.findViewById(R.id.seguidores);
        eventosAsistidos = (TextView) view.findViewById(R.id.asistidos);
        eventosOrganizados = (TextView) view.findViewById(R.id.organizados);
        tematicas = (TextView)view.findViewById(R.id.tematicas);
        fotoMiPerfil = (ImageButton)view.findViewById(R.id.fotoMiPerfil);
        fotoMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.contexto, "Has pulsado la imagen", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_GALLERY_REQUEST);
            }
        });
        reputacion = (RatingBar)view.findViewById(R.id.ratingBar);


        HTTPMethods httpMethods = new HTTPMethods(4);
        httpMethods.setToken_user(NavigationDrawerActivity.token);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        UsernameSponsor us = httpMethods.getUsernameSponsor();
        String username = us.getUsername();
        //String username = httpMethods.getResultado();
        //username = username.substring(14, username.length() - 2);

        httpMethods = new HTTPMethods(1);
        httpMethods.setUsername(username);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        user = httpMethods.getUser();

        idUsuario = user.getId();

        RatingBar puntuacion = (RatingBar) view.findViewById(R.id.ratingBar);
        puntuacion.setStepSize(0.5f);
        try {
            puntuacion.setRating(Float.parseFloat(user.getValoracion()));
        } catch (Exception e){
            Toast.makeText(NavigationDrawerActivity.contexto, "Aun no tienes puntuación", Toast.LENGTH_LONG).show();
        }

        name.setText(user.getUsername());

        mail.setText(Html.fromHtml("<b>" + "Email: " + "</b>" + user.getMail()));

        city.setText(Html.fromHtml("<b>" + "Ciudad: " + "</b>"));
        textView_contrasena_actual = (TextView)view.findViewById(R.id.textView_contrasena_actual);
        textView_contrasena_actual.setText(Html.fromHtml("<b>" + "Contraseña actual:" + "</b>"));
        textView_contrasena_nueva = (TextView)view.findViewById(R.id.textView_contrasena_nueva);
        textView_contrasena_nueva.setText(Html.fromHtml("<b>" + "Nueva contraseña:" + "</b>"));

        editText_user_ciudad = (EditText)view.findViewById(R.id.editText_user_ciudad);
        editText_user_ciudad.setText(user.getCiudad());
        editText_user_ciudad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        editText_contrasena_actual = (EditText)view.findViewById(R.id.editText_contrasena_actual);
        editText_contrasena_actual.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        editText_contrasena_nueva = (EditText)view.findViewById(R.id.editText_contrasena_nueva);
        editText_contrasena_nueva.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        Button modificar_usuario = (Button) view.findViewById(R.id.button_modificar_usuario);
        modificar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_contrasena_actual.getText().toString().equals("")) {
                    // contrasena actual no vacia
                    if (!editText_contrasena_actual.getText().toString().equals(user.getPassword())) {
                        Toast.makeText(NavigationDrawerActivity.contexto, "Contraseña actual incorrecta", Toast.LENGTH_LONG).show();
                    }
                    else { // contrasena actual correcta
                        if (editText_contrasena_nueva.getText().toString().equals("")) {
                            Toast.makeText(NavigationDrawerActivity.contexto, "Introduce una nueva contraseña", Toast.LENGTH_LONG).show();
                        }
                        else { // contrasenas actual y nueva correctas => quiere canviar contrasena
                            // Hacer el PUT
                            HTTPMethods httpMethods = new HTTPMethods(16);
                            httpMethods.setUser_id(Integer.parseInt(idUsuario));
                            httpMethods.setUserCiudad(editText_user_ciudad.getText().toString());
                            if (user.getIsVerified()) httpMethods.setUserVerified("True");
                            else httpMethods.setUserVerified("False");
                            if (user.getIsBanned()) httpMethods.setUserBanned("True");
                            else httpMethods.setUserBanned("False");
                            httpMethods.setPassword(editText_contrasena_nueva.getText().toString()); // contrasena nueva
                            if (nuevaFoto) {
                                httpMethods.setPic(encodedString);
                                NavigationDrawerActivity.change_image = true;
                                NavigationDrawerActivity.userimage = bm;
                            }
                            else httpMethods.setPic(user.getPic());
                            httpMethods.ejecutarHttpAsyncTask();
                            while (!httpMethods.getFinished());
                            Toast.makeText(NavigationDrawerActivity.contexto, "Los cambios han sido guardados", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else { // contrasena actual vacia
                    if (!editText_contrasena_nueva.getText().toString().equals("")) {
                        Toast.makeText(NavigationDrawerActivity.contexto, "Contraseña actual incorrecta", Toast.LENGTH_LONG).show();
                    }
                    else { // contrasenas actual y nueva vacias => no quiere canviar contrasena
                        // Hacer el PUT
                        HTTPMethods httpMethods = new HTTPMethods(16);
                        httpMethods.setUser_id(Integer.parseInt(idUsuario));
                        httpMethods.setUserCiudad(editText_user_ciudad.getText().toString());
                        if (user.getIsVerified()) httpMethods.setUserVerified("True");
                        else httpMethods.setUserVerified("False");
                        if (user.getIsBanned()) httpMethods.setUserBanned("True");
                        else httpMethods.setUserBanned("False");
                        httpMethods.setPassword(user.getPassword()); // contrasena actual
                        if (nuevaFoto) {
                            httpMethods.setPic(encodedString);
                            NavigationDrawerActivity.change_image = true;
                            NavigationDrawerActivity.userimage = bm;
                        }
                        else httpMethods.setPic(user.getPic());
                        httpMethods.ejecutarHttpAsyncTask();
                        while (!httpMethods.getFinished());
                        Toast.makeText(NavigationDrawerActivity.contexto, "Los cambios han sido guardados", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        byte[] decodedString = Base64.decode(user.getPic(), Base64.DEFAULT);
        Bitmap profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        fotoMiPerfil.setImageBitmap(profilePic);
        if(!user.getIsVerified()) {
            verified.setVisibility(View.INVISIBLE);
        }

        int idInt = Integer.parseInt(idUsuario);
        categorias = "";
        httpMethods = new HTTPMethods(5);
        httpMethods.setUser_id(idInt);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        String cats = httpMethods.getCategories();

        Integer numCategorias = 0;
        List<String> categoriasList = Arrays.asList(cats.split(","));
        for (String aux : categoriasList) {
            switch (aux) {
                case "0":
                    categorias += "Artístico, ";
                    ++numCategorias;
                    break;
                case "1":
                    categorias += "Automobilístico, ";
                    ++numCategorias;
                    break;
                case "2":
                    categorias += "Cinematográfico, ";
                    ++numCategorias;
                    break;
                case "3":
                    categorias += "Deportivo, ";
                    ++numCategorias;
                    break;
                case "4":
                    categorias += "Gastronómico, ";
                    ++numCategorias;
                    break;
                case "5":
                    categorias += "Literario, ";
                    ++numCategorias;
                    break;
                case "6":
                    categorias += "Moda, ";
                    ++numCategorias;
                    break;
                case "7":
                    categorias += "Musical, ";
                    ++numCategorias;
                    break;
                case "8":
                    categorias += "Otros, ";
                    ++numCategorias;
                    break;
                case "9":
                    categorias += "Político, ";
                    ++numCategorias;
                    break;
                case "10":
                    categorias += "Teatral, ";
                    ++numCategorias;
                    break;
                case "11":
                    categorias += "Tecnológico y científico, ";
                    ++numCategorias;
                    break;
            }
        }
        categorias = categorias.substring(0,categorias.length()-2);
        tematicas.setText(Html.fromHtml("<b>" + "Temáticas preferidas: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numCategorias+"</Font></u>"));
        tematicas.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //NavigationDrawerActivity.event_id = eventID;
                        MyDialogFragmentTematicas dialogFragment = new MyDialogFragmentTematicas();
                        Bundle bundle = new Bundle();
                        bundle.putString("categories", categorias);
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );

        Integer numSiguiendo;
        HTTPMethods httpMethods7 = new HTTPMethods(33);
        httpMethods7.setUser_id(Integer.parseInt(idUsuario));
        httpMethods7.ejecutarHttpAsyncTask();
        while (!httpMethods7.getFinished());
        final List<Follow> list_follows = httpMethods7.getFollows();
        if (list_follows != null) {
            numSiguiendo = list_follows.size();
        }
        else numSiguiendo = 0;
        siguiendo.setText(Html.fromHtml("<b>" + "Siguiendo: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numSiguiendo + "</Font></u>"));
        siguiendo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationDrawerActivity.follows = (ArrayList) list_follows;
                        MyDialogFragmentFollow dialogFragment = new MyDialogFragmentFollow();
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );

        Integer numSeguidores;
        HTTPMethods httpMethods8 = new HTTPMethods(34);
        httpMethods8.setUser_id(Integer.parseInt(idUsuario));
        httpMethods8.ejecutarHttpAsyncTask();
        while (!httpMethods8.getFinished());
        final List<Followers> list_followers = httpMethods8.getFollowers();
        if (list_followers != null) {
            numSeguidores = list_followers.size();
        }
        else numSeguidores = 0;
        seguidores.setText(Html.fromHtml("<b>" + "Seguidores: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numSeguidores + "</Font></u>"));
        seguidores.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationDrawerActivity.followers = (ArrayList) list_followers;
                        MyDialogFragmentFollowers dialogFragment = new MyDialogFragmentFollowers();
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );
        //Integer numEventosAsistidos = 0;
        //Obtengo el calendario de un user
        HTTPMethods httpMethods3 = new HTTPMethods(8);
        httpMethods3.setToken_user(NavigationDrawerActivity.token);
        httpMethods3.setUser_id(Integer.parseInt(idUsuario));
        httpMethods3.ejecutarHttpAsyncTask();
        while (!httpMethods3.getFinished());
        List<Calendario> list_calendario = httpMethods3.getCalendarios();

        final Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        System.out.println("HOLA" + date.getTime());

        if (list_calendario != null) {
            for (int i = 0; i < list_calendario.size(); ++i) {
                Integer eventID = list_calendario.get(i).getEventid();

                HTTPMethods httpMethods4 = new HTTPMethods(7);
                httpMethods4.setEvent_id(eventID.toString());
                httpMethods4.ejecutarHttpAsyncTask();
                while (!httpMethods4.getFinished()) ;
                Evento event = httpMethods4.getEvent();

                try {
                    if (!fechaAnteriorActual(event.getFecha_ini())) {
                        eventos.add(event);
                    }
                }
                catch (ParseException e) {}
                catch (datesException e) {}
            }
        }

        eventosAsistidos.setText(Html.fromHtml("<b>" + "Eventos asistidos: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + eventos.size() + "</Font></u>"));
        eventosAsistidos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //NavigationDrawerActivity.event_id = eventID;
                        NavigationDrawerActivity.events = eventos;
                        MyDialogFragment dialogFragment = new MyDialogFragment ();
                        dialogFragment.show(getActivity().getFragmentManager(), "hola");
                    }
                }
        );

        HTTPMethods httpMethods5 = new HTTPMethods(27);
        httpMethods5.setUser_id(Integer.parseInt(idUsuario));
        httpMethods5.ejecutarHttpAsyncTask();
        while (!httpMethods5.getFinished()) ;
        List<Evento> list_events = httpMethods5.getEvents();
        if (list_events != null) {
            for (int i = 0; i < list_events.size(); ++i) {
                if (list_events.get(i).getOrganizerId().equals(idUsuario)) eventos2.add(list_events.get(i));
            }
        }
        //Integer numEventosOrganizados = 0;
        eventosOrganizados.setText(Html.fromHtml("<b>" + "Eventos organizados: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+eventos2.size()+"</Font></u>"));
        eventosOrganizados.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //NavigationDrawerActivity.event_id = eventID;
                        NavigationDrawerActivity.events = eventos2;
                        MyDialogFragment dialogFragment = new MyDialogFragment();
                        dialogFragment.show(getActivity().getFragmentManager(), "hola");
                    }
                }
        );

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri geller = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Context c = NavigationDrawerActivity.contexto;
                Cursor cursor = c.getContentResolver().query(geller, filePathColumn, null, null, null);
                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(filePathColumn[0]);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                    cursor.close();
                    // PONER LA NUEVA IMAGEN EN EL IMAGEBUTTON
                    bm = BitmapFactory.decodeFile(path);
                    fotoMiPerfil.setImageBitmap(bm);
                    nuevaFoto = true;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    byte[] bb = bos.toByteArray();
                    encodedString = Base64.encodeToString(bb, Base64.DEFAULT);
                    // PUT de /users/<id>
                    /*
                    HTTPMethods httpMethods = new HTTPMethods(16);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    byte[] bb = bos.toByteArray();
                    String encodedString = Base64.encodeToString(bb, Base64.DEFAULT);
                    httpMethods.setPic(encodedString);
                    httpMethods.setUser_id(Integer.parseInt(idUsuario));
                    httpMethods.ejecutarHttpAsyncTask();
                    while (!httpMethods.getFinished());
                    NavigationDrawerActivity.change_image = true;
                    NavigationDrawerActivity.userimage = bm;
                    Toast.makeText(NavigationDrawerActivity.contexto, "Imagen modificada correctamente", Toast.LENGTH_LONG).show();
                    */
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //inflater.inflate(R.menu.menu_saldo, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public boolean fechaAnteriorActual(String dataIni) throws ParseException, datesException
    {
        try {
            String s = dataIni.substring(0, 4); int anyIni = Integer.parseInt(s);
            s = dataIni.substring(5, 7); int mesIni = Integer.parseInt(s);
            s = dataIni.substring(8, 10); int diaIni = Integer.parseInt(s);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            s = anyIni + "-" + mesIni + "-" + diaIni;
            Date date1 = sdf.parse(s);
            Date currentDate = new Date();
            currentDate = sdf.parse(sdf.format(currentDate));
            if (date1.before(currentDate)) return false;
            return true;
        }
        catch(NumberFormatException e){
            throw new datesException("");
        }
    }

    public class datesException extends Exception {
        public datesException(String msg) {
            super(msg);
        }
    }

}
