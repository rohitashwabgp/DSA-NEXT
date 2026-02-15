package prac.dsa;

import java.util.*;

public class GameTheory {

    // Return true if Alice can win given n stones
    public boolean canAliceWin(int stones) {
        if (stones <= 0) return false;
        for (int i = 1; i <= 3; i++) {
            if (stones - i >= 0) {
                if (!canAliceWin(stones - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Return true if Alice can win given n stones
    public boolean canAliceWinDP(int stones) {
        boolean[] dp = new boolean[stones + 1];
        dp[0] = false;
        dp[1] = true;
        for (int i = 0; i < stones; i++) {
            dp[i] = false;
            for (int take = 1; take <= 3; take++) {
                if (stones - take >= 0 && !dp[stones - take]) {
                    dp[stones] = true; // found a move that forces opponent to lose
                    break;
                }
            }
        }
        return dp[stones];
    }

    public boolean canAliceWin(int[] piles) {
        int n = piles.length;
        boolean[] dp = new boolean[n + 1];
        dp[0] = false; // no piles → current player loses

        for (int i = 1; i <= n; i++) {
            // Take 1 pile
            dp[i] = i - 1 >= 0 && !dp[i - 1];
            // Take 2 piles
            if (i - 2 >= 0 && !dp[i - 2]) dp[i] = true;
        }

        return dp[n];
    }

    public int strangePrinter(String s, int left, int right) {
        if (left > right) return 0;

        int ans = 1 + strangePrinter(s, left + 1, right); // print s[left] separately

        // try to merge with same characters later
        for (int i = left + 1; i <= right; i++) {
            if (s.charAt(i) == s.charAt(left)) {
                int temp = strangePrinter(s, left, i - 1) + strangePrinter(s, i + 1, right);
                ans = Math.min(ans, temp);
            }
        }

        return ans;
    }


    public int shipWithinDays(int[] weights, int days) {
        int maxWeight = 0;
        int minWeight = Integer.MAX_VALUE;
        for (int weight : weights) {
            maxWeight = maxWeight + weight;
            minWeight = Math.min(minWeight, weight);
        }

        while (minWeight < maxWeight) {
            int mid = minWeight + (maxWeight - minWeight) / 2;
            if (canBeCarried(weights, days, mid)) {
                maxWeight = mid - 1;
            } else {
                minWeight = mid + 1;
            }
        }
        return minWeight;
    }

    private boolean canBeCarried(int[] weights, int days, int mid) {
        int dayCount = 0;
        int runningWeight = 0;
        for (int weight : weights) {
            if (runningWeight + weight < mid) {
                runningWeight = runningWeight + weight;
            } else {
                dayCount++;
                runningWeight = 0;
            }
            if (dayCount > days) return false;
        }
        return true;
    }


    boolean canAliceWin(int[] piles, int index) {
        if (index >= piles.length) return false; // no piles left → current player loses

        // If Alice takes 1 pile and Bob loses → Alice wins
        if (!canAliceWin(piles, index + 1)) return true;

        // If Alice takes 2 piles and Bob loses → Alice wins
        return index + 1 < piles.length && !canAliceWin(piles, index + 2);

        // All moves lead to Bob winning → Alice loses
    }

    public static List<Integer> findSubstring(String s, String[] words) {
        int left = 0;
        int length = words[0].length();
        int totalLength = length * words.length;
        Map<String, Integer> freqCount = new HashMap<>();
        for (String word : words) {
            freqCount.put(word, freqCount.getOrDefault(word, 0) + 1);
        }
        StringBuilder current = new StringBuilder();
        List<Integer> result = new ArrayList<>();
        for (int right = 0; right < s.length(); right++) {
            current.append(s.charAt(right));
            if (right - left + 1 == length) {
                if (freqCount.containsKey(current.toString())) {
                    Map<String, Integer> freqCountTemp = new HashMap<>(freqCount);
                    freqCountTemp.put(current.toString(), freqCountTemp.get(current.toString()) - 1);
                    if (freqCountTemp.get(current.toString()) == 0) freqCountTemp.remove(current.toString());
                    int startIndex = right + 1;
                    int endIndex = startIndex + (totalLength - length);
                    while (startIndex < endIndex && endIndex <= s.length()) {
                        String next = s.substring(startIndex, startIndex + length);
                        if (!freqCountTemp.containsKey(next)) {
                            break;
                        }
                        freqCountTemp.put(next, freqCountTemp.get(next) - 1);
                        if (freqCountTemp.get(next) == 0) freqCountTemp.remove(next);
                        startIndex = startIndex + length;
                    }

                    if (startIndex == endIndex) {
                        result.add(left);
                    }
                }
                current.delete(0, 1);
                left++;
            }
        }
        return result;
    }

    public static List<Integer> findSubstringOptimal(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (words.length == 0) return result;

        int wordLen = words[0].length();

        Map<String, Integer> wordCount = new HashMap<>();
        for (String w : words) wordCount.put(w, wordCount.getOrDefault(w, 0) + 1);

        for (int i = 0; i < wordLen; i++) { // offset
            int left = i, right = i;
            Map<String, Integer> currCount = new HashMap<>();
            int count = 0;

            while (right + wordLen <= s.length()) {
                String word = s.substring(right, right + wordLen);
                right = right + wordLen;

                if (wordCount.containsKey(word)) {
                    currCount.put(word, currCount.getOrDefault(word, 0) + 1);
                    count++;

                    while (currCount.get(word) > wordCount.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        currCount.put(leftWord, currCount.get(leftWord) - 1);
                        count--;
                        left = left + wordLen;
                    }

                    if (count == words.length) {
                        result.add(left);
                    }
                } else {
                    currCount.clear();
                    count = 0;
                    left = right;
                }
            }
        }
        return result;
    }

    public boolean stoneGameIX(int[] stones) {
        int[] count = new int[3];

        for (int stone : stones) {
            count[stone % 3]++;
        }

        int c0 = count[0];
        int c1 = count[1];
        int c2 = count[2];

        if (c0 % 2 == 0) {
            return c1 >= 1 && c2 >= 1;
        } else {
            return Math.abs(c1 - c2) > 2;
        }
    }

    public static int stoneGameVIII(int[] stones) {
        // build prefix sums
        int n = stones.length;
        int[] prefix = new int[n];
        prefix[0] = stones[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        // start recursion from index 1 because x > 1
        return stoneGameRec(prefix, 1);
    }

    // Separate recursive function
    private static int stoneGameRec(int[] prefix, int index) {
        int n = prefix.length;

        // base case: last possible move
        if (index == n - 1) return prefix[index];

        // current player can take this prefix or skip to next
        int take = prefix[index] - stoneGameRec(prefix, index + 1);
        int skip = stoneGameRec(prefix, index + 1);

        return Math.max(take, skip);
    }


    public static int stoneGame(int[] stones) {
        int n = stones.length;
        int[] prefix = new int[n];
        prefix[0] = stones[0];
        for (int i = 1; i < n; i++) prefix[i] = prefix[i - 1] + stones[i];

        return helper(prefix, 0, n - 1);
    }

    private static int helper(int[] prefix, int left, int right) {
        if (left == right) return 0;

        // sum of remaining if remove left
        int sumLeftRemoved = prefix[right] - prefix[left];
        int takeLeft = sumLeftRemoved - helper(prefix, left + 1, right);

        // sum of remaining if remove right
        int sumRightRemoved = (left > 0 ? prefix[right - 1] - prefix[left - 1] : prefix[right - 1]);
        int takeRight = sumRightRemoved - helper(prefix, left, right - 1);

        return Math.max(takeLeft, takeRight);
    }

    public static int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;

        // Pair each stone with combined value
        int[][] stones = new int[n][2]; // [aliceValue, bobValue]
        for (int i = 0; i < n; i++) {
            stones[i][0] = aliceValues[i];
            stones[i][1] = bobValues[i];
        }

        // Sort stones by descending (alice + bob)
        Arrays.sort(stones, (a, b) -> (b[0] + b[1]) - (a[0] + a[1]));

        int aliceScore = 0;
        int bobScore = 0;

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                // Alice's turn
                aliceScore = aliceScore + stones[i][0];
            } else {
                // Bob's turn
                bobScore = bobScore + stones[i][1];
            }
        }

        return Integer.compare(aliceScore, bobScore);
    }


    public int stoneGameVI2(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;

        // Since values ≤ 100, combined ≤ 200
        // Create fixed-size buckets
        List<Integer>[] buckets = new ArrayList[201]; // 0-200

        for (int i = 0; i <= 200; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Bucket sort: O(n)
        for (int i = 0; i < n; i++) {
            int combined = aliceValues[i] + bobValues[i];
            buckets[combined].add(i);
        }

        // Process in descending order: O(201 + n) = O(n)
        int aliceScore = 0, bobScore = 0;
        boolean aliceTurn = true;

        for (int val = 200; val >= 0; val--) {
            for (int idx : buckets[val]) {
                if (aliceTurn) {
                    aliceScore += aliceValues[idx];
                } else {
                    bobScore += bobValues[idx];
                }
                aliceTurn = !aliceTurn;
            }
        }

        return Integer.compare(aliceScore, bobScore);
    }

    public boolean isScramble(String s1, String s2, int index) {
        if (s1.equals(s2)) return true;
        if (index == s1.length()) return false;
        for (int i = index; i < s1.length(); i++) {
            String scr1 = s1.substring(0, i + 1);
            String scr2 = s1.substring(i + 1);

            String scr3 = s2.substring(0, i + 1);
            String scr4 = s2.substring(i + 1);

            if (isScramble(scr2.concat(scr1), s2, i) || isScramble(s1, scr4.concat(scr3), i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScramble(String s1, String s2) {
        // Base case: strings are equal
        if (s1.equals(s2)) return true;

        // Prune impossible case: different characters
        if (!hasSameChars(s1, s2)) return false;

        int n = s1.length();
        for (int i = 1; i < n; i++) { // split position
            // Case 1: no swap
            if (isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) {
                return true;
            }
            // Case 2: swap
            if (isScramble(s1.substring(0, i), s2.substring(n - i)) && isScramble(s1.substring(i), s2.substring(0, n - i))) {
                return true;
            }
        }

        return false;
    }

    int count = 0;

    public int numDistinct(String s, String t, int sIndex, int tIndex) {
        if (tIndex >= t.length()) return 1;
        if (sIndex >= s.length()) return 0;
        int count = 0;
        if (s.charAt(sIndex) == t.charAt(tIndex)) {
            count = count + numDistinct(s, t, sIndex + 1, tIndex + 1);
        }
        count = count + numDistinct(s, t, sIndex + 1, tIndex);

        return count;
    }

    public int numDistinct(String s, String t) {
        return helper(s, t, 0, 0);
    }

    private int helper(String s, String t, int sIndex, int tIndex) {
        // If we reached end of t, we found a subsequence
        if (tIndex == t.length()) return 1;
        // If we reached end of s but t is not finished, no subsequence
        if (sIndex == s.length()) return 0;

        int count = 0;
        // Option 1: match current character if it matches
        if (s.charAt(sIndex) == t.charAt(tIndex)) {
            count += helper(s, t, sIndex + 1, tIndex + 1);
        }
        // Option 2: skip current character of s
        count += helper(s, t, sIndex + 1, tIndex);

        return count;
    }

    // Helper: check if two strings have same character counts
    private static boolean hasSameChars(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int[] count = new int[26];
        for (char c : s1.toCharArray()) count[c - 'a']++;
        for (char c : s2.toCharArray()) count[c - 'a']--;
        for (int c : count) if (c != 0) return false;
        return true;
    }


    public static void main(String[] args) {
        String s = "aaa";
        String[] words = {"a", "a"};
        System.out.println(findSubstring(s, words));
    }


}
