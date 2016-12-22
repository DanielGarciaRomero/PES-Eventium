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
import android.widget.ListView;

import java.util.ArrayList;

public class MyDialogFragmentComments extends DialogFragment {

    ListView lv;
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
        comments = new ArrayList<String>();
        comments.add("First comment!");
        comments.add("Second comment ...");
        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(c, R.drawable.defaultuserimage);
        Bitmap bitmap = drawable.getBitmap();
        images = new ArrayList<Bitmap>();
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
    }
}
