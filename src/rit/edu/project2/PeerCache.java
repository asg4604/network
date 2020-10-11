package rit.edu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeerCache {
    /**
     * A map to map the IP strings to the up status
     */
    private Map<String, HeartBeatStatus> heartBeatStatuses;
    private List<String> messagesReceived;
    private List<String> peersSeen;
    /**
     * Empty constructor for PeerCache
     */
    public PeerCache() {
        heartBeatStatuses = new HashMap<>();
        messagesReceived = new ArrayList<>();
        peersSeen = new ArrayList<>();
    }

    public PeerCache(Map<String, HeartBeatStatus> _hbMap, List<String> _messagesRecieved, List<String> _peersSeen) {
        this.heartBeatStatuses = _hbMap;
        this.messagesReceived = _messagesRecieved;
        this.peersSeen = _peersSeen;
    }

    public synchronized List<String> getMessagesReceived() {
        return this.messagesReceived;
    }

    public synchronized Map<String, HeartBeatStatus> getHeartBeatStatuses() {
        return this.heartBeatStatuses;
    }

    public synchronized List<String> getPeersSeen() {
        return this.peersSeen;
    }

    public synchronized void addPeersSeen(String ip) {
        this.getPeersSeen().add(ip);
    }

    public synchronized void addHeartBeatStatus(String ip, boolean up, long timestamp) {
        HeartBeatStatus addedHeartBeatStatus = new HeartBeatStatus(up, System.currentTimeMillis());
        this.getHeartBeatStatuses().put(ip, addedHeartBeatStatus);
    }

    public synchronized void addRecievedMessages(String recieved_message) {
        this.getMessagesReceived().add(recieved_message);
    }

}
