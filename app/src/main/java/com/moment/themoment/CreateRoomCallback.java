package com.moment.themoment;

public interface CreateRoomCallback {

    void setClientPlayerID(int id);

    void setRoomID(int id);

    void confirmDone(String response);

}
