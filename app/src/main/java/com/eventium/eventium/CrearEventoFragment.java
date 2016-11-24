package com.eventium.eventium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        final EditText direccion = (EditText) view.findViewById(R.id.editTextDir);
        final EditText fecha_ini = (EditText) view.findViewById(R.id.editText15);
        final EditText fecha_fin = (EditText) view.findViewById(R.id.editText16);
        final EditText hora_ini = (EditText) view.findViewById(R.id.editText17);
        final EditText hora_fin = (EditText) view.findViewById(R.id.editText18);
        final EditText precio = (EditText) view.findViewById(R.id.editText19);
        final EditText entradas = (EditText) view.findViewById(R.id.editTextEntradas);

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
                if (titulo.getText().toString().equals("") || entradas.getText().toString().equals("")
                        || ciudad.getText().toString().equals("") || direccion.getText().toString().equals("")
                        || fecha_ini.getText().toString().equals("") || fecha_fin.getText().toString().equals("")
                        || hora_ini.getText().toString().equals("") || hora_fin.getText().toString().equals("")
                        || precio.getText().toString().equals(""))
                    Toast.makeText(NavigationDrawerActivity.contexto, "No puedes dejar ningún campo en blanco", Toast.LENGTH_LONG).show();
                else {
                    try {
                        String dataIni = fecha_ini.getText().toString();
                        String dataFi = fecha_fin.getText().toString();
                        if (dataIni.length() != 10 || dataFi.length() != 10 || dataIni.charAt(2) != '/' || dataIni.charAt(5) != '/'
                                || dataFi.charAt(2) != '/' || dataFi.charAt(5) != '/')
                            Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de fechas incorrecto", Toast.LENGTH_LONG).show();
                        else {
                            if (!fechasValidas(dataIni, dataFi)) {
                                Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de fechas incorrecto", Toast.LENGTH_LONG).show();
                            }
                            else {
                                String horaIni = hora_ini.getText().toString();
                                String horaFi = hora_fin.getText().toString();
                                if (horaIni.length() != 5 || horaFi.length() != 5 || horaIni.charAt(2) != ':' || horaFi.charAt(2) != ':')
                                    Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de horas incorrecto", Toast.LENGTH_LONG).show();
                                else {
                                    if (!horasValidas(horaIni, horaFi)) {
                                        Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de horas incorrecto", Toast.LENGTH_LONG).show();
                                    }
                                    else {
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
                                }
                            }
                        }
                    } catch (ParseException e) {
                        // Nothing to do
                    } catch (hoursException e) {
                        Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de horas incorrecto", Toast.LENGTH_LONG).show();
                    } catch (datesException e) {
                        Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de fechas incorrecto", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    public class datesException extends Exception {
        public datesException(String msg) {
            super(msg);
        }
    }

    public class hoursException extends Exception {
        public hoursException(String msg) {
            super(msg);
        }
    }

    public boolean fechasValidas(String dataIni, String dataFi) throws ParseException, datesException
    {
        try {
            String s = dataIni.substring(0, 2); int diaIni = Integer.parseInt(s);
            s = dataFi.substring(0, 2); int diaFi = Integer.parseInt(s);
            s = dataIni.substring(3, 5); int mesIni = Integer.parseInt(s);
            s = dataFi.substring(3, 5); int mesFi = Integer.parseInt(s);
            s = dataIni.substring(6, 10); int anyIni = Integer.parseInt(s);
            s = dataFi.substring(6, 10); int anyFi = Integer.parseInt(s);
            if (diaIni <= 0 || diaFi <= 0 || diaIni > 31 || diaFi > 31 || mesIni <= 0 || mesFi <= 0 || mesIni > 12
                    || mesFi > 12 || anyIni < 2016 || anyFi < 2016) return false;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            s = anyIni + "-" + mesIni + "-" + diaIni;
            Date date1 = sdf.parse(s);
            s = anyFi + "-" + mesFi + "-" + diaFi;
            Date date2 = sdf.parse(s);
            Date currentDate = new Date();
            currentDate = sdf.parse(sdf.format(currentDate)); // para que no tenga en cuenta la
                                                              // diferencia de los getTime
                                                              // sin esto, si pongo 23/11/2016 en el
                                                              // emulador, dice que es before y devuelve false
            //System.out.println(sdf.format(currentDate)); 2016-11-23
            //System.out.println(sdf.format(date1)); 2016-11-23
            //System.out.println(date1.getTime()); 1479877200000
            //System.out.println(currentDate.getTime()); 1479877200000
            //if (date1.compareTo(currentDate) < 0 || date1.compareTo(date2) > 0) return false;
            if (date1.before(currentDate) || date1.after(date2)) return false;
            return true;
        }
        catch(NumberFormatException e){
            throw new datesException("");
        }
    }

    public boolean horasValidas(String horaIni, String horaFi) throws ParseException, hoursException
    {
        try {
            String s = horaIni.substring(0, 2); int hIni = Integer.parseInt(s);
            s = horaFi.substring(0, 2); int hFi = Integer.parseInt(s);
            s = horaIni.substring(3, 5); int minIni = Integer.parseInt(s);
            s = horaFi.substring(3, 5); int minFi = Integer.parseInt(s);
            if (hIni < 0 || hFi < 0 || hIni > 23 || hFi > 23 || minIni < 0 || minFi < 0 || minIni > 59 || minFi > 59)
                return false;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date1 = sdf.parse(horaIni);
            Date date2 = sdf.parse(horaFi);
            if (date1.compareTo(date2) > 0) return false;
            return true;
        }
        catch(NumberFormatException e){
            throw new hoursException("");
        }
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
