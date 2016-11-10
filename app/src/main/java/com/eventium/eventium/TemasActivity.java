package com.eventium.eventium;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

/**
 * Created by Abel on 13/10/2016.
 */

public class TemasActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temas);


        ToggleButton artistico = (ToggleButton) findViewById(R.id.button1);
        artistico.setOnClickListener(this);

        ToggleButton automobilistico = (ToggleButton) findViewById(R.id.button2);
        automobilistico.setOnClickListener(this);

        ToggleButton cinematografico = (ToggleButton) findViewById(R.id.button3);
        cinematografico.setOnClickListener(this);

        ToggleButton deportivo = (ToggleButton) findViewById(R.id.button4);
        deportivo.setOnClickListener(this);

        ToggleButton gastronomico = (ToggleButton) findViewById(R.id.button5);
        gastronomico.setOnClickListener(this);

        ToggleButton literario = (ToggleButton) findViewById(R.id.button6);
        literario.setOnClickListener(this);

        ToggleButton moda = (ToggleButton) findViewById(R.id.button7);
        moda.setOnClickListener(this);

        ToggleButton musical = (ToggleButton) findViewById(R.id.button8);
        musical.setOnClickListener(this);

        ToggleButton politico = (ToggleButton) findViewById(R.id.button9);
        politico.setOnClickListener(this);

        ToggleButton teatral = (ToggleButton) findViewById(R.id.button10);
        teatral.setOnClickListener(this);

        ToggleButton tecnologico = (ToggleButton) findViewById(R.id.button11);
        tecnologico.setOnClickListener(this);

        ToggleButton otros = (ToggleButton) findViewById(R.id.button12);
        otros.setOnClickListener(this);

        Button aceptar = (Button) findViewById(R.id.button14);
        aceptar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v instanceof ToggleButton) {
            if (((ToggleButton) v).isChecked()) {
                v.setBackgroundColor(Color.parseColor("#5FBD88"));
                Toast.makeText(getBaseContext(), "Has seleccionado la categoria: " + ((ToggleButton) v).getText(), Toast.LENGTH_LONG).show();
            }
            else {
                v.setBackgroundColor(Color.parseColor("#56E394"));
                Toast.makeText(getBaseContext(), "Has deseleccionado la categoria: " + ((ToggleButton) v).getText(), Toast.LENGTH_LONG).show();
            }
        }
        else if (v.getId() == R.id.button14){
            Toast.makeText(getBaseContext(), "Has pulsado aceptar", Toast.LENGTH_LONG).show();
            TemasActivity.this.startActivity(new Intent(TemasActivity.this, NavigationDrawerActivity.class));
        }
    }
}
