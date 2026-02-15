package prac.lld;

import java.util.*;


public class LFUCache {
    static class Node {
        int key, val, freq;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.freq = 1;
        }
    }

    Map<Integer, Node> cache;
    Map<Integer, LinkedHashSet<Node>> cacheCounter;
    int capacity;
    int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.cache = new HashMap<>();
        this.cacheCounter = new HashMap<>();
    }

    public int get(int key) {
        if (!cache.containsKey(key)) return -1;
        Node node = cache.get(key);
        updateFreq(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.val = value;
            updateFreq(node);
            return;
        }

        if (cache.size() >= capacity) {
            // Evict least frequently used node
            LinkedHashSet<Node> nodes = cacheCounter.get(minFreq);
            Node toRemove = nodes.getFirst();
            nodes.remove(toRemove);
            if (nodes.isEmpty()) cacheCounter.remove(minFreq);
            cache.remove(toRemove.key);
        }

        // Insert new node
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        cacheCounter.computeIfAbsent(1, ignore -> new LinkedHashSet<>()).add(newNode);
        minFreq = 1;
    }

    private void updateFreq(Node node) {
        int freq = node.freq;
        LinkedHashSet<Node> nodes = cacheCounter.get(freq);
        nodes.remove(node);
        if (nodes.isEmpty()) {
            cacheCounter.remove(freq);
            if (freq == minFreq) minFreq++;
        }
        node.freq++;
        cacheCounter.computeIfAbsent(node.freq, ignore -> new LinkedHashSet<>()).add(node);
    }
}