package prac.dsa;

import java.util.*;

public class Monotonic {

    private static int[] problem1(int[] nums, int k) {

        Deque<Integer> queue = new ArrayDeque<>();
        int left = 0;
        int[] result = new int[nums.length - k + 1];
        for (int right = 0; right < nums.length; right++) {
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[right]) {
                queue.pollLast();
            }
            queue.offerLast(right);
            if (right - left + 1 == k) {
                int ele = queue.peek();
                if (ele == left) {
                    queue.poll();
                }
                result[left] = nums[ele];
                left++;
            }
        }
        return result;
    }


    private static int[] temperature(int[] temp) {
        Stack<Integer> queue = new Stack<>();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            while (!queue.isEmpty() && temp[queue.peek()] < temp[i]) {
                int idx = queue.pop();
                result[idx] = i - idx + 1;
            }
            queue.push(i);
        }
        return result;
    }

//    private static int[][] closest(int[][] points, int k) {
//        PriorityQueue<int[]> queue = new PriorityQueue<>((b, a) ->
//                ((b[1] * b[1]) + (b[0] * b[0]) )- ((a[1] * a[1]) + (a[0] * a[0]))
//        );
//
//        for (int[] point : points) {
//            if (queue.size() < k)
//                queue.offer(point);
//            else {
//                int[] poi = queue.peek();
//                boolean isNearer =
//                        poi[0] * poi[0] + poi[1] * poi[1] > point[0] * point[0] + point[1] * point[1];
//                if (isNearer) {
//                    queue.poll();
//                    queue.offer(point);
//                }
//
//            }
//        }
//
//    }

    //Wrong for negative yay!!
    public static int shortestSubarray(int[] nums, int k) {
        int left = 0;
        // int[] freqMap = new int[nums.ll]
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        long[] prefix = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        for (int right = 0; right < prefix.length; right++) {
            while (prefix[right] < prefix[left]) {
                left++;
            }
            while (prefix[right] - prefix[left] >= k) {
                minLength = Math.min(minLength, right - left);
                left++;
            }

        }
        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }

    public static int shortestSubarrayNext(int[] nums, int k) {

        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        Deque<Integer> dq = new LinkedList<>();
        int minLen = n + 1;

        for (int i = 0; i <= n; i++) {
            while (!dq.isEmpty() && prefix[i] - prefix[dq.peekFirst()] >= k) {
                minLen = Math.min(minLen, i - dq.pollFirst());
            }
            while (!dq.isEmpty() && prefix[i] <= prefix[dq.peekLast()]) {
                dq.pollLast();
            }
            dq.offerLast(i);
        }

        return minLen <= n ? minLen : -1;
    }

    public int shortestSubarrayII(int[] nums, int k) {

        int n = nums.length;
        long sum = 0;
        int minLen = Integer.MAX_VALUE;

        Deque<long[]> dq = new ArrayDeque<>();
        dq.offerLast(new long[]{0, -1});   // {prefixSum, index}

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            // Check if we found valid subarray
            while (!dq.isEmpty() && sum - dq.peekFirst()[0] >= k) {
                minLen = Math.min(minLen, i - (int) dq.pollFirst()[1]);
            }
            // Maintain increasing prefix sums
            while (!dq.isEmpty() && sum <= dq.peekLast()[0]) {
                dq.pollLast();
            }
            dq.offerLast(new long[]{sum, i});
        }

        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    private static int[] nextGreater(int[] nums) {
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums.length * 2; i++) {
            int j = i % nums.length;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[j]) {
                int idx = stack.pop();
                result[idx] = nums[j];
            }
            stack.push(j);
        }

        return result;
    }

    private static int[] windowMaxMin(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];

        int size = nums.length;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            while (!queue.isEmpty() && nums[queue.peek()] >= nums[i]) {
                queue.pop();
            }

            left[i] = queue.isEmpty() ? -1 : queue.peekFirst();
            queue.push(i);
        }
        queue.clear();

        for (int i = size - 1; i >= 0; i--) {
            while (!queue.isEmpty() && nums[queue.peek()] >= nums[i]) {
                queue.poll();
            }

            right[i] = queue.isEmpty() ? size : queue.peek();
            queue.push(i);
        }
        int[] result = new int[size + 1];
        for (int i = 0; i < size; i++) {
            int length = right[i] - left[i] - 1;
            result[length] = Math.max(nums[i], result[length]);
        }
        for (int i = size - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        int[] ans = new int[size];
        for (int i = 1; i <= size; i++) {
            ans[i - 1] = result[i];
        }

        return ans;

    }

    static class State {
        public int to;
        public int strength;

        public State(int to, int strength) {
            this.to = to;
            this.strength = strength;
        }
    }

    private static int[] neuron(int size, List<Integer> neuronFrom, List<Integer> neuronTo, List<Integer> strength) {

        List<List<Integer>> childrens = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            childrens.add(new ArrayList<>());
        }
        for (int i = 0; i < neuronFrom.size(); i++) {
            childrens.get(neuronFrom.get(i)).add(neuronTo.get(i));
            childrens.get(neuronTo.get(i)).add(neuronFrom.get(i));
        }

        int[] result = new int[size + 1];
        int[] val = new int[size + 1];
        for (int i = 1; i <= size; i++) {
            val[i] = strength.get(i - 1) == 0 ? -1 : 1;
        }
        first(childrens, 1, 0, val, result);
        reroot(childrens, 1, 0, result);
        return Arrays.stream(result).skip(1).toArray();
    }

    private static void reroot(List<List<Integer>> childrens, int current, int parent, int[] result) {

        for (Integer child : childrens.get(current)) {
            if (child == parent) continue;
            int originalCurrent = result[current];
            int originalChild = result[child];

            result[current] = result[current] - Math.max(0, result[child]);
            result[child] = result[child] + Math.max(0, result[current]);
            reroot(childrens, child, current, result);
            result[current] = originalCurrent;
            result[child] = originalChild;
        }
    }

    private static void first(List<List<Integer>> childrens, int current, int parent, int[] val, int[] result) {
        result[current] = val[current];
        for (Integer child : childrens.get(current)) {
            if (child == parent) continue;
            first(childrens, child, current, val, result);
            result[current] = result[current] + Math.max(result[child], 0);
        }
    }


    public static void main(String[] args) {
        //  System.out.println(Arrays.toString(temperature(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        // System.out.println(shortestSubarrayNext(new int[]{84, -37, 32, 40, 95}, 167));
        System.out.println(Arrays.toString(nextGreater(new int[]{1, 2, 1})));
    }
}
