package com.eventium.eventium;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.eventium.eventium.TabFragments.TabOneFragment;
import com.eventium.eventium.TabFragments.TabTwoFragment;
import com.eventium.eventium.TabFragments.TabThreeFragment;

public class EventosActivity extends AppCompatActivity {

    public static String PACKAGE_NAME;
    public static Context contexto;
    private Toolbar mytoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        contexto = getBaseContext();

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        mytoolbar.setNavigationIcon(R.drawable.ic_menu);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabOneFragment(), "DESTACADOS");
        adapter.addFragment(new TabTwoFragment(), "RECOMENDADOS");
        adapter.addFragment(new TabThreeFragment(), "TODOS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getBaseContext(), "Has clicado en el menu", Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.action_filter) {
            Toast.makeText(getBaseContext(), "Has clicado en filtrar", Toast.LENGTH_LONG).show();
            EventosActivity.this.startActivity(new Intent(EventosActivity.this, UsuariosActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
