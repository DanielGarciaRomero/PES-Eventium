package com.eventium.eventium;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class CrearEventoFragment extends Fragment {

    public CrearEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        final EditText titulo = (EditText) view.findViewById(R.id.editText13);

        final Spinner categoria = (Spinner) view.findViewById(R.id.spinner);
        final ImageButton foto = (ImageButton) view.findViewById(R.id.imageButton);
        final EditText ciudad = (EditText) view.findViewById(R.id.editText14);
        final EditText fecha_ini = (EditText) view.findViewById(R.id.editText15);
        final EditText fecha_fin = (EditText) view.findViewById(R.id.editText16);
        final EditText hora_ini = (EditText) view.findViewById(R.id.editText17);
        final EditText hora_fin = (EditText) view.findViewById(R.id.editText18);
        final EditText precio = (EditText) view.findViewById(R.id.editText19);

        Button crear_evento = (Button) view.findViewById(R.id.button17);
        crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPMethods httpMethods = new HTTPMethods(11);

                //hardcodeado
                httpMethods.setUser_id(1);

                httpMethods.setEvent_title(titulo.getText().toString());
                httpMethods.setEvent_categoria(categoria.getSelectedItem().toString());

                //BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(MainActivity.contexto, R.drawable.defaultuserimage);
                BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(MainActivity.contexto, R.drawable.trump);
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                byte[] bb = bos.toByteArray();
                String encodedString = Base64.encodeToString(bb, Base64.DEFAULT);
                httpMethods.setEvent_pic(encodedString);

                httpMethods.setEvent_ciudad(ciudad.getText().toString());
                httpMethods.setEvent_fecha_ini(fecha_ini.getText().toString());
                httpMethods.setEvent_fecha_fin(fecha_fin.getText().toString());
                httpMethods.setEvent_hora_ini(hora_ini.getText().toString());
                httpMethods.setEvent_hora_fin(hora_fin.getText().toString());
                httpMethods.setEvent_precio(precio.getText().toString());

                httpMethods.ejecutarHttpAsyncTask();
                while (!httpMethods.getFinished());
                Toast.makeText(MainActivity.contexto, "Evento creado correctamente", Toast.LENGTH_LONG).show();

            }
        });
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
        //inflater.inflate(R.menu.menu_crearEvento, menu);
    }
}
