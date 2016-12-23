package com.eventium.eventium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.util.TimeZone;

public class CrearEventoFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    ImageButton arrowSpinner;
    private static final int IMAGE_GALLERY_REQUEST = 1;
    String path;
    ImageButton foto;
    String encodedString;

    int seletedPicker; // 0 -> fechaIni, 1 -> fechaFi, 2-> horaIni, 3-> horaFi
    String fechaIni, fechaFi, horaIni, horaFi;
    Spinner categoria;
    EditText titulo, ciudad, direccion, precio, entradas, descripcion;
    TextView fecha_ini, fecha_fin, hora_ini, hora_fin;

    public CrearEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //Toast.makeText(this, "Dia seleccionado: " + dayOfMonth + "/" + (monthOfYear+1) + "/" + year, Toast.LENGTH_SHORT).show();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, monthOfYear);
        String s = new SimpleDateFormat("E d MMM yyyy").format(cal.getTime());
        if (seletedPicker == 0) {
            fecha_ini.setText(s);
            int mes = monthOfYear+1;
            fechaIni = year + "/";
            if (mes < 10) fechaIni += "0" + mes + "/";
            else fechaIni += mes + "/";
            if (dayOfMonth < 10) fechaIni += "0" + dayOfMonth;
            else fechaIni += dayOfMonth;
            //System.out.println(fechaIni);
        }
        else if (seletedPicker == 1) {
            fecha_fin.setText(s);
            int mes = monthOfYear+1;
            fechaFi = year + "/";
            if (mes < 10) fechaFi += "0" + mes + "/";
            else fechaFi += mes + "/";
            if (dayOfMonth < 10) fechaFi += "0" + dayOfMonth;
            else fechaFi += dayOfMonth;
            //System.out.println(fechaFi);
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        //Toast.makeText(this, "Hora seleccionada: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        if (seletedPicker == 2) {
            if (hourOfDay < 10) horaIni = "0" + hourOfDay + ":";
            else horaIni = hourOfDay + ":";
            if (minute < 10) horaIni += "0" + minute;
            else horaIni += minute;
            hora_ini.setText(horaIni);
        }
        else if (seletedPicker == 3) {
            if (hourOfDay < 10) horaFi = "0" + hourOfDay + ":";
            else horaFi = hourOfDay + ":";
            if (minute < 10) horaFi += "0" + minute;
            else horaFi += minute;
            hora_fin.setText(horaFi);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        titulo = (EditText) view.findViewById(R.id.crear_evento_titulo);
        categoria = (Spinner) view.findViewById(R.id.spinner);
        ciudad = (EditText) view.findViewById(R.id.crear_evento_ciudad);
        direccion = (EditText) view.findViewById(R.id.crear_evento_direccion);
        fecha_ini = (TextView) view.findViewById(R.id.crear_evento_fechaIni);
        fecha_fin = (TextView) view.findViewById(R.id.crear_evento_fechaFi);
        hora_ini = (TextView) view.findViewById(R.id.crear_evento_horaIni);
        hora_fin = (TextView) view.findViewById(R.id.crear_evento_horaFi);
        precio = (EditText) view.findViewById(R.id.crear_evento_precio);
        entradas = (EditText) view.findViewById(R.id.crear_evento_entradas);
        descripcion = (EditText) view.findViewById(R.id.crear_evento_descripcion);

        fechaIni = "dd/mm/aaaa";
        fechaFi = "dd/mm/aaaa";
        fecha_ini.setText(fechaIni);
        fecha_fin.setText(fechaFi);

        fecha_ini.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                CrearEventoFragment.this,
                                c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH)
                        );
                        seletedPicker = 0;
                        dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
                    }
                }
        );

        fecha_fin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        DatePickerDialog dpd = DatePickerDialog.newInstance(
                                CrearEventoFragment.this,
                                c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH)
                        );
                        seletedPicker = 1;
                        dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
                    }
                }
        );

        horaIni = "hh:mm";
        horaFi = "hh:mm";
        hora_ini.setText(horaIni);
        hora_fin.setText(horaFi);

        hora_ini.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        TimePickerDialog dpd = TimePickerDialog.newInstance(
                                CrearEventoFragment.this,
                                c.get(Calendar.HOUR),
                                c.get(Calendar.MINUTE),
                                true);
                        seletedPicker = 2;
                        dpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
                    }
                }
        );

        hora_fin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar c = Calendar.getInstance();
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
                        TimePickerDialog dpd = TimePickerDialog.newInstance(
                                CrearEventoFragment.this,
                                c.get(Calendar.HOUR),
                                c.get(Calendar.MINUTE),
                                true);
                        seletedPicker = 3;
                        dpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
                    }
                }
        );

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

        Button crear_evento = (Button) view.findViewById(R.id.button_crear_evento);
        crear_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NavigationDrawerActivity.contexto, "Has pulsado en crear evento", Toast.LENGTH_LONG).show();
                if (titulo.getText().toString().equals("") || entradas.getText().toString().equals("")
                        || ciudad.getText().toString().equals("") || direccion.getText().toString().equals("")
                        || fecha_ini.getText().toString().equals("dd/mm/aaaa") || fecha_fin.getText().toString().equals("dd/mm/aaaa")
                        || hora_ini.getText().toString().equals("hh:mm") || hora_fin.getText().toString().equals("hh:mm")
                        || precio.getText().toString().equals(""))
                    Toast.makeText(NavigationDrawerActivity.contexto, "No puedes dejar ningún campo en blanco", Toast.LENGTH_LONG).show();
                else { // Ningun campo relevante vacio
                    try {
                        if (!fechasValidas(fechaIni, fechaFi)) {
                            Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de fechas incorrecto", Toast.LENGTH_LONG).show();
                        }
                        else { // Fechas validas
                            if (!horasValidas(horaIni, horaFi, fechaIni, fechaFi)) {
                                Toast.makeText(NavigationDrawerActivity.contexto, "Intervalo de horas incorrecto", Toast.LENGTH_LONG).show();
                            }
                            else { // Horas validas
                                HTTPMethods httpMethods = new HTTPMethods(11);
                                httpMethods.setToken_user(NavigationDrawerActivity.token);
                                httpMethods.setEvent_title(titulo.getText().toString());
                                httpMethods.setEvent_categoria(categoria.getSelectedItem().toString());
                                httpMethods.setEvent_pic(encodedString);
                                httpMethods.setEvent_ciudad(ciudad.getText().toString());
                                httpMethods.setEvent_direccion(direccion.getText().toString());
                                httpMethods.setEvent_fecha_ini(fechaIni);
                                httpMethods.setEvent_fecha_fin(fechaFi);
                                httpMethods.setEvent_hora_ini(horaIni);
                                httpMethods.setEvent_hora_fin(horaFi);
                                httpMethods.setEvent_precio(precio.getText().toString());
                                httpMethods.setEvent_url_entradas(entradas.getText().toString());
                                httpMethods.setEvent_descripcion(descripcion.getText().toString());
                                httpMethods.ejecutarHttpAsyncTask();
                                while (!httpMethods.getFinished());
                                Toast.makeText(MainActivity.contexto, "Evento creado correctamente", Toast.LENGTH_LONG).show();
                                ((NavigationDrawerActivity)getActivity()).fromAnyWhereToVerEventos();
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
            String s = dataIni.substring(0, 4); int anyIni = Integer.parseInt(s);
            s = dataFi.substring(0, 4); int anyFi = Integer.parseInt(s);
            s = dataIni.substring(5, 7); int mesIni = Integer.parseInt(s);
            s = dataFi.substring(5, 7); int mesFi = Integer.parseInt(s);
            s = dataIni.substring(8, 10); int diaIni = Integer.parseInt(s);
            s = dataFi.substring(8, 10); int diaFi = Integer.parseInt(s);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            s = anyIni + "-" + mesIni + "-" + diaIni;
            Date date1 = sdf.parse(s);
            s = anyFi + "-" + mesFi + "-" + diaFi;
            Date date2 = sdf.parse(s);
            Date currentDate = new Date();
            currentDate = sdf.parse(sdf.format(currentDate));
            if (date1.before(currentDate) || date1.after(date2)) return false;
            return true;
        }
        catch(NumberFormatException e){
            throw new datesException("");
        }
    }

    public boolean horasValidas(String horaIni, String horaFi, String dataIni, String dataFi) throws ParseException, hoursException
    {
        try {
            String s = horaIni.substring(0, 2); int hIni = Integer.parseInt(s);
            s = horaFi.substring(0, 2); int hFi = Integer.parseInt(s);
            s = horaIni.substring(3, 5); int minIni = Integer.parseInt(s);
            s = horaFi.substring(3, 5); int minFi = Integer.parseInt(s);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date hora1 = sdf.parse(horaIni);
            Date hora2 = sdf.parse(horaFi);
            if (dataIni.equals(dataFi)) {
                if (hora1.compareTo(hora2) > 0) return false;
            }
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
