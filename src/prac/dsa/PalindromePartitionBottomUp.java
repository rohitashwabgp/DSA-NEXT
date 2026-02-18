package prac.dsa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PalindromePartitionBottomUp {

    public static boolean[][] findAllPalindromes(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];

        // 1️⃣ Single characters are palindromes
        for (int i = 0; i < n; i++) pal[i][i] = true;

        // 2️⃣ Substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            pal[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
        }

        // 3️⃣ Substrings of length 3+
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // s[i..j] is palindrome if ends match AND middle substring is palindrome
                pal[i][j] = s.charAt(i) == s.charAt(j) && pal[i + 1][j - 1];
            }
        }

        return pal;
    }

    private static final int MOD = 1_000_000_007;

    //classic LeetCode 730 approach
    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        int[] next = new int[n];
        int[] prev = new int[n];

        // 1️⃣ Precompute next occurrence
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            prev[i] = map.getOrDefault(c, -1);
            map.put(c, i);
        }

        map.clear();
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            next[i] = map.getOrDefault(c, n);
            map.put(c, i);
        }

        // 2️⃣ DP for all substrings
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (len == 1) {
                    dp[i][j] = 1; // single character palindrome
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        int low = next[i];
                        int high = prev[j];
                        if (low > high) {
                            dp[i][j] = 2 * dp[i + 1][j - 1] + 2; // no same letter inside
                        } else if (low == high) {
                            dp[i][j] = 2 * dp[i + 1][j - 1] + 1; // one same letter inside
                        } else {
                            dp[i][j] = 2 * dp[i + 1][j - 1] - dp[low + 1][high - 1]; // multiple inside
                        }
                    } else {
                        dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1];
                    }
                    dp[i][j] = ((dp[i][j] % MOD) + MOD) % MOD; // keep positive
                }
            }
        }

        return dp[0][n - 1];
    }

    public static int countPalindromes(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        int count = 0;

        // single characters
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
            count++;
        }

        // substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                pal[i][i + 1] = true;
                count++;
            }
        }

        // substrings of length 3+
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && pal[i + 1][j - 1]) {
                    pal[i][j] = true;
                    count++;
                }
            }
        }

        return count;
    }

    public int palindromeCut(String s, int[] cost) {
        int n = s.length();

        // 1️⃣ Precompute palindrome table
        boolean[][] pal = new boolean[n][n];
        for (int i = 0; i < n; i++) pal[i][i] = true;
        for (int i = 0; i < n - 1; i++) pal[i][i + 1] = s.charAt(i) == s.charAt(i + 1);

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                pal[i][j] = s.charAt(i) == s.charAt(j) && pal[i + 1][j - 1];
            }
        }

        // 2️⃣ Bottom-up DP
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (pal[j][i]) {
                    int cutCost = (j > 0 ? cost[j - 1] : 0); // cost after previous palindrome
                    dp[i] = Math.min(dp[i], cutCost + (j > 0 ? dp[j - 1] : 0));
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        PalindromePartitionBottomUp obj = new PalindromePartitionBottomUp();
        String s = "aab";
        int[] cost = {1, 2};
        System.out.println(obj.palindromeCut(s, cost)); // Output: 2
    }
}

