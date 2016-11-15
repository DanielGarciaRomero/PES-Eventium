package com.eventium.eventium;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Abel on 18/10/2016.
 */

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button follow = (Button) findViewById(R.id.followbutton);
        follow.setOnClickListener(this);

        Button report = (Button) findViewById(R.id.reportbutton);
        report.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.followbutton) {
            Toast.makeText(getBaseContext(), "Has pulsado Follow", Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.reportbutton) {
            Toast.makeText(getBaseContext(), "Has pulsado Report", Toast.LENGTH_LONG).show();
        }
    }
}
