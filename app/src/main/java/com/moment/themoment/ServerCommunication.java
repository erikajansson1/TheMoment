package com.moment.themoment;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

public class ServerCommunication implements ServerCommunicationCallback {
    private Activity activity;
    //TODO generic type for interface?
    private JoinRandomRoomCallback joinRandomRoomCallback;
    private ResultPageCallback resultPageCallback;
    private CreateRoomCallback createRoomCallback;
    private WriteClaimCallback writeClaimCallback;
    private VoteOnClaimCallback voteOnClaimCallback;
    private JoinRoomCallback joinRoomCallback;
    private WaitForPlayersCallback waitForPlayersCallback;
    private MainCallback mainCallback;
    private WaitForClaimCallback waitForClaimCallback;

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
    public void savePlayerToDB(Player player, JoinRandomRoomCallback joinRandomRoomCallback) {
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(packager(player), "storeToDB", "storePlayer", this).execute();
    }


    /**
     query function with a callback to jumpToClaim
     */
    //public void claimReady(){
    // this.claimReadyCallback = claimReadyCallback;
    //check if claim is stored in DB
    // new CallServer(packager(player),"storeToDB").execute();

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @param roomID
     * @param createRoomCallback is the callback class, needed for callback in this class.
     */
    public void savePlayerToDB(Player player, int roomID, CreateRoomCallback createRoomCallback) {
        this.createRoomCallback = createRoomCallback;
        new CallServer(packager(player, roomID), "storeToDB", "storePlayer", this).execute();
    }

    /**
     * Saves a player to the database. Should return ID to the user that should be updated on the
     * user to keep track in the communication from there on.
     * @param player
     * @param joinRoomCallback is the callback class, needed for callback in this class.
     */
    public void savePlayerToDB(Player player, JoinRoomCallback joinRoomCallback) {
        this.joinRoomCallback = joinRoomCallback;
        Log.i("sending player", packager(player));
        new CallServer(packager(player), "storeToDB", "storePlayer", this).execute();
    }


    /**
     * Checks if server with DB is online and responsive. If so enables main menu
     * @param mainCallback is the callback class, needed for callback in this class.
     */
    public void checkConnection(MainCallback mainCallback) {
        this.mainCallback = mainCallback;
        new CallServer(null, "utils", "isServerAndDBUp", this).execute();
    }

    /**
     * Calls server for a random room number which server confirms is available.
     * @param playerID is the players id searching for a room.
     * @param joinRandomRoomCallback is the callback class, needed for callback in this class.
     */
    public void getRandomRoom(int playerID, JoinRandomRoomCallback joinRandomRoomCallback) {
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        Log.i("sending playerID", String.valueOf(playerID));
        new CallServer(packager(playerID), "getFromDB", "getRandomRoom", this).execute();
    }

    /**
     * ask server for a complete refresh of room from db
     * @param roomID room to request
     * @param resultPageCallback is the callback class, needed for callback in this class.
     */
    public void updateResultRoom(int roomID, ResultPageCallback resultPageCallback) {
        this.resultPageCallback = resultPageCallback;
        new CallServer(Integer.toString(roomID), "getFromDB", "getRoomByID", this).execute();
    }

    /**
     * Calls server to remove player by id in db
     * @param roomID room player is currently in
     * @param playerID is the id of the player to be removed
     * @param resultPageCallback is the callback class, needed for callback in this class.
     */
    public void removePlayerFromDb(int roomID, int playerID, ResultPageCallback resultPageCallback) {
        this.resultPageCallback = resultPageCallback;
        new CallServer(packager(roomID, playerID), "storeToDB", "removePlayerByID", this).execute();
    }

    public void removePlayerFromDb(int playerID, JoinRandomRoomCallback joinRandomRoomCallback) {
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(packager(playerID), "storeToDB", "removePlayerByID", this).execute();
    }

    public void removePlayerFromDb(int playerID, JoinRoomCallback joinRoomCallback) {
        this.joinRandomRoomCallback = joinRandomRoomCallback;
        new CallServer(packager(playerID), "storeToDB", "removePlayerByID", this).execute();
    }

    /**
     * Calls server to check up room wished to joined in
     * @param roomID room player wishes to join
     * @param playerID is the id of the player
     * @param joinRoomCallback is the callback class, needed for callback in this class.
     */
    public void joinRoom(int roomID, int playerID, JoinRoomCallback joinRoomCallback) {
        this.joinRoomCallback = joinRoomCallback;
        new CallServer(packager(roomID, playerID), "getFromDB", "getFreeRoom", this).execute();
    }


    public void createRoom(CreateRoomCallback createRoomCallback) {
        this.createRoomCallback = createRoomCallback;
        new CallServer(null, "storeToDB", "createRoom", this).execute();
    }

    /**
     * tells the server to update room size in db
     * @param room used to retrieve id and gives to server
     * @param createRoomCallback is the callback class, needed for callback in this class.
     */
    public void updateRoomSize(Room room, CreateRoomCallback createRoomCallback) {
        this.createRoomCallback = createRoomCallback;
        new CallServer(packager(room.getID(), room.getNumOfPlayers()), "storeToDB", "updateRoomSize", this).execute();
    }

    public void updateClaimAndAnswer(Claim claim, WriteClaimCallback writeClaimCallback) {
        this.writeClaimCallback = writeClaimCallback;
        new CallServer(packager(claim), "storeToDB", "updateClaim", this).execute();
    }

    public void newClaimAndAnswer(Claim claim, Player player, WriteClaimCallback writeClaimCallback) {
        this.writeClaimCallback = writeClaimCallback;
        Log.e("newClaimAndAnswer", "newClaimAndAnswer");
        Log.e("packager", packager(claim, player));
        new CallServer(packager(claim, player), "storeToDB", "newClaim", this).execute();

    }

    public void updateScore(int playerID, int newScore, VoteOnClaimCallback voteOnClaimCallback) {
        this.voteOnClaimCallback = voteOnClaimCallback;
        new CallServer(packager(playerID, newScore), "storeToDB", "updatePlayerScore", this).execute();
    }

    public void countPlayers(int currentRoomID, WaitForPlayersCallback waitForPlayersCallback) {
        this.waitForPlayersCallback = waitForPlayersCallback;
        new CallServer(String.valueOf(currentRoomID), "getFromDB", "getRoomByID", this).execute();
    }

    /**
     * Will ask server if all players in the room have entered a claim, is not the callback will
     * return false
     * @param currentRoomID
     * @param waitForClaimCallback
     */
    public void isClaimsDone(int currentRoomID, int round, WaitForClaimCallback waitForClaimCallback) {
        this.waitForClaimCallback = waitForClaimCallback;
        new CallServer(packager(currentRoomID, round), "utils", "isRoundDone", this).execute();
    }

    /**
     * Returns the current claim to be played
     * @param currentRoomID
     * @param waitForClaimCallback
     */
    public void getCurrentClaim(int currentRoomID, WaitForClaimCallback waitForClaimCallback){
        this.waitForClaimCallback = waitForClaimCallback;
        new CallServer(String.valueOf(currentRoomID), "getFromDB", "currentClaim", this).execute();
    }

    /**
     * tells the server to update the round counter for player ID in db.
     * @param player used to retrieve id and gives to server
     * @param resultPageCallback is the callback class, needed for callback in this class.
     */
    public void declareRoundAnswered(Player player, ResultPageCallback resultPageCallback) {
        this.resultPageCallback = resultPageCallback;
        new CallServer(packager(player.getID(), player.getRound()), "storeToDB", "storePlayerRound", this).execute();
    }

    /**
     * asks the server if all players in room is done with their current round.
     * @param roomID to check if everybody is done.
     * @param resultPageCallback is the callback class, needed for callback in this class.
     */
    public void checkIfRoundComplete(int roomID, int round, ResultPageCallback resultPageCallback) {
        this.resultPageCallback = resultPageCallback;
        new CallServer(packager(roomID, round), "utils", "isRoundDone", this).execute();
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
            case "getFreeRoom":
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
            case "updateClaim":
                callBackSetPlayerID(output);
                break;
            case "newClaim":
                callBackUpdatedClaim(output);
                break;
            case "updatePlayerScore":
                callBackSetPlayerID(output);
                break;
            case "currentClaim":
                callBackClaim(output);
                break;
        }
    }

    /**
     * Handles callbacks from the removePlayer call. forwards to correct activity method.
     * @param output boolean deciding outcome
     */
    private void callBackRemovedPlayer(String output) {
        Log.e("Got output to callback", output);
        if (resultPageCallback != null) {
            resultPageCallback.JumptoMainMenu(output);
        }
    }

    /**
     * Handles callbacks from the isRoundDone call. forwards to correct activity method.
     * @param output boolean deciding outcome
     */
    private void callBackIsRoundDone(String output) {
        Log.e("Got output to callback", output);
        if (resultPageCallback != null) {
            resultPageCallback.ifDoneCallRoomUpdate(output);
        }else if (waitForClaimCallback != null) {
            waitForClaimCallback.updateWaitForClaim(output);
        }
    }

    /**
     * Handles callbacks from various response calls. forwards to correct activity method.
     * @param output boolean deciding outcome
     */
    private void callBackResponse(String output) {
        Log.e("Got output to callback", output);
        if (createRoomCallback != null) {
            createRoomCallback.confirmDone(output);
        } else if (resultPageCallback != null) {
            resultPageCallback.checkIfRoundIsFinished(output);
        }
    }

    /**
     * Handles callbacks from the create room call. forwards to correct activity method.
     * @param output string which contains room id
     */
    private void callBackReturnRoomID(String output) {
        Log.e("Got output to callback", output);
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
        Log.e("Got output to callback:", output);
        Gson gson = new Gson();
        Room room = gson.fromJson(output, Room.class);
        if (joinRandomRoomCallback != null) {
            joinRandomRoomCallback.setPlayersRoom(room);
        } else if (resultPageCallback != null) {
            resultPageCallback.updateResultList(room);
        } else if (joinRoomCallback != null) {
            joinRoomCallback.setPlayersRoom(room);
        } else if (waitForPlayersCallback != null) {
            waitForPlayersCallback.updateNbrOfPlayers(room);
        }
    }


    /**
     * handles the servers response in the form of a id
     * @param output is a numerical value in string form
     */
    private void callBackSetPlayerID(String output) {
        Log.e("Got output to callback", output);
        if (output.equals("Failed")) {
            activity.findViewById(R.id.saveUsername).setEnabled(true);
            //TODO Handle Failure
        } else {
            int idToSet = Integer.parseInt(output);
            if (joinRandomRoomCallback != null) {
                joinRandomRoomCallback.setClientPlayerID(idToSet);
            } else if (createRoomCallback != null) {
                createRoomCallback.setClientPlayerID(idToSet);
            } else if (writeClaimCallback != null) {
                writeClaimCallback.goToWaitForClaim();
            } else if (voteOnClaimCallback != null) {
                voteOnClaimCallback.goToResult();
            } else if (joinRoomCallback != null) {
                joinRoomCallback.setClientPlayerID(idToSet);
            }
        }
    }

    /**
     * handles the response from server if its up and running or down
     * @param output true or false in string format
     */
    private void callBackServerStatus(String output) {
        if (output != null) {
            this.mainCallback.unlockMenu();
        } else {
            this.mainCallback.serverNotReachable();
            checkConnection(this.mainCallback);
        }
    }

    private void callBackUpdatedClaim(String output) {
        Log.e("Got output to callback", output);
        if (output.equals("Failed")) {
            Log.i("CallBackUpdatedClaim", "failed!");           //TODO Handle Failure
        } else {
            if (writeClaimCallback != null) {
                writeClaimCallback.goToWaitForClaim();
            }
        }
    }

    private void callBackClaim(String output) {
        if(output.equals("Failed")) {
            Log.i("CallBackClaim", "failed!");           //TODO Handle Failure
        }else{
            Gson gson = new Gson();
            Claim claim = gson.fromJson(output, Claim.class);
            if (waitForClaimCallback != null) {
                waitForClaimCallback.updateCurrentClaim(claim);
            }
        }
    }

}