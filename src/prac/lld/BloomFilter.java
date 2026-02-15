package prac.lld;

import java.util.BitSet;

public class BloomFilter {
    private final BitSet bitSet;
    private final int size;
    private final int hashCount;

    public BloomFilter(int size, int hashCount) {
        this.size = size;
        this.hashCount = hashCount;
        this.bitSet = new BitSet(size);
    }

    private int hash(String item, int seed) {
        return Math.abs((item.hashCode() ^ seed) % size);
    }

    public void add(String item) {
        for (int i = 0; i < hashCount; i++) {
            bitSet.set(hash(item, i));
        }
    }

    public boolean contains(String item) {
        for (int i = 0; i < hashCount; i++) {
            if (!bitSet.get(hash(item, i))) {
                return false;
            }
        }
        return true;
    }
}
