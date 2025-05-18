package dsa;

import java.util.List;
import java.util.Comparator;

public class BubbleSort {
    
    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        int n = list.size();
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    // Swap elements
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            
            // If no swapping occurred in this pass, array is sorted
            if (!swapped) {
                break;
            }
        }
    }
    
    // Helper method to sort movies by rating in descending order
    public static <T extends Comparable<T>> void sortDescending(List<T> list) {
        sort(list, (a, b) -> b.compareTo(a));
    }
    
    // Helper method to sort movies by rating in ascending order
    public static <T extends Comparable<T>> void sortAscending(List<T> list) {
        sort(list, Comparable::compareTo);
    }
} 