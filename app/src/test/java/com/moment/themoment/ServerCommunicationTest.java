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
        Player test1 = new Player("Fredrik");
        Player test2 = new Player("Henri");
        Gson gg = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        String whattype = "basic";
        sendJSON.add(test1);
        sendJSON.add(whattype);
        String testerJson = gg.toJson(sendJSON);
        String testagainst = gg.toJson(test2);
        ServerCommunication serverComm = new ServerCommunication();
        String returnString = serverComm.SendToServer(testerJson);
        //Player returnS = gg.fromJson(returnString, Player.class);
        assertEquals(testagainst, returnString);
    }


    /*
     *Checks if when sending wrong type it won't give right back
     */
    @Test
    public void sendWrongType(){
        Gson gg = new Gson();
        String test = "WrongTypeSent";
        String sendTest = gg.toJson(test);
        ServerCommunication serverComm = new ServerCommunication();
        String returnString = serverComm.SendToServer(test);
        String testagainst = null;
        assertEquals(testagainst, returnString);
    }

    /**
     * Checks to see if handles when wrong objects is given to
     */
    /*
    @Test
    public void sendWrongObject(){
        Player test1 = new Player("Fredrik");
        Player test2 = new Player("Henri");
        Room testRoom = new Room(test2);
        Gson gg = new Gson();
        List<Object> sendJSON = new ArrayList<Object>();
        String whattype = "basic";
        sendJSON.add(test1);
        sendJSON.add(whattype);
        String testerJson = gg.toJson(sendJSON);
        String testagainst = gg.toJson(test2);
        ServerCommunication serverComm = new ServerCommunication();
        String returnString = serverComm.SendToServer(testerJson);
        //Player returnS = gg.fromJson(returnString, Player.class);
        assertEquals(testagainst, returnString);
    }
*/
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
