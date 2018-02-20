package com.moment.themoment;

import java.io.Serializable;

public class Player implements Serializable {
    private int id;
    private String name;
    private int score;
    private Boolean answer;
    private Claim claim;
    private Boolean isPlayer;

    Player(String name){
        this.name = name;
        this.answer = false;
        this.score = 0;
        this.claim = null;
        this.isPlayer = true;
    }

    /**
     * get method for player name
     * @return name of player
     */
    public String getName(){return this.name; }

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

}
