/*
Given an array, find the first element that appears twice, where "first" means the duplicate whose second occurrence happens earliest.

Input:  [5, 3, 4, 3, 5, 6]
Output: 3
*/

import java.util.HashSet;

public class FirstEarliestDuplicate {
    public static int findFirstDuplicate(int[] arr) {
        HashSet<Integer> seen = new HashSet<>();
        int firstDuplicate = -1;

        // Traverse the array from right to left
        for (int i=0; i< arr.length; i++ ) {
            if (seen.contains(arr[i])) {
                firstDuplicate = arr[i]; // Update to the most recent right-to-left duplicate
                break;
            } else {
                seen.add(arr[i]);
            }
        }

        return firstDuplicate;
    }

    public static void main(String[] args) {
        int[] input = {5, 3, 4, 3, 5, 6};
        int result = findFirstDuplicate(input);
        System.out.println("Output: " + result); // Output: 3
    }
}