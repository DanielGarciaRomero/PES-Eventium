package com.eventium.eventium;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity implements View.OnClickListener {

    private android.widget.Toolbar toolbar;
    ListView lv;
    ArrayList<String> usernames;
    ArrayList<Uri> imagenes;
    AdapterImagenesListView adapEvent;
    Toolbar my_toolbar;
    Context context;
    Button botonFiltrarUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        botonFiltrarUsuarios = (Button) findViewById(R.id.botonFiltrarUsuarios);
        botonFiltrarUsuarios.setOnClickListener(this);

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle("Usuarios");
        my_toolbar.setNavigationIcon(R.drawable.ic_menu);

        lv = (ListView) findViewById(R.id.listViewUsuarios);

        usernames = new ArrayList<String>();
        usernames.add("Username 1");
        usernames.add("Username 2");
        usernames.add("Username 3");
        usernames.add("Username 4");
        usernames.add("Username 5");

        imagenes = new ArrayList<Uri>();
        for (int i = 0; i < usernames.size(); ++i) {
            Uri geller = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.defaultuserimage);
            imagenes.add(geller);
        }

        context = this;
        adapEvent = new AdapterImagenesListView(context, usernames, imagenes);
        lv.setAdapter(adapEvent);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapEvent.eventList.get(position);
                Toast.makeText(getBaseContext(), "Has cliclado en " + item, Toast.LENGTH_LONG).show();
                //Intent i = new Intent(UsuariosActivity.this, GetUsuarioActivity.class);
                //i.putExtra("username", item);
                //UsuariosActivity.this.startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.botonFiltrarUsuarios) {
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
