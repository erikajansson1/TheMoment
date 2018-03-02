package com.moment.themoment;

import java.io.Serializable;

class Claim implements Serializable {
    private int id;
    private String claim;
    private Boolean correctAnswer;

    /**
     * constructor
     *
     * @param claim         related to player
     * @param correctAnswer is the thruthfullnes of the claim
     */
    Claim(String claim, Boolean correctAnswer) {
        this.claim = claim;
        this.correctAnswer = correctAnswer;
    }
public void setClaim(String theClaim){this.claim = theClaim;}

public void setCorrectAnswer(Boolean answ){this.correctAnswer = answ;}
    /**
     * set method for attribute ID
     * @param id representing id number in DB
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * get method for attribute ID
     * @return this.ID
     */
    public int getID() {
        return this.id;
    }

    public String getClaim() {
        return this.claim;
    }

    public Boolean getAnsw() {
        return this.correctAnswer;
    }


}