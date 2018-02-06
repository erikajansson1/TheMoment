package com.moment.themoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import org.json.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Desiree on 2018-01-28.
 */


public class BackgroundTask extends AsyncTask<Void, Void,Void> {
    JSONObject obj;
    String temp;
    Context c;
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
        /*
        final JSONObject rjson = new JSONObject();
        try {
            rjson.put("gid","asd");
            rjson.put("uid","asdd");
            rjson.put("name","assgd");
            rjson.put("phone","agssd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
        String url = "http://192.168.1.41/tes.php";
        //String url = "http://192.168.1.41/tes.php?rjson=\"+json";
        try
        {
            URL myurl = new URL(url);
            /*
            HttpURLConnection hue = (HttpURLConnection)myurl.openConnection();
            hue.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(hue.getInputStream()));
            */
            final String json = "{\"name\":\"Fredrik\",\"answer\":\"dinmamma\"}";
            URL url2 = new URL("http://130.243.190.220/tes.php?jsonobj="+json);
            URLConnection conn = url2.openConnection();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                //obj.put("Dunno",line);
                temp = line;
                //System.out.println(line);
            }
            rd.close();

            //value = reader.readLine();
            h.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(c, "Calculating result...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(c, "Answer = " + temp, Toast.LENGTH_LONG).show();
                    Toast.makeText(c, "Data Saved in server...", Toast.LENGTH_SHORT).show();
              //      Toast.makeText(c, "Value = " + value, Toast.LENGTH_SHORT).show();
                    Toast.makeText(c, "Current json send: " + json, Toast.LENGTH_SHORT).show();
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


