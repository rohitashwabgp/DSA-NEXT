package prac.dsa;

public class BinarySearch {

    int binary() {
        int[] arr = {2, 5, 8, 12, 16, 23, 38};
        int x = 16;

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                return mid;
            } else if (arr[mid] > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    static int[] binary2() {
        int[] arr = {2, 4, 4, 4, 7, 9};
        int x = 4;
        int left = 0;
        int right = arr.length - 1;
        int startIndex = -1;
        int endIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                startIndex = mid;           // store index
                right = mid - 1;     // move left to find earlier occurrence
            } else if (arr[mid] < x) {
                left = mid + 1;      // search right half
            } else {
                right = mid - 1;     // search left half
            }
        }
        left = 0;
        right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                endIndex = mid;           // store index
                left = mid + 1;     // move left to find earlier occurrence
            } else if (arr[mid] > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return new int[]{startIndex, endIndex};
    }

    static int rotatedArray() {
        int[] arr = {4, 5, 6, 7, 0, 1, 2};
        int x = 0;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                return mid;
            }
            if (arr[left] <= arr[mid]) {
                if (x >= arr[left] && x < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // Right half is sorted
                if (x > arr[mid] && x <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }

    private static int minimum() {
        int[] input = {3, 4, 5, 1, 2};

        int left = 0;
        int right = input.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (input[mid] > input[right]) {
                // Minimum is in right half
                left = mid + 1;
            } else {
                // Minimum is in left half including mid
                right = mid;
            }
        }

        return input[left];
    }

    private static int allocate() {
        int[] books = {12, 34, 67, 90};
        int student = 2;
        int low = 0;
        int high = 0;

        for (int book : books) {
            low = Math.max(low, book);
            high = high + book;
        }
        int result = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canAllocate(mid, books, student)) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    private static boolean canAllocate(int mid, int[] books, int students) {
        int student = 1;
        int pages = 0;
        for (int book : books) {
            if (pages + book > mid) {
                student++;
                pages = book;
                if (student > students) {
                    return false;
                }
            } else {
                pages = pages + book;
            }
        }
        return true;
    }

    private static double findMedianSortedArrays(int[] numbs1, int[] numbs2) {

        // Step 1: Ensure numbs1 is smaller
        if (numbs1.length > numbs2.length) {
            return findMedianSortedArrays(numbs2, numbs1);
        }

        int num1Size = numbs1.length;
        int num2Size = numbs2.length;

        int totalLeft = (num1Size + num2Size + 1) / 2;

        int low = 0;
        int high = num1Size;

        while (low <= high) {

            int i = (low + high) / 2;      // partition in numbs1
            int j = totalLeft - i;         // partition in numbs2

            // Border handling
            int left1 = (i == 0) ? Integer.MIN_VALUE : numbs1[i - 1];
            int right1 = (i == num1Size) ? Integer.MAX_VALUE : numbs1[i];

            int left2 = (j == 0) ? Integer.MIN_VALUE : numbs2[j - 1];
            int right2 = (j == num2Size) ? Integer.MAX_VALUE : numbs2[j];

            // Correct partition
            if (left1 <= right2 && left2 <= right1) {
                if ((num1Size + num2Size) % 2 == 0) {
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    return Math.max(left1, left2);
                }
            } else if (left1 > right2) {
                high = i - 1;
            } else {
                low = i + 1;
            }
        }

        return 0.0; // Should never reach here
    }

    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;   // handle k > n

        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }


    private static double findMedian(int[] numbs1, int[] numbs2) {
        if (numbs2.length < numbs1.length) {
            return findMedian(numbs2, numbs1);
        }
        int num1Size = numbs1.length;
        int num2Size = numbs2.length;

        int totalLeft = (num1Size + num2Size + 1) / 2;

        int low = 0;
        int high = num1Size;

        while (low <= high) {
            int i = low + (high - low) / 2;
            int j = totalLeft - i;

            int left1 = i == 0 ? Integer.MIN_VALUE : numbs1[i - 1];
            int right1 = i == num1Size ? Integer.MAX_VALUE : numbs1[i];

            int left2 = j == 0 ? Integer.MIN_VALUE : numbs2[j - 1];
            int right2 = j == num2Size ? Integer.MAX_VALUE : numbs2[j];

            if (left1 <= right2 && left2 <= right1) {
                if ((num1Size + num2Size) % 2 == 0) {
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    return Math.max(left1, left2);
                }
            } else if (left1 > right2) {
                high = i - 1;
            } else {
                low = i + 1;
            }
        }
        return 0.0;
    }


    public static void main(String[] args) {
        System.out.println(minimum());
    }
}
