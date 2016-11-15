package com.eventium.eventium.TabFragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.eventium.eventium.HTTPMethods;
import com.eventium.eventium.MainActivity;
import com.eventium.eventium.PasswordActivity;
import com.eventium.eventium.R;

/**
 * Created by Abel on 11/11/2016.
 */

public class PasswordFragment extends Fragment {
    private Toolbar mytoolbar;
    private TabLayout tabLayout;
    private EditText emailText;
    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_password, container, false);
        if (savedInstanceState == null) {
            tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        }
        emailText = (EditText) view.findViewById(R.id.editText7);
        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        view.findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPMethods httpMethods = new HTTPMethods(2);
                httpMethods.setMail(emailText.getText().toString());
                httpMethods.ejecutarHttpAsyncTask();
                while (!httpMethods.getFinished()) ;
                Toast.makeText(MainActivity.contexto, "Mail enviado", Toast.LENGTH_LONG).show();
                //PasswordFragment.this.startActivity(new Intent(PasswordFragment.this, MainActivity.class));
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
