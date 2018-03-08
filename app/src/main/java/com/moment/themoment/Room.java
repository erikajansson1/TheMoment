package com.moment.themoment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private int id;
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
     * get method for currentClaimNo
     * @return int, currentClaimNo
     */
    public int getCurrentClaimNo() {
        return this.currentClaimNo;
    }

    /**
     * set method for currentClaimNo
     * @param currentClaimNo int to set
     */
    public void setCurrentClaimNo(int currentClaimNo) {
        this.currentClaimNo = currentClaimNo;
    }

    /**
     * Increments the currentClaimNo if possible. If its not possible it will revert to zero.
     * This indicates all claims have been presented and a new round of writeClaim should begin.
     * @return boolean depending on if increment was sucesfull or failed.
     */
    public Boolean setNextClaim() {
        if (this.currentClaimNo < (this.playerList.size()-1)) {
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
        return this.id;
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
        this.id = newID;
    }


    /**
     * set method for number of players
     * @param numOfPlayers int to set
     */
    public void setNumOfPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * Adds player to the playerList of the room
     * @param clientPlayer to add to playerList
     */
    public void addPlayer(Player clientPlayer){
        this.playerList.add(clientPlayer);
        return;
    }

    /**
     * Checks what the lowest round currently is in this game by checking other players rounds
     * @return int that is lowest round
     */
    public int getLowestRound( ){
        int currLow = playerList.get(0).getRound();
        for (int i = 0; i < this.playerList.size(); i++){
            if(this.playerList.get(i).getIsPlayer() && this.playerList.get(i).getRound() < currLow) {
                    currLow = this.playerList.get(i).getRound();
            }
        }
    return currLow;
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
