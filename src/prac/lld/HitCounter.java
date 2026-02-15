package prac.lld;

import java.util.LinkedList;
import java.util.Queue;

public class HitCounter {
    Queue<Long> timestamps;
    long[] timestampsFixed;
    int[] frequencyCount;
    int interval;

    public HitCounter(String type, int interval) {
        if (type.equals("FIXED")) {
            timestampsFixed = new long[interval];
            frequencyCount = new int[interval];
        } else {
            timestamps = new LinkedList<>();
        }
        this.interval = interval;
    }

    void hit(long timestamp) {
        timestamps.offer(timestamp);
    }

    // Return number of hits in the past 5 minutes (300 seconds)
    int getHits(int timestamp) {
        while (!timestamps.isEmpty() && timestamps.peek() < timestamp - interval) {
            timestamps.poll();
        }
        return timestamps.size();
    }

    void hitFixed(int timestamp) {
        int idx = timestamp % interval;
        if (timestampsFixed[idx] != timestamp) {
            timestampsFixed[idx] = timestamp;
            frequencyCount[idx] = 1;
        } else {
            frequencyCount[idx]++;
        }
    }

    // Return number of hits in the past 5 minutes (300 seconds)
    int getHitsFixed(int timestamp) {
        int count = 0;
        for (int i = 0; i < interval; i++) {
            if (timestamp - timestampsFixed[i] < interval)
                count += frequencyCount[i];
        }
        return count;
    }
}
