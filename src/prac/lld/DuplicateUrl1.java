package prac.lld;

import java.util.concurrent.atomic.AtomicLongArray;

public class DuplicateUrl1 {
    private final BloomFilter store;

    static class BloomFilter {
        private final int k;      // number of hash functions
        private final int m;      // number of bits
        private final AtomicLongArray bits; // atomic long array for lock-free bits

        public BloomFilter(int k, int m) {
            this.k = k;
            this.m = m;
            int size = (m + 63) / 64; // number of longs needed
            this.bits = new AtomicLongArray(size);
        }

        private int hash(String item, int seed) {
            int hash = item.hashCode();
            return ((hash ^ seed) & 0x7fffffff) % m;
        }

        private void setBit(int index) {
            int longIndex = index / 64;
            long mask = 1L << (index % 64);
            long oldVal;
            long newVal;
            do {
                oldVal = bits.get(longIndex);
                newVal = oldVal | mask;
            } while (!bits.compareAndSet(longIndex, oldVal, newVal));
        }

        private boolean getBit(int index) {
            int longIndex = index / 64;
            long mask = 1L << (index % 64);
            return (bits.get(longIndex) & mask) != 0;
        }

        public void add(String url) {
            for (int i = 0; i < k; i++) {
                setBit(hash(url, i));
            }
        }

        public boolean contains(String url) {
            for (int i = 0; i < k; i++) {
                if (!getBit(hash(url, i))) return false;
            }
            return true;
        }
    }

    public DuplicateUrl1(int n, double p) {
        double mDouble = -(n * Math.log(p)) / Math.pow(Math.log(2), 2);
        int m = (int) Math.ceil(mDouble);
        int k = Math.max(1, (int) Math.round((m / (double) n) * Math.log(2)));
        store = new BloomFilter(k, m);
    }

    public void add(String url) {
        store.add(url);
    }

    public boolean contains(String url) {
        return store.contains(url);
    }
}
