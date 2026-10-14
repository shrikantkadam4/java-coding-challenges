/*
Given an array, find the first element that appears twice, where "first" means the duplicate whose second occurrence happens earliest.

Input:  [5, 3, 4, 3, 5, 6]
Output: 3
*/

import java.util.*;

public class FirstEarliestDuplicate1{
    public static void main (String[] args){
        int[] mynumbers= {5, 3, 4, 3, 5, 6};

        Map<Integer, Integer> myMap= new LinkedHashMap<>();

        for (int number: mynumbers) {
            myMap.put(number, myMap.getOrDefault(number, 0) + 1) ;
        }

        int mynumber=0;

        for (int number: mynumbers) {
            if (myMap.get(Integer.valueOf(number)) == 2) {
                mynumber=number;
                break;
            }
        }

        if (mynumber!=0)
            System.out.println("first repeating number - " + mynumber);
        else
            System.out.println("first repeating number not found");
    }
}