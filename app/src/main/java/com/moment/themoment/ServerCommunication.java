package com.moment.themoment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
    private JoinRoomCallback joinRoomCallback;
    private WaitForPlayersActivityCallback waitForPlayersActivityCallback;

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
     * @param joinRandomRoomCallback is the callback class, needed for callback in this class.
     */
    public void savePlayerToDB(Player player, JoinRandomRoomCallback joinRandomRoomCallback){
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(packager(player),"storeToDB","storePlayer",this).execute();
    }

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @param roomID
     * @param roomSize
     * @param createRoomCallback
     */
    //TODO Make savePlayerToDB generic so don't matter what callback
    public void savePlayerToDB(Player player, int roomID, int roomSize, CreateRoomCallback createRoomCallback){
        this.createRoomCallback = createRoomCallback;
        new CallServer(packager(player,roomID,roomSize),"storeToDB","storePlayer",this).execute();
    }

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @param joinRoomCallback
     */
    public void savePlayerToDB(Player player, JoinRoomCallback joinRoomCallback){
        this.joinRoomCallback = joinRoomCallback;
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

    /**
     * ask server for a complete refresh of room from db
     * @param roomID room to request
     * @param resultPageActivityCallback is the callback class, needed for callback in this class.
     */
    public void updateResultRoom(int roomID,ResultPageActivityCallback resultPageActivityCallback) {
        this.resultPageActivityCallback = resultPageActivityCallback;
        new CallServer(Integer.toString(roomID),"getFromDB","getRoomByID",this).execute();
    }

    /**
     * Calls server to remove player by id in db
     * @param roomID room player is currently in
     * @param playerID is the id of the player to be removed
     * @param resultPageActivityCallback is the callback class, needed for callback in this class.
     */
    public void removePlayerFromDb(int roomID, int playerID, ResultPageActivityCallback resultPageActivityCallback) {
        this.resultPageActivityCallback = resultPageActivityCallback;
        new CallServer(packager(roomID,playerID),"storeToDB","removePlayerByID",this).execute();
    }

    public void joinRoom(int roomID, JoinRoomCallback joinRoomCallback){
        this.joinRoomCallback = joinRoomCallback;
        new CallServer(null, "getFromDB", "getRoomByID", this).execute();
    }

    public void createRoom(CreateRoomCallback createRoomCallback){
        this.createRoomCallback = createRoomCallback;
        new CallServer(null, "storeToDB", "createRoom", this).execute();
    }

    /**
     * tells the server to update room size in db
     * @param room used to retrieve id and gives to server
     * @param createRoomCallback is the callback class, needed for callback in this class.
     */
    public void updateRoomSize(Room room, CreateRoomCallback createRoomCallback){
        this.createRoomCallback = createRoomCallback;
        new CallServer(packager(room.getID(),room.getNumOfPlayers()), "storeToDB", "updateRoomSize", this).execute();
    }

    public void saveClaimAndAnswer(Player player, WriteClaimCallback writeClaimCallback){
        this.writeClaimCallback = writeClaimCallback;
        new CallServer(packager(player), "storeToDB", "storePlayer", this).execute();
    }

    public void updateScore(Player player, VoteOnClaimCallback voteOnClaimCallback){
        this.voteOnClaimCallback = voteOnClaimCallback;
        new CallServer(packager(player), "storeToDB", "storePlayer", this).execute();
    }

    public void countPlayers(Room currentRoom, WaitForPlayersActivityCallback waitForPlayersActivityCallback) {
        this.waitForPlayersActivityCallback = waitForPlayersActivityCallback;
        new CallServer(packager(currentRoom), "getFromDB", "getRoomByID", this).execute();
    }

    /**
     * tells the server to update the round counter for player ID in db.
     * @param player used to retrieve id and gives to server
     * @param resultPageActivityCallback is the callback class, needed for callback in this class.
     */
    public void declareRoundAnswered(Player player, ResultPageActivityCallback resultPageActivityCallback) {
        this.resultPageActivityCallback = resultPageActivityCallback;
        new CallServer(packager(player.getID(),player.getRound()),"storeToDB","storePlayerRound",this).execute();
    }

    /**
     * asks the server if all players in room is done with their current round.
     * @param roomID to check if everybody is done.
     * @param resultPageActivityCallback  is the callback class, needed for callback in this class.
     */
    public void checkIfRoundComplete(int roomID, int round, ResultPageActivityCallback resultPageActivityCallback) {
        this.resultPageActivityCallback = resultPageActivityCallback;
        new CallServer(packager(roomID,round),"utils","isRoundDone",this).execute();
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
                callBackReturnRoomID(output);
                break;
            case "updateRoom":
                callBackReturnRoom(output);
                break;
            case "updateRoomSize":
                callBackResponse(output);
                break;
            case "storePlayerRound":
                callBackResponse(output);
                break;
            case "isRoundDone":
                callBackIsRoundDone(output);
                break;
            case "removePlayerInRoom":
                callBackRemovedPlayer(output);
                break;
        }
    }

    /**
     * Handles callbacks from the removePlayer call. forwards to correct activity method.
     * @param output boolean deciding outcome
     */
    private void callBackRemovedPlayer(String output) {
        Log.e("Got output to callback",output);
        if (resultPageActivityCallback != null) {
            resultPageActivityCallback.JumptoMainMenu(output);
        }
    }

    /**
     * Handles callbacks from the isRoundDone call. forwards to correct activity method.
     * @param output boolean deciding outcome
     */
    private void callBackIsRoundDone(String output) {
        Log.e("Got output to callback",output);
        if (resultPageActivityCallback != null) {
            resultPageActivityCallback.ifDoneCallRoomUpdate(output);
        }
    }

    /**
     * Handles callbacks from various response calls. forwards to correct activity method.
     * @param output boolean deciding outcome
     */
    private void callBackResponse(String output) {
        Log.e("Got output to callback",output);
        if (createRoomCallback != null) {
            createRoomCallback.confirmDone(output);
        } else if (resultPageActivityCallback != null) {
            resultPageActivityCallback.checkIfRoundIsFinished(output);
        }
    }

    /**
     * Handles callbacks from the create room call. forwards to correct activity method.
     * @param output string which contains room id
     */
    private void callBackReturnRoomID(String output) {
        Log.e("Got output to callback",output);
        int idToSet = Integer.parseInt(output);
        if (createRoomCallback != null) {
            createRoomCallback.setRoomID(idToSet);
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
            //createRoomCallback.setPlayersRoom(room);
        }else if (joinRoomCallback != null) {
            joinRoomCallback.setPlayersRoom(room);
        }else if (waitForPlayersActivityCallback != null) {
            waitForPlayersActivityCallback.updateNbrOfPlayers(room);
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
            }else if(writeClaimCallback != null){
                writeClaimCallback.goToWaitForClaim();
            }else if(voteOnClaimCallback != null){
                voteOnClaimCallback.goToResult();
            }else if(joinRoomCallback != null){
                joinRoomCallback.setClientPlayerID(idToSet);
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
}