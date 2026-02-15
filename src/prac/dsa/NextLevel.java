package prac.dsa;

import java.util.*;
import java.util.stream.Collectors;

public class NextLevel {
    private static int lis1() {
        int[] nums = {10, 1, 2, 4, 7, 2};
        int limit = 5;

        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();
        int left = 0, right = 0;
        int maxLen = 0;

        while (right < nums.length) {
            // Maintain decreasing order in maxDeque
            while (!maxDeque.isEmpty() && nums[right] > maxDeque.peekLast()) {
                maxDeque.pollLast();
            }
            maxDeque.offerLast(nums[right]);

            // Maintain increasing order in minDeque
            while (!minDeque.isEmpty() && nums[right] < minDeque.peekLast()) {
                minDeque.pollLast();
            }
            minDeque.offerLast(nums[right]);

            // Shrink window if condition violated
            while (!maxDeque.isEmpty() && !minDeque.isEmpty() && maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                if (nums[left] == maxDeque.peekFirst()) maxDeque.pollFirst();
                if (nums[left] == minDeque.peekFirst()) minDeque.pollFirst();
                left++;
            }

            // Update max length
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }

        return maxLen;
    }

    static class StudentLeaderboard {
        Map<String, Integer> scoreCard = new HashMap<>();
        TreeMap<Integer, Set<String>> scoreCardOrdered = new TreeMap<>();

        public boolean add(String name, int score) {
            if (scoreCard.containsKey(name)) {
                return false;
            }
            scoreCard.put(name, score);
            scoreCardOrdered.computeIfAbsent(score, (k) -> new HashSet<>()).add(name);
            return true;
        }

        public boolean update(String name, int score) {
            if (!scoreCard.containsKey(name)) {
                return false;
            }
            Integer oldScore = scoreCard.put(name, score);
            scoreCardOrdered.get(oldScore).remove(name);
            if (scoreCardOrdered.get(oldScore).isEmpty()) {
                scoreCardOrdered.remove(oldScore);
            }
            scoreCardOrdered.computeIfAbsent(score, (k) -> new HashSet<>()).add(name);
            return true;
        }

        public List<String> topK(int k) {
            return scoreCardOrdered.descendingMap().values().stream().flatMap(Collection::stream).limit(k).collect(Collectors.toList());
        }

        public List<String> aboveK(int k) {
            return scoreCardOrdered.tailMap(k, true).values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        }
    }

    private static int maxKSum() {
        int[] nums = {2, -1, 5, -2, 3, 2};
        int k = 3;
        int left = 0;
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum = sum + nums[right];
            if (right - left + 1 == k) {
                maxSum = Math.max(maxSum, sum);
                sum = sum - nums[left];
                left++;
            }
        }
        return maxSum;
    }

    static class HitCounter {
        private int[] times;   // store timestamp for each slot
        private int[] hits;    // store hit count for each slot

        public HitCounter() {
            times = new int[300]; // 5 minutes window = 300 seconds
            hits = new int[300];
        }

        public void hit(int timestamp) {
            int idx = timestamp % 300;
            if (times[idx] != timestamp) {
                // Slot is old, reset
                times[idx] = timestamp;
                hits[idx] = 1;
            } else {
                // Slot already has current timestamp
                hits[idx]++;
            }
        }

        // Return hits in past 5 minutes (300 seconds) from given timestamp
        public int getHits(int timestamp) {
            int total = 0;
            for (int i = 0; i < 300; i++) {
                if (timestamp - times[i] < 300) {
                    total += hits[i];
                }
            }
            return total;
        }
    }

    public static int getMinimum() {
        int[] nums = {1, 3, 3, 3, 2};
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // nums[mid] == nums[right], cannot decide, reduce search space
                right--;
            }
        }
        return nums[left];

    }

    private static int minCutStick() {
        int n = 7;
        int[] cuts = {1, 3, 4, 5};
        int m = cuts.length;
        int[] allCuts = new int[m + 2];
        allCuts[0] = 0;
        System.arraycopy(cuts, 0, allCuts, 1, m);
        allCuts[m + 1] = n;
        Arrays.sort(allCuts);
        int len = allCuts.length;
        int[][] dp = new int[len][len];

        for (int l = 2; l < len; l++) {
            for (int i = 0; i + l < len; i++) {
                int j = i + l;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j] + (allCuts[j] - allCuts[i]));
                }
            }
        }
        return dp[0][len - 1]; // min cost for the whole stick
    }

    // Recursive function
    static int minCost(int[] allCuts, int i, int j) {
        if (i + 1 == j) return 0;
        int cost = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) {
            int currentCost = minCost(allCuts, i, k) + minCost(allCuts, k, j) + (allCuts[j] - allCuts[i]);
            cost = Math.min(cost, currentCost);
        }
        return cost;
    }

    // Separate method to prepare allCuts array
    static int prepareAllCuts(int n, int[] cuts) {
        int[] allCuts = new int[cuts.length + 2];
        allCuts[0] = 0;
        allCuts[allCuts.length - 1] = n;
        System.arraycopy(cuts, 0, allCuts, 1, cuts.length);
        java.util.Arrays.sort(allCuts);
        return minCost(allCuts, 0, allCuts.length - 1);
    }

    static int burstBalloon() {
        int[] nums = {3, 1, 5, 8};
        int[] extended = new int[nums.length + 2];
        System.arraycopy(nums, 0, extended, 1, nums.length);
        extended[0] = 1;
        extended[extended.length - 1] = 1;

        int[][] dp = new int[extended.length][extended.length];
        // int maxCost = Integer.MIN_VALUE;
        for (int len = 2; len < extended.length; len++) {
            for (int i = 0; i + len < extended.length; i++) {
                int j = i + len;
                int cost;
                for (int k = i + 1; k < j; k++) {
                    cost = dp[i][k] + (extended[i] * extended[k] * extended[j]) + dp[k][j];
                    dp[i][j] = Math.max(cost, dp[i][j]);
                }
            }
        }
        return dp[0][extended.length - 1];
    }

    // Recursive function to compute minimum score in interval [i, j]
    static int minScore(int[] values, int i, int j) {
        if (i + 1 >= j) return 0; // base case: interval too small for triangle

        int cost = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) { // pick vertex k inside interval
            int currentCost = minScore(values, i, k) + values[i] * values[k] * values[j] + minScore(values, k, j);
            cost = Math.min(cost, currentCost);
        }
        return cost;
    }

    // Prepare input and call recursion
    static int minScoreTriangulation(int[] values) {
        return minScore(values, 0, values.length - 1);
    }

    static int minScoreTriangulationDp(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];

        // Bottom-up interval DP
        // len = interval length
        for (int len = 2; len < n; len++) {      // interval must be at least 3 vertices
            for (int i = 0; i + len < n; i++) {
                int j = i + len;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i + 1; k < j; k++) {
                    int cost = dp[i][k] + values[i] * values[k] * values[j] + dp[k][j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1]; // full polygon
    }

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    static int maxPath = Integer.MIN_VALUE;

    static int maxPathTree(TreeNode node) {
        if (node == null) return 0;
        int left = Math.max(0, maxPathTree(node.left));
        int right = Math.max(0, maxPathTree(node.right));
        int current = node.val + left + right;
        maxPath = Math.max(current, maxPath);
        return Math.max(node.val + left, node.val + right);
    }

    static boolean isCycle(ListNode node) {
        ListNode slow = node;
        ListNode fast = node;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    static int kthSmallest = -1;
    static int count = 0;

    static void kthSmallestBST(TreeNode node, int k) {
        if (node == null) return;
        kthSmallestBST(node.left, k);
        count++;
        if (count == k) {
            kthSmallest = node.val;
            return;
        }
        kthSmallestBST(node.right, k);
    }

    static ListNode reverseKGroup(ListNode node, int k) {
        ListNode temp = node;
        int count = 0;

        while (temp != null && count < k) {
            temp = temp.next;
            count++;
        }

        if (count < k) return node;

        ListNode prev = null;
        ListNode curr = node;

        for (int i = 0; i < k; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        node.next = reverseKGroup(curr, k);
        return prev;
    }

    static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    static boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;

        if (node.val <= min || node.val >= max) return false;

        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    static int max = 0;

    static int diameter(TreeNode node) {
        if (node == null) return 0;
        int left = diameter(node.left);
        int right = diameter(node.right);
        max = Math.max(max, left + right);
        return 1 + Math.max(left, right);
    }

    static TreeNode LCA(TreeNode node, TreeNode node1, TreeNode node2) {
        if (node == null || node == node1 || node == node2) return node;
        TreeNode leftSubTree = LCA(node.left, node1, node2);
        TreeNode rightSubTree = LCA(node.right, node1, node2);
        if (leftSubTree != null && rightSubTree != null) return node;
        return leftSubTree != null ? leftSubTree : rightSubTree;
    }

    static int maxWidth(TreeNode root) {
        if (root == null) return 0;

        long maxWidth = 0;

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0L));

        while (!queue.isEmpty()) {
            int size = queue.size();
            long minIndex = queue.peek().index;  // normalize to prevent overflow
            long first = 0, last = 0;

            for (int i = 0; i < size; i++) {
                Pair current = queue.poll();
                long currIndex = current.index - minIndex;

                if (i == 0) first = currIndex;
                if (i == size - 1) last = currIndex;

                if (current.node.left != null) queue.offer(new Pair(current.node.left, 2 * currIndex + 1));

                if (current.node.right != null) queue.offer(new Pair(current.node.right, 2 * currIndex + 2));
            }

            maxWidth = Math.max(maxWidth, last - first + 1);
        }

        return (int) maxWidth;
    }

    static int rottenOrange() {
        int[][] grid = {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int fresh = 0;

        Queue<int[]> queue = new LinkedList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 2) {
                    queue.offer(new int[]{row, col});
                } else if (grid[row][col] == 1) {
                    fresh++;
                }
            }
        }

        if (fresh == 0) return 0;
        int minutes = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean currentRotten = false;
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];
                for (int[] direction : directions) {
                    int dRow = direction[0] + row;
                    int dCol = direction[1] + col;
                    if (dRow < 0 || dCol < 0 || dCol >= grid[0].length || dRow >= grid.length) {
                        continue;
                    }
                    if (grid[dRow][dCol] == 1) {
                        fresh--;
                        grid[dRow][dCol] = 2;
                        queue.offer(new int[]{dRow, dCol});
                        currentRotten = true;
                    }
                }
            }
            if (currentRotten) minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }


    static class Pair {
        TreeNode node;
        long index;

        Pair(TreeNode node, long index) {
            this.node = node;
            this.index = index;
        }
    }

    static int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        Queue<int[]> queue = new LinkedList<>();
        int[][] dist = new int[m][n];

        // Initialize
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    dist[i][j] = -1; // mark unvisited
                }
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int[] dir : directions) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nc >= 0 && nr < m && nc < n && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return dist;
    }

    static boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    static boolean dfs(char[][] board, String word, int i, int j, int index) {
        if (index == word.length()) return true;
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return false;
        if (board[i][j] != word.charAt(index)) return false;

        char temp = board[i][j];
        board[i][j] = '#'; // mark visited

        boolean found = dfs(board, word, i + 1, j, index + 1) ||
                dfs(board, word, i - 1, j, index + 1) ||
                dfs(board, word, i, j + 1, index + 1) ||
                dfs(board, word, i, j - 1, index + 1);

        board[i][j] = temp; // backtrack

        return found;
    }

    static int wordLadder() {
        String beginWord = "hit";
        String endWord = "cog";
        String[] wordList = {"hot", "dot", "dog", "lot", "log", "cog"};
        Set<String> wordSet = new HashSet<>(Arrays.asList(wordList));
        Set<String> startSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        startSet.add(beginWord);
        endSet.add(endWord);
        wordSet.remove(beginWord);
        if (!wordSet.contains(endWord)) return 0;
        int level = 1;
        while (!startSet.isEmpty() && !endSet.isEmpty()) {
            if (startSet.size() > endSet.size()) {
                Set<String> temp = startSet;
                startSet = endSet;
                endSet = temp;
            }

            Set<String> currentSet = new HashSet<>();
            for (String current : startSet) {
                char[] currentChars = current.toCharArray();
                for (int i = 0; i < currentChars.length; i++) {
                    char original = currentChars[i];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        currentChars[i] = ch;
                        String newWord = new String(currentChars);
                        if (endSet.contains(newWord)) {
                            //early exit
                            return level + 1;
                        }

                        if(wordSet.contains(newWord)) {
                            currentSet.add(newWord);
                            wordSet.remove(newWord);
                        }
                    }
                    currentChars[i] = original;
                }
            }
            startSet = currentSet;
            level++;
        }
        return 0;
    }


    public static void main(String[] args) {
//        System.out.println(lis1());
//        System.out.println(maxKSum());
//        System.out.println(minCutStick());
        System.out.println(rottenOrange());
    }
}
