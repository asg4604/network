package rit.edu.project2;

public interface GossipProtocol {
    int PORT_NO = 42069;
    String IS_UP_QUERY = "U UP?";
    String HEART_BEAT = "HELLO";
    int BROADCAST_PORT = 42070;
    String BC_HEADER = "BC";
    String PEER_DISC_HEADER = "PD";
    String HEARTBEAT_HEADER = "HB";
}
