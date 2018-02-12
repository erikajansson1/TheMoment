package com.moment.themoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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

public class ServerCommunication extends AsyncTask<Void, Void, Void> {

    private String json;
    private String newJson;
    private String type;
    private String phpFileName;
    private String serverAdress;

    ServerCommunication(){
        this.json = null;
        this.newJson = null;
        this.type = null;
        this.phpFileName = null;
        this.serverAdress = "http://188.166.91.53/";

    }

    /**
     * Return json object from the class
     * @return
     */
    public String getJson(){
        return this.json;
    }

    public String getNewJson(){
        return this.newJson;
    }

    @Override
    protected Void doInBackground(Void... args0){
        String temp = null;
        try {
            URL url = new URL(this.serverAdress+"tes.php?jsonobj="+json);
            URLConnection sender = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                temp = line;//.concat(line);
            }
            this.newJson = temp;
            reader.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to send JSON to the server, unless you have added a "Type" to your JSON then you
     * should use another function that does this for you.
     * @param sentJson
     * @return
     */
    public String SendToServer(String sentJson) {
        this.json = sentJson;
        doInBackground();
        return this.getNewJson();
    }

    /**
     * Send a request to the server and depending on whatCase to different PHP files. Returns json
     * @param json
     * @param whatCase
     * @return json
     */
    private String requestFromServer(final String json, Integer whatCase){
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
    public Integer SavePlayerToDB(Player player){
        Gson g = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        String type = "saveplayer";
        sendJSON.add(player);
        sendJSON.add(type);
        String sendJson = g.toJson(sendJSON);
        this.json = sendJson;
        String recvJSON = SendToServer(sendJson);
        //TODO convert back what is recieved
        return 0;
    }

    /**
     * When creating a room you should save in the database to be able to start the session and
     * so that people can enter the room, returns id number for room
     * @param room
     * @return ID
     */
    public Integer SaveRoomToDB(Room room) {
        Gson g = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        String type = "saveRoom";
        sendJSON.add(room);
        sendJSON.add(type);
        String sendJson = g.toJson(sendJSON);
        this.json = sendJson;
        String recvJSON = SendToServer(sendJson);
        //TODO convert back what is recieved
        return 0;
    }

    /**
     * Tries to add player to the room, will return room if succeeding.
     * @param player
     * @param roomID
     * @return
     */
    public <T> void JoinRoomInServer(Player player, Integer roomID){
        Gson g = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        String type = "joinRoom";
        sendJSON.add(player);
        sendJSON.add(type);
        String sendJson = g.toJson(sendJSON);
        this.json = sendJson;
        String recvJSON = SendToServer(sendJson);
        //TODO convert back what is recieved
        return;
    }

    /**
     * Sends claim to server, will return true or false depending on if sent.
     * @param player
     * @param room
     * @param claim
     * @return
     */
    public Boolean SendClaimToServer(Player player, Room room, Claim claim){
        Gson g = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        String type = "sendClaim";
        sendJSON.add(player);
        sendJSON.add(type);
        String sendJson = g.toJson(sendJSON);
        this.json = sendJson;
        String recvJSON = SendToServer(sendJson);
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
     * Checks if server with DB is online and responsive
     * @return Boolean
     */
    private void checkConnection() {
        this.phpFileName = "utils.php"
    }
}