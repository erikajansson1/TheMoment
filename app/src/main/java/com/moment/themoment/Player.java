package com.moment.themoment;
/**
 * Created by Dess on 2018-02-06.
 */

public class Player {
    private String name;
    private Boolean answer;
    private int score;

    /*
    * Creates a person, used right now just for basic concept in Json building
     */
    public Player(String name){
        this.name = name;
        this.answer = false;
        this.score = 0;
    }
}
