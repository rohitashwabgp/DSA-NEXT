package prac.dsa;

public class Binary {


    public int[] searchRange(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        int start = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
            if (target == nums[mid]) {
                start = mid;
            }
        }
        left = 0;
        right = 0;
        int end = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target > nums[mid]) {
                left = mid + 1;

            } else {
                right = mid - 1;
            }

            if (target == nums[mid]) {
                end = mid;
            }
        }
        return new int[]{start, end};
    }

    public boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target)
                return true;
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right--;
            } else if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }

    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[left] == nums[right] && nums[right] == nums[mid]) {
                left++;
                right--;
            } else if (nums[left] <= nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }

    public int findRadius(int[][] matrix) {
        int[][] directions = new int[][]{{0, -1}, {1, 0}, {0, 1}, {0, -1}};
        int[][] dp = new int[][]
        for (int[] dir : directions) {

        }
    }
}
