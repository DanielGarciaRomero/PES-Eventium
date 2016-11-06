package com.eventium.eventium;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView calendar;
    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle(R.string.toolbar_calendario);
        mytoolbar.setNavigationIcon(R.drawable.ic_menu);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day){
                Toast.makeText(getApplicationContext(), day + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getBaseContext(), "Has clicado en el menu", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}