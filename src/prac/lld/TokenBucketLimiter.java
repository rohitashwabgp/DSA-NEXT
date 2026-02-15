package prac.lld;

public class TokenBucketLimiter {

    long lastTimeStamp;
    int capacity;
    double refillRate;
    double token;

    public TokenBucketLimiter(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.lastTimeStamp = System.currentTimeMillis();
        this.token = 0;
    }

    public boolean allowRequest() {
        long timeNow = System.currentTimeMillis();
        double moreToAdd = refillRate * (timeNow - lastTimeStamp) / 1000.0;
        token = Math.min(token + moreToAdd, capacity);
        lastTimeStamp = timeNow;
        if (token >= 1) {
            token--;
            return true;
        }
        return false;
    }

}
