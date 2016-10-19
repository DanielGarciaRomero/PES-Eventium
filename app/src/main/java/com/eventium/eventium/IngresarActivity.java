package com.eventium.eventium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IngresarActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle(R.string.toolbar_saldo);
        mytoolbar.setNavigationIcon(R.drawable.ic_menu);

        Button realizar_ingreso = (Button) findViewById(R.id.button15);
        realizar_ingreso.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getBaseContext(), "Has clicado en el menu", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button15) {
            Toast.makeText(getBaseContext(), "Has pulsado relizar ingreso", Toast.LENGTH_LONG).show();
        }
    }
}
