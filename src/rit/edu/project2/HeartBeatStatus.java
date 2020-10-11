package rit.edu.project2;

public class HeartBeatStatus {
    private boolean up;
    private long lastUpTimeStamp;

    HeartBeatStatus(boolean _up, long _lastUpTimeStamp) {
        this.up = _up;
        this.lastUpTimeStamp = _lastUpTimeStamp;
    }

    public synchronized void setLastUpTimeStamp(long lastUpTimeStamp) {
        this.lastUpTimeStamp = lastUpTimeStamp;
    }

    public synchronized void setUp(boolean up) {
        this.up = up;
    }

    public synchronized long getLastUpTimeStamp() {
        return lastUpTimeStamp;
    }

    public synchronized boolean isUp() {
        return up;
    }
}
