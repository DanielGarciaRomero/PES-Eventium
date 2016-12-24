package com.eventium.eventium;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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
    int numOpiniones;
    public MostrarEventoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_evento, container, false);

        Bundle bundle = getArguments();
        eventID = bundle.getString("event");
        NavigationDrawerActivity.event_id = eventID;
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
        final TextView numOpinionestv = (TextView) view.findViewById(R.id.opinionesEvento);
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

        final TextView patrocinadores = (TextView) view.findViewById(R.id.patrocinadoresEvento);
        final TextView entradas = (TextView) view.findViewById(R.id.webEntradas);
        final Button promocionar = (Button) view.findViewById(R.id.botonPromocionar);
        final Button reportar = (Button) view.findViewById(R.id.botonReportar);

        promocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavigationDrawerActivity.event_id = eventID;
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
        categoria.setText(Html.fromHtml("<b>" + "Categoria: " + "</b>" + cat));
        //categoria.setText(Html.fromHtml("<u><FONT COLOR=\"#0055AA\" >"+numOpiniones+"</Font></u>"));
        //categoria.setText("Categoria: " + cat);
        byte[] decodedString = Base64.decode(event.getPic(), Base64.DEFAULT);
        Bitmap profilePic = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imagen.setImageBitmap(profilePic);
        if (descripcion != null) descripcion.setText(Html.fromHtml("<b>" + "Descripción: " + "</b>" + event.getDescripcion()));
        else descripcion.setText(Html.fromHtml("<b>" + "Descripción: " + "</b>" + "-"));
        ciudad.setText(Html.fromHtml("<b>" + "Ciudad: " + "</b>" + event.getCiudad()));
        String dir = event.getDireccion();
        if (dir != null) direccion.setText(Html.fromHtml("<b>" + "Dirección: " + "</b>" + event.getDireccion()));
        else direccion.setText(Html.fromHtml("<b>" + "Dirección: " + "</b>" + "-"));

        String dataIni = event.getFecha_ini();
        String dataFi = event.getFecha_fin();
        String s = dataIni.substring(0, 4); int anyIni = Integer.parseInt(s);
        s = dataFi.substring(0, 4); int anyFi = Integer.parseInt(s);
        s = dataIni.substring(5, 7); int mesIni = Integer.parseInt(s);
        s = dataFi.substring(5, 7); int mesFi = Integer.parseInt(s);
        s = dataIni.substring(8, 10); int diaIni = Integer.parseInt(s);
        s = dataFi.substring(8, 10); int diaFi = Integer.parseInt(s);
        String fechas = diaIni + "/" + mesIni + "/" + anyIni + " - " + diaFi + "/" + mesFi + "/" + anyFi;
        String hIni = event.getHora_ini(); String hFin = event.getHora_fin();
        float f = Float.parseFloat(event.getPrecio());
        int preu = (int) f;
        s = Integer.toString(preu) + " €";

        if (dataIni.equals(dataFi)) fecha.setText(Html.fromHtml("<b>" + "Fecha de inicio y fin: " + "</b>" + diaIni + "/" + mesIni + "/" + anyIni));
        else fecha.setText(Html.fromHtml("<b>" + "Fecha de inicio y fin: " + "</b>" + fechas));

        //if (dataIni.equals(dataFi)) fecha.setText("Fecha: " + diaIni + "/" + mesIni + "/" + anyIni);
        //else fecha.setText("Fecha: " + fechas);

        if (hIni.equals(hFin)) hora.setText(Html.fromHtml("<b>" + "Hora de inicio y fin: " + "</b>" + hIni));
        else hora.setText(Html.fromHtml("<b>" + "Hora: " + "</b>" + hIni + "-" + hFin));
        //if (hIni.equals(hFin)) hora.setText("Hora: " + hIni);
        //else hora.setText("Hora: " + hIni + " - " + hFin);
        precio.setText(Html.fromHtml("<b>" + "Precio: " + "</b>" + s));

        HTTPMethods httpMet = new HTTPMethods(23);
        httpMet.setEvent_id(NavigationDrawerActivity.event_id);
        httpMet.ejecutarHttpAsyncTask();
        while (!httpMet.getFinished());
        numOpiniones = httpMet.getSizeComentarios();

        numOpinionestv.setText(Html.fromHtml("<b>" + "Opiniones: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >"+numOpiniones+"</Font></u>"));
        numOpinionestv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //NavigationDrawerActivity.event_id = eventID;
                        MyDialogFragmentComments dialogFragment = new MyDialogFragmentComments();
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );

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
        organizador.setText(Html.fromHtml("<b>" + "Organizador: " + "</b>" + username));

        Integer numAsistentes = 0;
        asistentes.setText(Html.fromHtml("<b>" + "Asistentes: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numAsistentes + "</Font></u>"));

        patrocinadores.setText(Html.fromHtml("<b>" + "Patrocinadores: " + "</b>"));

        entradas.setText(Html.fromHtml("<b>" + "Entradas: " + "</b>"));

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
