package prac.lld;

import java.util.HashMap;
import java.util.Map;

public class CacheSystem2 {
    private static final int CAPACITY = 100;

    static class Node {
        String key;
        String value;
        Node next;
        Node prev;
        int freq;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    static class DoublyLinkedList {
        Node head;
        Node tail;
        int size;

        public DoublyLinkedList() {
            head = new Node("", "");
            tail = new Node("", "");
            size = 0;
            head.next = tail;
            tail.prev = head;
        }

        public void addFirst(Node node) {
            node.next = head.next;
            head.next.prev = node;
            node.prev = head;
            head.next = node;
            size++;
        }

        public void addLast(Node node) {
            tail.prev.next = node;
            node.prev = tail.prev;
            node.next = tail;
            tail.prev = node;
            size++;
        }

        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        public Node removeFirst() {
            if (size > 0) {
                Node remove = head.next;
                head.next = head.next.next;
                head.next.prev = head;
                size--;
                return remove;
            }
            return null;
        }

        public Node removeLast() {
            if (size > 0) {
                Node remove = tail.prev;
                tail.prev = tail.prev.prev;
                tail.prev.next = tail;
                size--;
                return remove;
            }
            return null;
        }
    }

    Map<String, Node> cache = new HashMap<>();
    Map<Integer, DoublyLinkedList> freqMap = new HashMap<>();
    int capacity = CAPACITY;
    int leastFreq = 0;

    public CacheSystem2(int capacity) {
        this.capacity = capacity;
    }

    public void put(String key, String value) {
        if (capacity == 0) return;
        if (cache.containsKey(key)) {
            Node nodeToUpdate = cache.get(key);
            nodeToUpdate.value = value;
            DoublyLinkedList oldList = freqMap.get(nodeToUpdate.freq);
            oldList.remove(nodeToUpdate);
            if (oldList.size == 0 && nodeToUpdate.freq == leastFreq) {
                leastFreq++;
            }
            nodeToUpdate.freq++;
            freqMap.computeIfAbsent(nodeToUpdate.freq, (k) -> new DoublyLinkedList()).addFirst(nodeToUpdate);
            return;
        }
        if (cache.size() == capacity) {
            DoublyLinkedList listFreqList = freqMap.get(leastFreq);
            Node listFreqNode = listFreqList.removeLast();
            cache.remove(listFreqNode.key);
            if(listFreqList.size == 0){
                freqMap.remove(listFreqNode.freq);
            }
        }
        Node nodeToInsert = new Node(key, value);
        cache.put(key, nodeToInsert);
        freqMap.computeIfAbsent(1, (k) -> new DoublyLinkedList()).addFirst(nodeToInsert);
        leastFreq = nodeToInsert.freq;
    }

    public String get(String key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node nodeToReturn = cache.get(key);
        DoublyLinkedList oldList = freqMap.get(nodeToReturn.freq);
        oldList.remove(nodeToReturn);
        if (oldList.size == 0 && nodeToReturn.freq == leastFreq) {
            leastFreq++;
        }
        nodeToReturn.freq++;
        freqMap.computeIfAbsent(nodeToReturn.freq, (ignore) -> new DoublyLinkedList()).addFirst(nodeToReturn);
        return nodeToReturn.value;
    }

}
