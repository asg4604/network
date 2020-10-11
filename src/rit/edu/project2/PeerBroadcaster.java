package rit.edu.project2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PeerBroadcaster {
    public static void main( String[] args ) throws IOException {
        broadCastIPInfo();
    }

    public static void broadcast(String broadcastString) {
        try {
            DatagramPacket dp = new DatagramPacket(broadcastString.getBytes(), broadcastString.length(), InetAddress.getByName("255.255.255.255"), GossipProtocol.BROADCAST_PORT);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.send(dp);
        } catch (IOException ioException) {
            System.out.println("Failed to broadcast message:" + broadcastString);
            ioException.printStackTrace();
        }
    }

    public static void broadCastIPInfo() {
        try {
            broadcast(GossipProtocol.BC_HEADER + ":" + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }
    }
}

