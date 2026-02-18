package prac.dsa;

import java.util.Arrays;

public class LevelHigh {
    static int[][] directions = {{-1, -1}, {-1, 0}, {0, -1}};


    private static int maximumPathSum(int[][] grid, int row, int col, int[][] memo) {
        int max = Integer.MIN_VALUE;
        if (row == 0 && col == 0) {
            return memo[row][col] = grid[row][col];
        }
        if (memo[row][col] != Integer.MIN_VALUE) {
            return memo[row][col];
        }
        for (int[] direction : directions) {
            int dRow = row + direction[0];
            int dCol = col + direction[1];
            if (dCol < 0 || dRow < 0 || dRow >= grid.length || dCol >= grid[0].length || grid[dRow][dCol] == Integer.MIN_VALUE)
                continue;
            max = Math.max(grid[row][col] + maximumPathSum(grid, dRow, dCol, memo), max);
        }
        return memo[row][col] = max;
    }

    private static int maximumPathSum(int[][] grid) {
        int[][] memo = new int[grid.length][grid[0].length];
        for (int[] each : memo) {
            Arrays.fill(each, Integer.MIN_VALUE);
        }
        return maximumPathSum(grid, grid.length - 1, grid[0].length - 1, memo);
    }

    private static int maximumPathSumII(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        // initialize DP
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);

        dp[0][0] = grid[0][0]; // starting cell

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == Integer.MIN_VALUE) continue; // blocked

                int maxPrev = Integer.MIN_VALUE;

                // diagonal
                if (i > 0 && j > 0 && dp[i - 1][j - 1] != Integer.MIN_VALUE)
                    maxPrev = Math.max(maxPrev, dp[i - 1][j - 1]);

                // up
                if (i > 0 && dp[i - 1][j] != Integer.MIN_VALUE) maxPrev = Math.max(maxPrev, dp[i - 1][j]);

                // left
                if (j > 0 && dp[i][j - 1] != Integer.MIN_VALUE) maxPrev = Math.max(maxPrev, dp[i][j - 1]);

                if (maxPrev != Integer.MIN_VALUE) dp[i][j] = grid[i][j] + maxPrev;
                // else dp[i][j] stays as Integer.MIN_VALUE (no path)
            }
        }

        return dp[m - 1][n - 1]; // maximum sum to bottom-right
    }

    private static int palindromePartition(String s, int[] cost, int indexLeft, int[] dp, boolean[][] pal) {
        if (indexLeft == s.length()) return 0;
        if (dp[indexLeft] != -1) return dp[indexLeft];
        int currentCost = Integer.MAX_VALUE;
        for (int end = indexLeft; end < s.length(); end++) {
            pal[indexLeft][end] = !pal[indexLeft][end] ? isPalindrome(s, indexLeft, end) : pal[indexLeft][end];
            if (pal[indexLeft][end]) {
                int cutCost = (end < cost.length ? cost[end] : 0);
                currentCost = Math.min(currentCost, cutCost + palindromePartition(s, cost, end + 1, dp, pal));
            }
        }
        return dp[indexLeft] = currentCost;
    }

    private static boolean isPalindrome(String s, int indexLeft, int indexRight) {
        while (indexLeft <= indexRight && s.charAt(indexLeft++) == s.charAt(indexRight--)) ;
        return indexLeft >= indexRight;
    }

}
