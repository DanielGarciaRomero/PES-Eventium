package com.eventium.eventium;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPMethods {

    public static String resultado;
    public static Boolean finished;
    public static Integer user_id;
    public static Integer peticion_id;

    public HTTPMethods(Integer id){
        finished = false;
        peticion_id = id;
    }

    public void ejecutarHttpAsyncTask(){
        if (peticion_id == 0) new HttpAsyncTask().execute("http://10.4.41.168:5000/users"); //get de users
        else if (peticion_id == 1) new HttpAsyncTask().execute("http://10.4.41.168:5000/users/" + user_id.toString()); //get de un user
        else if (peticion_id == 2) new HttpAsyncTask().execute("http://10.4.41.168:5000/mail"); //recuperar contraseña
    }

    public String getResultado(){
        return resultado;
    }

    public Boolean getFinished(){
        return finished;
    }

    public void setUser_id(Integer id){
        user_id = id;
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
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.contexto, "Received!", Toast.LENGTH_LONG).show();
        }
    }

}
