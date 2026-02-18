package prac.dsa;

public class ShortestHamiltonianPath {

    public static int shortestPath(int n, int[][] edges) {
        int INF = 1000000000;
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                graph[i][j] = (i == j) ? 0 : INF;

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph[u][v] = w;
            graph[v][u] = w;
        }

        int FULL = (1 << n);
        int[][] dp = new int[FULL][n];
        for (int i = 0; i < FULL; i++)
            for (int j = 0; j < n; j++)
                dp[i][j] = INF;

        dp[1][0] = 0; // start at node 0

        for (int mask = 1; mask < FULL; mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue;
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;
                    dp[mask | (1 << v)][v] = Math.min(dp[mask | (1 << v)][v],
                            dp[mask][u] + graph[u][v]);
                }
            }
        }

        int ans = INF;
        for (int i = 0; i < n; i++)
            ans = Math.min(ans, dp[FULL - 1][i]); // all nodes visited, ending at any node

        return ans;
    }

    public static void main(String[] args) {
        int n = 4;
        int[][] edges = {{0,1,10},{0,2,15},{0,3,20},{1,2,35},{1,3,25},{2,3,30}};
        System.out.println(shortestPath(n, edges)); // Output: 80
    }
}
