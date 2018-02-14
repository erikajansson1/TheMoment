package com.moment.themoment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ServerCommunication implements ServerCommunicationCallback {
    private Activity activity;
    private Context context;
    //TODO what the hell is the generic type for interface callbacks?
    private JoinRandomRoomCallback joinRandomRoomCallback;

    /**
     * Constructor
     * @param activity
     */
    ServerCommunication(Activity activity) {
        this.activity = activity;
    }

    /**
     * Creates a JSON from one or multiple objects given.
     * @param objectV
     * @return returns JSON
     */
    private String packager(Object... objectV) {
        Gson g = new Gson();
        List<Object> objectList = new ArrayList<Object>();
        for (Object object: objectList) {
            objectList.add(object);
        }
        String sendJson = g.toJson(objectList);
        return sendJson;
    }

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @return ID
     */
    public void savePlayerToDB(Player player, JoinRandomRoomCallback joinRandomRoomCallback){
        String jsonString = packager(player);
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(jsonString,"storeToDB","storePlayer",this).execute();
        return;
    }

    /**
     * Checks if server with DB is online and responsive. If so enables main menu
     * @param context
     */
    public void checkConnection(Context context) {
        this.context = context;
        new CallServer(null,"utils","isServerAndDBUp",this).execute();
        return;
    }


    /**
     * ------------------ CALLBACKS BELOW -------------------------
     */

    /**
     *
     * @param function
     * @param output
     */
    @Override
    public void processFinish(String function, String output) {
        switch (function) {
            case "isServerAndDBUp":
                callBackServerStatus(output);
                break;
            case "storePlayer":
                callBackSetPlayerID(output);
        }
        return;
    }

    /**
     *
     * @param output
     */
    private void callBackSetPlayerID(String output) {
        Log.e("Got output to callback",output);
        if (output.equals("Failed")) {
            activity.findViewById(R.id.saveUsername).setEnabled(true);
            //TODO Handle Failure
        } else {
            int idToSet = Integer.parseInt(output);
            joinRandomRoomCallback.setClientPlayerID(idToSet);
        }
    }

    /**
     *
     * @param output
     */
    private void callBackServerStatus(String output) {
        if (output.equals("True")) {
            activity.findViewById(R.id.JoinRoom).setEnabled(true);
            activity.findViewById(R.id.JRR).setEnabled(true);
            activity.findViewById(R.id.CreateRoom).setEnabled(true);
        } else {
            Toast.makeText(context, "Server connection failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Reconnecting...", Toast.LENGTH_SHORT).show();
            checkConnection(context);
        }
    }

    /**
     * ------------ Old and in need for redefintion methods below ------------
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

}