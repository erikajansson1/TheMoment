package com.moment.themoment;
/**
 * Created by Dess on 2018-02-06.
 */

public class Player {
    private String name;
    private Boolean answer;
    private int score;
    private String claim;

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

    public String getClaim(){
        return this.claim;
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

    public void setClaim(String givenClaim){
        this.claim = givenClaim;
    }



}
