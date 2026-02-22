package prac.heal;

import java.util.*;

public class DynamicProgramming {


    public static int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (String s : strs) {
            int zeros = 0, ones = 0;

            for (char c : s.toCharArray()) {
                if (c == '0') zeros++;
                else ones++;
            }

            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(
                            dp[i][j],
                            1 + dp[i - zeros][j - ones]
                    );
                }
            }
        }

        return dp[m][n];
    }

    public int maxProfit(int[] prices, int left, int right) {
        int maxProfit = 0;
        int bestBuy = Integer.MAX_VALUE;
        int bestSell = 1;
        for (int i = left; i < right; i++) {
            bestSell = Math.max(bestSell, prices[i]);
            maxProfit = Math.max(bestSell - bestBuy, maxProfit);
            bestBuy = Math.min(bestBuy, prices[i]);
        }
        return maxProfit;
    }

    private int partition(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, maxProfit(prices, 0, i) + maxProfit(prices, i, prices.length));
        }
        return maxProfit;
    }

    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = dp[i] + dp[i - coin];
            }
        }
        return dp[amount];
    }


    public static int deleteAndEarn(int[] nums) {
//        int maxVal = 0;
//        for (int n : nums) maxVal = Math.max(maxVal, n);

        int[] points = new int[nums.length];
        for (int n : nums) points[n] = points[n] + n; // total points for each number

        // House Robber DP
        int take = 0, skip = 0;
        for (int point : points) {
            int temp = skip;
            skip = Math.max(skip, take);
            take = point + temp;
        }
        return Math.max(take, skip);
    }



    public int rob(int[] nums) {
        int take = 0;
        int skip = 0;

        for (int num : nums) {
            int temp = skip;
            skip = Math.max(take, skip);
            take = num + temp;
        }

        return Math.max(take, skip);
    }


    public static void main(String[] args) {
        //   System.out.println(findMaxForm(new String[]{"10", "0001", "111001", "1", "0"}, 5, 3));
        // System.out.println(change(5, new int[]{1, 2, 5}));
        System.out.println(deleteAndEarn(new int[]{2, 2, 3, 3, 3, 4}));
    }
}
