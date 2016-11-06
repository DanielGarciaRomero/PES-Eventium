package com.eventium.eventium;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
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
    ArrayList<String> usernames;
    RVAdapter adapter;
    private List<UserModel> mUserModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usernames = new ArrayList<String>();
        usernames.add("abelmenor");
        usernames.add("alvaroma94");
        usernames.add("aridez");
        usernames.add("dagaro");
        usernames.add("link-adri");
        usernames.add("rodergas");
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

        mUserModel = new ArrayList<>();
        for (int i = 0; i < usernames.size(); ++i) {
            Uri geller = Uri.parse("android.resource://" + NavigationDrawerActivity.PACKAGE_NAME + "/" + R.raw.defaultuserimage);
            mUserModel.add(new UserModel(geller, usernames.get(i)));
        }

        adapter= new RVAdapter(false);
        adapter.setRVU(mUserModel);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(NavigationDrawerActivity.contexto, rv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String item = adapter.getItemRVU(position);
                        Toast.makeText(NavigationDrawerActivity.contexto, item, Toast.LENGTH_LONG).show();
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
