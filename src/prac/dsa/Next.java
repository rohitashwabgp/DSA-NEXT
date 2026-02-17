package prac.dsa;

public class Next {

    public int maxSubArray(int[] nums) {
        int maxi = Integer.MIN_VALUE;
        int current = Integer.MIN_VALUE;

        for (int num : nums) {
            current = Math.max(current + num, current);
            maxi = Math.max(maxi, current);
        }
        return maxi;

    }

    int minCoin = Integer.MAX_VALUE;

    public int coinChange(int[] coins, int amount) {

        coinChangeC(coins, amount, 0, 0);
        return minCoin == Integer.MAX_VALUE ? -1 : minCoin;
    }

    public void coinChangeC(int[] coins, int amount, int index, int toto) {
        if (index == coins.length || amount < 0) return;
        if (amount == 0) {
            minCoin = Math.min(toto, minCoin);
        }
        coinChangeC(coins, amount - coins[index], index, toto + 1);
        coinChangeC(coins, amount, index + 1, toto);
    }

    public int minDistance(String word1, String word2) {
        return solve(word1, word2, 0, 0);
    }

    private int solve(String w1, String w2, int i, int j) {

        // Base case 1: word1 finished → insert remaining of word2
        if (i == w1.length()) {
            return w2.length() - j;
        }

        // Base case 2: word2 finished → delete remaining of word1
        if (j == w2.length()) {
            return w1.length() - i;
        }

        // If characters match → move both
        if (w1.charAt(i) == w2.charAt(j)) {
            return solve(w1, w2, i + 1, j + 1);
        }

        // If not match → try all 3 operations
        int delete = solve(w1, w2, i + 1, j);        // delete from word1
        int insert = solve(w1, w2, i, j + 1);        // insert into word1
        int replace = solve(w1, w2, i + 1, j + 1);   // replace

        return 1 + Math.min(delete, Math.min(insert, replace));
    }



    public int lengthOfLIS(int[] nums, int index, int prevIndex) {

        if (index == nums.length) return 0;

        int taken = 0;
        if (prevIndex == -1 || nums[prevIndex] < nums[index]) {
            taken = 1 + lengthOfLIS(nums, index + 1, index);
        }
        int notTaken = lengthOfLIS(nums, index + 1, prevIndex);
        return Math.max(taken, notTaken);
    }

    public int lisBinary(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;

        for (int item : nums) {
            int left = 0;
            int right = size;
            while (right > left) {
                int mid = left + (right - left) / 2;
                if (tails[mid] > item) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            tails[left] = item;
            if (size == left) {
                size++;
            }
        }
        return size;
    }


}
