package rit.edu.project2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortChecker {
    // Define port to be checked for client
    public static final int PORT_NO = GossipProtocol.PORT_NO;

    /**
     * Checks if a peer is up given IP and a timeout
     * @param ip The ip as a string
     * @param timeout The timeout time in milliseconds
     * @return True if peer is up and false if peer is down
     */
    public static boolean peerIsUp(String ip, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, PORT_NO), timeout);
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    public static String readHeartBeat(DataInputStream dis) {
        try {
            String input_data = dis.readUTF();
        } catch (IOException ioException) {
            System.out.println("Failed to read I/O");
            return null;
        }
        return null;
    }


    /**
     * Some dummy code to test the peer up functionality
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println(peerIsUp("127.0.0.1", 200));
        // System.out.println(readHeartBeat());
    }
}
