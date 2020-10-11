package rit.edu.project2;

import java.io.IOException;
import java.net.*;

public class PeerBroadcastReceiver extends Thread {
    private Peer peerRef;
    private PeerCache peerCache;
    private int bcPortNo;

    public PeerBroadcastReceiver(Peer _peerRef, PeerCache _peerCache, int _bcPortNo) {
        this.peerCache = _peerCache;
        this.bcPortNo = _bcPortNo;
        this.peerRef = _peerRef;
    }

    private void handleMessage(String message) {
        String[] message_pieces = message.trim().split(":");
        String header = message_pieces[0].trim();
        String message_content = message_pieces[1].trim();
        System.out.println("ALL MESSAGES:" + message);
        if (header.equals(GossipProtocol.BC_HEADER)) {
            peerCache.addRecievedMessages(message);
            peerRef.onPeerDiscover(message);
            String localIp = null;
            // If the peer ip is our own, then we can ignore
            try {
                localIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            }
            if (!localIp.equals(message_content)) { // Fuck
                peerCache.addPeersSeen(message_content);
            } else {
                System.out.println("Seen our own broadcast: " + message_content);
            }
        }

    }

    @Override
    public void run() {
        DatagramSocket serverSocket = null;
        try {
            // Bind to 0.0.0.0
            serverSocket = new DatagramSocket(null);
            InetSocketAddress zero_addr = new InetSocketAddress("0.0.0.0", bcPortNo);
            serverSocket.bind(zero_addr);
            byte[] receiveData = new byte[255];
            String sendString = "BC:" + InetAddress.getLocalHost().getHostAddress();
            byte[] sendData = sendString.getBytes("UTF-8");

            System.out.printf("Listening on udp:%s:%d%n",
                    InetAddress.getLocalHost().getHostAddress(), bcPortNo);
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);

            while(true)
            {
                serverSocket.receive(receivePacket);
                String sentence = new String( receivePacket.getData(), 0,
                        receivePacket.getLength() );

                System.out.println("RECEIVED: " + sentence);
                // Update the peer cache upon learning about a peer
                handleMessage(sentence);
                // now send acknowledgement packet back to sender (both peers learn about each other)
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            serverSocket.close();
        }
        // should close serverSocket in finally block
    }

    public static void main(String[] args) {

    }
}