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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Abel on 11/11/2016.
 */

public class MiPerfilFragment extends Fragment  {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    String path;

    TextView name;
    TextView mail;
    TextView tematicas;
    //TextView city;
    //TextView direction;
    ImageView verified;
    ImageButton fotoMiPerfil;
    RatingBar reputacion;
    String idUsuario;
    String categorias;


    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_miperfil, container, false);
        name = (TextView)view.findViewById(R.id.username);
        mail = (TextView)view.findViewById(R.id.emailtext);
        verified = (ImageView)view.findViewById(R.id.verified);
        tematicas = (TextView)view.findViewById(R.id.tematicas);
        fotoMiPerfil = (ImageButton)view.findViewById(R.id.fotoMiPerfil);
        fotoMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.contexto, "Has pulsado la imagen", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_GALLERY_REQUEST);
            }
        });
        reputacion = (RatingBar)view.findViewById(R.id.ratingBar);


        HTTPMethods httpMethods = new HTTPMethods(4);
        httpMethods.setToken_user(NavigationDrawerActivity.token);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        String username = httpMethods.getResultado();
        username = username.substring(14, username.length() - 2);

        httpMethods = new HTTPMethods(1);
        httpMethods.setUsername(username);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        Usuario user = httpMethods.getUser();

        idUsuario = user.getId();

        name.setText(user.getUsername());
        mail.setText("Email: " + user.getMail());
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
        tematicas.setText("Temáticas preferidas: " + categorias);

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
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    fotoMiPerfil.setImageBitmap(bm);
                    // PUT de /users/<id>
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
                    // No se modifica, manana se arregla
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

}
