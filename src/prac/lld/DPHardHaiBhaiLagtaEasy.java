package prac.lld;

import java.util.*;

public class DPHardHaiBhaiLagtaEasy {

    public int mergeStones(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) != 0) return -1;

        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stones[i];
        }

        return solve(stones, prefix, 0, n - 1, k);
    }

    private int solve(int[] stones, int[] prefix, int i, int j, int k) {

        if (j - i + 1 < k) return 0;

        int minCost = Integer.MAX_VALUE;

        for (int mid = i; mid < j; mid = mid + (k - 1)) {
            int left = solve(stones, prefix, i, mid, k);
            int right = solve(stones, prefix, mid + 1, j, k);
            minCost = Math.min(minCost, left + right);
        }
        if ((j - i) % (k - 1) == 0) {
            minCost = minCost + prefix[j + 1] - prefix[i];
        }

        return minCost;
    }

    static class State {
        int distance;
        int fuel;
        int count;

        public State(int distance, int fuel, int count) {
            this.distance = distance;
            this.fuel = fuel;
            this.count = count;
        }
    }

    public static int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        int fuel = startFuel;
        int stops = 0;
        int prev = 0;

        for (int[] station : stations) {
            int distance = station[0] - prev;
            fuel = fuel - distance;

            while (fuel < 0 && !maxHeap.isEmpty()) {
                fuel = fuel + maxHeap.poll();
                stops++;
            }

            if (fuel < 0) return -1;

            maxHeap.add(station[1]);
            prev = station[0];
        }

        // Handle distance to target
        fuel = fuel - (target - prev);

        while (fuel < 0 && !maxHeap.isEmpty()) {
            fuel = fuel + maxHeap.poll();
            stops++;
        }

        return fuel < 0 ? -1 : stops;
    }


    public int minRefuelStops2(int target, int init, int[][] stations) {
        int an = 0, i = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        while (target > init) {
            while (i < stations.length && stations[i][0] <= init) {
                pq.add(stations[i][1]);
                i++;
            }
            if (pq.isEmpty()) return -1;
            init = init + pq.poll();
            an++;
        }
        return an;
    }

    static class CatMouseState {
        //true for cat
        boolean turn;
        int mousePosi;
        int catPosi;

        public CatMouseState(boolean turn, int mousePosi, int catPosi) {
            this.turn = turn;
            this.catPosi = catPosi;
            this.mousePosi = mousePosi;
        }
    }

//    public int catMouseGame(int[][] graph) {
//        Queue<CatMouseState> queue = new LinkedList<>();
//        queue.offer(new CatMouseState(false, 1, 2));
//        int[][][] inDegree = new int[graph.length][graph.length][1];
//        while (!queue.isEmpty()) {
//            CatMouseState catMouseState = queue.poll();
//            if(catMouseState.turn){
//                for(int next: graph[catMouseState.catPosi]){
//                      if(next == 0) continue;
//                      if
//                }
//            } else {
//                for(int[] next: graph){
//
//                }
//            }
//
//        }
//    }


    public int numPermsDISequence(String s) {
        int n = s.length();

        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= n; i++) {

            // prefix sum of previous row
            int[] prefix = new int[n + 2];
            for (int j = 0; j < i; j++) {
                prefix[j + 1] = prefix[j] + dp[i - 1][j];
            }

            for (int j = 0; j <= i; j++) {
                if (s.charAt(i - 1) == 'I') {
                    // sum from 0 to j-1
                    dp[i][j] = prefix[j];
                } else {
                    // 'D'
                    // sum from j to i-1
                    dp[i][j] = prefix[i] - prefix[j];
                }
            }
        }

        int ans = 0;
        for (int j = 0; j <= n; j++) {
            ans += dp[n][j];
        }

        return ans;
    }

    public int superEggDrop(int k, int n) {

        // Base cases
        if (n == 0 || n == 1) return n;
        if (k == 1) return n;

        int minMoves = Integer.MAX_VALUE;

        for (int x = 1; x <= n; x++) {

            int breakCase = superEggDrop(k - 1, x - 1);
            int surviveCase = superEggDrop(k, n - x);

            int worst = 1 + Math.max(breakCase, surviveCase);

            minMoves = Math.min(minMoves, worst);
        }

        return minMoves;
    }


    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {

        return dfs(0, 0, 0, n, minProfit, group, profit);
    }

    private int dfs(int index, int membersUsed, int profitSoFar, int n, int minProfit, int[] group, int[] profit) {

        // All crimes considered
        if (index == group.length) {
            return profitSoFar >= minProfit ? 1 : 0;
        }

        int count = 0;

        // Option 1: Skip this crime
        count = count + dfs(index + 1, membersUsed, profitSoFar, n, minProfit, group, profit);

        // Option 2: Take this crime (if members allow)
        if (membersUsed + group[index] <= n) {
            count = count + dfs(index + 1, membersUsed + group[index], profitSoFar + profit[index], n, minProfit, group, profit);
        }

        return count;
    }


    public int numPermsDISequenceRe(String s) {
        int n = s.length();
        boolean[] used = new boolean[n + 1];
        return backtrack(s, -1, 0, used);
    }

    private int backtrack(String s, int prev, int index, boolean[] used) {
        if (index == s.length() + 1) {
            return 1;
        }
        int count = 0;
        for (int num = 0; num < used.length; num++) {
            if (used[num]) continue;
            // first number case
            if (prev == -1 || (s.charAt(index - 1) == 'I' && prev < num) || (s.charAt(index - 1) == 'D' && prev > num)) {
                used[num] = true;
                count = count + backtrack(s, num, index + 1, used);
                used[num] = false; // backtrack
            }
        }
        return count;
    }


    public static void main(String[] args) {
        int target = 100, startFuel = 10;
        int[][] stations = {{10, 60}, {20, 30}, {30, 30}, {60, 40}};
        System.out.println(minRefuelStops(target, startFuel, stations));
    }

}
