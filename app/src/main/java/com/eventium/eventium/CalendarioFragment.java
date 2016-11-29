package com.eventium.eventium;

import android.app.ActionBar;
import android.app.Notification;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarioFragment extends Fragment {

    public CalendarioFragment() {
        // Required empty public constructor
    }

    CompactCalendarView calendar;
    TextView mes;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendario, container, false);

        calendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        calendar.setUseThreeLetterAbbreviation(true);

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        System.out.println("HOLA" + date.getTime());
        df.applyPattern("MMMM");
        String s = df.format(date);
        s += " - ";
        df.applyPattern("yyyy");
        s += df.format(date);

        mes = (TextView) view.findViewById(R.id.textView14);
        mes.setText(s);

        //21 octubre 2016

        Event ev1 = new Event(Color.rgb(95, 189, 136), 1480437742653L, "HOLA");
        calendar.addEvent(ev1);

        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = NavigationDrawerActivity.contexto;

                if (dateClicked.toString().compareTo("Fri Oct 21 00:00:00 EDT 2016") == 0) {
                    Toast.makeText(context, "HOLA", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No Events Planned por this day", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mes.setText(dateFormatMonth.format(firstDayOfNewMonth));
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
        //inflater.inflate(R.menu.menu_calendario, menu);
    }

    /*CalendarView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendario, container, false);
        //calendar = (CalendarView) view.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day){
                Toast.makeText(NavigationDrawerActivity.contexto, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
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
        //inflater.inflate(R.menu.menu_calendario, menu);
    }*/

}
