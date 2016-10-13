package com.eventium.eventium;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class EventosActivity extends AppCompatActivity implements View.OnClickListener{

    private android.widget.Toolbar toolbar;
    ListView lv;
    ArrayList<String> eventos;
    ArrayList<String> eventosDestacados;
    ArrayList<String> eventosRecomendados;
    ArrayList<Uri> imagenesDestacados;
    ArrayList<Uri> imagenesRecomendados;
    ArrayList<Uri> imagenes;
    AdapterImagenesListView adapEvent;
    Toolbar my_toolbar;
    Context context;
    Button botonFiltrarEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        botonFiltrarEventos = (Button) findViewById(R.id.botonFiltrarEventos);
        botonFiltrarEventos.setOnClickListener(this);

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        my_toolbar.setNavigationIcon(R.drawable.ic_menu);

        lv = (ListView) findViewById(R.id.listViewEventos);

        eventos = new ArrayList<String>();
        eventos.add("Info evento 1");
        eventos.add("Info evento 2");
        eventos.add("Info evento 3");
        eventos.add("Info evento 4");
        eventos.add("Info evento 5");
        eventos.add("Info evento 6");

        imagenes = new ArrayList<Uri>();
        for (int i = 0; i < eventos.size(); ++i) {
                Uri geller = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.unavailable);
                imagenes.add(geller);
        }

        eventosDestacados = new ArrayList<String>();
        eventosDestacados.add("Info evento 1");
        eventosDestacados.add("Info evento 2");

        imagenesDestacados = new ArrayList<Uri>();
        for (int i = 0; i < eventosDestacados.size(); ++i) {
            Uri geller = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.unavailable2);
            imagenesDestacados.add(geller);
        }

        eventosRecomendados = new ArrayList<String>();
        eventosRecomendados.add("Info evento 3");
        eventosRecomendados.add("Info evento 4");

        imagenesRecomendados = new ArrayList<Uri>();
        for (int i = 0; i < eventosRecomendados.size(); ++i) {
            Uri geller = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.unavailable3);
            imagenesRecomendados.add(geller);
        }

        context = this;
        adapEvent = new AdapterImagenesListView(context, eventosDestacados, imagenesDestacados);
        lv.setAdapter(adapEvent);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapEvent.eventList.get(position);
                Toast.makeText(getBaseContext(), "Has cliclado en " + item, Toast.LENGTH_LONG).show();
                //Intent i = new Intent(EventosActivity.this, GetEventoActivity.class);
                //i.putExtra("evento", item);
                //EventosActivity.this.startActivity(i);
            }
        });

        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("DESTACADOS",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("RECOMENDADOS",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("TODOS",
                res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.getTabWidget().getChildAt(0).getLayoutParams().width = 40;
        tabs.getTabWidget().getChildAt(1).getLayoutParams().width = 80;
        TabWidget tw = (TabWidget) tabs.findViewById(android.R.id.tabs);
        View tabView = tw.getChildTabViewAt(0);
        TextView tv = (TextView)tabView.findViewById(android.R.id.title);
        tv.setTextSize(12);
        tabView = tw.getChildTabViewAt(1);
        tv = (TextView)tabView.findViewById(android.R.id.title);
        tv.setTextSize(12);
        tabView = tw.getChildTabViewAt(2);
        tv = (TextView)tabView.findViewById(android.R.id.title);
        tv.setTextSize(12);

        // http://stackoverflow.com/questions/14722654/tabwidget-current-tab-bottom-line-color
        /*
        for (int i = 0; i < tabs.getTabWidget().getChildCount(); ++i) {
            tabs.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_selector);
        }
        */

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == "mitab1") {
                    adapEvent = new AdapterImagenesListView(context, eventosDestacados, imagenesDestacados);
                    lv.setAdapter(adapEvent);
                } else if (tabId == "mitab2") {
                    adapEvent = new AdapterImagenesListView(context, eventosRecomendados, imagenesRecomendados);
                    lv.setAdapter(adapEvent);
                } else if (tabId == "mitab3") {
                    adapEvent = new AdapterImagenesListView(context, eventos, imagenes);
                    lv.setAdapter(adapEvent);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.botonFiltrarEventos) {
            Toast.makeText(getBaseContext(), "Has clicado en filtrar", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eventos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getBaseContext(), "Has clicado en el menu", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
