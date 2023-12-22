package ngordnet.ngrams;

import java.util.*;

public class TreeMapExample {
    public static void main(String[] args) {
        // Create a TreeMap with some key-value pairs
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("A", 10);
        treeMap.put("B", 20);
        treeMap.put("C", 30);
        treeMap.put("D", 40);

        // Calculate the sum of all values in the TreeMap
        int sum = 0;
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            sum += entry.getValue();
        }

        // Print the sum
        System.out.println("Sum of all values in the TreeMap: " + sum);
    }
}