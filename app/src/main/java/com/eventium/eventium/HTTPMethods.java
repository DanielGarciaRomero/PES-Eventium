package com.eventium.eventium;

import android.os.AsyncTask;
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
        else if (peticion_id == 10) new HttpAsyncTask().execute("http://10.4.41.168:5000/user"); //post de un user
    }

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
                httpGet.setHeader("clave", "alvaroma94");
                httpResponse = httpclient.execute(httpGet);
            }
            else httpResponse = httpclient.execute(httpGet);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            resultado_json = inputStream;

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
            Toast.makeText(MainActivity.contexto, "Received!", Toast.LENGTH_LONG).show();
        }
    }

}
