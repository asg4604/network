package rit.edu.project2;

import java.util.List;

public class Peer {
    public PeerCache peerCache;
    public PeerBroadcastReceiver peerBroadcastReceiver;
    public HeartBeatSender peerHeartBeatSender;
    public HeartBeatMonitor peerHeatBeatMonitor;
    public Peer() {
        this.peerCache = new PeerCache();
        this.peerBroadcastReceiver = new PeerBroadcastReceiver(this, peerCache, GossipProtocol.BROADCAST_PORT);

        // Assemble subset list of neighboring peers
        List<String> neighborList = this.peerCache.getPeersSeen();
        System.out.println(neighborList.size());
        this.peerHeartBeatSender = new HeartBeatSender(peerCache, GossipProtocol.PORT_NO, neighborList);
        this.peerHeatBeatMonitor = new HeartBeatMonitor(this, peerCache, GossipProtocol.PORT_NO);
    }

    public static void main(String[] args) {
        // Initalize peer cache and send out broadcast for discovery
        Peer p = new Peer();
        p.peerBroadcastReceiver.start();
        p.peerHeartBeatSender.start();
        p.peerHeatBeatMonitor.start();
        // Send out our own broadCast
        PeerBroadcaster.broadCastIPInfo();
    }

    public void onPeerDiscover(String message) {
        System.out.println("Discovered peer:" + message);
    }

    public void onPeerHeartBeat(String ip, long timestamp) {

    }

}
