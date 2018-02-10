package com.moment.themoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//TODO: Add that you send with the JSON what type the Json is.
public class BackgroundTask extends AsyncTask<Void, Void,Void> {
    JSONObject obj;
    String temp;
    Context c;
    String superguy;
    String json;
    String value;
    Handler h = new Handler();
    BackgroundTask(Context c)
    {
        this.c = c;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... Voids){
        //String url = "http://192.168.1.41/tes.php";
        try
        {
            //Make the JSON more generic
            Player guy = new Player("Fredrik");
            Gson g = new Gson();
            superguy = g.toJson(guy); //This makes an JSON string of whatever object you give

            URL url2 = new URL("http://188.166.91.53/test.php?jsonobj="+superguy);
            //URL url2 = new URL("http://192.168.1.41/tes.php?jsonobj="+superguy);
            URLConnection conn = url2.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                temp = line;
            }
            rd.close();
            h.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(c, "Calculating result...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(c, "Answer = " + temp, Toast.LENGTH_LONG).show();
                    Toast.makeText(c, "Data Saved in server...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}