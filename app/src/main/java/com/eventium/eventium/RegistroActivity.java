package com.eventium.eventium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username;
    EditText email;
    EditText contrasena;
    EditText confirmar_contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button crear_cuenta = (Button) findViewById(R.id.button2);
        crear_cuenta.setOnClickListener(this);

        username = (EditText) findViewById(R.id.editText3);
        username.setOnClickListener(this);

        email = (EditText) findViewById(R.id.editText4);
        email.setOnClickListener(this);

        contrasena = (EditText) findViewById(R.id.editText5);
        contrasena.setOnClickListener(this);

        confirmar_contrasena = (EditText) findViewById(R.id.editText6);
        confirmar_contrasena.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button2) {
            HTTPMethods httpMethods = new HTTPMethods(10);
            httpMethods.setUsername(username.getText().toString());
            httpMethods.setMail(email.getText().toString());
            httpMethods.setPassword(contrasena.getText().toString());
            httpMethods.setPic("");
            httpMethods.ejecutarHttpAsyncTask();
            while (!httpMethods.getFinished());
            Toast.makeText(getBaseContext(), "Registrado correctamente", Toast.LENGTH_LONG).show();
            RegistroActivity.this.startActivity(new Intent(RegistroActivity.this, MainActivity.class));
        }
    }
}
