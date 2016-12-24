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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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


    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_miperfil, container, false);
        name = (TextView)view.findViewById(R.id.username);
        city = (TextView)view.findViewById(R.id.ciudadtext);
        //direction = (TextView)view.findViewById(R.id.direcciontext);
        mail = (TextView)view.findViewById(R.id.emailtext);
        verified = (ImageView)view.findViewById(R.id.verified);
        siguiendo = (TextView) view.findViewById(R.id.siguiendo);
        seguidores = (TextView) view.findViewById(R.id.seguidores);
        eventosAsistidos = (TextView) view.findViewById(R.id.asistidos);
        eventosOrganizados = (TextView) view.findViewById(R.id.organizados);
        //opiniones = (TextView) view.findViewById(R.id.opiniones);
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
        String username = httpMethods.getResultado();
        username = username.substring(14, username.length() - 2);

        httpMethods = new HTTPMethods(1);
        httpMethods.setUsername(username);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        Usuario user = httpMethods.getUser();

        idUsuario = user.getId();

        name.setText(user.getUsername());
        //mail.setText("Email: " + user.getMail());
        mail.setText(Html.fromHtml("<b>" + "Email: " + "</b>" + user.getMail()));
        city.setText(Html.fromHtml("<b>" + "Ciudad: " + "</b>" + "..."));
        //direction.setText(Html.fromHtml("<b>" + "Dirección: " + "</b>" + "..."));
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
        //tematicas.setText("Temáticas preferidas: " + categorias);
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

        Integer numSiguiendo = 0;
        siguiendo.setText(Html.fromHtml("<b>" + "Siguiendo: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numSiguiendo + "</Font></u>"));

        Integer numSeguidores = 0;
        seguidores.setText(Html.fromHtml("<b>" + "Seguidores: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numSeguidores+"</Font></u>"));

        Integer numEventosAsistidos = 0;
        eventosAsistidos.setText(Html.fromHtml("<b>" + "Eventos asistidos: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numEventosAsistidos+"</Font></u>"));

        Integer numEventosOrganizados = 0;
        eventosOrganizados.setText(Html.fromHtml("<b>" + "Eventos organizados: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numEventosOrganizados+"</Font></u>"));

        Integer numOpiniones = 0;
        //opiniones.setText(Html.fromHtml("<b>" + "Opiniones: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numOpiniones+"</Font></u>"));

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
