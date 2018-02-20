package com.moment.themoment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private int ID;
    private int numOfPlayers;
    private ArrayList<Player> playerList;
    private int currentClaimNo;

    /**
     * Constructor
     */
    Room(){
        playerList = new ArrayList<Player>();
        this.currentClaimNo = 0;
    }

    /**
     * constructor
     * @param numOfPlayers which the room will hold
     */
    Room(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        this.currentClaimNo = 0;
    }

    /**
     * constructor
     * @param player whom should be included
     */
    Room(Player player){
        this.playerList = new ArrayList<>();
        this.playerList.add(player);
        this.currentClaimNo = 0;
    }

    /**
     * retrieves the currentClaim to be presented
     * @return the claim based upon currentClaimNo
     */
    public Claim getCurrentClaim() {
        return this.playerList.get(this.currentClaimNo).getClaim();
    }

    /**
     * Increments the currentClaimNo if possible. If its not possible it will revert to zero.
     * This indicates all claims have been presented and a new round of writeClaim should begin.
     * @return boolean depending on if increment was sucesfull or failed.
     */
    public Boolean setNextClaim() {
        if (this.currentClaimNo < (this.numOfPlayers-1)) {
            this.currentClaimNo++;
            return true;
        } else {
            this.currentClaimNo = 0;
            return false;
        }
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

    public int getAmountOfPlayers() {
        int amountOfPlayers = playerList.size();
        return amountOfPlayers;
    }

    /**
     * set method for rooms id
     * @param newID is the id to be set
     */
    public void setID(int newID){
        this.ID = newID;
    }


    /**
     * set method for number of players
     * @param numOfPlayers
     */
    public void setNumOfPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * Adds player to the playerList of the room
     * @param clientPlayer
     */
    public void addPlayer(Player clientPlayer){
        this.playerList.add(clientPlayer);
        return;
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
