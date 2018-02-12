package com.moment.themoment;

import java.util.List;

public class Room {
    private int ID = 0;
    private int numOfPlayers;
    private List<Player> playerList;

    public Room(Player player){
        this.numOfPlayers = 1;
        this.playerList.add(player);
        ServerCommunication server = new ServerCommunication();
        this.ID = server.SaveRoomToDB(this);
    }

    public Room(Player player, Room room){
        this.numOfPlayers = room.getNumOfPlayers();
        this.ID = room.getID();
    }

    public int getID(){
        return this.ID;
    }

    public int getNumOfPlayers(){
        return this.numOfPlayers;
    }

    public List<Player> getPlayerList(){
        return this.playerList;
    }

    public void setID(int newID){
        this.ID = newID;
    }


}
