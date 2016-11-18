package com.eventium.eventium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    //TextView city;
    //TextView direction;
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
        mail.setText("Email: " + user.getMail());
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

        List<String> categoriasList = Arrays.asList(cats.split(","));
        for (String aux : categoriasList) {
            switch (aux) {
                case "0":
                    categorias += "Artístico, ";
                    break;
                case "1":
                    categorias += "Automobilístico, ";
                    break;
                case "2":
                    categorias += "Cinematográfico, ";
                    break;
                case "3":
                    categorias += "Deportivo, ";
                    break;
                case "4":
                    categorias += "Gastronómico, ";
                    break;
                case "5":
                    categorias += "Literario, ";
                    break;
                case "6":
                    categorias += "Moda, ";
                    break;
                case "7":
                    categorias += "Musical, ";
                    break;
                case "8":
                    categorias += "Político, ";
                    break;
                case "9":
                    categorias += "Teatral, ";
                    break;
                case "10":
                    categorias += "Tecnológico y científico, ";
                    break;
                case "11":
                    categorias += "Otros, ";
                    break;
            }
        }
        categorias = categorias.substring(0,categorias.length()-2);
        System.out.println(categorias);
        tematicas.setText("Temáticas preferidas: " + categorias);
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
