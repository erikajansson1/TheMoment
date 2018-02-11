package com.moment.themoment;

import java.util.List;

public class Room {
    int ID;
    int numOfPlayers;
    List<Player> playerList;

    public Room(Player player){
        this.ID = 0;
        this.numOfPlayers = 1;
        this.playerList.add(player);
    }

}
