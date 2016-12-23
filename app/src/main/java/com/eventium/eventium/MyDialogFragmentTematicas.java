package com.eventium.eventium;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class MyDialogFragmentTematicas extends DialogFragment {

    ListView lv;
    //EditText et_comentario;
    //Button publicar;
    Context c;
    AdapterTematicas adapTem;
    //ArrayList<String> usernames;
    ArrayList<String> categories;
    String[] aux;
    ArrayList<Bitmap> images;

    String categorias;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dialog_tematicas, container, false);
       Bundle bundle = getArguments();
       categorias = bundle.getString("categories");
       //System.out.println(categorias);
       getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lv = (ListView) view.findViewById(R.id.listViewTematicas);
        c = NavigationDrawerActivity.contexto;
        categories = new ArrayList<String>();
        images = new ArrayList<Bitmap>();
        aux = categorias.split(", ");
        for (int i = 0; i < aux.length; ++i) {
            categories.add(aux[i]);
            if (aux[i].equals("Artístico")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.artistico);
                images.add(bm);
            }
            else if (aux[i].equals("Automobilístico")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.automobilistico);
                images.add(bm);
            }
            else if (aux[i].equals("Cinematográfico")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.cinematografico);
                images.add(bm);
            }
            else if (aux[i].equals("Deportivo")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.deportivo);
                images.add(bm);
            }
            else if (aux[i].equals("Gastronómico")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.gastronomico);
                images.add(bm);
            }
            else if (aux[i].equals("Literario")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.literario);
                images.add(bm);
            }
            else if (aux[i].equals("Moda")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.moda);
                images.add(bm);
            }
            else if (aux[i].equals("Musical")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.musical);
                images.add(bm);
            }
            else if (aux[i].equals("Político")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.politico);
                images.add(bm);
            }
            else if (aux[i].equals("Teatral")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.teatral);
                images.add(bm);
            }
            else if (aux[i].equals("Tecnológico y científico")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.tecnologico_y_cientifico);
                images.add(bm);
            }
            else if (aux[i].equals("Otros")){
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.otros);
                images.add(bm);
            }
        }
        System.out.println(categories);
        System.out.println(categories.size());
        System.out.println(images.size());
        adapTem = new AdapterTematicas(c, categories, images);
        lv.setAdapter(adapTem);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }
}
