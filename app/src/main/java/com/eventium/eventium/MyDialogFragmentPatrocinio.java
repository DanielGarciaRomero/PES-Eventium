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
import android.widget.ListView;
import java.util.ArrayList;

public class MyDialogFragmentPatrocinio extends DialogFragment {

    ListView lv;
    Context c;
    AdapterPatrocinadores adapPatro;
    ArrayList<String> usernames;
    ArrayList<Bitmap> images;
    ArrayList<Calendario> calenSponsors;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dialog_tematicas, container, false);
       getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        c = NavigationDrawerActivity.contexto;
        lv = (ListView) view.findViewById(R.id.listViewTematicas);
        usernames = new ArrayList<String>();
        images = new ArrayList<Bitmap>();
        calenSponsors = NavigationDrawerActivity.calenSponsors;

        if (calenSponsors != null) {
            for (int i = 0; i < calenSponsors.size(); ++i) {
                String userID = calenSponsors.get(i).getUserid().toString();
                HTTPMethods httpMetode = new HTTPMethods(1);
                httpMetode.setUsername(userID);
                httpMetode.ejecutarHttpAsyncTask();
                while (!httpMetode.getFinished());
                Usuario user = httpMetode.getUser();
                String encodedImage = user.getPic();
                String username = user.getUsername();
                byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                // anadir a los ArrayList correspondientes
                usernames.add(username);
                images.add(bm);
            }
        }

        adapPatro = new AdapterPatrocinadores(c, usernames, images);
        lv.setAdapter(adapPatro);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ¿Que hacer si me clican en un patrocinador? En teoria, nada
            }
        });
    }
}
