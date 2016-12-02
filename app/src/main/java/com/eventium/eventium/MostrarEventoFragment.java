package com.eventium.eventium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.view.Menu;
import android.view.MenuInflater;

import static com.eventium.eventium.R.id.textView;

/**
 * Created by Abel on 02/12/2016.
 */

public class MostrarEventoFragment extends Fragment {
    private String eventID;
    public MostrarEventoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_evento, container, false);

        Bundle bundle = getArguments();
        eventID = bundle.getString("event");
        final TextView titulo = (TextView) view.findViewById(R.id.tituloEvento);
        final TextView categoria = (TextView) view.findViewById(R.id.categoriaEvento);
        final ImageView imagen = (ImageView) view.findViewById(R.id.fotoEvento);
        final TextView descripcion = (TextView) view.findViewById(R.id.descripcionEvento);
        final TextView organizador = (TextView) view.findViewById(R.id.organizadorEvento);
        final TextView ciudad = (TextView) view.findViewById(R.id.ciudadEvento);
        final TextView direccion = (TextView) view.findViewById(R.id.direccionEvento);
        final TextView fecha = (TextView) view.findViewById(R.id.fechaEvento);
        final TextView hora = (TextView) view.findViewById(R.id.horaEvento);
        final TextView precio = (TextView) view.findViewById(R.id.precioEvento);
        //aqui irá el mapa//
        final ToggleButton asistir = (ToggleButton) view.findViewById(R.id.botonAsistir);
        final TextView asistentes = (TextView) view.findViewById(R.id.asistentesEvento);
        //aqui irán las opiniones//
        final TextView patrocinadores = (TextView) view.findViewById(R.id.patrocinadoresEvento);
        final TextView entradas = (TextView) view.findViewById(R.id.webEntradas);
        final Button promocionar = (Button) view.findViewById(R.id.botonPromocionar);
        final Button reportar = (Button) view.findViewById(R.id.botonReportar);

        HTTPMethods httpMethods = new HTTPMethods(7);
        httpMethods.setEvent_id(eventID);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        Evento event = httpMethods.getEvent();
        titulo.setText(event.getTitle());
        categoria.setText("Categoria: " + event.getCategoria());
        byte[] decodedString = Base64.decode(event.getPic(), Base64.DEFAULT);
        Bitmap profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imagen.setImageBitmap(profilePic);
        descripcion.setText("Descripcion: " + event.getDescripcion());
        organizador.setText("Organizado por: " + event.getOrganizerId());
        ciudad.setText("Ciudad: " + event.getCiudad());
        direccion.setText("Direccion: " + event.getDireccion());
        String fIni = event.getFecha_ini(); String fFin = event.getFecha_fin();
        if (fIni.equals(fFin)) fecha.setText("Fecha: " + fIni);
        else fecha.setText("Fecha: de " + fIni + " a " + fFin);
        String hIni = event.getHora_ini(); String hFin = event.getHora_fin();
        if (hIni.equals(hFin)) hora.setText("Hora: " + hIni);
        else hora.setText("Hora: de " + hIni + " a " + hFin);
        precio.setText("Precio: " + event.getPrecio() + "€");
        //aqui iria algo para gestionar si asistes o no al evento y poner el boton en funcion a eso
        //aqui iria lo de los asistentes
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
