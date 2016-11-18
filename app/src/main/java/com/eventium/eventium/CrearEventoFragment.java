package com.eventium.eventium;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

    ImageButton arrowSpinner;
    private static final int IMAGE_GALLERY_REQUEST = 1;
    String path;
    ImageButton foto;
    String encodedString;

    public CrearEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        final EditText titulo = (EditText) view.findViewById(R.id.editText13);
        final Spinner categoria = (Spinner) view.findViewById(R.id.spinner);
        final EditText ciudad = (EditText) view.findViewById(R.id.editText14);
        final EditText fecha_ini = (EditText) view.findViewById(R.id.editText15);
        final EditText fecha_fin = (EditText) view.findViewById(R.id.editText16);
        final EditText hora_ini = (EditText) view.findViewById(R.id.editText17);
        final EditText hora_fin = (EditText) view.findViewById(R.id.editText18);
        final EditText precio = (EditText) view.findViewById(R.id.editText19);

        arrowSpinner = (ImageButton) view.findViewById(R.id.arrowSpinner);
        arrowSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoria.performClick();
            }
        });

        foto = (ImageButton) view.findViewById(R.id.imageButton);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_GALLERY_REQUEST);
            }
        });

        BitmapDrawable drawable = (BitmapDrawable) ContextCompat.getDrawable(MainActivity.contexto, R.drawable.unavailable);
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bb = bos.toByteArray();
        encodedString = Base64.encodeToString(bb, Base64.DEFAULT);

        Button crear_evento = (Button) view.findViewById(R.id.button17);
        crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPMethods httpMethods = new HTTPMethods(11);

                httpMethods.setToken_user(NavigationDrawerActivity.token);

                httpMethods.setEvent_title(titulo.getText().toString());
                httpMethods.setEvent_categoria(categoria.getSelectedItem().toString());

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
                    foto.setImageBitmap(bm);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                    byte[] bb = bos.toByteArray();
                    encodedString = Base64.encodeToString(bb, Base64.DEFAULT);
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
        //inflater.inflate(R.menu.menu_crearEvento, menu);
    }
}
