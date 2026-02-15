package prac.lld;

public class LeakyBucketLimiter {
    int capacity;
    double leakRate;
    long lastTimeStamp;
    double currentVolume;

    public LeakyBucketLimiter(int capacity, double leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.lastTimeStamp = System.currentTimeMillis();
        this.currentVolume = 0;
    }

    public boolean allowRequest() {
        long timeNow = System.currentTimeMillis();
        double leaked = (timeNow - lastTimeStamp) / 1000.0 * leakRate;
        currentVolume = Math.max(0, currentVolume - leaked);
        if (currentVolume < capacity) {
            currentVolume++;
            lastTimeStamp = timeNow;
            return true;
        } else {
            return false; // bucket full
        }
    }

}
