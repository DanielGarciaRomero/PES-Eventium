package com.eventium.eventium;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.eventium.eventium.HTTPMethods.lat;
import static com.eventium.eventium.HTTPMethods.lng;
import static com.eventium.eventium.R.id.textView;

/**
 * Created by Abel on 02/12/2016.
 */

public class MostrarEventoFragment extends Fragment  {
    private String eventID;
    int numOpiniones;
    private String myUsername;
    private Boolean soySponsor;
    private String username;
    MapView mMapView;
    private GoogleMap googleMap;
    public MostrarEventoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mostrar_evento, container, false);
        NavigationDrawerActivity.minimizarApp = 2;

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
        final RatingBar puntuacion = (RatingBar) view.findViewById(R.id.ratingBar);
        puntuacion.setStepSize(0.5f);
        //boolean isVoted = false;

        mMapView = (MapView) view.findViewById(R.id.google);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                // googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                HTTPMethods httpMethods99 = new HTTPMethods(99);
                String daux = direccion.getText().toString().replace("Dirección: ", "");
                String caux = ciudad.getText().toString().replace("Ciudad: ", "");

                String daux2 = daux.replace(" ", 	"%20");
                String caux2 = caux.replace(" ", 	"%20");
                httpMethods99.setEvent_direccion(daux2);
                httpMethods99.setEvent_ciudad(caux2);
                httpMethods99.ejecutarHttpAsyncTask();
                while (!httpMethods99.getFinished());

                LatLng sydney = new LatLng(lat, lng);
                googleMap.addMarker(new MarkerOptions().position(sydney).title(titulo.getText().toString()).snippet(daux + " , " + caux));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

        final ToggleButton asistir = (ToggleButton) view.findViewById(R.id.botonAsistir);
        asistir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Obtengo el username con el token
                HTTPMethods httpMethods1 = new HTTPMethods(4);
                httpMethods1.setToken_user(NavigationDrawerActivity.token);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());
                UsernameSponsor us = httpMethods1.getUsernameSponsor();
                String username = us.getUsername();
                //String username = httpMethods1.getResultado();
                //username = username.substring(14, username.length() - 2);

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
        final Button patrocinar = (Button) view.findViewById(R.id.botonPatrocinar);
        final Button reportar = (Button) view.findViewById(R.id.botonReportar);
        final Button editar = (Button) view.findViewById(R.id.botonEditar);
        final Button eliminar = (Button) view.findViewById(R.id.botonEliminar);

        promocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragmentPromocionar dialogFragment = new MyDialogFragmentPromocionar ();
                dialogFragment.show(getActivity().getFragmentManager(), "hola");
            }
        });

        reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPMethods httpMethods2 = new HTTPMethods(7);
                httpMethods2.setEvent_id(eventID);
                httpMethods2.ejecutarHttpAsyncTask();
                while (!httpMethods2.getFinished());
                Evento event = httpMethods2.getEvent();

                HTTPMethods httpMethods = new HTTPMethods(26);
                httpMethods.setToken_user(NavigationDrawerActivity.token);
                httpMethods.setEvent_id(eventID);
                httpMethods.ejecutarHttpAsyncTask();
                while (!httpMethods.getFinished());

                Toast.makeText(NavigationDrawerActivity.contexto, "Has reportado este evento", Toast.LENGTH_LONG).show();

                if (event.getnReports().equals("4")){
                    ((NavigationDrawerActivity) getActivity()).fromAnyWhereToVerEventos();
                }
            }
        });

        RatingBar.OnRatingBarChangeListener listener = new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                String sRating = Integer.toString((int)puntuacion.getRating());
                HTTPMethods httpMethods = new HTTPMethods(35);
                httpMethods.setEvent_id(NavigationDrawerActivity.event_id);
                httpMethods.setToken_user(NavigationDrawerActivity.token);
                httpMethods.setEventPoints(sRating);
                httpMethods.ejecutarHttpAsyncTask();
                while (!httpMethods.getFinished());
            }

        };


        HTTPMethods httpMethods = new HTTPMethods(7);
        httpMethods.setEvent_id(eventID);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        Evento event = httpMethods.getEvent();
        titulo.setText(event.getTitle());

        try {
            puntuacion.setRating(Float.parseFloat(event.getValoracion()));
        } catch (Exception e){
            Toast.makeText(NavigationDrawerActivity.contexto, "Este evento aun no tiene puntuación", Toast.LENGTH_LONG).show();
        }


        puntuacion.setOnRatingBarChangeListener(listener);
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

        if (dataIni.equals(dataFi)) fecha.setText(Html.fromHtml("<b>" + "Fecha: " + "</b>" + diaIni + "/" + mesIni + "/" + anyIni));
        else fecha.setText(Html.fromHtml("<b>" + "Fecha: " + "</b>" + fechas));

        if (hIni.equals(hFin)) hora.setText(Html.fromHtml("<b>" + "Hora de inicio y fin: " + "</b>" + hIni));
        else hora.setText(Html.fromHtml("<b>" + "Hora: " + "</b>" + hIni + "-" + hFin));
        precio.setText(Html.fromHtml("<b>" + "Precio: " + "</b>" + s));

        HTTPMethods httpMet = new HTTPMethods(23);
        httpMet.setEvent_id(NavigationDrawerActivity.event_id);
        httpMet.ejecutarHttpAsyncTask();
        while (!httpMet.getFinished());
        numOpiniones = httpMet.getSizeComentarios();

        numOpinionestv.setText(Html.fromHtml("<b>" + "Opiniones: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + numOpiniones + "</Font></u>"));
        numOpinionestv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialogFragmentComments dialogFragment = new MyDialogFragmentComments();
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );

        /*
        httpMethods = new HTTPMethods(0);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        List<Usuario> users = httpMethods.getUsers();
        username = "";
        for(Usuario usuario : users) {
            if (usuario.getId().equals(event.getOrganizerId())) {
                username = usuario.getUsername();
                break;
            }
        }
        */
        HTTPMethods httpAux = new HTTPMethods(1);
        httpAux.setUsername(event.getOrganizerId());
        httpAux.ejecutarHttpAsyncTask();
        while (!httpAux.getFinished());
        username = httpAux.getUser().getUsername();

        organizador.setText(Html.fromHtml("<b>" + "Organizador: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + username + "</Font></u>"));
        organizador.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment;
                        if (!username.equals(myUsername)) fragment = new PerfilFragment();
                        else fragment = new MiPerfilFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("user", username);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
        );

        HTTPMethods httpMethods3 = new HTTPMethods(29);
        httpMethods3.setEvent_id(eventID);
        httpMethods3.ejecutarHttpAsyncTask();
        while (!httpMethods3.getFinished());
        final List<Calendario> list_calendario = httpMethods3.getCalendarios();
        asistentes.setText(Html.fromHtml("<b>" + "Asistentes: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + list_calendario.size() + "</Font></u>"));
        asistentes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationDrawerActivity.calendarios = (ArrayList) list_calendario;
                        MyDialogFragmentCalendario dialogFragment = new MyDialogFragmentCalendario();
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );

        HTTPMethods httpMetode = new HTTPMethods(31);
        httpMetode.setEvent_id(eventID);
        httpMetode.ejecutarHttpAsyncTask();
        while (!httpMetode.getFinished());
        final List<Calendario> sponsors = httpMetode.getCalendarios();
        patrocinadores.setText(Html.fromHtml("<b>" + "Patrocinadores: " + "</b>" + "<u><FONT COLOR=\"#0055AA\" >" + sponsors.size() + "</Font></u>"));
        patrocinadores.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationDrawerActivity.calenSponsors = (ArrayList) sponsors;
                        MyDialogFragmentPatrocinio dialogFragment = new MyDialogFragmentPatrocinio();
                        dialogFragment.show(getActivity().getFragmentManager(), "");
                    }
                }
        );

        entradas.setText(Html.fromHtml("<b>" + "Entradas: " + "</b>" + event.getUrl()));

        httpMethods = new HTTPMethods(4);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        UsernameSponsor us = httpMethods.getUsernameSponsor();
        myUsername = us.getUsername();
        soySponsor = us.getSponsor();
        //System.out.println("Soy sponsor? " + soySponsor);

        if (!myUsername.equals(username)) {
            promocionar.setVisibility(View.GONE);
            editar.setVisibility(View.GONE);
            eliminar.setVisibility(View.GONE);
        }
        if (!soySponsor) {
            patrocinar.setVisibility(View.GONE);
        }

        patrocinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NavigationDrawerActivity.contexto, "Se le notificara a " + username + " que quieres patrocinar su evento", Toast.LENGTH_LONG).show();
                HTTPMethods httpMetodo = new HTTPMethods(30);
                httpMetodo.setToken_user(NavigationDrawerActivity.token);
                httpMetodo.setEvent_id(eventID);
                httpMetodo.ejecutarHttpAsyncTask();
                while (!httpMetodo.getFinished());
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NavigationDrawerActivity.contexto, "Editar evento", Toast.LENGTH_LONG).show();
                ((NavigationDrawerActivity)getActivity()).fromMostrarToEditarEvento(eventID);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Estás seguro de que deseas eliminar este evento?");
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HTTPMethods httpMet = new HTTPMethods(25);
                        httpMet.setEvent_id(NavigationDrawerActivity.event_id);
                        httpMet.setToken_user(NavigationDrawerActivity.token);
                        httpMet.ejecutarHttpAsyncTask();
                        while (!httpMet.getFinished());
                        Toast.makeText(NavigationDrawerActivity.contexto, "El evento ha sido eliminado", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        ((NavigationDrawerActivity)getActivity()).fromAnyWhereToVerEventos();
                    }
                });
                builder.show();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
