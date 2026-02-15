package prac.dsa;

import java.util.ArrayList;
import java.util.List;

public class ReRooting {

    public static List<Integer> neuronStrength(
            int n,
            List<Integer> neuronFrom,
            List<Integer> neuronTo,
            List<Integer> strongConnectivity) {

        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int u = neuronFrom.get(i);
            int v = neuronTo.get(i);
            graph[u].add(v);
            graph[v].add(u);
        }

        int[] value = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            value[i] = strongConnectivity.get(i - 1) == 1 ? 1 : -1;
        }

        int[] dp = new int[n + 1];
        int[] result = new int[n + 1];

        maximizeDown(1, 0, graph, value, dp);   // bottom-up
        reRoot(1, 0, graph, result, dp);   // re-rooting

        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            ans.add(result[i]);

        return ans;
    }

    static void maximizeDown(int node, int parent, List<Integer>[] graph, int[] value, int[] dp) {
        dp[node] = value[node];

        for (int neighbor : graph[node]) {
            if (neighbor == parent) continue;

            maximizeDown(neighbor, node, graph, value, dp);

            if (dp[neighbor] > 0)
                dp[node] = dp[node] + dp[neighbor];
        }
    }

    static void reRoot(int node, int parent, List<Integer>[] graph, int[] result, int[] dp) {

        result[node] = dp[node];

        for (int neighbor : graph[node]) {
            if (neighbor == parent) continue;

            int originalNode = dp[node];
            int originalChild = dp[neighbor];

            // Remove child contribution from parent
            if (dp[neighbor] > 0)
                dp[node] = dp[node] - dp[neighbor];

            // Add parent contribution to child
            if (dp[node] > 0)
                dp[neighbor] = dp[neighbor] + dp[node];

            reRoot(neighbor, node, graph, result, dp);

            // Restore values
            dp[node] = originalNode;
            dp[neighbor] = originalChild;
        }
    }

    static class State {
        int to;
        int cost;

        public State(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
//    private static void dfs(int current, int parent, List<List<State>> adjacent) {
//
//    }

//    public static int minReorder(int n, int[][] connections) {
//        List<List<State>> adjacent = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            adjacent.add(new ArrayList<>());
//        }
//        for (int[] connection : connections) {
//            adjacent.get(connection[0]).add(new State(connection[1], 0));
//            adjacent.get(connection[1]).add(new State(connection[0], 1));
//        }
//
//        dfs(0, -1, adjacent);
//
//    }

    public static void main(String[] args) {
        int n = 6;
        int[][] connections = {{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}};
     //   System.out.println(minReorder(n, connections));
    }

}
