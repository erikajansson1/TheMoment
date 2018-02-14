package com.moment.themoment;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CallServer extends AsyncTask<Void, Void, String> {
    private ServerCommunicationCallback callback;
    private String json;
    private String phpFileName;
    private String serverAdress;
    private String phpFunction;

    CallServer(String json, String phpFileName, String phpFunction, ServerCommunicationCallback callback){
        this.callback = callback;
        this.json = json;
        this.phpFileName = phpFileName;
        this.phpFunction = phpFunction;
        this.serverAdress = "http://188.166.91.53/";

    }

    @Override
    protected String doInBackground(Void... args0){
        String temp = "";
        URL url = null;
        try {
            if(this.json == null) {
                url = new URL(this.serverAdress+this.phpFileName+".php?function="+this.phpFunction);
                Log.e("Call:",url.getHost());
                Log.e("query: ",url.getQuery());
                Log.e("query: ",url.toString());
            } else {
                // Use "&" sign in GET methods to glue variables.
                url = new URL(this.serverAdress+this.phpFileName+".php?function="+this.phpFunction+"&jsonobj="+json);
            }
            URLConnection sender = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.e("DB says",sb.toString());
            reader.close();
            return sb.toString();
        }catch(Exception e) {
            Log.e("ExceptionType:",e.getClass().getCanonicalName());
            e.printStackTrace();

        }
    return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // pass the result to the callback function
        this.callback.processFinish(this.phpFunction,result);
    }
}
