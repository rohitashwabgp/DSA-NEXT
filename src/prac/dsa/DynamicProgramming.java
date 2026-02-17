package prac.dsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DynamicProgramming {

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

    private static int list() {
        int[] numbs = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] tails = new int[numbs.length];
        int size = 0;
        for (int num : numbs) {
            int left = 0;
            int right = size;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tails[left] = num;
            if (left == size) size++;
        }
        return size;
    }

    public static long getMinimum(long n) {
        long left = 1;
        long right = n;
        long answer = n;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (isValid(n, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private static boolean isValid(long n, long k) {
        long x = 0;
        long y = 0;
        long remaining = n;

        while (remaining > 0) {
            long take = Math.min(k, remaining);
            x = x + take;
            remaining = remaining - take;

            long twentyPercent = remaining / 5;
            y += twentyPercent;
            remaining -= twentyPercent;
        }

        return x >= y;
    }


    public static List<Integer> lis(int[] numbs) {
        int n = numbs.length;
        int[] tails = new int[n];
        int[] prev = new int[n];
        int[] pos = new int[n];

        Arrays.fill(prev, -1);
        int size = 0;

        for (int i = 0; i < n; i++) {
            int left = 0, right = size;
            while (left < right) {
                int mid = (left + right) / 2;
                if (tails[mid] < numbs[i]) left = mid + 1;
                else right = mid;
            }
            tails[left] = numbs[i];
            pos[left] = i;
            if (left > 0) prev[i] = pos[left - 1];
            if (left == size) size++;
        }

        List<Integer> res = new ArrayList<>();
        int k = pos[size - 1];
        while (k != -1) {
            res.add(numbs[k]);
            k = prev[k];
        }
        Collections.reverse(res);
        return res;

    }

}
