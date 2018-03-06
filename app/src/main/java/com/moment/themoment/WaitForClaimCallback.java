package com.moment.themoment;

public interface WaitForClaimCallback {

    /**
     * Recieves answer if everyone has gotten to the same round.
     * @param response String that will in function convert to boolean
     */
    void updateWaitForClaim(String response);

    /**
     * Updates the room from the response of the server. Then grabs first current claim
     * that will then be sent to voteOnClaim.
     * @param room to update the currentRoom
     */
    void updateRoom(Room room);

    void getUpdatedClaimsRoom();

    void stillInTheGame(Boolean reply);

    void JumptoMainMenu(String output);

}
