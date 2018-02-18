package com.moment.themoment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

public class ServerCommunication implements ServerCommunicationCallback {
    private Activity activity;
    private Context context;
    //TODO what the hell is the generic type for interface callbacks?
    private JoinRandomRoomCallback joinRandomRoomCallback;
    private ResultPageActivityCallback resultPageActivityCallback;
    private CreateRoomCallback createRoomCallback;
    private WriteClaimCallback writeClaimCallback;
    private VoteOnClaimCallback voteOnClaimCallback;

    /**
     * Constructor
     * @param activity from where its created
     */
    ServerCommunication(Activity activity) {
        this.activity = activity;
    }

    /**
     * Creates a JSON from one or multiple objects given.
     * @param objectV a vector of objects which are to be serialised
     * @return returns JSON
     */
    private String packager(Object... objectV) {
        Gson g = new Gson();
        return g.toJson(objectV);
    }

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player whom are to be save to DB
     */
    public void savePlayerToDB(Player player, JoinRandomRoomCallback joinRandomRoomCallback){
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(packager(player),"storeToDB","storePlayer",this).execute();
    }

    //TODO Make savePlayerToDB generic so don't matter what callback
    public void savePlayerToDBCreateRoom(Player player, CreateRoomCallback createRoomCallback){
        this.createRoomCallback = createRoomCallback;
        new CallServer(packager(player),"storeToDB","storePlayer",this).execute();
    }


    /**
     * Checks if server with DB is online and responsive. If so enables main menu
     * @param context c
     */
    public void checkConnection(Context context) {
        this.context = context;
        new CallServer(null,"utils","isServerAndDBUp",this).execute();
    }

    /**
     * Calls server for a random room number which server confirms is available.
     * @param joinRandomRoomCallback is the callback class, needed for callback in this class.
     */
    public void getRandomRoom(JoinRandomRoomCallback joinRandomRoomCallback) {
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(null,"getFromDB","getRandomRoom",this).execute();
    }

    public void updateResultRoom(int roomID,ResultPageActivityCallback resultPageActivityCallback) {
        this.resultPageActivityCallback = resultPageActivityCallback;
        new CallServer(Integer.toString(roomID),"getFromDB","getRoomByID",this).execute();
    }

    public void createRoom(CreateRoomCallback createRoomCallback){
        this.createRoomCallback = createRoomCallback;
        new CallServer(null, "storeToDB", "createRoom", this).execute();
    }

    //Should only be used in creation of room to add number of players and, might be deleted later depending on php
    public void updateRoom(Room room, CreateRoomCallback createRoomCallback){
        this.createRoomCallback = createRoomCallback;
        new CallServer(packager(room), "storeToDB", "updateRoom", this).execute();
    }

    public void saveClaimAndAnswer(Player player, WriteClaimCallback writeClaimCallback){
        this.writeClaimCallback = writeClaimCallback;
        new CallServer(packager(player), "storeToDB", "storePlayer", this).execute();
    }

    public void updateScore(Player player, VoteOnClaimCallback voteOnClaimCallback){
        this.voteOnClaimCallback = voteOnClaimCallback;
        new CallServer(packager(player), "storeToDB", "storePlayer", this).execute();
    }
    /*
     * ------------------ CALLBACKS BELOW -------------------------
     */

    /**
     * callback method which switches to correct handling method depending on the original caller.
     * @param function which was called server side
     * @param output answer from server
     */
    @Override
    public void processFinish(String function, String output) {
        switch (function) {
            case "isServerAndDBUp":
                callBackServerStatus(output);
                break;
            case "storePlayer":
                callBackSetPlayerID(output);
                break;
            case "getRandomRoom":
                callBackReturnRoom(output);
                break;
            case "getRoomByID":
                callBackReturnRoom(output);
                break;
            case "createRoom":
                callBackReturnRoom(output);
                break;
            case "updateRoom":
                callBackReturnRoom(output);
        }
    }

    /**
     * Handles the recieved room from server
     * @param output is a JSON string containing room, player and claims
     */
    private void callBackReturnRoom(String output) {
        Log.e("Got output to callback",output);
        Gson gson = new Gson();
        Room room = gson.fromJson(output, Room.class);
        if (joinRandomRoomCallback != null) {
            joinRandomRoomCallback.setPlayersRoom(room);
        } else if (resultPageActivityCallback != null) {
            resultPageActivityCallback.updateResultList(room);
        }else if (createRoomCallback != null) {
            createRoomCallback.setPlayersRoom(room);
        }
    }


    /**
     * handles the servers response in the form of a id
     * @param output is a numerical value in string form
     */
    private void callBackSetPlayerID(String output) {
        Log.e("Got output to callback",output);
        if (output.equals("Failed")) {
            activity.findViewById(R.id.saveUsername).setEnabled(true);
            //TODO Handle Failure
        } else {
            int idToSet = Integer.parseInt(output);
            if(joinRandomRoomCallback != null) {
                joinRandomRoomCallback.setClientPlayerID(idToSet);
            }else if(createRoomCallback != null){
                createRoomCallback.setClientPlayerID(idToSet);
            }
                else if(writeClaimCallback != null){
                writeClaimCallback.goToWaitForClaim();
            }
                 else if(voteOnClaimCallback != null){
                    voteOnClaimCallback.goToResult();
            }
        }
    }

    /**
     * handles the response from server if its up and running or down
     * @param output true or false in string format
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



    /*
     * ------------ Old and in need for redefintion methods below ------------
     */

    /**
     * When creating a room you should save in the database to be able to start the session and
     * so that people can enter the room, returns id number for room
     * @param room v
     * @return ID v
     */
    /*
    public Integer saveRoomToDB(Room room) {
        Integer roomID = (Integer) queryServer("saveRoom", room);
        return roomID;
    }
    */
    /**
     * Tries to add player to the room, will return room if succeeding.
     * @param player v
     * @param roomID v
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
     * @param player v
     * @param room v
     * @param claim v
     * @return
     */
    /*
    public Boolean sendClaimToServer(Player player, Room room, Claim claim){
        //TODO room should hold players and indicate locally which player is client's
        Boolean succes = (Boolean) queryServer("sendClaim",room);
        return false;
    }
    */



}