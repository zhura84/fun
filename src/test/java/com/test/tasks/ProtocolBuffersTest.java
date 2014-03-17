package com.test.tasks;

import com.test.task1.Client;
import com.test.task1.Server;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProtocolBuffersTest {

    @Test
    public void testClientServerTask() throws IOException {
        Server server = new Server(12345);
        server.start();
        Client client = new Client("localhost", 12345);
        for (int i = 0 ; i < 100 ; i++) {
            String response = client.sendHelloRequest("Andrey" + i);
            assertEquals("Hello, Andrey" + i, response);
        }
        server.stop();
    }
}
