package com.moment.themoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

public class ServerCommunication extends AsyncTask<Void, Void, Boolean> {

    private String json;
    private String serverResponse;
    private String type;
    private String phpFileName;
    private String serverAdress;
    private String phpFunction;

    ServerCommunication(){
        this.json = null;
        this.serverResponse = null;
        this.type = null;
        this.phpFileName = null;
        this.phpFunction = null;
        this.serverAdress = "http://188.166.91.53/";

    }

    /**
     * Return json object from the class
     * No clue why this exists
     * @return String
     */
    public String getJson(){
        return this.json;
    }

    /**
     * No clue why this exists
     * @return serverResponse
     */
    public String getNewJson(){
        return this.serverResponse;
    }

    @Override
    protected Boolean doInBackground(Void... args0){
        String temp = null;
        URL url = null;
        try {
            if(this.json != null) {
                // Use & sign in get methods to glue variables.
                url = new URL(this.serverAdress+this.phpFileName+".php?function="+this.phpFunction+"&jsonobj="+json);
            } else {
                url = new URL(this.serverAdress+this.phpFileName+".php?function="+this.phpFunction);
            }
            URLConnection sender = url.openConnection();
            InputStreamReader stream = new InputStreamReader(sender.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            String line;
            while ((line = reader.readLine()) != null) {
                temp = temp.concat(line);
            }
            this.serverResponse = temp;
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        this.json = null;
        this.type = null;
        this.phpFileName = null;
        this.phpFunction = null;

        return true;
    }

    /**
     * Method to send JSON to the server, unless you have added a "Type" to your JSON then you
     * should use another function that does this for you.
     * @return
     */
    private String sendToServer() {
        if(doInBackground()) {
            return this.serverResponse;
        } else {
            return "Failed";
        }
    }

    /**
     * Send a request to the server and depending on whatCase to different PHP files. Returns json
     * @param json
     * @param whatCase
     * @return json
     */
    private String requestFromServer(final String json, Integer whatCase){
        //TODO: Is this needed?
        execute();
        doInBackground();
        //TODO create json here
        //TODO request server for response
        return null;
    }

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @return ID
     */
    public Integer savePlayerToDB(Player player){
        Integer playerID = (Integer) queryServer("savePlayer", player);
        return playerID;
    }

    /**
     * When creating a room you should save in the database to be able to start the session and
     * so that people can enter the room, returns id number for room
     * @param room
     * @return ID
     */
    public Integer saveRoomToDB(Room room) {
        Integer roomID = (Integer) queryServer("saveRoom", room);
        return roomID;
    }

    /**
     * Tries to add player to the room, will return room if succeeding.
     * @param player
     * @param roomID
     * @return
     */
    public Room joinRoomInServer(Player player, Integer roomID){
        Room room = (Room) queryServer("joinRoom", roomID, player);
        return room;
    }

    /**
     * Sends claim to server, will return true or false depending on if sent.
     * @param player
     * @param room
     * @param claim
     * @return
     */
    public Boolean sendClaimToServer(Player player, Room room, Claim claim){
        //TODO room should hold players and indicate locally which player is client's
        Boolean succes = (Boolean) queryServer("sendClaim",room);
        return false;
    }

    /**
     * Creates a JSON holding both function call and necessary objects in objectV
     * @param function
     * @param objectV
     * @return returns object from server response
     */
    private Object queryServer(String function, Object... objectV) {
        Gson g = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        sendJSON.add(function);
        for (Object object: sendJSON) {
            sendJSON.add(object);
        }
        String sendJson = g.toJson(sendJSON);
        this.json = sendJson;
        String recvJSON = sendToServer();
        //TODO convert back what is recieved
        return false;
    }

    /**
     * Send request to server to respond a update of the room.
     * @param player
     * @param room
     * @return room
     */
    public Room updateRoomRequest(Player player, Room room){
        //TODO update the room information.
        return null;
    }

    /**
     * Checks if server with DB is online and responsive. If so enables main menu
     * @param JoinRoomBtn
     * @param JRRBtn
     * @param CreateRoomBtn
     * @param context
     * @return Boolean depending on if server is up and running or not
     */
    public Boolean checkConnection(final Button JoinRoomBtn, final Button JRRBtn, final Button CreateRoomBtn,final Context context) {
        int x = 0;
        final Handler handler = new Handler();

        if(checkConnectionAction()) {
            JoinRoomBtn.setEnabled(true);
            JRRBtn.setEnabled(true);
            CreateRoomBtn.setEnabled(true);
            return true;
        } else {
            checkConnectionActionRetry(context, handler);
        }
        return false;
    }

    /**
     * Tries to reconnect to the server. With a delay on 8 sec.
     * @param context
     * @param handler
     */
    private void checkConnectionActionRetry(final Context context, final Handler handler) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Server connection failed", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Reconnecting...", Toast.LENGTH_SHORT).show();
                if(!checkConnectionAction()) {
                    // Not a good solution since it will consume memory slowly but steadily.
                    checkConnectionActionRetry(context,handler);
                }
                return;
            }
        }, 8000);
        return;
    }

    /**
     * Asks the server if its up and running and if the database is responding.
     * @return Boolean
     */
    private Boolean checkConnectionAction() {
        this.phpFileName = "utils.php";
        this.phpFunction = "isServerAndDBUp";
        String response = sendToServer();
        if (response.equals("True")) {
            return true;
        } else {
            return false;
        }
    }
}