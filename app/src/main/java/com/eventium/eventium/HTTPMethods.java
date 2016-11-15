package com.eventium.eventium;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HTTPMethods {

    public static InputStream resultado_json;
    public static String resultado;
    public static List<Usuario> users;
    public static List<Evento> events;
    public static Boolean finished;
    public static Integer user_id;
    public static Integer peticion_id;
    public static String username;
    public static String password;
    public static String mail;
    public static String pic;
    public static String categories;
    public static String event_id;
    public static String event_title;
    public static String event_fecha_ini;
    public static String event_fecha_fin;
    public static String event_hora_ini;
    public static String event_hora_fin;
    public static String event_precio;
    public static String event_ciudad;
    public static String event_pic;
    public static String event_categoria;
    public static String token_user;
    public static String code;

    public HTTPMethods(Integer id){
        finished = false;
        peticion_id = id;
        event_id = "";
    }

    public void ejecutarHttpAsyncTask(){
        if (peticion_id == 0) new HttpAsyncTask().execute("http://10.4.41.168:5000/users"); //get de users
        else if (peticion_id == 1) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString()); //get de un user
        else if (peticion_id == 2) new HttpAsyncTask().execute("http://10.4.41.168:5000/mail"); //recuperar contraseña
        else if (peticion_id == 3) new HttpAsyncTask().execute("http://10.4.41.168:5000/events"); //get de eventos
        else if (peticion_id == 4) new HttpAsyncTask().execute("http://10.4.41.168:5000/me"); //get de mi id
        else if (peticion_id == 10) new HttpAsyncTask().execute("http://10.4.41.168:5000/users"); //post de un user
        else if (peticion_id == 11) new HttpAsyncTask().execute("http://10.4.41.168:5000/events"); //post de un event
        else if (peticion_id == 12) new HttpAsyncTask().execute("http://10.4.41.168:5000/login"); //login
        else if (peticion_id == 15) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/categories"); //put categorias de un user
    }

    public List<Usuario> getUsers(){return users;}

    public String getCode(){return code;}

    public void setEvent_title(String title){event_title = title;}

    public void setEvent_fecha_ini(String fecha_ini){event_fecha_ini = fecha_ini;}

    public void setEvent_fecha_fin(String fecha_fin){event_fecha_fin = fecha_fin;}

    public void setEvent_hora_ini(String hora_ini){event_hora_ini = hora_ini;}

    public void setEvent_hora_fin(String hora_fin){event_hora_fin = hora_fin;}

    public void setEvent_precio(String precio){event_precio = precio;}

    public void setEvent_ciudad(String ciudad){event_ciudad = ciudad;}

    public void setEvent_pic(String pic){event_pic = pic;}

    public void setEvent_categoria(String categoria){event_categoria = categoria;}

    public void setCategories(String categorias) { categories = categorias; }

    public List<Evento> getEvents() { return events; }

    public String getResultado(){
        return resultado;
    }

    public InputStream getResultado_json(){
        return resultado_json;
    }

    public Boolean getFinished(){
        return finished;
    }

    public void setUser_id(Integer id){
        user_id = id;
    }

    public void setUsername(String nickname){
        username = nickname;
    }

    public void setPassword(String contrasena){
        password = contrasena;
    }

    public void setMail(String correo){
        mail = correo;
    }

    public void setToken_user(String token){token_user = token;}

    public void setPic(String foto){pic = foto;}

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            HttpResponse httpResponse;
            HttpGet httpGet = new HttpGet(url);
            // make GET request to the given URL
            if (peticion_id == 2) {
                httpGet.setHeader("clave", mail);
                httpResponse = httpclient.execute(httpGet);
            }
            else if (peticion_id == 4){
                httpGet.setHeader("token", token_user);
                httpResponse = httpclient.execute(httpGet);
            }
            else httpResponse = httpclient.execute(httpGet);
            code = httpResponse.getStatusLine().toString();

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            //resultado_json = inputStream;
            if (peticion_id < 3 ) readJsonStreamUsuarios(inputStream);
            else if (peticion_id == 3) readJsonStreamEventos(inputStream);

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        finished = true;
        resultado = result;

        return result;
    }

    public static String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try{
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse;
            HttpPost httpPost = new HttpPost(url);
            if (peticion_id == 10) {
                // create a list to store HTTP variables and their values
                List nameValuePairs = new ArrayList();
                // add an HTTP variable and value pair
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("mail", mail));
                nameValuePairs.add(new BasicNameValuePair("pic", pic));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 11){
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("id", event_id));
                nameValuePairs.add(new BasicNameValuePair("organizerId", user_id.toString()));
                nameValuePairs.add(new BasicNameValuePair("title", event_title));
                nameValuePairs.add(new BasicNameValuePair("hora_ini", event_hora_ini));
                nameValuePairs.add(new BasicNameValuePair("hora_fin", event_hora_fin));
                nameValuePairs.add(new BasicNameValuePair("fecha_ini", event_fecha_ini));
                nameValuePairs.add(new BasicNameValuePair("fecha_fin", event_fecha_fin));
                nameValuePairs.add(new BasicNameValuePair("precio", event_precio));
                nameValuePairs.add(new BasicNameValuePair("pic", event_pic));
                nameValuePairs.add(new BasicNameValuePair("ciudad", event_ciudad));
                nameValuePairs.add(new BasicNameValuePair("categoria", event_categoria));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 12){
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            httpResponse = httpclient.execute(httpPost);
            code = httpResponse.getStatusLine().toString();
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            resultado_json = inputStream;
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        }
        catch (Exception e) {
            Log.d("post", e.getLocalizedMessage());
        }
        finished = true;
        resultado = result;

        return result;
    }

    public static String PUT(String url){
        InputStream inputStream = null;
        String result = "";
        try{
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse;
            HttpPut httpPut = new HttpPut(url);
            if (peticion_id == 15){
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("categories", categories));
                httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            httpResponse = httpclient.execute(httpPut);
            code = httpResponse.getStatusLine().toString();
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            resultado_json = inputStream;
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        }
        catch (Exception e) {
            Log.d("post", e.getLocalizedMessage());
        }
        finished = true;
        resultado = result;

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            if (peticion_id < 10) return GET(urls[0]);
            else if (peticion_id >= 10 && peticion_id < 15) return POST(urls[0]);
            else return PUT(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(MainActivity.contexto, "Received!", Toast.LENGTH_LONG).show();
        }
    }

    //JSON USUARIOS
    public static void readJsonStreamUsuarios(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            //return leerArrayUsuarios(reader);
            //events = leerArrayEventos(reader);
            users = leerArrayUsuarios(reader);
        } finally {
            reader.close();
        }
    }

    public static void readJsonStreamEventos(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            //return leerArrayUsuarios(reader);
            events = leerArrayEventos(reader);
            //users = leerArrayUsuarios(reader);
        } finally {
            reader.close();
        }
    }

    public static List leerArrayUsuarios(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList usuarios = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            usuarios.add(leerUsuario(reader));
        }
        reader.endArray();
        return usuarios;
    }

    public static List leerArrayEventos(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList eventos = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            eventos.add(leerEvento(reader));
        }
        reader.endArray();
        return eventos;
    }

    public static Usuario leerUsuario(JsonReader reader) throws IOException {
        String username = null;
        String mail = null;
        String password = null;
        String pic = null;
        String id = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "username":
                    username = reader.nextString();
                    break;
                case "mail":
                    mail = reader.nextString();
                    break;
                case "password":
                    password = reader.nextString();
                    break;
                case "pic":
                    pic = reader.nextString();
                    break;
                case "id":
                    id = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Usuario(username, mail, password, pic, id);
    }

    public static Evento leerEvento(JsonReader reader) throws IOException {
        String title = null;
        String id = null;
        String organizedId = null;
        String ciudad = null;
        String pic = null;
        String precio = null;
        String fecha_ini = null;
        String fecha_fin = null;
        String hora_ini = null;
        String hora_fin = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "title":
                    title = reader.nextString();
                    break;
                case "id":
                    id = reader.nextString();
                    break;
                case "organizedId":
                    organizedId = reader.nextString();
                    break;
                case "ciudad":
                    ciudad = reader.nextString();
                    break;
                case "pic":
                    pic = reader.nextString();
                    break;
                case "precio":
                    precio = reader.nextString();
                    break;
                case "fecha_ini":
                    fecha_ini = reader.nextString();
                    break;
                case "fecha_fin":
                    fecha_fin = reader.nextString();
                    break;
                case "hora_ini":
                    hora_ini = reader.nextString();
                    break;
                case "hora_fin":
                    hora_fin = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Evento(title, id, organizedId, ciudad, pic, precio, fecha_ini, fecha_fin, hora_ini, hora_fin);
    }
}
