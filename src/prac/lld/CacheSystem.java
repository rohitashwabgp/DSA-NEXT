package prac.lld;

import java.util.*;

public class CacheSystem {
    static class Node {
        int key, value, freq;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
            freq = 1;
        }
    }

    static class DoublyLinkedList {
        Node head, tail;
        int size;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void add(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeLast() {
            if (size > 0) {
                Node node = tail.prev;
                remove(node);
                return node;
            }
            return null;
        }
    }

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Node> nodeMap;
    private final Map<Integer, DoublyLinkedList> freqMap;

    public CacheSystem(int capacity) {
        this.capacity = capacity;
        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
        minFreq = 0;
    }

    public int get(int key) {
        if (!nodeMap.containsKey(key)) return -1;
        Node node = nodeMap.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            if (nodeMap.size() >= capacity) {
                DoublyLinkedList minList = freqMap.get(minFreq);
                Node toRemove = minList.removeLast();
                nodeMap.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            nodeMap.put(key, newNode);
            freqMap.computeIfAbsent(1, k -> new DoublyLinkedList()).add(newNode);
            minFreq = 1;
        }
    }

    private void updateFreq(Node node) {
        int freq = node.freq;
        DoublyLinkedList oldList = freqMap.get(freq);
        oldList.remove(node);

        if (freq == minFreq && oldList.size == 0) minFreq++;

        node.freq++;
        freqMap.computeIfAbsent(node.freq, k -> new DoublyLinkedList()).add(node);
    }
}


