package com.eventium.eventium;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        getDialog().setTitle("Eventos");
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
            String fechas = eventos.get(i).getFecha_ini() + " - " + eventos.get(i).getFecha_fin();
            String horas = eventos.get(i).getHora_ini() + " - " + eventos.get(i).getHora_fin();
            String precio = eventos.get(i).getPrecio() + " €";
            //Uri geller = Uri.parse("android.resource://" + NavigationDrawerActivity.PACKAGE_NAME + "/" + R.raw.unavailable);
            String encodedImage = eventos.get(i).getPic();
            byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap base64BitmapImage = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            //aqui cambios
            mEventModel.add(new EventModel(base64BitmapImage, eventos.get(i).getTitle(), eventos.get(i).getCiudad(), fechas, horas, precio, eventos.get(i).getId()));
        }

        adapter = new RVAdapter(true);
        adapter.setRVE(mEventModel);
        recyclerview.setAdapter(adapter);
    }
}
