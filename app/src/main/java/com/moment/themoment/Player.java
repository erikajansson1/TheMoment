package com.moment.themoment;

import java.io.Serializable;

public class Player implements Serializable {
    private int id;
    private String name;
    private int score;
    private Boolean answer;
    private Claim claim;
    private Boolean isPlayer;
    private int round;

    Player(String name){
        this.name = name;
        this.answer = false;
        this.score = 0;
        this.claim = null;
        this.isPlayer = true;
        this.round = 0;
    }

    /**
     * increment method for the round value.
     * Meant to be used by resutlt page to increment the round counter if needed.
     */
    public void incrementRound () {
        this.round++;
    }

    /**
     * set method for round
     * @param val is the value to be set
     */
    public void setRound(int val) {
        this.round = val;
    }

    /**
     * get method for round.
     * @return this.round.
     */
    public int getRound() {
        return this.round;
    }

    /**
     * get method for player name
     * @return name of player
     */
    public String getName(){return this.name; }

    /**
     * get method for attribute isPlayer
     * @return this.isPlayer boolean
     */
    public Boolean getIsPlayer() {
        return this.isPlayer;
    }

    /**
     * get method for player for answer
     * @return answer player has choosen
     */
    public Boolean getAnswer(){
        return this.answer;
    }

    /**
     * get method for player for score
     * @return players current score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * method for updating players score
     * @param newScore to update the player with
     */
    public void updateScore(int newScore) {
        this.score = newScore;
    }

    /**
     * get method for player claim
     * @return claim currently using
     */
    public Claim getClaim(){
        return this.claim;
    }

    /**
     * get method for player id
     * @return player id
     */
    public int getID() { return this.id; }

    /**
     * set method for player id
     * @param id to be set
     */
    public void setID(int id) { this.id = id; }

    /**
     * set method for player answer
     * @param givenAnswer is the answer given
     */
    public void setAnswer(Boolean givenAnswer){ this.answer = givenAnswer; }

    /**
     * set method for player claim
     * @param givenClaim is the claim given
     */
    public void setClaim(Claim givenClaim){ this.claim = givenClaim;}

    /**
     * Change score to given int
     * @param givenScore is the score to be set
     */
    public void setScore(int givenScore){ this.score = givenScore; }

    /**
     * set method for isplayer attribute
     * @param bool which is true or false depending on client
     */
    public void setIsPlayer(boolean bool) {this.isPlayer = bool;}


    /**
     * Adds one point to the score
     */
    public void addScore(){ this.score += 1; }

    public boolean answeredCorrect() {
        return this.answer == this.claim.getAnsw();
    }

    /**
     * determines if players claim is the same as claimID
     * @param claimID id to compare with
     * @return bool telling if id's are the same
     */
    public boolean claimIsClients(int claimID) {
        return this.claim.getID() == claimID;
    }
}
