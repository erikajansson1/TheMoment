package com.moment.themoment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

public class ServerCommunication implements AsyncServerCall {
    private Activity activity;

    ServerCommunication(Activity activity) {
        this.activity = activity;
    }
    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @return ID
     */
    /*
    public Integer savePlayerToDB(Player player){
        Integer playerID = (Integer) queryServer("savePlayer", player);
        return playerID;
    }
    */
    /**
     * When creating a room you should save in the database to be able to start the session and
     * so that people can enter the room, returns id number for room
     * @param room
     * @return ID
     */
    /*
    public Integer saveRoomToDB(Room room) {
        Integer roomID = (Integer) queryServer("saveRoom", room);
        return roomID;
    }
    */
    /**
     * Tries to add player to the room, will return room if succeeding.
     * @param player
     * @param roomID
     * @return
     */
    /*
    public Room joinRoomInServer(Player player, Integer roomID){
        Room room = (Room) queryServer("joinRoom", roomID, player);
        String roomResult = sendToServer();
        if(roomResult.equals("Failed")){
            return null;
        }
        else{
            return roomResult;
        }
        //TODO, handle if the person is not allowed to join the room
        //return room;
    }
    */
    /**
     * Sends claim to server, will return true or false depending on if sent.
     * @param player
     * @param room
     * @param claim
     * @return
     */
    /*
    public Boolean sendClaimToServer(Player player, Room room, Claim claim){
        //TODO room should hold players and indicate locally which player is client's
        Boolean succes = (Boolean) queryServer("sendClaim",room);
        return false;
    }
    */
    /**
     * Creates a JSON holding both function call and necessary objects in objectV
     * @param function
     * @param objectV
     * @return returns object from server response
     */
    /*
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
    */
    /**
     * Send request to server to respond a update of the room.
     * @param player
     * @param room
     * @return room
     */
    /*
    public Room updateRoomRequest(Player player, Room room){
        //TODO update the room information.
        return null;
    }
    */
    /**
     * Checks if server with DB is online and responsive. If so enables main menu
     * @param context
     */

    public void checkConnection(Context context) {
        new CallServer(null,"utils","isServerAndDBUp",this).execute();
        /*
        if(checkConnectionAction()) {
            JoinRoomBtn.setEnabled(true);
            JRRBtn.setEnabled(true);
            CreateRoomBtn.setEnabled(true);
        } else {
           // checkConnectionActionRetry(context, handler);
        }
        return;
        */
    }

    /**
     * Tries to reconnect to the server. With a delay on 8 sec.
     * @param context
     * @param handler
     */
    /*
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
    */
    /**
     * Asks the server if its up and running and if the database is responding.
     * @return Boolean
     */
    /*
    private Boolean checkConnectionAction() {
        this.phpFileName = "utils";
        this.phpFunction = "isServerAndDBUp";
        String response = sendToServer();
        Log.e("2 - DB says",response);
        if (response.equals("True")) {
            return true;
        } else {
            return false;
        }
    }
    */
/*
    private void unlockMainMenu(String result) {
        if(result.equals("True")) {
            Button JoinRoomBtn = findViewById(R.id.JoinRoom);
            Button JRRBtn = findViewById(R.id.JRR);
            Button CreateRoomBtn = findViewById(R.id.CreateRoom);
            JoinRoomBtn.setEnabled(true);
            JRRBtn.setEnabled(true);
            CreateRoomBtn.setEnabled(true);
        } else {
            // checkConnectionActionRetry(context, handler);
        }
        return;
    }
*/
    @Override
    public void processFinish(String function, String output) {
        switch (function) {
            case "isServerAndDBUp":
                processServerStatus(output);
        }
        return;
    }

    private void processServerStatus(String output) {
        Button JoinRoomBtn = activity.findViewById(R.id.JoinRoom);
        Button JRRBtn = activity.findViewById(R.id.JRR);
        Button CreateRoomBtn = activity.findViewById(R.id.CreateRoom);
        JoinRoomBtn.setEnabled(true);
        JRRBtn.setEnabled(true);
        CreateRoomBtn.setEnabled(true);
    }
}