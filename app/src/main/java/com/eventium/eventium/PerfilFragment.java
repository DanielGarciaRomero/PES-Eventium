package com.eventium.eventium;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;
/**
 * Created by Abel on 11/11/2016.
 */

public class PerfilFragment extends Fragment  {

    private Toolbar mytoolbar;
    private TabLayout tabLayout;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil, container, false);
        if (savedInstanceState == null) {
            tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        }
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
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
