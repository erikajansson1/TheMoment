package com.moment.themoment;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for the class ServerCommunication, still in early phase
 */
public class ServerCommunicationTest {
    @Test
    public void sendJson(){
        Player guy = new Player("Fredrik");
        Gson gg = new Gson();
        String superguy = gg.toJson(guy);
        Player tester = new Player("Henri");
        String testerJson = gg.toJson(tester);
        // Context of the app under test.
        ServerCommunication serverComm = new ServerCommunication();
        String returnString = serverComm.SendToServer(superguy);
        //g.fromJson(returnString, Player.class);
        assertEquals(testerJson, returnString);
    }

    @Test
    public void savePlayertodb() throws Exception{
        assertEquals("", "");
    }

}
