package com.eventium.eventium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
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
/**
 * Created by Abel on 11/11/2016.
 */

public class PerfilFragment extends Fragment  {

    private Toolbar mytoolbar;
    private TabLayout tabLayout;

    TextView name;
    TextView mail;
    //TextView city;
    //TextView direction;
    ImageView verified;
    ImageView fotoPerfil;
    RatingBar reputacion;
    private String username;

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
        verified = (ImageView)view.findViewById(R.id.verified);
        fotoPerfil = (ImageView)view.findViewById(R.id.fotoPerfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.contexto, "Has pulsado la imagen", Toast.LENGTH_LONG).show();
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
        if(!user.getIsVerified()) {
            verified.setVisibility(View.INVISIBLE);
        }
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
