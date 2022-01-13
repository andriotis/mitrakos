import java.io.IOException;
import java.net.*;
import java.util.*;

public class userApplication {

    public static void main(String[] args) throws IOException {
        // my setup
        int myPort = 48016;

        // ithaki setup
        int ithakiPort = 38016;
        InetAddress ithakiIP = InetAddress.getByAddress(new byte[]{(byte) 155, (byte) 207, (byte) 18, (byte) 208});

        // request codes
        byte[] echo = "E2789".getBytes();

        // create socket and bind it to my port
        DatagramSocket socket = new DatagramSocket(myPort);

        // echo packet length is 32 bytes, thus I create a buffer to hold all of them
        byte[] buf = new byte[32];

        // create packet with destination ithaki's ip and port to send echo request code
        DatagramPacket code = new DatagramPacket(echo, echo.length, ithakiIP, ithakiPort);

        // create packet to save ithaki's response
        DatagramPacket response = new DatagramPacket(buf, buf.length);

        long start, end;
        ArrayList<Long> responses = new ArrayList<Long>();
        long startOfMeasurement = System.nanoTime();
        while (System.nanoTime() - startOfMeasurement < 1L * 60 * 1000 * 1000 * 1000) {
            socket.send(code);

            start = System.nanoTime();
            socket.receive(response);
            end = System.nanoTime();

            responses.add((end - start)/1_000_000);
        }
        System.out.println(responses);
    }
}
