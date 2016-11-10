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

    public HTTPMethods(Integer id){
        finished = false;
        peticion_id = id;
    }

    public void ejecutarHttpAsyncTask(){
        if (peticion_id == 0) new HttpAsyncTask().execute("http://10.4.41.168:5000/users"); //get de users
        else if (peticion_id == 1) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString()); //get de un user
        else if (peticion_id == 2) new HttpAsyncTask().execute("http://10.4.41.168:5000/mail"); //recuperar contraseña
        else if (peticion_id == 3) new HttpAsyncTask().execute("http://10.4.41.168:5000/events"); //get de eventos
        else if (peticion_id == 10) new HttpAsyncTask().execute("http://10.4.41.168:5000/user"); //post de un user
    }

    public List<Usuario> getUsers(){
        return users;
    }

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

    public void setPic(String foto){
        pic = foto;
    }

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
            else httpResponse = httpclient.execute(httpGet);

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
            httpResponse = httpclient.execute(httpPost);
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
            else return POST(urls[0]);
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
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Evento(title, id, organizedId);
    }
}
