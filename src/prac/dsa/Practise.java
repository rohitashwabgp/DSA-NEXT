package prac.dsa;

import java.util.*;


public class Practise {
    static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    static class State {
        public int to;
        public int weight;

        public State(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class UnionFind {
        int[] parents;
        int[] rank;

        public UnionFind(int size) {
            this.parents = new int[size];
            this.rank = new int[size];
            for (int parent = 0; parent < size; parent++) {
                parents[parent] = parent;
            }
        }

        public int find(int node1) {
            if (parents[node1] != node1) {
                parents[node1] = find(parents[node1]);
            }
            return parents[node1];
        }

        public boolean union(int node1, int node2) {
            int rootNode1 = find(node1);
            int rootNode2 = find(node2);
            if (rootNode1 == rootNode2) return false;
            if (rank[rootNode1] > rank[rootNode2]) {
                parents[rootNode2] = rootNode1;
            } else if (rank[rootNode1] < rank[rootNode2]) {
                parents[rootNode1] = rootNode2;
            } else {
                parents[rootNode2] = rootNode1;
                rank[rootNode1]++;
            }
            return true;
        }
    }


    public static int shortest(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        if (grid[0][0] == 1 || grid[n - 1][m - 1] == 1) return -1;

        int[][] dist = new int[n][m];
        for (int[] row : dist) Arrays.fill(row, -1);

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        dist[0][0] = 1;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];

            if (r == n - 1 && c == m - 1) return dist[r][c];

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nc >= 0 && nr < n && nc < m && grid[nr][nc] == 0 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return -1;
    }


    static int connected(int[][] grid) {
        int count = 0;
        //  boolean[][] visited = new boolean[grid.length][grid.length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != 0) {
                    dfs(grid, row, col);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfs(int[][] grid, int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || grid[row][col] == 0) {
            return;
        }
        grid[row][col] = 0;
        for (int[] d : dirs) {
            int nr = row + d[0], nc = col + d[1];
            dfs(grid, nr, nc);
        }
    }

    public static int minWeight(int[][] edges, int size) {

        List<State>[] adj = new List[size];

        for (int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(new State(edge[1], edge[2]));
        }
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        queue.offer(new State(0, 0));
        int[] dist = new int[size];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            int curr = state.to;
            int weight = state.weight;
            if (curr == size - 1) {
                return weight;
            }
            if (weight > dist[curr]) continue; // skip stale
            for (State next : adj[curr]) {
                int nextWeight = weight + next.weight;
                int nextDestination = next.to;
                if (dist[nextDestination] > nextWeight) {
                    dist[nextDestination] = nextWeight;
                    queue.offer(new State(nextDestination, nextWeight));
                }
            }
        }
        return -1;
    }

    public static int minimumSpanningTree(int[][] edges, int size) {
        List<State>[] adj = new ArrayList[size];
        for (int i = 0; i < size; i++) adj[i] = new ArrayList<>();
        // undirected graph
        for (int[] e : edges) {
            adj[e[0]].add(new State(e[1], e[2]));
            adj[e[1]].add(new State(e[0], e[2]));
        }
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        pq.offer(new State(0, 0));
        boolean[] visited = new boolean[size];
        int total = 0;
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int u = cur.to;
            int w = cur.weight;

            if (visited[u]) continue;
            visited[u] = true;
            total += w;

            for (State nxt : adj[u]) {
                if (!visited[nxt.to]) {
                    pq.offer(new State(nxt.to, nxt.weight));
                }
            }
        }

        return total;
    }

    public static int[] ordering(int[][] edges, int size) {
        List<Integer>[] adj = new List[size];

        for (int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
        }
        int[] inDegree = new int[size];
        for (int[] edge : edges) {
            inDegree[edge[1]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        int[] result = new int[size];
        int i = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            result[i] = curr;
            i++;
            for (int next : adj[curr]) {
                if (--inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return i == size ? result : new int[0];
    }


    public int minimumSpanning(int[][] edges, int size) {
        UnionFind uf = new UnionFind(size);
        Arrays.sort(edges, Comparator.comparingInt(edge -> edge[2]));
        int total = 0;
        int accepted = 0;
        for (int[] edge : edges) {
            if (uf.find(edge[0]) != uf.find(edge[1])) {
                uf.union(edge[0], edge[1]);
                total = total + edge[2];
                accepted++;
                if (accepted == size - 1) break;
            }
        }
        return total;
    }

    public int[][] shortestDistance() {
        int n = 4;
        int[][] edges = {{0, 1, 3}, {0, 3, 7}, {1, 0, 8}, {1, 2, 2}, {2, 3, 1}, {3, 0, 2}};
        int[][] distance = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(distance[i], Integer.MAX_VALUE);
            distance[i][i] = 0;
        }

        for (int[] e : edges) {
            distance[e[0]][e[1]] = Math.min(distance[e[0]][e[1]], e[2]);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
        }
        return distance;
    }

    private static int[] minimumDistance() {
        int n = 5;
        int[][] edges = {{0, 1, 4}, {0, 2, 1}, {2, 1, 2}, {1, 3, 1}, {2, 3, 5}, {3, 4, 3}};
        int source = 0;
        List<State>[] adjacent = new List[n];
        for (int i = 0; i < n; i++) {
            adjacent[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adjacent[edge[0]].add(new State(edge[1], edge[2]));
        }
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
//        for (int i = 0; i < n; i++) {
//            if(indegree[i] ==0){
//                queue.offer(new State(i))
//            }
//        }
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        queue.offer(new State(source, 0));
        dist[0] = 0;
        while (!queue.isEmpty()) {
            State current = queue.poll();
            int currTo = current.to;
            int currWt = current.weight;
            if (currWt > dist[currTo]) continue;
            for (State next : adjacent[currTo]) {
                if (next.weight + currWt < dist[next.to]) {
                    dist[next.to] = currWt + next.weight;
                    queue.offer(new State(next.to, currWt + next.weight));
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {{0, 1, 2}, {0, 2, 4}, {1, 2, 1}, {1, 3, 7}, {2, 4, 3}, {3, 4, 1}};
        System.out.println(Arrays.toString(minimumDistance()));
        System.out.println(minWeight(edges, n));
        System.out.println(connected(new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 0, 0}}));
    }
}
