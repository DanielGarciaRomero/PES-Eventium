package com.eventium.eventium;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
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
    public static Usuario user;
    public static UsernameSponsor usuarioSponsor;
    public static Evento event;
    public static List<Usuario> users;
    public static List<Evento> events;
    public static List<Calendario> calendarios;
    public static List<Comentario> comentarios;
    public static Boolean finished;
    public static Integer user_id;
    public static Integer peticion_id;
    public static String username;
    public static String password;
    public static String mail;
    public static String pic;
    public static String categories;
    public static String user_ciudad;
    public static String user_verified;
    public static String user_banned;

    public static String event_id;
    public static String event_title;
    public static String event_fecha_ini;
    public static String event_fecha_fin;
    public static String event_hora_ini;
    public static String event_hora_fin;
    public static String event_precio;
    public static String event_ciudad;
    public static String event_pic;
    public static Integer event_categoria;
    public static String event_direccion;
    public static String event_url_entradas;
    public static String event_descripcion;
    public static String comentario;

    public static String token_user;
    public static String code;
    public static String destacado;
    public static String nreports;
    public static String CardNumber;
    public static String cvc;
    public static String money;
    public static String direccionFiltraje;

    public HTTPMethods(Integer id){
        finished = false;
        //hardcoded para crear evento de momento
        destacado = "false";
        nreports = "0";
        peticion_id = id;
        event_id = "";
        comentarios = null;
    }

    public void ejecutarHttpAsyncTask(){
        if (peticion_id == 0) new HttpAsyncTask().execute("http://10.4.41.168:5000/users"); //get de users
        else if (peticion_id == 1) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + username); //get de un user
        else if (peticion_id == 2) new HttpAsyncTask().execute("http://10.4.41.168:5000/mail"); //recuperar contraseña
        else if (peticion_id == 3) new HttpAsyncTask().execute("http://10.4.41.168:5000/events"); //get de eventos
        else if (peticion_id == 4) new HttpAsyncTask().execute("http://10.4.41.168:5000/me"); //get de mi username
        else if (peticion_id == 5) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/categories"); //get categorias de un user
        else if (peticion_id == 6) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/follows"); //get follows de un user
        else if (peticion_id == 7) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString()); //get de un evento
        else if (peticion_id == 8) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/calendar"); //get calendario de un user
        else if (peticion_id == 9) new HttpAsyncTask().execute(direccionFiltraje);
        else if (peticion_id == 21) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/recommended"); //get events recommendeds
        else if (peticion_id == 22) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/destacados"); //get events destacados
        else if (peticion_id == 10) new HttpAsyncTask().execute("http://10.4.41.168:5000/users"); //post de un user
        else if (peticion_id == 11) new HttpAsyncTask().execute("http://10.4.41.168:5000/events"); //post de un event
        else if (peticion_id == 12) new HttpAsyncTask().execute("http://10.4.41.168:5000/login"); //login
        else if (peticion_id == 13) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/calendar"); //post de calendar - asistire

        else if (peticion_id == 14) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString() + "/comments"); //POST de un comentario
        else if (peticion_id == 23) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString() + "/comments"); //GET de comentarios
        else if (peticion_id == 24) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString()); //PUT de un evento

        else if (peticion_id == 18) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString()); //put de destacado
        else if (peticion_id == 17) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/wallet"); //put de saldo
        else if (peticion_id == 16) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString()); //PUT de USER
        else if (peticion_id == 15) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/categories"); //put categorias de un user
        else if (peticion_id == 20) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString() + "/calendar/" + event_id); //post de calendar - asistire

        else if (peticion_id == 25) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString()); //DELETE de un evento
        else if (peticion_id == 26) new HttpAsyncTask().execute("http://10.4.41.168:5000/events/" + event_id.toString() + "/report"); //PUT de un report event
    }

    public void setDireccionFiltraje(String direccion){direccionFiltraje = direccion;}

    public void setCardNumber(String cNumber){CardNumber = cNumber;}

    public List<Calendario> getCalendarios(){return calendarios;}

    public List<Comentario> getComentarios(){return comentarios;}

    public int getSizeComentarios(){
        if (comentarios == null) return 0;
        else return comentarios.size();
    }

    public void setCvc(String c){cvc = c;}

    public void setMoney(String m){money = m;}

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

    public void setEvent_direccion(String direccion){event_direccion = direccion;}

    public void setEvent_url_entradas(String url_entradas){event_url_entradas = url_entradas;}

    public void setEvent_descripcion(String descripcion){event_descripcion = descripcion;}

    public void setComentario(String comment){ comentario = comment; }

    public void setnReports(String nrep){ nreports = nrep; }

    public void setEvent_categoria(String categoria){
        switch (categoria) {
            case "Artistico":
                event_categoria = 0;
                break;
            case "Automobilistico":
                event_categoria = 1;
                break;
            case "Cinematografico":
                event_categoria = 2;
                break;
            case "Deportivo":
                event_categoria = 3;
                break;
            case "Gastronomico":
                event_categoria = 4;
                break;
            case "Literario":
                event_categoria = 5;
                break;
            case "Moda":
                event_categoria = 6;
                break;
            case "Musical":
                event_categoria = 7;
                break;
            case "Politico":
                event_categoria = 8;
                break;
            case "Teatral":
                event_categoria = 9;
                break;
            case "Tecnologico y cientifico":
                event_categoria = 10;
                break;
            case "Otros":
                event_categoria = 11;
                break;
        }
    }

    public void setCategories(String categorias) { categories = categorias; }

    public List<Evento> getEvents() { return events; }

    public Usuario getUser() { return user; }

    public UsernameSponsor getUsernameSponsor() { return usuarioSponsor ; }

    public Evento getEvent() { return event; }

    public String getResultado(){
        return resultado;
    }

    public String getCategories() {return categories;}

    public InputStream getResultado_json(){
        return resultado_json;
    }

    public Boolean getFinished(){
        return finished;
    }

    public void setDestacado(String des){ destacado = des;}

    public void setEvent_id(String id) { event_id = id; }

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

    public void setUserCiudad(String ciudad){
        user_ciudad = ciudad;
    }

    public void setUserVerified(String verified){
        user_verified = verified;
    }

    public void setUserBanned(String banned){
        user_banned = banned;
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
            else if (peticion_id == 8) {
                httpGet.setHeader("token", token_user);
                httpResponse = httpclient.execute(httpGet);
            }
            else if (peticion_id == 21) {
                httpGet.setHeader("token", token_user);
                httpResponse = httpclient.execute(httpGet);
            }
            else httpResponse = httpclient.execute(httpGet);
            code = httpResponse.getStatusLine().toString();

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            //resultado_json = inputStream;
            if (peticion_id == 1) readJsonStreamUsuario(inputStream);
            else if (peticion_id < 3 ) readJsonStreamUsuarios(inputStream);
            else if (peticion_id == 3) readJsonStreamEventos(inputStream);
            else if (peticion_id == 4) readJsonStreamUsernameSponsor(inputStream);

            else if (peticion_id == 9) readJsonStreamEventos(inputStream);

            else if (peticion_id == 5) readJsonStreamCategorias(inputStream);
            else if (peticion_id == 7) readJsonStreamEvento(inputStream);
            else if (peticion_id == 8) readJsonStreamCalendario(inputStream);
            else if (peticion_id == 21) readJsonStreamEventos(inputStream);
            else if (peticion_id == 22) readJsonStreamEventos(inputStream);
            else if (peticion_id == 23) readJsonStreamComentarios(inputStream);

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
                httpPost.setHeader("token", token_user);
                List nameValuePairs = new ArrayList();
                //nameValuePairs.add(new BasicNameValuePair("organizerId", user_id.toString()));
                nameValuePairs.add(new BasicNameValuePair("title", event_title));
                nameValuePairs.add(new BasicNameValuePair("hora_ini", event_hora_ini));
                nameValuePairs.add(new BasicNameValuePair("hora_fin", event_hora_fin));
                nameValuePairs.add(new BasicNameValuePair("fecha_ini", event_fecha_ini));
                nameValuePairs.add(new BasicNameValuePair("fecha_fin", event_fecha_fin));
                nameValuePairs.add(new BasicNameValuePair("precio", event_precio));
                nameValuePairs.add(new BasicNameValuePair("pic", event_pic));
                nameValuePairs.add(new BasicNameValuePair("ciudad", event_ciudad));
                nameValuePairs.add(new BasicNameValuePair("categoria", event_categoria.toString()));
                nameValuePairs.add(new BasicNameValuePair("destacado", destacado));
                nameValuePairs.add(new BasicNameValuePair("descripcion", event_descripcion));
                nameValuePairs.add(new BasicNameValuePair("url", event_url_entradas));
                nameValuePairs.add(new BasicNameValuePair("nreports", nreports));
                nameValuePairs.add(new BasicNameValuePair("direccion", event_direccion));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 12){
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 13){
                httpPost.setHeader("token", token_user);
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("eventId", event_id));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 14){ // POST comentario
                httpPost.setHeader("token", token_user);
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("text", comentario));
                nameValuePairs.add(new BasicNameValuePair("userid", user_id.toString()));
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
            else if (peticion_id == 16){
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("pic", pic));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("verified", user_verified));
                nameValuePairs.add(new BasicNameValuePair("banned", user_banned));
                nameValuePairs.add(new BasicNameValuePair("ciudad", user_ciudad));
                httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 17){
                httpPut.setHeader("token", token_user);
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("card", CardNumber));
                nameValuePairs.add(new BasicNameValuePair("cvc", cvc));
                nameValuePairs.add(new BasicNameValuePair("money", money));
                httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 18){
                httpPut.setHeader("token", token_user);
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("destacado", destacado));
                httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            else if (peticion_id == 24){
                httpPut.setHeader("token", token_user);
                List nameValuePairs = new ArrayList();
                nameValuePairs.add(new BasicNameValuePair("title", event_title));
                nameValuePairs.add(new BasicNameValuePair("hora_ini", event_hora_ini));
                nameValuePairs.add(new BasicNameValuePair("hora_fin", event_hora_fin));
                nameValuePairs.add(new BasicNameValuePair("fecha_ini", event_fecha_ini));
                nameValuePairs.add(new BasicNameValuePair("fecha_fin", event_fecha_fin));
                nameValuePairs.add(new BasicNameValuePair("precio", event_precio));
                nameValuePairs.add(new BasicNameValuePair("pic", event_pic));
                nameValuePairs.add(new BasicNameValuePair("ciudad", event_ciudad));
                nameValuePairs.add(new BasicNameValuePair("categoria", event_categoria.toString()));
                nameValuePairs.add(new BasicNameValuePair("destacado", destacado));
                nameValuePairs.add(new BasicNameValuePair("descripcion", event_descripcion));
                nameValuePairs.add(new BasicNameValuePair("url", event_url_entradas));
                nameValuePairs.add(new BasicNameValuePair("nreports", nreports));
                nameValuePairs.add(new BasicNameValuePair("direccion", event_direccion));
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

    public static String DELETE(String url){
        InputStream inputStream = null;
        String result = "";
        try{
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse;
            HttpDelete httpDelete = new HttpDelete(url);
            if (peticion_id == 20){
                httpDelete.setHeader("token", token_user);
            }
            else if (peticion_id == 25){
                httpDelete.setHeader("token", token_user);
            }
            httpResponse = httpclient.execute(httpDelete);
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
            if (peticion_id < 10 || peticion_id == 21 || peticion_id == 22 || peticion_id == 23) return GET(urls[0]);
            else if (peticion_id >= 10 && peticion_id < 15) return POST(urls[0]);
            else if ( (peticion_id >= 15 && peticion_id < 20) || peticion_id == 24 || peticion_id == 26) return PUT(urls[0]);
            else return DELETE(urls[0]);
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

    public static void readJsonStreamCalendario(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            //return leerArrayUsuarios(reader);
            //events = leerArrayEventos(reader);
            calendarios = leerArrayCalendario(reader);
        } finally {
            reader.close();
        }
    }

    public static void readJsonStreamComentarios(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            comentarios = leerArrayComentarios(reader);
        } finally {
            reader.close();
        }
    }

    public static void readJsonStreamUsuario(InputStream in) throws IOException {
        //Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            user = leerUsuario(reader);
        } finally {
            reader.close();
        }
    }

    public static void readJsonStreamUsernameSponsor(InputStream in) throws IOException {
        //Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            usuarioSponsor = leerUsernameSponsor(reader);
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

    public static void readJsonStreamEvento (InputStream in) throws  IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            event = leerEvento(reader);
        } finally {
            reader.close();
        }
    }

    public static void readJsonStreamCategorias(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            categories = leerCategorias(reader);

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

    public static List leerArrayCalendario(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList calendarios = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            calendarios.add(leerCalendario(reader));
        }
        reader.endArray();
        return calendarios;
    }

    public static List leerArrayComentarios(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList comentarios = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            comentarios.add(leerComentario(reader));
        }
        reader.endArray();
        return comentarios;
    }

    public static Usuario leerUsuario(JsonReader reader) throws IOException {
        String username = null;
        String mail = null;
        String password = null;
        String pic = null;
        String id = null;
        String saldo = null;
        Boolean isVerified = null;
        Boolean isBanned = null;
        String ciudad = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "username":
                    username = reader.nextString();
                    break;
                case "saldo":
                    saldo = reader.nextString();
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
                case "verified":
                    isVerified = reader.nextBoolean();
                    break;
                case "banned":
                    isBanned = reader.nextBoolean();
                    break;
                case "ciudad":
                    try {
                        ciudad = reader.nextString();
                    } catch (Exception e) {
                        reader.nextNull();
                        ciudad = null;
                    } finally {
                        break;
                    }
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Usuario(username, saldo, mail, password, pic, id, isVerified, isBanned, ciudad);
    }

    public static UsernameSponsor leerUsernameSponsor(JsonReader reader) throws IOException {
        String username = null;
        Boolean sponsor = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "username":
                    username = reader.nextString();
                    break;
                case "sponsor":
                    sponsor = reader.nextBoolean();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new UsernameSponsor(username, sponsor);
    }

    public static Calendario leerCalendario(JsonReader reader) throws IOException {
        Integer eventid = null;
        Integer userid = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "eventid":
                    eventid = reader.nextInt();
                    break;
                case "userid":
                    userid = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Calendario(eventid, userid);
    }

    public static Comentario leerComentario(JsonReader reader) throws IOException {
        Integer eventid = null;
        String text = null;
        Integer userid = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "eventid":
                    eventid = reader.nextInt();
                    break;
                case "text":
                    text = reader.nextString();
                    break;
                case "userid":
                    userid = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Comentario(eventid, text, userid);
    }

    public static Evento leerEvento(JsonReader reader) throws IOException {
        String title = null;
        String id = null;
        String organizerId = null;
        String ciudad = null;
        String pic = null;
        String precio = null;
        String fecha_ini = null;
        String fecha_fin = null;
        String hora_ini = null;
        String hora_fin = null;
        String categoria = null;
        String descripcion = null;
        String direccion = null;
        String url = null;
        String nreports = null; // anadido para el PUT de evento
        Boolean destacado = false; // anadido para el PUT de evento

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
                case "organizerId":
                    organizerId = reader.nextString();
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
                case "categoria":
                    categoria = reader.nextString();
                    break;
                case "descripcion":
                    try {
                        descripcion = reader.nextString();
                    } catch (Exception e) {
                        reader.nextNull();
                        descripcion = null;
                    } finally {
                        break;
                    }
                case "direccion":
                    direccion = reader.nextString();
                    break;
                case "url":
                    url = reader.nextString();
                    break;
                case "nreports":
                    nreports = reader.nextString();
                    break;
                case "destacado":
                    destacado = reader.nextBoolean();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Evento(title, id, organizerId, ciudad, pic, precio, fecha_ini, fecha_fin, hora_ini, hora_fin, categoria,
                descripcion, direccion, url, nreports, destacado);
        //return new Evento(title, id, organizerId, ciudad, pic, precio, fecha_ini, fecha_fin, hora_ini, hora_fin, categoria, descripcion, direccion, url, nreports);
    }

    public static String leerCategorias(JsonReader reader) throws IOException {
        String cats = "";
        String id;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextString();
                    break;
                case "categories":
                    reader.beginArray();
                    Boolean primer = true;
                    while (reader.hasNext()) {
                        if (primer) {
                            cats += reader.nextString();
                            primer = false;
                        } else {
                            cats += "," + reader.nextString();
                        }
                    }
                    reader.endArray();
                    break;
            }
        }
        reader.endObject();
        return cats;
    }
}
