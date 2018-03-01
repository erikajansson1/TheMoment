package com.moment.themoment;

public interface ResultPageCallback {

    void updateResultList(Room room);

    void checkIfRoundIsFinished (String output);

    void ifDoneCallRoomUpdate(String answer);

    void JumptoMainMenu(String output);
}
