package prac.lld;

import java.util.HashMap;
import java.util.Map;


public class FileSystem {
    abstract static class Node {
        String name;

        public Node(String name) {
            this.name = name;
        }
    }

    static class FileNode extends Node {
        String content;

        public FileNode(String name, String content) {
            super(name);
            this.content = content;
        }
    }

    static class DirectoryNode extends Node {
        Map<String, Node> children;

        public DirectoryNode(String name) {
            super(name);
            children = new HashMap<>();
        }
    }

    private final DirectoryNode root;

    public FileSystem() {
        root = new DirectoryNode(""); // root directory
    }

    // Helper to navigate to a directory node
    private DirectoryNode navigateToDir(String path) {
        String[] parts = path.split("/");
        DirectoryNode curr = root;
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (!curr.children.containsKey(part)) {
                throw new RuntimeException("Path not found: " + path);
            }
            Node next = curr.children.get(part);
            if (!(next instanceof DirectoryNode)) {
                throw new RuntimeException(part + " is not a directory");
            }
            curr = (DirectoryNode) next;
        }
        return curr;
    }

    public void mkdir(String path) {
        String[] parts = path.split("/");
        DirectoryNode curr = root;
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (!curr.children.containsKey(part)) {
                curr.children.put(part, new DirectoryNode(part));
            }
            Node next = curr.children.get(part);
            if (!(next instanceof DirectoryNode)) {
                throw new RuntimeException(part + " already exists as a file");
            }
            curr = (DirectoryNode) next;
        }
    }

    public void createFile(String path, String content) {
        int lastSlash = path.lastIndexOf("/");
        String dirPath = path.substring(0, lastSlash);
        String fileName = path.substring(lastSlash + 1);

        DirectoryNode dir = dirPath.isEmpty() ? root : navigateToDir(dirPath);
        dir.children.put(fileName, new FileNode(fileName, content));
    }

    public String readFile(String path) {
        int lastSlash = path.lastIndexOf("/");
        String dirPath = path.substring(0, lastSlash);
        String fileName = path.substring(lastSlash + 1);

        DirectoryNode dir = dirPath.isEmpty() ? root : navigateToDir(dirPath);
        Node node = dir.children.get(fileName);
        if (!(node instanceof FileNode)) {
            throw new RuntimeException("File not found: " + path);
        }
        return ((FileNode) node).content;
    }
}
