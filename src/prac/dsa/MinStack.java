package prac.dsa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinStack {
    static class Node {
        public Node next;
        public Node prev;
        public String val;

        public Node(String val) {
            this.val = val;
        }
    }

    Node head;
    Node tail;

    public MinStack() {
        this.head = new Node("");
        this.tail = new Node("");
        this.head.next = tail;
        this.tail.prev = head;
    }

    private static int maxSum(int[] nums) {

        int[] prefix = new int[nums.length + 1];
        prefix[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }
        int[][] dp = new int[nums.length][nums.length];

        for (int len = 1; len <= nums.length; len++) {
            for (int left = 0; left + len - 1 < nums.length; left++) {
                int right = left + len - 1;
                for (int k = left; k <= right; k++) {
                    int leftPart = (k > left) ? dp[left][k - 1] : 0;
                    int rightPart = (k < right) ? dp[k + 1][right] : 0;
                    int total = (prefix[right + 1] - prefix[left]) * (right - left + 1);
                    dp[left][right] = Math.max(dp[left][right], leftPart + total + rightPart);
                }
            }
        }
        return dp[0][nums.length - 1];
    }

    private static int coins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public boolean wordBreak(String s, Set<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        Set<String> dic = new HashSet<>(wordDict);
        for (int i = 0; i <= s.length(); i++) {
            if (!dp[i]) continue;
            for (String word : dic) {
                int end = i + word.length();
                if (end <= s.length() && s.startsWith(word, i)) {
                    dp[end] = true;
                }
            }
        }

        return dp[s.length()];
    }

    public boolean wordBreak(String s, List<String> wordDict) {

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        int maxLen = 0;

        for (String word : wordDict)
            maxLen = Math.max(maxLen, word.length());

        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= Math.max(i - maxLen - 1, 0); j--) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }

    public static int knapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }

        return dp[capacity];
    }

    private static int stones(int[] stones, int index) {
        if (index >= stones.length) return 0;
        int oneStone = (stones[index] - stones(stones, index + 1));
        int twoStone = stones[index] + stones[index + 1] - stones(stones, index + 2);

        return oneStone > 0 ? oneStone : twoStone;
    }

    private static int stonesr(int[] stones, int index) {
        if (index >= stones.length) return 0;

        // take 1 stone
        int takeOne = stones[index] - stones(stones, index + 1);

        // take 2 stones (only if possible)
        int takeTwo = Integer.MIN_VALUE;
        if (index + 1 < stones.length) {
            takeTwo = stones[index] + stones[index + 1] - stones(stones, index + 2);
        }

        return Math.max(takeOne, takeTwo);
    }


    public static int knapsack2D(int[] values, int[] weights, int capacity) {
        int n = values.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // Skip current item
                dp[i][w] = dp[i - 1][w];

                // Take current item if it fits
                if (w >= weights[i - 1]) {
                    dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                }
            }
        }

        return dp[n][capacity];
    }


    private static boolean wordBreak1(String s, Set<String> dict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 0; i <= s.length(); i++) {
            if (!dp[i]) continue;

            for (String word : dict) {
                int end = i + word.length();
                if (end <= s.length() && s.startsWith(word, i)) {
                    dp[end] = true;
                }
            }
        }

        return dp[s.length()];
    }


}
