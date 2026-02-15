package prac.lld;

import java.util.TreeMap;

class StockPriceSystem {
    private final TreeMap<Long, Integer> timePriceMap;   // timestamp -> price
    private final TreeMap<Integer, Integer> priceFreqMap; // price -> frequency
    private long latestTime;

    public StockPriceSystem() {
        timePriceMap = new TreeMap<>();
        priceFreqMap = new TreeMap<>();
        latestTime = 0;
    }

    public void update(long timestamp, int price) {
        // If the timestamp already exists, reduce frequency of old price
        if (timePriceMap.containsKey(timestamp)) {
            int oldPrice = timePriceMap.get(timestamp);
            priceFreqMap.put(oldPrice, priceFreqMap.get(oldPrice) - 1);
            if (priceFreqMap.get(oldPrice) == 0) {
                priceFreqMap.remove(oldPrice);
            }
        }

        // Update new price
        timePriceMap.put(timestamp, price);
        priceFreqMap.put(price, priceFreqMap.getOrDefault(price, 0) + 1);

        // Update latest timestamp
        latestTime = Math.max(latestTime, timestamp);
    }

    public int current() {
        return timePriceMap.get(latestTime);
    }

    public int maximum() {
        return priceFreqMap.lastKey();
    }

    public int minimum() {
        return priceFreqMap.firstKey();
    }
}
