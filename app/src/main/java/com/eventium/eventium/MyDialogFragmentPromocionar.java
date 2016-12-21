package com.eventium.eventium;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eventium.eventium.TabFragments.EventModel;
import com.eventium.eventium.TabFragments.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyDialogFragmentPromocionar extends DialogFragment {

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.dialog_promocionar, container, false);
       getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
       return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final RadioButton radioplatino = (RadioButton) view.findViewById(R.id.promocionar_platino);
        radioplatino.setChecked(true);
        final RadioButton radiooro = (RadioButton) view.findViewById(R.id.promocionar_oro);
        final RadioButton radioplata = (RadioButton) view.findViewById(R.id.promocionar_plata);

        TextView textPromocionar = (TextView) view.findViewById(R.id.promocionar);
        TextView textCancelar = (TextView) view.findViewById(R.id.cancelar);

        textCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        textPromocionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(NavigationDrawerActivity.token);

                String saldo;
                Integer numtarjeta = 11111111;
                Integer cvc = 111;

                //Restarme el dinero
                HTTPMethods httpMethods1 = new HTTPMethods(4);
                httpMethods1.setToken_user(NavigationDrawerActivity.token);
                httpMethods1.ejecutarHttpAsyncTask();
                while (!httpMethods1.getFinished());
                String username = httpMethods1.getResultado();
                username = username.substring(14, username.length() - 2);

                HTTPMethods httpMethods2 = new HTTPMethods(1);
                httpMethods2.setUsername(username);
                httpMethods2.ejecutarHttpAsyncTask();
                while (!httpMethods2.getFinished());
                Usuario user = httpMethods2.getUser();

                saldo = user.getSaldo();
                Integer aux = 0;

                if (radioplatino.isChecked()) {
                    aux = -190;
                }
                else if (radiooro.isChecked()) {
                    aux = -3;
                }
                else if (radioplata.isChecked()) {
                    aux = -1;
                }

                if ((aux*(-1)) > Integer.parseInt(saldo)) {
                    //dismiss();
                    Toast.makeText(MainActivity.contexto, "No tienes saldo suficiente para este plan de promoción", Toast.LENGTH_LONG).show();
                }
                else {
                    //Peticion para promocionar un evento
                    HTTPMethods httpMethods = new HTTPMethods(18);
                    httpMethods.setDestacado("true");
                    httpMethods.setToken_user(NavigationDrawerActivity.token);
                    httpMethods.setEvent_id(NavigationDrawerActivity.event_id);
                    httpMethods.ejecutarHttpAsyncTask();
                    while (!httpMethods.getFinished());

                    HTTPMethods httpMethods3 = new HTTPMethods(17);
                    httpMethods3.setCardNumber(numtarjeta.toString());
                    httpMethods3.setCvc(cvc.toString());
                    httpMethods3.setMoney(aux.toString());
                    httpMethods3.setToken_user(NavigationDrawerActivity.token);
                    httpMethods3.setUser_id(Integer.parseInt(user.getId()));
                    httpMethods3.ejecutarHttpAsyncTask();
                    while (!httpMethods3.getFinished()) ;

                    NavigationDrawerActivity.change_saldo = true;

                    //Tengo que obtener el nuevo saldo
                    HTTPMethods httpMethods4 = new HTTPMethods(1);
                    httpMethods4.setUsername(username);
                    httpMethods4.ejecutarHttpAsyncTask();
                    while (!httpMethods4.getFinished()) ;
                    user = httpMethods4.getUser();

                    NavigationDrawerActivity.usersaldo = user.getSaldo();

                    ((NavigationDrawerActivity) getActivity()).fromPromocionarEventoToVerEventos();
                    dismiss();
                }
            }
        });
    }
}
