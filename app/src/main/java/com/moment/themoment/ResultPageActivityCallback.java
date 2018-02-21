package com.moment.themoment;

public interface ResultPageActivityCallback {

    void updateResultList(Room room);

    void checkIfRoundIsFinished (String output);

    void ifDoneCallRoomUpdate(String answer);

    void JumptoMainMenu(String output);
}
