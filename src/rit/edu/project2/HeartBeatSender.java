package rit.edu.project2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class HeartBeatSender extends Thread {
    private PeerCache peerCache;
    private int hbPortNo;
    private List<String> neighborIPs;
    public HeartBeatSender(PeerCache peerCache, int hbPortNo, List<String> neighborIPs) {
        this.peerCache = peerCache;
        this.hbPortNo = hbPortNo;
        this.neighborIPs = neighborIPs;
    }

    private void sendHeartBeatToPeer(String neighborIP) {
        try {
            String sendString = GossipProtocol.HEARTBEAT_HEADER + ":" + GossipProtocol.HEART_BEAT + "," + System.currentTimeMillis() + "," + InetAddress.getLocalHost().getHostAddress();
            DatagramPacket dp = new DatagramPacket(sendString.getBytes(), sendString.length(), InetAddress.getByName(neighborIP), GossipProtocol.PORT_NO);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.send(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendOutAllHeartBeats() {
        for (String ipAddr : neighborIPs) {
            sendHeartBeatToPeer(ipAddr);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                sendOutAllHeartBeats();
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                System.out.println("This peer has stopped sending heart beats");
                return;
            }
        }
    }
}
