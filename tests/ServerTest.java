import com.tony.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testItAcceptsAClientRequest() throws Exception {
        Server server = new Server(5000);
        new Thread(server).start();
        Socket clientMockSocket = new Socket("localhost", 5000);
        clientMockSocket.sendUrgentData(1);
        assertEquals("Accepted a new client request\n", outContent.toString());
    }

}