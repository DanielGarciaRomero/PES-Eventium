package com.eventium.eventium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView registro = (TextView) findViewById(R.id.textView3);
        registro.setOnClickListener(this);

        TextView password = (TextView) findViewById(R.id.textView4);
        password.setOnClickListener(this);

        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView3) {
            Toast.makeText(getBaseContext(), "Has pulsado para registrarte", Toast.LENGTH_LONG).show();
            //MainActivity.this.startActivity(new Intent(MainActivity.this,IngActivity.class));
        }
        else if (v.getId() == R.id.textView4) {
            Toast.makeText(getBaseContext(), "Has pulsado olvidar contraseña", Toast.LENGTH_LONG).show();
            //MainActivity.this.startActivity(new Intent(MainActivity.this, RecActivity.class));
        }
        else if (v.getId() == R.id.button) {
            Toast.makeText(getBaseContext(), "has pulsado login", Toast.LENGTH_LONG).show();
            //MainActivity.this.startActivity(new Intent(MainActivity.this, RecActivity.class));
        }
    }
}
