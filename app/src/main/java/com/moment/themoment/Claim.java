package com.moment.themoment;

import java.io.Serializable;

class Claim implements Serializable {
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

    public String getClaim() {
        return this.claim;
    }

    public Boolean getAnsw() {
        return this.correctAnswer;
    }

}