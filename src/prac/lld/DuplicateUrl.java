package prac.lld;

import java.util.BitSet;

public class DuplicateUrl {
    private final BloomFilter store;

    static class BloomFilter {
        long k;
        final BitSet store;

        public BloomFilter(long k, int m) {
            this.k = k;
            this.store = new BitSet(m);
        }

        private int hash(String item, int seed) {
            int hash = item.hashCode();
            return Math.abs((hash ^ seed) % store.size());
        }

        private void add(String url) {
            for (int i = 0; i < k; i++) {
                int hash = hash(url, i);
                synchronized(store) {
                    store.set(hash);
                }
            }
        }

        private boolean contains(String url) {
            for (int i = 0; i < k; i++) {
                int hash = hash(url, i);
                if (!store.get(hash)) {
                    return false;
                }
            }
            return true;
        }
    }

    public DuplicateUrl(int n, double p) {
        double m = -(n * Math.log(p)) / Math.pow(Math.log(2), 2);
        long k = (long) ((m / n) * Math.log(2));
        store = new BloomFilter(k, (int) m);
    }

    public void add(String url) {
        store.add(url);
    }

    public boolean contains(String url) {
        return store.contains(url);
    }
}


