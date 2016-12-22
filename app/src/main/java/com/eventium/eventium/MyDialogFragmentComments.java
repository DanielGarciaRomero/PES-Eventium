package com.eventium.eventium;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

public class MyDialogFragmentComments extends DialogFragment {

    ListView lv;
    EditText comentario;
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
        usernames.add("Alvaro");
        usernames.add("Daniel");
        usernames.add("Alvaro");
        usernames.add("Daniel");
        usernames.add("Señor Rambal");
        comments = new ArrayList<String>();
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
        images = new ArrayList<Bitmap>();
        images.add(bitmap);
        images.add(bitmap);
        images.add(bitmap);
        images.add(bitmap);
        images.add(bitmap);
        adapCom = new AdapterComments(c, usernames, comments, images);
        lv.setAdapter(adapCom);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ¿Que hacer si me clican en un comentario? En teoria, nada
            }
        });
        comentario = (EditText) view.findViewById(R.id.editText_add_comment);
        publicar = (Button) view.findViewById(R.id.botonPublicar);
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Has pulsado Publicar", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
    }
}
