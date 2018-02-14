package com.moment.themoment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private int ID;
    private int numOfPlayers;
    private ArrayList<Player> playerList;

    /**
     * Constructor
     */
    Room(){
    }

    /**
     * constructor
     * @param numOfPlayers which the room will hold
     */
    Room(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * constructor
     * @param player whom should be included
     */
    Room(Player player){
        this.playerList = new ArrayList<>();
        this.playerList.add(player);
    }

    /**
     * get method for room id
     * @return numerical id
     */
    public int getID(){
        return this.ID;
    }

    /**
     * get method for rooms number of players
     * @return in describing number of players
     */
    public int getNumOfPlayers(){
        return this.numOfPlayers;
    }

    /**
     * get method for rooms player list
     * @return the playerlist
     */
    public List<Player> getPlayerList(){
        return this.playerList;
    }

    /**
     * set method for rooms id
     * @param newID is the id to be set
     */
    public void setID(int newID){
        this.ID = newID;
    }

    /**
     * replaces clients player object in playerlist as for not having duplicates
     * @param clientPlayer is the clients player object
     */
    public void replaceCurrPlayer(Player clientPlayer) {
        int playerID = clientPlayer.getID();
        for (int i = 0; i < this.playerList.size(); i++) {
            if (playerID == playerList.get(i).getID()) {
                playerList.set(i,clientPlayer);
                playerList.get(i).setIsPlayer(true);
            } else {
                playerList.get(i).setIsPlayer(false);
            }
        }
    }
}
