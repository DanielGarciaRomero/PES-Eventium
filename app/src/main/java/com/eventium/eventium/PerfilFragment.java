package com.eventium.eventium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class PerfilFragment extends Fragment  {

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
    ImageView fotoPerfil;
    RatingBar reputacion;
    private String username;
    String idUsuario;
    String categorias;
    ArrayList<Evento> eventos;
    ArrayList<Evento> eventos2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil, container, false);
        NavigationDrawerActivity.minimizarApp = 0;
        Bundle bundle = getArguments();
        username = bundle.getString("user");
        eventos = new ArrayList<>();
        eventos2 = new ArrayList<>();
        view.findViewById(R.id.followbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPMethods httpMethods = new HTTPMethods(32);
                httpMethods.setToken_user(NavigationDrawerActivity.token);
                httpMethods.setUser_id(NavigationDrawerActivity.myUserID);
                httpMethods.setIdfollow(Integer.parseInt(idUsuario));
                httpMethods.ejecutarHttpAsyncTask();
                while (!httpMethods.getFinished()) ;
                Toast.makeText(MainActivity.contexto, "Has seguido a " + username, Toast.LENGTH_LONG).show();
            }
        });
        view.findViewById(R.id.reportbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //++contador;
                HTTPMethods httpMethods2 = new HTTPMethods(1);
                httpMethods2.setUser_id(Integer.parseInt(idUsuario));
                httpMethods2.ejecutarHttpAsyncTask();
                while (!httpMethods2.getFinished()) ;
                Usuario user = httpMethods2.getUser();

                if (!(user.getNreports().equals(5))) {
                    HTTPMethods httpMethods = new HTTPMethods(28);
                    httpMethods.setToken_user(NavigationDrawerActivity.token);
                    httpMethods.setEvent_id(idUsuario);
                    httpMethods.ejecutarHttpAsyncTask();
                    while (!httpMethods.getFinished()) ;
                    //if (user.getNreports().equals(4)) {
                    //   ((NavigationDrawerActivity) getActivity()).fromAnyWhereToVerEventos();
                    //}
                }
            }
        });
        name = (TextView)view.findViewById(R.id.username);
        city = (TextView)view.findViewById(R.id.ciudadtext);
        //direction = (TextView)view.findViewById(R.id.direcciontext);
        siguiendo = (TextView) view.findViewById(R.id.siguiendo);
        seguidores = (TextView) view.findViewById(R.id.seguidores);
        eventosAsistidos = (TextView) view.findViewById(R.id.asistidos);
        eventosOrganizados = (TextView) view.findViewById(R.id.organizados);
        //opiniones = (TextView) view.findViewById(R.id.opiniones);
        mail = (TextView)view.findViewById(R.id.emailtext);
        tematicas = (TextView)view.findViewById(R.id.tematicas);
        verified = (ImageView)view.findViewById(R.id.verified);
        fotoPerfil = (ImageView)view.findViewById(R.id.fotoPerfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.contexto, "Has pulsado la imagen", Toast.LENGTH_LONG).show();
                //lo dejo por si hay que ampliarla o algo
            }
        });
        reputacion = (RatingBar)view.findViewById(R.id.ratingBar);


        HTTPMethods httpMethods = new HTTPMethods(1);
        httpMethods.setUsername(username);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        Usuario user = httpMethods.getUser();
        name.setText(user.getUsername());
        mail.setText(Html.fromHtml("<b>" + "Email: " + "</b>" + user.getMail()));
        if (user.getCiudad() != null) city.setText(Html.fromHtml("<b>" + "Ciudad: " + "</b>" + user.getCiudad()));
        else city.setText(Html.fromHtml("<b>" + "Ciudad: " + "</b>"));
        //direction.setText(Html.fromHtml("<b>" + "Dirección: " + "</b>" + "..."));
        byte[] decodedString = Base64.decode(user.getPic(), Base64.DEFAULT);
        Bitmap profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        fotoPerfil.setImageBitmap(profilePic);
        idUsuario = user.getId();
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
                    categorias += "Político, ";
                    ++numCategorias;
                    break;
                case "9":
                    categorias += "Teatral, ";
                    ++numCategorias;
                    break;
                case "10":
                    categorias += "Tecnológico y científico, ";
                    ++numCategorias;
                    break;
                case "11":
                    categorias += "Otros, ";
                    ++numCategorias;
                    break;
            }
        }
        //if (categorias.length() != 0) categorias = categorias.substring(0,categorias.length()-2);
        //else categorias = "Ninguna";
        //tematicas.setText("Temáticas preferidas: " + categorias);
        categorias = categorias.substring(0,categorias.length()-2);
        //tematicas.setText("Temáticas preferidas: " + categorias);
        tematicas.setText(Html.fromHtml("<b>" + "Temáticas preferidas: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numCategorias + "</Font></u>"));
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

        //Integer numSiguiendo = 0;
        HTTPMethods httpMethods7 = new HTTPMethods(33);
        httpMethods7.setUser_id(Integer.parseInt(idUsuario));
        httpMethods7.ejecutarHttpAsyncTask();
        while (!httpMethods7.getFinished());
        final List<Follow> list_follows = httpMethods7.getFollows();
        System.out.println(list_follows.size());
        siguiendo.setText(Html.fromHtml("<b>" + "Siguiendo: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + list_follows.size() + "</Font></u>"));
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

        Integer numSeguidores = 0;
        seguidores.setText(Html.fromHtml("<b>" + "Seguidores: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numSeguidores+"</Font></u>"));

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

        eventosAsistidos.setText(Html.fromHtml("<b>" + "Eventos asistidos: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+eventos.size()+"</Font></u>"));
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

        Integer numOpiniones = 0;
        //opiniones.setText(Html.fromHtml("<b>" + "Opiniones: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numOpiniones + "</Font></u>"));

        return view;
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
