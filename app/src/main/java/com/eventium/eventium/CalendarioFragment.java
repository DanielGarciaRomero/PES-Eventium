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

import java.text.ParseException;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendario, container, false);

        try {

            calendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
            calendar.setUseThreeLetterAbbreviation(true);

            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
            //System.out.println("HOLA" + date.getTime());
            //Para mostrar el primer mes y año
            df.applyPattern("MMMM");
            String s = df.format(date);
            s += " - ";
            df.applyPattern("yyyy");
            s += df.format(date);

            //He indicado que asistire a 3 eventos con fechas iniciales tal
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String fecha1 = "2016/11/12";
            fecha1 = fecha1.substring(0,4) + "-" + fecha1.substring(5,7) + "-" + fecha1.substring(8,10);
            Date date1 = df2.parse(fecha1);
            System.out.println(df2.format(date1));
            System.out.println(date1.getTime());
            Event ev1 = new Event(Color.rgb(95, 189, 136), date1.getTime(), "HOLA");
            calendar.addEvent(ev1);

            mes = (TextView) view.findViewById(R.id.textView14);
            mes.setText(s);

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
        } catch (ParseException e) {
            return view;
        }
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

}
