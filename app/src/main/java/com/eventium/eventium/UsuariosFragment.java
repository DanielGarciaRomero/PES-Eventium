package com.eventium.eventium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.Toast;
import com.eventium.eventium.TabFragments.UserModel;
import com.eventium.eventium.TabFragments.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsuariosFragment extends Fragment implements SearchView.OnQueryTextListener {

    public UsuariosFragment() {
        // Required empty public constructor
    }

    RecyclerView rv;
    RVAdapter adapter;
    private List<UserModel> mUserModel;
    private String myUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //GET DE USERS
        HTTPMethods httpMethods = new HTTPMethods(0);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        List<Usuario> list_users = httpMethods.getUsers();
        mUserModel = new ArrayList<>();
        if (list_users != null) {
            for (int i = 0; i < list_users.size(); ++i) {
                String encodedImage = list_users.get(i).getPic();
                byte[] decodedImage = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap base64BitmapImage = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                mUserModel.add(new UserModel(base64BitmapImage, list_users.get(i).getUsername()));
            }
        }

        //GET DE MI USER PARA NO ACCEDER A MI PROPIO PERFIL
        httpMethods = new HTTPMethods(4);
        httpMethods.ejecutarHttpAsyncTask();
        while (!httpMethods.getFinished());
        UsernameSponsor us = httpMethods.getUsernameSponsor();
        myUsername = us.getUsername();
        //myUsername = httpMethods.getResultado();
        //myUsername = myUsername.substring(14, myUsername.length()-2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_usuarios, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recyclerview4);
        rv.setLayoutManager(new LinearLayoutManager(NavigationDrawerActivity.contexto));
        rv.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        adapter= new RVAdapter(false);
        adapter.setRVU(mUserModel);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(NavigationDrawerActivity.contexto, rv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String item = adapter.getItemRVU(position);
                        Toast.makeText(NavigationDrawerActivity.contexto, item, Toast.LENGTH_LONG).show();
                        Fragment fragment;
                        if(!item.equals(myUsername)) fragment = new PerfilFragment();
                        else fragment = new MiPerfilFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("user", item);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contenedor_principal, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_usuarios, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Busca a un usuario");
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilterRVU(mUserModel);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<UserModel> filteredModelList = filter(mUserModel, newText);
        adapter.setFilterRVU(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<UserModel> filter(List<UserModel> models, String query) {
        query = query.toLowerCase();

        final List<UserModel> filteredModelList = new ArrayList<>();
        for (UserModel model : models) {
            final String text = model.getUsername().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
