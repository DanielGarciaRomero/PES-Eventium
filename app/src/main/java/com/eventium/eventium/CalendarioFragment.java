package com.eventium.eventium;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarioFragment extends Fragment {

    public CalendarioFragment() {
        // Required empty public constructor
    }

    List<Evento> eventos;
    CompactCalendarView calendar;
    TextView mes;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    Integer idUsuario;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendario, container, false);

        try {

            eventos = new ArrayList<>();

            calendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
            calendar.setUseThreeLetterAbbreviation(true);

            final Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
            //System.out.println("HOLA" + date.getTime());
            //Para mostrar el primer mes y año
            df.applyPattern("MMMM");
            String s = df.format(date);
            s += " - ";
            df.applyPattern("yyyy");
            s += df.format(date);

            mes = (TextView) view.findViewById(R.id.textView14);
            mes.setText(s);

            //Obtengo el username con el token
            HTTPMethods httpMethods1 = new HTTPMethods(4);
            httpMethods1.setToken_user(NavigationDrawerActivity.token);
            httpMethods1.ejecutarHttpAsyncTask();
            while (!httpMethods1.getFinished());
            String username = httpMethods1.getResultado();
            username = username.substring(14, username.length() - 2);

            //Obtengo el usuario con el username
            HTTPMethods httpMethods2 = new HTTPMethods(1);
            httpMethods2.setUsername(username);
            httpMethods2.ejecutarHttpAsyncTask();
            while (!httpMethods2.getFinished());
            Usuario user = httpMethods2.getUser();

            idUsuario = Integer.parseInt(user.getId());

            //Obtengo el calendario de un user
            HTTPMethods httpMethods3 = new HTTPMethods(8);
            httpMethods3.setToken_user(NavigationDrawerActivity.token);
            httpMethods3.setUser_id(idUsuario);
            httpMethods3.ejecutarHttpAsyncTask();
            while (!httpMethods3.getFinished());
            List<Calendario> list_calendario = httpMethods3.getCalendarios();

            if (list_calendario != null) {
                for (int i = 0; i < list_calendario.size(); ++i) {
                    Integer eventID = list_calendario.get(i).getEventid();

                    HTTPMethods httpMethods4 = new HTTPMethods(7);
                    httpMethods4.setEvent_id(eventID.toString());
                    httpMethods4.ejecutarHttpAsyncTask();
                    while (!httpMethods4.getFinished());
                    Evento event = httpMethods4.getEvent();

                    String fecha1 = event.getFecha_ini();
                    Boolean existe = false;

                    for (int j = 0; j < eventos.size(); ++j) {
                        if (eventos.get(j).getFecha_ini().equals(fecha1)) existe = true;
                    }

                    eventos.add(event);

                    if (!existe){
                        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                        fecha1 = fecha1.substring(0,4) + "-" + fecha1.substring(5,7) + "-" + fecha1.substring(8,10);
                        Date date1 = df2.parse(fecha1);
                        //System.out.println(df2.format(date1));
                        //System.out.println(date1.getTime());
                        Event ev1 = new Event(Color.RED, date1.getTime(), "HOLA");
                        calendar.addEvent(ev1);
                    }
                }
            }

            calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    Boolean trobat = false;
                    Context context = NavigationDrawerActivity.contexto;

                    String año = dateClicked.toString().substring(24,28);
                    String mes = dateClicked.toString().substring(4,7);
                    String dia = dateClicked.toString().substring(8,10);
                    if (mes.equals("Jan")) mes = "01";
                    else if (mes.equals("Feb")) mes = "02";
                    else if (mes.equals("Mar")) mes = "03";
                    else if (mes.equals("Apr")) mes = "04";
                    else if (mes.equals("May")) mes = "05";
                    else if (mes.equals("Jun")) mes = "06";
                    else if (mes.equals("Jul")) mes = "07";
                    else if (mes.equals("Aug")) mes = "08";
                    else if (mes.equals("Sep")) mes = "09";
                    else if (mes.equals("Oct")) mes = "10";
                    else if (mes.equals("Nov")) mes = "11";
                    else if (mes.equals("Dec")) mes = "12";
                    String fecha = año + "/" + mes + "/" + dia;

                    ArrayList<Evento> eventosdeldia;
                    eventosdeldia = new ArrayList<Evento>();
                    for (int i = 0; i < eventos.size(); ++i){
                        if (eventos.get(i).getFecha_ini().equals(fecha)){
                            //Toast.makeText(context, "Hay un evento", Toast.LENGTH_SHORT).show();
                            trobat = true;
                            eventosdeldia.add(eventos.get(i));
                        }
                    }
                    if (trobat){
                        NavigationDrawerActivity.events = eventosdeldia;
                        MyDialogFragment dialogFragment = new MyDialogFragment ();
                        dialogFragment.show(getActivity().getFragmentManager(), "hola");
                    }
                    else Toast.makeText(context, "No tienes ningún evento programado para este día", Toast.LENGTH_SHORT).show();
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
