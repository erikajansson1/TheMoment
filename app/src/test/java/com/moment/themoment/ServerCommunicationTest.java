package com.moment.themoment;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for the class ServerCommunication, still in early phase
 * this tests send a json that wants to have returned a player
 */
public class ServerCommunicationTest {
    @Test
    public void sendJson(){
        Player guy = new Player("Fredrik");
        Gson gg = new Gson();
        String superguy = gg.toJson(guy);
        Player tester = new Player("Henri");
        //String testerJson = gg.toJson(tester);
        type whatType = new type("basic");
        //String newtype = gg.toJson(whatType);
        List<Object> sendJSON = new ArrayList<Object>();
        sendJSON.add(tester);
        sendJSON.add(whatType);
        String testerJson = gg.toJson(sendJSON);
        ServerCommunication serverComm = new ServerCommunication();
        String returnString = serverComm.SendToServer(superguy);
        //gg.fromJson(returnString, Player.class);
        assertEquals(testerJson, returnString);
    }

    @Test
    public void savePlayertodb() throws Exception{
        assertEquals("", "");
    }

    @Test
    public void saveRoomToDB(){
        assertEquals("","");
    }

    @Test
    public void joinRoomInServer(){
        assertEquals("","");
    }

    @Test
    public void sendClaimToServer(){
        assertEquals("","");
    }

    @Test
    public void updateRoomRequest(){
        assertEquals("","");
    }

}
