package com.eventium.eventium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button crear_cuenta = (Button) findViewById(R.id.button2);
        crear_cuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button2) {
            Toast.makeText(getBaseContext(), "Has pulsado crear cuenta", Toast.LENGTH_LONG).show();
            //MainActivity.this.startActivity(new Intent(MainActivity.this, RecActivity.class));
        }
    }
}
