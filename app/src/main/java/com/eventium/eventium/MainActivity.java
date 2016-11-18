package com.eventium.eventium;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context contexto;
    public static String token;
    EditText user;
    EditText contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = null;

        TextView registro = (TextView) findViewById(R.id.textView3);
        registro.setOnClickListener(this);

        TextView password = (TextView) findViewById(R.id.textView4);
        password.setOnClickListener(this);

        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener(this);

        user = (EditText) findViewById(R.id.editText);
        user.setOnClickListener(this);

        contra = (EditText) findViewById(R.id.editText2);
        contra.setOnClickListener(this);

        String languageToLoad  = "es"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        contexto = getBaseContext();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView3) {
            //Toast.makeText(getBaseContext(), "Has pulsado para registrarte", Toast.LENGTH_LONG).show();
            MainActivity.this.startActivity(new Intent(MainActivity.this, RegistroActivity.class));
        }
        else if (v.getId() == R.id.textView4) {
            //Toast.makeText(getBaseContext(), "Has pulsado olvidar contraseña", Toast.LENGTH_LONG).show();
            MainActivity.this.startActivity(new Intent(MainActivity.this, PasswordActivity.class));
        }
        else if (v.getId() == R.id.button) {
            if (user.getText().toString().equals("") || contra.getText().toString().equals(""))
                Toast.makeText(getBaseContext(), "No puedes dejar ningún campo en blanco", Toast.LENGTH_LONG).show();
            else {
                HTTPMethods httpMethods = new HTTPMethods(12);
                httpMethods.setUsername(user.getText().toString());
                httpMethods.setPassword(contra.getText().toString());
                httpMethods.ejecutarHttpAsyncTask();
                while (!httpMethods.getFinished());
                if (httpMethods.getCode().equals("HTTP/1.0 200 OK")){
                    String aux = httpMethods.getResultado();
                    //System.out.println(aux);
                    String aux2 = aux.substring(11, aux.length() - 2);
                    token = aux2;
                    //System.out.println(aux2);
                    MainActivity.this.startActivity(new Intent(MainActivity.this, NavigationDrawerActivity.class));
                }
                else if (httpMethods.getCode().equals("HTTP/1.0 404 NOT FOUND")){
                    Toast.makeText(getBaseContext(), "Username y/o contraseña inválidos", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
