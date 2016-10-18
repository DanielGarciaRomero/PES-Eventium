package com.eventium.eventium;

import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.eventium.eventium.TabFragments.UserModel;
import com.eventium.eventium.TabFragments.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView rv;
    Toolbar mytoolbar;
    ArrayList<String> usernames;
    RVAdapter adapter;
    private List<UserModel> mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle(R.string.toolbar_users);
        mytoolbar.setNavigationIcon(R.drawable.ic_menu);

        usernames = new ArrayList<String>();
        usernames.add("abelmenor");
        usernames.add("alvaroma94");
        usernames.add("aridez");
        usernames.add("dagaro");
        usernames.add("link-adri");
        usernames.add("rodergas");

        mUserModel = new ArrayList<>();
        for (int i = 0; i < usernames.size(); ++i) {
            Uri geller = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.defaultuserimage);
            mUserModel.add(new UserModel(geller, usernames.get(i)));
        }

        rv = (RecyclerView) findViewById(R.id.recyclerview4);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter= new RVAdapter(false);
        adapter.setRVU(mUserModel);
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eventos, menu);
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
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getBaseContext(), "Has clicado en el menu", Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.action_filter) {
            Toast.makeText(getBaseContext(), "Has clicado en filtrar", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
