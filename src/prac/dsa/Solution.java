package prac.dsa;

import java.util.*;

public class Solution {
    static class Job {
        int start, end, profit;

        Job(int s, int e, int p) {
            start = s;
            end = e;
            profit = p;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int size = startTime.length;
        Job[] jobs = new Job[startTime.length];
        for (int i = 0; i < startTime.length; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        Arrays.sort(jobs, Comparator.comparingInt(a -> a.end));

        int[] dp = new int[size];
        dp[0] = jobs[0].profit;
        for (int i = 1; i < size; i++) {
            int left = 0;
            int right = i - 1;
            int idx = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (jobs[i].start < jobs[mid].end) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                    idx = mid;
                }
            }
            dp[i] = Math.max(dp[i - 1], jobs[i].profit + idx == -1 ? 0 : dp[idx]);
        }
        return dp[size - 1];
    }

    public boolean isMatchI(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        // Empty pattern matches empty string
        dp[0][0] = true;

        // Handle patterns like "*", "**", "***"
        for (int j = 1; j <= m; j++) {
            if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 1];
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                char pc = p.charAt(j - 1);
                if (pc == s.charAt(i - 1) || pc == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pc == '*') {
                    // '*' can match empty or any sequence
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        return dp[n][m];
    }

    public boolean isMatch(String s, String p) {
        Boolean[][] dp = new Boolean[s.length() + 1][p.length() + 1];
        for (Boolean[] each : dp)
            Arrays.fill(each, null);
        return helper(s, p, 0, 0, dp);
    }

    private boolean helper(String s, String p, int i, int j, Boolean[][] dp) {
        if (dp[i][j] != null) return dp[i][j];
        // Both finished
        if (i == s.length() && j == p.length()) return dp[i][j] = true;

        // Pattern finished but string not
        if (j == p.length()) return dp[i][j] = false;

        // String finished but pattern may have '*' remaining
        if (i == s.length()) {
            for (int k = j; k < p.length(); k++)
                if (p.charAt(k) != '*') return dp[i][j] = false;
            return dp[i][j] = true;
        }

        char sc = s.charAt(i);
        char pc = p.charAt(j);

        if (pc == '?') {
            return dp[i][j] = helper(s, p, i + 1, j + 1, dp);
        } else if (pc == '*') {
            // '*' matches 0 or more characters
            return dp[i][j] = helper(s, p, i, j + 1, dp) || helper(s, p, i + 1, j, dp);
        } else {
            return dp[i][j] = sc == pc && helper(s, p, i + 1, j + 1, dp);
        }
    }

    public int numRollsToTarget(int[] wt, int[] val, int[] qty, int W) {

        ArrayList<int[]> items = new ArrayList<>();

        // convert to 0/1 items
        for (int i = 0; i < wt.length; i++) {
            int q = qty[i];
            int power = 1;

            while (q > 0) {
                int take = Math.min(power, q);
                items.add(new int[]{wt[i] * take, val[i] * take});
                q -= take;
                power *= 2;
            }
        }

        // normal 0/1 knapsack
        int[] dp = new int[W + 1];

        for (int[] it : items) {
            int w = it[0];
            int v = it[1];

            for (int j = W; j >= w; j--) {
                dp[j] = Math.max(dp[j], dp[j - w] + v);
            }
        }

        return dp[W];
    }

    public boolean isRectangleCover(int[][] rectangles) {

        Set<String> set = new HashSet<>();

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        int area = 0;

        for (int[] r : rectangles) {
            int x1 = r[0], y1 = r[1], x2 = r[2], y2 = r[3];

            minX = Math.min(minX, x1);
            minY = Math.min(minY, y1);
            maxX = Math.max(maxX, x2);
            maxY = Math.max(maxY, y2);

            area = area + (x2 - x1) * (y2 - y1);

            String[] corners = {x1 + " " + y1, x1 + " " + y2, x2 + " " + y1, x2 + " " + y2};

            for (String c : corners) {
                if (!set.add(c)) set.remove(c);
            }
        }

        int bigArea = (maxX - minX) * (maxY - minY);

        if (area != bigArea) return false;

        if (set.size() != 4) return false;

        return set.contains(minX + " " + minY) && set.contains(minX + " " + maxY) && set.contains(maxX + " " + minY) && set.contains(maxX + " " + maxY);
    }


    public int maxWeight(int[][] arr) {

        Arrays.sort(arr, (a, b) -> a[1] - b[1]); // sort by end

        int n = arr.length;
        int[] dp = new int[n];

        dp[0] = arr[0][2];

        for (int i = 1; i < n; i++) {

            int take = arr[i][2];

            int j = lastNonOverlap(arr, i);

            if (j != -1) take += dp[j];

            dp[i] = Math.max(dp[i - 1], take);
        }

        return dp[n - 1];
    }

    private int lastNonOverlap(int[][] arr, int i) {

        int l = 0, r = i - 1, ans = -1;

        while (l <= r) {
            int m = (l + r) / 2;

            if (arr[m][1] <= arr[i][0]) {
                ans = m;
                l = m + 1;
            } else r = m - 1;
        }
        return ans;
    }


    public int numRollsToTarget(int n, int ko, int target) {
        int mod = 1000_000_007;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int am = 1; am <= target; am++) {
                for (int k = 1; k <= ko; k++) {
                    if (am - k >= 0) {
                        dp[i][am] = (dp[i][am] + dp[i - 1][am - k]) % mod;
                    }
                }
            }
        }
        return dp[n][target];
    }


}
