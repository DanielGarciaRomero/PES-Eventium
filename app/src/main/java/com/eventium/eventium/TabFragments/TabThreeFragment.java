package com.eventium.eventium.TabFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eventium.eventium.CalendarioActivity;

import com.eventium.eventium.Evento;
import com.eventium.eventium.HTTPMethods;
import com.eventium.eventium.MainActivity;
import com.eventium.eventium.NavigationDrawerActivity;

import com.eventium.eventium.R;
import com.eventium.eventium.RecyclerItemClickListener;
import com.eventium.eventium.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TabThreeFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerview;
    private List<EventModel> mEventModel;
    private RVAdapter adapter;
    ArrayList<Evento> eventos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventos = new ArrayList<Evento>();

        HTTPMethods httpMethods = new HTTPMethods(3);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished()) ;
        List<Evento> list_events = httpMethods.getEvents();
        if (list_events == null){
            Toast.makeText(MainActivity.contexto, "No hi ha connexió amb el servidor", Toast.LENGTH_LONG).show();
        }
        else {
            for (int i = 0; i < list_events.size(); ++i) {
                eventos.add(list_events.get(i));
            }
        }

        /*eventos = new ArrayList<String>();
        eventos.add("Concierto de Estopa");
        eventos.add("Exposicion de Dali");
        eventos.add("Festival de Sitges");
        eventos.add("Maraton de Barcelona");
        eventos.add("Mobile World Congress");
        eventos.add("Salon del Manga");*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_three_fragment, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        return view;
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
            Uri geller = Uri.parse("android.resource://" + NavigationDrawerActivity.PACKAGE_NAME + "/" + R.raw.unavailable);
            mEventModel.add(new EventModel(geller, eventos.get(i).getTitle(), eventos.get(i).getCiudad(), fechas, horas, precio));
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
                        //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(getActivity(), CalendarioActivity.class);
                        //startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.menu_eventos, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Busca un evento");

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilterRVE(mEventModel);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<EventModel> filteredModelList = filter(mEventModel, newText);
        adapter.setFilterRVE(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<EventModel> filter(List<EventModel> models, String query) {
        query = query.toLowerCase();

        final List<EventModel> filteredModelList = new ArrayList<>();
        for (EventModel model : models) {
            final String text = model.getTitulo().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}