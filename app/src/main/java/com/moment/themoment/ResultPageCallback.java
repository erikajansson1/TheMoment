package com.moment.themoment;

import android.view.View;

public interface ResultPageCallback {

    void checkIfRoundIsFinished (String output);

    void JumptoMainMenu(String output);

    void setRoundComplete(Boolean result);

    void callForRoomUpdate(View view);

    void stillInTheGame(Boolean reply);

    void handleRoomUpdate(Room room);
}
