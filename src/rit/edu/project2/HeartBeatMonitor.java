package rit.edu.project2;

import java.io.IOException;
import java.net.*;

public class HeartBeatMonitor extends Thread {
    private PeerCache peerCache;
    private int hbPortNo;
    private Peer peerRef;

    public HeartBeatMonitor(Peer peer, PeerCache peerCache, int hbPortNo) {
        this.peerRef = peer;
        this.peerCache = peerCache;
        this.hbPortNo = hbPortNo;
    }

    public void handleMessage(String message) {
        String[] pieces = message.split(":");
        // Malformed message means we quit
        if (pieces.length < 2) return;
        String header_seen = pieces[0];
        String message_content = pieces[1];
        System.out.println("Sentence:" + message);
        if (header_seen.equals(GossipProtocol.HEARTBEAT_HEADER)) {
            String[] message_pieces = message_content.split(",");
            String sentence = message_pieces[0];
            String time_stamp = message_pieces[1];
            String ip = message_pieces[2];

            // Record the heart beat statuses
            this.peerCache.addHeartBeatStatus(ip, true, Long.parseLong(time_stamp) );

            // Call any necessary peer methods
            this.peerRef.onPeerHeartBeat(ip, Long.parseLong(time_stamp));
        }

        // Add the recieved messages to peer messages
        this.peerCache.addRecievedMessages(message);


    }

    @Override
    public void run() {
        // Open up a socket to listen for heartbeats
        DatagramSocket serverSocket = null;
        byte[] recieveData = new byte[255];
        try {
            serverSocket = new DatagramSocket(null);
            InetSocketAddress zero_addr = new InetSocketAddress("0.0.0.0", GossipProtocol.PORT_NO);
            serverSocket.bind(zero_addr);

        } catch (SocketException se) {
            System.out.println("Failed to open heartbeat socket:");
            se.printStackTrace();
        }
        DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
        while (true) {
            try {
                serverSocket.receive(recievePacket);
                String sentence = new String(recievePacket.getData(), 0, recievePacket.getLength());
                // DBUG: System.out.println("HBM Recieved:" + sentence);
                handleMessage(sentence);
            } catch (IOException ioException) {
                System.out.println("HBM Failed to recieve:");
                ioException.printStackTrace();
            }
        }
    }
}

