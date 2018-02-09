package com.moment.themoment;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for the class ServerCommunication, still in early phase
 */
public class ServerCommunicationTest {
    @Test
    public void sendJson() throws Exception{
        Player guy = new Player("Fredrik", "dinmamma");
        Gson g = new Gson();
        String superguy = g.toJson(guy);
        Player tester = new Player("Henri", "Something");
        // Context of the app under test.
        ServerCommunication serverComm = new ServerCommunication();
        String returnString = serverComm.SendToServer(superguy);
        g.fromJson(returnString, Player.class);
        assertEquals(tester, returnString);
    }

    @Test
    public void savePlayertodb() throws Exception{
        assertEquals("", "");
    }

}
