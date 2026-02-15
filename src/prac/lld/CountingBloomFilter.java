package prac.lld;

public class CountingBloomFilter {
    private final int[] counts;
    private final int size;
    private final int hashCount;

    public CountingBloomFilter(int size, int hashCount) {
        this.size = size;
        this.hashCount = hashCount;
        this.counts = new int[size];
    }

    private int hash(String item, int seed) {
        return Math.abs((item.hashCode() ^ seed) % size);
    }

    public void add(String item) {
        for (int i = 0; i < hashCount; i++) {
            counts[hash(item, i)]++;
        }
    }

    public boolean contains(String item) {
        for (int i = 0; i < hashCount; i++) {
            if (counts[hash(item, i)] == 0) return false;
        }
        return true;
    }

    public void remove(String item) {
        for (int i = 0; i < hashCount; i++) {
            int idx = hash(item, i);
            if (counts[idx] > 0) counts[idx]--;
        }
    }
}
