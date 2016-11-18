package com.eventium.eventium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

/**
 * Created by Abel on 11/11/2016.
 */

public class MiPerfilFragment extends Fragment  {

    TextView name;
    TextView mail;
    //TextView city;
    //TextView direction;
    ImageView verified;
    ImageButton fotoMiPerfil;
    RatingBar reputacion;

    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_miperfil, container, false);
        name = (TextView)view.findViewById(R.id.username);
        mail = (TextView)view.findViewById(R.id.emailtext);
        verified = (ImageView)view.findViewById(R.id.verified);
        fotoMiPerfil = (ImageButton)view.findViewById(R.id.fotoMiPerfil);
        fotoMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.contexto, "Has pulsado la imagen", Toast.LENGTH_LONG).show();
            }
        });
        reputacion = (RatingBar)view.findViewById(R.id.ratingBar);

        HTTPMethods httpMethods = new HTTPMethods(4);
        httpMethods.setToken_user(NavigationDrawerActivity.token);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        String username = httpMethods.getResultado();
        System.out.println(username);
        username = username.substring(14, username.length() - 2); // bug arreglado
        System.out.println(username);

        httpMethods = new HTTPMethods(1);
        httpMethods.setUsername(username);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
            Usuario user = httpMethods.getUser();
            name.setText(user.getUsername());
            mail.setText("Email: " + user.getMail());
            byte[] decodedString = Base64.decode(user.getPic(), Base64.DEFAULT);
            Bitmap profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            fotoMiPerfil.setImageBitmap(profilePic);
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
