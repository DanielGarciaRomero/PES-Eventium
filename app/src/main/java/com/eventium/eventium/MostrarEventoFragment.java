package com.eventium.eventium;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

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
        asistir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Obtengo el username con el token
                HTTPMethods httpMethods1 = new HTTPMethods(4);
                httpMethods1.setToken_user(NavigationDrawerActivity.token);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());
                String username = httpMethods1.getResultado();
                username = username.substring(14, username.length() - 2);

                //Obtengo el usuario con el username
                HTTPMethods httpMethods2 = new HTTPMethods(1);
                httpMethods2.setUsername(username);
                httpMethods2.ejecutarHttpAsyncTask();
                while (!httpMethods2.getFinished());
                Usuario user = httpMethods2.getUser();

                Integer idUsuario = Integer.parseInt(user.getId());

                if (isChecked) {
                    HTTPMethods httpMethods3 = new HTTPMethods(13);
                    httpMethods3.setToken_user(NavigationDrawerActivity.token);
                    httpMethods3.setUser_id(idUsuario);
                    httpMethods3.setEvent_id(eventID);
                    httpMethods3.ejecutarHttpAsyncTask();
                    while (!httpMethods3.getFinished());
                } else {
                    HTTPMethods httpMethods3 = new HTTPMethods(20);
                    httpMethods3.setToken_user(NavigationDrawerActivity.token);
                    httpMethods3.setUser_id(idUsuario);
                    httpMethods3.setEvent_id(eventID);
                    httpMethods3.ejecutarHttpAsyncTask();
                    while (!httpMethods3.getFinished());
                }
            }
        });

        final TextView asistentes = (TextView) view.findViewById(R.id.asistentesEvento);
        //aqui irán las opiniones//
        final TextView patrocinadores = (TextView) view.findViewById(R.id.patrocinadoresEvento);
        final TextView entradas = (TextView) view.findViewById(R.id.webEntradas);
        final Button promocionar = (Button) view.findViewById(R.id.botonPromocionar);
        final Button reportar = (Button) view.findViewById(R.id.botonReportar);

        promocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationDrawerActivity.event_id = eventID;
                MyDialogFragmentPromocionar dialogFragment = new MyDialogFragmentPromocionar ();
                dialogFragment.show(getActivity().getFragmentManager(), "hola");
            }
        });

        HTTPMethods httpMethods = new HTTPMethods(7);
        httpMethods.setEvent_id(eventID);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        Evento event = httpMethods.getEvent();
        titulo.setText(event.getTitle());
        String cat = event.getCategoria();
        switch (cat) {
            case "0":
                cat = "Artístico";
                break;
            case "1":
                cat = "Automobilístico, ";
                break;
            case "2":
                cat = "Cinematográfico";
                break;
            case "3":
                cat = "Deportivo";
                break;
            case "4":
                cat = "Gastronómico";
                break;
            case "5":
                cat = "Literario";
                break;
            case "6":
                cat = "Moda";
                break;
            case "7":
                cat = "Musical";
                break;
            case "8":
                cat = "Político";
                break;
            case "9":
                cat = "Teatral";
                break;
            case "10":
                cat = "Tecnológico y científico";
                break;
            case "11":
                cat = "Otros";
                break;
        }
        categoria.setText("Categoria: " + cat);
        byte[] decodedString = Base64.decode(event.getPic(), Base64.DEFAULT);
        Bitmap profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imagen.setImageBitmap(profilePic);
        descripcion.setText("Descripcion: " + event.getDescripcion());
        ciudad.setText("Ciudad: " + event.getCiudad());
        String dir = event.getDireccion();
        if (dir != null) direccion.setText("Direccion: " + event.getDireccion());
        else direccion.setText("Direccion: No hay informacion disponible");
        String fIni = event.getFecha_ini(); String fFin = event.getFecha_fin();
        if (fIni.equals(fFin)) fecha.setText("Fecha: " + fIni);
        else fecha.setText("Fecha: de " + fIni + " a " + fFin);
        String hIni = event.getHora_ini(); String hFin = event.getHora_fin();
        if (hIni.equals(hFin)) hora.setText("Hora: " + hIni);
        else hora.setText("Hora: de " + hIni + " a " + hFin);
        precio.setText("Precio: " + event.getPrecio() + "€");

        httpMethods = new HTTPMethods(0);

        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        List<Usuario> users = httpMethods.getUsers();
        String username = "";
        for(Usuario usuario : users) {
            if (usuario.getId().equals(event.getOrganizerId())) {
                username = usuario.getUsername();
                break;
            }
        }
        organizador.setText("Organizado por: " + username);
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
