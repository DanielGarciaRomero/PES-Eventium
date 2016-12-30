package com.eventium.eventium;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDialogFragmentComments extends DialogFragment {

    ListView lv;
    EditText et_comentario;
    Button publicar;
    Context c;
    AdapterComments adapCom;
    ArrayList<String> usernames;
    ArrayList<String> comments;
    ArrayList<Bitmap> images;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dialog_comments, container, false);
       getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        c = NavigationDrawerActivity.contexto;
        lv = (ListView) view.findViewById(R.id.listViewComments);
        usernames = new ArrayList<String>();
        comments = new ArrayList<String>();
        images = new ArrayList<Bitmap>();
        /*
        usernames.add("Alvaro");
        usernames.add("Daniel");
        usernames.add("Alvaro");
        usernames.add("Daniel");
        usernames.add("Señor Rambal");
        comments.add("Dada la enorme propensión de los españoles a jugar a la lotería en comparación con otros países, ¿significa esto que somos una sociedad de" +
                " esperanzados irracionales? Si es así, lo somos la gran mayoría, pues solo un 22% dice no participar en el sorteo de la lotería en Navidad ...");
        comments.add("Compramos lotería por si acaso les toca a quienes nos rodean. Los anuncios de Lotería de Navidad explotan esa envidia sin pudor alguno," +
                " poniendo el énfasis no tanto en los agraciados con el Gordo, sino en los desgraciados que, habiendo podido, no compraron el décimo ganador.");
        comments.add("Yo no compro lotería de Navidad desde que los anuncios los dejó de protagonizar el calvo aquel.");
        comments.add("Mucho mejor el de Raphael y la Caballé, o el de Antoñito y el sobre.");
        comments.add("Ya que mencionáis al calvo: ¿sabéis que no era calvo? Se tenía que rapar cada año para grabar el anuncio. Firmó un acuerdo de exclusividad " +
                "con Loterías y Apuestas del Estado que le impedía participar en cualquier otra producción audiovisual entre 1998 y 2006 a cambio de 48.000 euros al año.");
        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(c, R.drawable.defaultuserimage);
        Bitmap bitmap = drawable.getBitmap();
        images.add(bitmap);
        images.add(bitmap);
        images.add(bitmap);
        images.add(bitmap);
        images.add(bitmap);
        */

        HTTPMethods httpMethods1 = new HTTPMethods(23);
        httpMethods1.setEvent_id(NavigationDrawerActivity.event_id);
        httpMethods1.ejecutarHttpAsyncTask();
        while (!httpMethods1.getFinished());
        List<Comentario> listaDeComentarios = httpMethods1.getComentarios();

        if (listaDeComentarios != null) {
            for (int i = 0; i < listaDeComentarios.size(); ++i) {
                String userID = listaDeComentarios.get(i).getUserid().toString();
                String text = listaDeComentarios.get(i).getText();

                // lo ideal seria hacer un GET de username dado userID
                // y no un GET de todos y buscar quien coincide
                /*
                httpMethods1 = new HTTPMethods(0);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());
                List<Usuario> listaDeUsuarios = httpMethods1.getUsers();
                String username = "";
                Boolean trobat = false;
                int j = 0;
                while (!trobat && j < listaDeUsuarios.size()) {
                    if (listaDeUsuarios.get(j).getId().equals(userID)) {
                        username = listaDeUsuarios.get(j).getUsername();
                        trobat = true;
                    }
                    else ++j;
                }
                // obtengo al usuario con su username y me quedo con su foto
                httpMethods1 = new HTTPMethods(1);
                httpMethods1.setUsername(username);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());
                Usuario user = httpMethods1.getUser();
                */
                httpMethods1 = new HTTPMethods(1);
                httpMethods1.setUsername(userID);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());
                Usuario user = httpMethods1.getUser();
                String encodedImage = user.getPic();
                String username = user.getUsername();
                byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

                // anadir a los ArrayList correspondientes
                usernames.add(username);
                comments.add(text);
                images.add(bm);
            }
        }

        adapCom = new AdapterComments(c, usernames, comments, images);
        lv.setAdapter(adapCom);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ¿Que hacer si me clican en un comentario? En teoria, nada
            }
        });
        et_comentario = (EditText) view.findViewById(R.id.editText_add_comment);
        publicar = (Button) view.findViewById(R.id.botonPublicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = et_comentario.getText().toString();
                HTTPMethods httpMethods1 = new HTTPMethods(14);
                httpMethods1.setToken_user(NavigationDrawerActivity.token);
                httpMethods1.setEvent_id(NavigationDrawerActivity.event_id);
                httpMethods1.setUser_id(NavigationDrawerActivity.myUserID);
                httpMethods1.setComentario(comentario);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());

                if (httpMethods1.getCode().equals("HTTP/1.0 200 OK")) Toast.makeText(c, "Ya has comentado este evento", Toast.LENGTH_LONG).show();

                else {
                    Toast.makeText(c, "Tu comentario ha sido publicado", Toast.LENGTH_LONG).show();
                    ((NavigationDrawerActivity) getActivity()).fromAnyWhereToVerEventos();
                    dismiss();
                }
            }
        });
    }
}
