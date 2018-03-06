package com.moment.themoment;

public interface ResultPageCallback {

    void updateResultList(Room room);

    void checkIfRoundIsFinished (String output);

    void JumptoMainMenu(String output);

    void setRoundComplete(String output);

    void callForRoomUpdate();
}
