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

import java.util.Arrays;
import java.util.List;

/**
 * Created by Abel on 11/11/2016.
 */

public class PerfilFragment extends Fragment  {

    TextView name;
    TextView mail;
    TextView tematicas;
    TextView city;
    TextView direction;
    TextView siguiendo;
    TextView seguidores;
    TextView eventosAsistidos;
    TextView eventosOrganizados;
    TextView opiniones;
    ImageView verified;
    ImageView fotoPerfil;
    RatingBar reputacion;
    private String username;
    String idUsuario;
    String categorias;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil, container, false);
        Bundle bundle = getArguments();
        username = bundle.getString("user");
        view.findViewById(R.id.followbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.contexto, "Has pulsado Follow", Toast.LENGTH_LONG).show();
            }
        });
        view.findViewById(R.id.reportbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.contexto, "Has pulsado Report", Toast.LENGTH_LONG).show();
            }
        });
        name = (TextView)view.findViewById(R.id.username);
        city = (TextView)view.findViewById(R.id.ciudadtext);
        direction = (TextView)view.findViewById(R.id.direcciontext);
        siguiendo = (TextView) view.findViewById(R.id.siguiendo);
        seguidores = (TextView) view.findViewById(R.id.seguidores);
        eventosAsistidos = (TextView) view.findViewById(R.id.asistidos);
        eventosOrganizados = (TextView) view.findViewById(R.id.organizados);
        opiniones = (TextView) view.findViewById(R.id.opiniones);mail = (TextView)view.findViewById(R.id.emailtext);
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
        city.setText(Html.fromHtml("<b>" + "Ciudad: " + "</b>" + "..."));
        direction.setText(Html.fromHtml("<b>" + "Dirección: " + "</b>" + "..."));
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

        Integer numSiguiendo = 0;
        siguiendo.setText(Html.fromHtml("<b>" + "Siguiendo: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numSiguiendo + "</Font></u>"));

        Integer numSeguidores = 0;
        seguidores.setText(Html.fromHtml("<b>" + "Seguidores: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numSeguidores+"</Font></u>"));

        Integer numEventosAsistidos = 0;
        eventosAsistidos.setText(Html.fromHtml("<b>" + "Eventos asistidos: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numEventosAsistidos+"</Font></u>"));

        Integer numEventosOrganizados = 0;
        eventosOrganizados.setText(Html.fromHtml("<b>" + "Eventos organizados: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numEventosOrganizados+"</Font></u>"));

        Integer numOpiniones = 0;
        opiniones.setText(Html.fromHtml("<b>" + "Opiniones: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numOpiniones + "</Font></u>"));

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

}
