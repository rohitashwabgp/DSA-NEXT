package prac.lld;

import java.util.HashMap;
import java.util.Map;


// LRU Cache class
public class LRUCache {
    // Node class
    static class Node {
        int key, val;
        Node prev, next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    // Doubly Linked List class
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

        // Add node right after head
        void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        // Remove any node
        void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        // Remove last node (LRU)
        Node removeTail() {
            if (size == 0) return null;
            Node node = tail.prev;
            removeNode(node);
            return node;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final DoublyLinkedList dll;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        dll = new DoublyLinkedList();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        dll.removeNode(node);
        dll.addToHead(node); // move to most recently used
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            dll.removeNode(node);
            dll.addToHead(node);
        } else {
            if (map.size() >= capacity) {
                Node removed = dll.removeTail();
                map.remove(removed.key);
            }
            Node newNode = new Node(key, value);
            dll.addToHead(newNode);
            map.put(key, newNode);
        }
    }
}
