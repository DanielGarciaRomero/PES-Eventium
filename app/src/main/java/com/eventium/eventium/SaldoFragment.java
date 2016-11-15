package com.eventium.eventium;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SaldoFragment extends Fragment {

    public SaldoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_ingresar, container, false);
        Button realizar_ingreso = (Button) view.findViewById(R.id.button15);
        realizar_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NavigationDrawerActivity.contexto, "Has pulsado realizar ingreso", Toast.LENGTH_LONG).show();
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
