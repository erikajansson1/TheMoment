package com.moment.themoment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Dess on 2018-02-06.
 */

public class ServerCommunication {
/**
    String temp;
    Context c;
    Handler h = new Handler();
    ServerCommunication(Context c) {
        this.c = c;
    }
    public Void SendToServer(final String json) {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.41/tes.php?jsonobj="+json);
                    URLConnection con = url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        temp += line;
                    }
                    rd.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(c, "Calculating result...", Toast.LENGTH_SHORT).show();
                Toast.makeText(c, "Here: " + temp, Toast.LENGTH_SHORT).show();
            };
        };

        return null;
   */
    }
    /**
     * This is taken from http://hmkcode.com/android-send-json-data-to-server/ just to makes tests
     * @param inputStream
     * @return
     * @throws IOException
     */
/**
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
}
*/