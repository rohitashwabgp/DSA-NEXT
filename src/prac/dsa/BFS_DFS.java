package prac.dsa;

import java.util.*;

public class BFS_DFS {
    static class State {
        int to;
        int cost;

        State(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int capacity) {
            this.parent = new int[capacity];
            this.rank = new int[capacity];
            for (int i = 0; i < capacity; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(x);
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false;
            }

            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootY] > rank[rootX]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }

    private static boolean cycle() {
        int n = 3;
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}};
        UnionFind uf = new UnionFind(n);
        int count = 0;
        for (int[] edge : edges) {
            boolean isCycle = uf.union(edge[0], edge[1]);
            count++;
            if (!isCycle) return true;
        }
        return false;
    }

    private static int flightFare() {
        int n = 3;
        int[][] flights = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        int src = 0, dst = 2;

        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));

        queue.offer(new State(src, 0));
        List<List<State>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] flight : flights) {
            adj.get(flight[0]).add(new State(flight[1], flight[2]));
        }
        int[] dist = new int[3];
        Arrays.fill(dist, Integer.MAX_VALUE);
        while (!queue.isEmpty()) {
            State current = queue.poll();
            int to = current.to;
            int cost = current.cost;
            if (dist[to] < cost) continue;
            if (to == dst) return cost;
            for (State state : adj.get(to)) {
                int next = state.to;
                int nextCost = cost + state.cost;
                if (nextCost < dist[next]) {
                    dist[next] = nextCost;
                    queue.offer(new State(next, nextCost));
                }
            }
        }
        return -1;
    }

    private static int prims() {
        int n = 4;
        int[][] edges = {{0, 1, 1}, {0, 2, 4}, {1, 2, 2}, {1, 3, 3}, {2, 3, 5}};
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(new int[]{edge[1], edge[2]});
            adj.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        boolean[] visited = new boolean[n];
        int weight = 0;
        int count = 0;
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {

            int[] current = queue.poll();
            if (visited[current[0]]) continue;
            count++;
            visited[current[0]] = true;
            weight = weight + current[1];
            if (count == n) break;
            for (int[] edge : adj.get(current[0])) {
                if (!visited[edge[0]]) {
                    queue.offer(new int[]{edge[0], edge[1]});
                }
            }
        }
        return weight;
    }

    private static int[] bellman() {
        int n = 3;
        int[][] edges = {{0, 1, 4}, {0, 2, 2}, {2, 1, 1}};
        int[] dist = new int[n];
        Arrays.fill(dist, (int) 1e8);
        dist[0] = 0;
        for (int v = 0; v < n; v++) {
            for (int[] edge : edges) {
                int a = edge[0];
                int b = edge[1];
                int wt = edge[2];
                if (dist[a] != 1e8 && dist[a] + wt < dist[b]) {
                    if (v == n - 1) return new int[]{-1};
                    dist[b] = dist[a] + wt;
                }
            }
        }
        return dist;
    }

    private static int[] topoSort() {
        int n = 4;
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
        }
        int[] indegree = new int[n];

        for (int[] edge : edges) {
            indegree[edge[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int[] result = new int[n];
        int i = 0;
        while (!queue.isEmpty()) {
            int item = queue.poll();
            result[i] = item;
            i++;
            for (int next : adj.get(item)) {
                if (--indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return result;
    }

    private static int[][] floyd(int[][] dist) {
        for (int k = 0; k < dist.length; k++) {
            for (int i = 0; i < dist.length; i++) {
                for (int j = 0; j < dist.length; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        System.out.println(prims());
    }
}
