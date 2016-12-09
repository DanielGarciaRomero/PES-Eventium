package com.eventium.eventium;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.eventium.eventium.TabFragments.EventModel;
import com.eventium.eventium.TabFragments.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyDialogFragment extends DialogFragment {

    private RecyclerView recyclerview;
    private List<EventModel> mEventModel;
    private RVAdapter adapter;
    ArrayList<Evento> eventos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventos = new ArrayList<Evento>();

        eventos = NavigationDrawerActivity.events;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_three_fragment, container, false);
        //getDialog().setTitle("Eventos");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        mEventModel = new ArrayList<>();
        for (int i = 0; i < eventos.size(); ++i) {
            String dataIni = eventos.get(i).getFecha_ini();
            String dataFi = eventos.get(i).getFecha_fin();
            String s = dataIni.substring(0, 4); int anyIni = Integer.parseInt(s);
            s = dataFi.substring(0, 4); int anyFi = Integer.parseInt(s);
            s = dataIni.substring(5, 7); int mesIni = Integer.parseInt(s);
            s = dataFi.substring(5, 7); int mesFi = Integer.parseInt(s);
            s = dataIni.substring(8, 10); int diaIni = Integer.parseInt(s);
            s = dataFi.substring(8, 10); int diaFi = Integer.parseInt(s);
            String fechas = diaIni + "/" + mesIni + "/" + anyIni + " - " + diaFi + "/" + mesFi + "/" + anyFi;
            String horas = eventos.get(i).getHora_ini() + " - " + eventos.get(i).getHora_fin();

            float f = Float.parseFloat(eventos.get(i).getPrecio());
            int preu = (int) f;
            String precio = Integer.toString(preu) + " €";

            String encodedImage = eventos.get(i).getPic();
            byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap base64BitmapImage = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            mEventModel.add(new EventModel(base64BitmapImage, eventos.get(i).getTitle(), eventos.get(i).getCiudad(), fechas, horas, precio, eventos.get(i).getId()));
        }

        adapter = new RVAdapter(true);
        adapter.setRVE(mEventModel);
        recyclerview.setAdapter(adapter);
        recyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(NavigationDrawerActivity.contexto, recyclerview, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String item = adapter.getItemRVE(position);
                        Toast.makeText(NavigationDrawerActivity.contexto, item, Toast.LENGTH_LONG).show();
                        ((NavigationDrawerActivity)getActivity()).fromCalendarioToMostrarEvento(item);
                        dismiss();
                        /*Fragment fragment = new MostrarEventoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event", item);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();*/
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }
}
