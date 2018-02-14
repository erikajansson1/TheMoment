package com.moment.themoment;
/**
 * Created by Dess on 2018-02-06.
 */

public class Player {
    private String name;
    private int id;
    private Boolean answer;
    private int score;
    private Claim claim;

    /*
    * Creates a person, used right now just for basic concept in Json building
     */
    public Player(String name){
        this.name = name;
        this.answer = false;
        this.score = 0;
        this.claim = null;
    }

    public String getName(){
        return this.name;
    }

    public Boolean getAnswer(){
        return this.answer;
    }

    public int getScore(){
        return this.score;
    }

    public Claim getClaim(){
        return this.claim;
    }


    public void setID(int id) {
        this.id = id;
        return;
    }

    public void setAnswer(Boolean givenAnswer){
        this.answer = givenAnswer;
        return;
    }

    /**
     * Will change score to given int
     * @param givenScore
     */
    public void setScore(int givenScore){
        this.score = givenScore;
        return;
    }

    /**
     * Adds one point to the score
     */
    public void addScore(){
        this.score += 1;
        return;
    }

    public void setClaim(Claim givenClaim){
        this.claim = givenClaim;
    }



}
