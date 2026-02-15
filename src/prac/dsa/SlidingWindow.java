package prac.dsa;

import java.util.HashMap;
import java.util.Map;

public class SlidingWindow {

    public static int atMostK() {
        String s = "eceba";
        int k = 2;
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : s.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        Map<Character, Integer> window = new HashMap<>();

        int left = 0;
        int distinct = 0;
        int max = 0;
        for (int right = 0; right < s.length(); right++) {
            char curr = s.charAt(right);
            window.put(curr, window.getOrDefault(curr, 0) + 1);
            while (window.size() > k) {
                char leftCh = s.charAt(left);
                window.put(leftCh, window.get(leftCh) - 1);
                if (window.get(leftCh) == 0) {
                    window.remove(leftCh);
                }
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public static int subarraysWithKDistinct(int[] nums, int k) {

        Map<Integer, Integer> window = new HashMap<>();
        int left = 0;
        int count = 0;
        for (int right = 0; right < nums.length; right++) {
            int curr = nums[right];
            window.put(curr, window.getOrDefault(curr, 0) + 1);
            while (window.size() > k) {
                int leftCh = nums[left];
                window.put(leftCh, window.get(leftCh) - 1);
                if (window.get(leftCh) == 0) {
                    window.remove(leftCh);
                }
                left++;
            }
            count = count + (right - left + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        // System.out.println(atMostK());
        int[] nums = {1, 2, 1, 2, 3};
        int k = 2;

        System.out.println(subarraysWithKDistinct(nums, k) - subarraysWithKDistinct(nums, k - 1));
    }
}
