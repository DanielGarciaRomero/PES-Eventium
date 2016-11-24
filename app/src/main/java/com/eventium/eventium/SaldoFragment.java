package com.eventium.eventium;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaldoFragment extends Fragment {

    EditText numtarjeta;
    EditText cvc;
    EditText saldo;

    public SaldoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_ingresar, container, false);
        numtarjeta = (EditText)view.findViewById(R.id.editText8);
        cvc = (EditText)view.findViewById(R.id.editText11);
        saldo = (EditText)view.findViewById(R.id.editText12);
        Button realizar_ingreso = (Button) view.findViewById(R.id.button15);
        realizar_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numtarjeta.getText().toString().equals("") || cvc.getText().toString().equals("")
                        || saldo.getText().toString().equals(""))
                    Toast.makeText(NavigationDrawerActivity.contexto, "No puedes dejar ningún campo en blanco", Toast.LENGTH_LONG).show();
                else {
                    //Obtengo el username con el token
                    HTTPMethods httpMethods1 = new HTTPMethods(4);
                    httpMethods1.setToken_user(NavigationDrawerActivity.token);
                    httpMethods1.ejecutarHttpAsyncTask();
                    while (!httpMethods1.getFinished());
                    String username = httpMethods1.getResultado();
                    username = username.substring(14, username.length() - 2);

                    //Obtengo el usuario con el username
                    HTTPMethods httpMethods2 = new HTTPMethods(1);
                    httpMethods2.setUsername(username);
                    httpMethods2.ejecutarHttpAsyncTask();
                    while (!httpMethods2.getFinished());
                    Usuario user = httpMethods2.getUser();

                    HTTPMethods httpMethods = new HTTPMethods(17);
                    httpMethods.setCardNumber(numtarjeta.getText().toString());
                    httpMethods.setCvc(cvc.getText().toString());
                    httpMethods.setMoney(saldo.getText().toString());
                    httpMethods.setToken_user(NavigationDrawerActivity.token);
                    httpMethods.setUser_id(Integer.parseInt(user.getId()));
                    httpMethods.ejecutarHttpAsyncTask();
                    while (!httpMethods.getFinished());

                    NavigationDrawerActivity.change_saldo = true;

                    //Tengo que obtener el nuevo saldo
                    HTTPMethods httpMethods3 = new HTTPMethods(1);
                    httpMethods3.setUsername(username);
                    httpMethods3.ejecutarHttpAsyncTask();
                    while (!httpMethods3.getFinished());
                    user = httpMethods3.getUser();

                    NavigationDrawerActivity.usersaldo = user.getSaldo();
                }
                //Toast.makeText(NavigationDrawerActivity.contexto, "Has pulsado realizar ingreso", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //inflater.inflate(R.menu.menu_saldo, menu);
    }
}
