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

import java.util.ArrayList;

/**
 * Created by Abel on 13/10/2016.
 */

public class TemasActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Integer> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temas);

        categorias = new ArrayList<Integer>();
        for (int i = 0; i < 12; ++i){categorias.add(i, 0);}

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
                //Toast.makeText(getBaseContext(), "Has seleccionado la categoria: " + ((ToggleButton) v).getText(), Toast.LENGTH_LONG).show();
                if (((ToggleButton) v).getTextOff().equals("ARTISTICO")) categorias.set(0, 1);
                else if (((ToggleButton) v).getText().equals("AUTOMOBILISTICO")) categorias.set(1, 1);
                else if (((ToggleButton) v).getText().equals("CINEMATOGRAFICO")) categorias.set(2, 1);
                else if (((ToggleButton) v).getText().equals("DEPORTIVO")) categorias.set(3, 1);
                else if (((ToggleButton) v).getText().equals("GASTRONOMICO")) categorias.set(4, 1);
                else if (((ToggleButton) v).getText().equals("LITERARIO")) categorias.set(5, 1);
                else if (((ToggleButton) v).getText().equals("MODA")) categorias.set(6, 1);
                else if (((ToggleButton) v).getText().equals("MUSICAL")) categorias.set(7, 1);
                else if (((ToggleButton) v).getText().equals("POLITICO")) categorias.set(9, 1);
                else if (((ToggleButton) v).getText().equals("TEATRAL")) categorias.set(10, 1);
                else if (((ToggleButton) v).getText().equals("TECNOLOGICO Y CIENTIFICO")) categorias.set(11, 1);
                else if (((ToggleButton) v).getText().equals("OTROS")) categorias.set(8, 1);
            }
            else {
                v.setBackgroundColor(Color.parseColor("#56E394"));
                //Toast.makeText(getBaseContext(), "Has deseleccionado la categoria: " + ((ToggleButton) v).getText(), Toast.LENGTH_LONG).show();
                if (((ToggleButton) v).getTextOff().equals("ARTISTICO")) categorias.set(0, 0);
                else if (((ToggleButton) v).getText().equals("AUTOMOBILISTICO")) categorias.set(1, 0);
                else if (((ToggleButton) v).getText().equals("CINEMATOGRAFICO")) categorias.set(2, 0);
                else if (((ToggleButton) v).getText().equals("DEPORTIVO")) categorias.set(3, 0);
                else if (((ToggleButton) v).getText().equals("GASTRONOMICO")) categorias.set(4, 0);
                else if (((ToggleButton) v).getText().equals("LITERARIO")) categorias.set(5, 0);
                else if (((ToggleButton) v).getText().equals("MODA")) categorias.set(6, 0);
                else if (((ToggleButton) v).getText().equals("MUSICAL")) categorias.set(7, 0);
                else if (((ToggleButton) v).getText().equals("POLITICO")) categorias.set(9, 0);
                else if (((ToggleButton) v).getText().equals("TEATRAL")) categorias.set(10, 0);
                else if (((ToggleButton) v).getText().equals("TECNOLOGICO Y CIENTIFICO")) categorias.set(11, 0);
                else if (((ToggleButton) v).getText().equals("OTROS")) categorias.set(8, 0);
            }
        }
        else if (v.getId() == R.id.button14){
            //Toast.makeText(getBaseContext(), "Has pulsado aceptar", Toast.LENGTH_LONG).show();
            /*String aux = artistico.toString() + "," + automobilistico.toString() + "," + cinematografico.toString() + "," +
                    deportivo.toString() + "," + gastronomico.toString() + "," + literario.toString() + "," +
                    moda.toString() + "," + musical.toString() + "," + politico.toString() + "," +
                    teatral.toString() + "," + tecnologico.toString() + "," + otros.toString();*/
            Boolean primer = true;
            String aux = "";
            Integer i;
            for (i = 0; i < categorias.size(); ++i){
                if (primer){
                    if (categorias.get(i) == 1) {aux += i.toString(); primer = false;}
                }
                else {
                    if (categorias.get(i) == 1) aux += "," + i.toString();
                }
            }

            HTTPMethods httpMethods = new HTTPMethods(15);
            httpMethods.setCategories(aux);

            /*HTTPMethods httpMethods1 = new HTTPMethods(4);
            httpMethods1.setToken_user(MainActivity.token);
            httpMethods1.ejecutarHttpAsyncTask();
            while (!httpMethods1.getFinished());
            System.out.println(MainActivity.token);
            String id_user = httpMethods1.getResultado();
            System.out.println(id_user);*/
            //Habra que hacer substring y quedarnos con el user bien

            httpMethods.setUser_id(1);
            httpMethods.ejecutarHttpAsyncTask();
            while (!httpMethods.getFinished());
            //System.out.println(aux);
            TemasActivity.this.startActivity(new Intent(TemasActivity.this, NavigationDrawerActivity.class));
        }
    }
}
