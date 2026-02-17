package prac.dsa;

import java.util.*;

public class DPRevisited {

    static boolean partitionEqualSum(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int n = target; n >= num; n--) {
                dp[n] = dp[n] || dp[n - num];
            }
        }
        return dp[target];
    }

    static int coins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int num : coins) {
            for (int n = num; n <= amount; n++) {
                dp[n] = 1 + Math.min(dp[n], dp[n - num]);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static int lengthOfLIS(int[] numbs) {
        int n = numbs.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int ans = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (numbs[j] < numbs[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    static int editDistance(String s1, String s2) {
        int s1Size = s1.length();
        int s2Size = s2.length();
        int[][] dp = new int[s1Size + 1][s2Size + 1];
        for (int i = 0; i <= s1Size; i++) {
            dp[i][0] = i;
        }

        for (int i = 0; i <= s2Size; i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= s1Size; i++) {
            for (int j = 1; j <= s2Size; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[s1Size][s2Size];
    }

    public static List<String> wordBreak(String s, List<String> wordDict) {

        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();

        // dp[i] stores all sentences that can form substring s[0..i-1]
        List<List<String>> dp = new ArrayList<>();
        for (int i = 0; i <= n; i++) dp.add(new ArrayList<>());

        dp.getFirst().add(""); // base case: empty string

        for (int i = 1; i <= n; i++) {
            List<String> sentences = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                String word = s.substring(j, i);
                if (wordSet.contains(word)) {
                    for (String prev : dp.get(j)) {
                        if (prev.isEmpty()) sentences.add(word);
                        else sentences.add(prev + " " + word);
                    }
                }
            }
            dp.set(i, sentences);
        }

        return dp.get(n);
    }


    public static List<String> wordBreakII(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();

        // dp[i] = list of sentences for prefix s[0..i-1]
        List<List<String>> dp = new ArrayList<>();
        for (int i = 0; i <= n; i++) dp.add(new ArrayList<>());

        dp.getFirst().add(""); // base case: empty string

        for (int i = 1; i <= n; i++) {
            List<String> sentences = new ArrayList<>();
            for (String word : wordSet) {
                int len = word.length();
                if (i >= len && s.startsWith(word, i - len)) {
                    for (String prev : dp.get(i - len)) {
                        if (prev.isEmpty()) sentences.add(word);
                        else sentences.add(prev + " " + word);
                    }
                }
            }
            dp.set(i, sentences);
        }

        return dp.get(n);
    }


    public static List<String> wordBreakIIRecur(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        Map<String, List<String>> memo = new HashMap<>();
        return dfs(s, wordSet, memo);
    }

    private static List<String> dfs(String s, Set<String> wordSet, Map<String, List<String>> memo) {
        if (memo.containsKey(s)) return memo.get(s);

        List<String> result = new ArrayList<>();

        if (s.isEmpty()) {
            result.add("");
            return result;
        }

        for (String word : wordSet) {
            if (s.startsWith(word)) {
                String remaining = s.substring(word.length());
                List<String> subSentences = dfs(remaining, wordSet, memo);
                for (String sub : subSentences) {
                    if (sub.isEmpty()) result.add(word);
                    else result.add(word + " " + sub);
                }
            }
        }

        memo.put(s, result);
        return result;
    }


    public static List<String> wordBreakIIRecursion(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfs(s, 0, wordSet, memo);
    }

    private static List<String> dfs(String s, int start, Set<String> wordSet, Map<Integer, List<String>> memo) {

        if (memo.containsKey(start)) return memo.get(start);

        List<String> result = new ArrayList<>();

        if (start == s.length()) {
            result.add(""); // base case: reached end
            return result;
        }

        for (String word : wordSet) {
            int end = start + word.length();
            if (end <= s.length() && s.startsWith(word, start)) {
                List<String> subSentences = dfs(s, end, wordSet, memo);
                for (String sub : subSentences) {
                    if (sub.isEmpty()) result.add(word);
                    else result.add(word + " " + sub);
                }
            }
        }

        memo.put(start, result);
        return result;
    }


}
