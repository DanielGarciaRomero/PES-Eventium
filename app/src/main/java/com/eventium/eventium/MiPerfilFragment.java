package com.eventium.eventium;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Abel on 11/11/2016.
 */

public class MiPerfilFragment extends Fragment  {

    private Toolbar mytoolbar;
    private TabLayout tabLayout;

    TextView name;

    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil, container, false);
        if (savedInstanceState == null) {
            tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        }
        name = (TextView)view.findViewById(R.id.username);
        view.findViewById(R.id.followbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.contexto, "Has pulsado Follow", Toast.LENGTH_LONG).show();
            }
        });
        view.findViewById(R.id.reportbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.contexto, "Has pulsado Report", Toast.LENGTH_LONG).show();
            }
        });

        System.out.println("hola1");
        HTTPMethods httpMethods = new HTTPMethods(1);
        httpMethods.setUsername("user50");
        httpMethods.ejecutarHttpAsyncTask();
        System.out.println("hola2");
        while (!httpMethods.getFinished());
        System.out.println("hola3");
        if (httpMethods.getCode().equals("HTTP/1.0 200 OK")){
            Usuario user = httpMethods.getUser();
            name.setText(user.getUsername());
        }

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
