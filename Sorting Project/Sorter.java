import java.util.ArrayList;
import java.util.Collections;

public class Sorter
{
    /**
     * Displays the values from a given array in the following format
     * [1, 2, 3, 4]
     */
    public static void printArray(int[] array)
    {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) 
        {
            System.out.print(array[i]);
            if (i < array.length - 1) 
            {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Displays the values from a given ArrayList in the following format
     * <1, 2, 3, 4>
     */
    public static void printArrayList(ArrayList<Integer> array)
    {
        System.out.print("<");
        for (int i = 0; i < array.size(); i++) 
        {
            System.out.print(array.get(i));
            if (i < array.size() - 1) 
            {
                System.out.print(", ");
            }
        }
        System.out.println(">");
    }   

    /**
     * generateRandomArray - creates an integer array of random
     * numbers
     */
    public static int[] generateRandomArray(int size, int min, int max)
    {
        int[] randomArray = new int[size];
        for (int i = 0; i < size; i++) 
        {
            int randomNum = (int) (Math.random() * (max - min + 1)) + min;
            randomArray[i] = randomNum;
        }
        return randomArray;
    }

    /**
     * generateRandomArrayList - creates an ArrayList of random
     * integers
     */
    public static ArrayList<Integer> generateRandomArrayList(int size, int min, int max)
    {
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < size; i++) 
        {
            int randomNum = (int) (Math.random() * (max - min + 1)) + min;
            nums.add(randomNum);
        }
        return nums;
    }

    /** Returns true / false based on if the array is sorted */ 
    public static boolean isSorted(int[] nums)
    {
        for (int i = 0; i < nums.length - 1; i++) 
        {
            if (nums[i] > nums[i + 1]) 
            {
                return false;
            }
        }
        return true;
    }

    /** Same as above except for an ArrayList */ 
    public static boolean isSorted(ArrayList<Integer> nums)
    {
        for (int i = 0; i < nums.size() - 1; i++) 
        {
            if (nums.get(i) > nums.get(i + 1)) 
            {
                return false;
            }
        }
        return true;
    }

    /** Swap helper */ 
    private static void swap(int[] array, int pos1, int pos2)
    {
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    /** bubbleSort */ 
    public static void bubbleSort(int[] list)
    {   
        for (int i = 0; i < list.length - 1; i++) 
        {
            for (int j = 0; j < list.length - 1 - i; j++) 
            {
                if (list[j] > list[j + 1]) 
                {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    /** selectionSort */ 
    public static void selectionSort(int[] list)
    {
        int n = list.length;
        for (int i = 0; i < n - 1; i++) 
        {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) 
            {
                if (list[j] < list[minIndex]) 
                {
                    minIndex = j;
                }
            }
            swap(list, i, minIndex);
        }
    }

    /** insertionSort (fixed version) */ 
    public static void insertionSort(int[] list)
    {
        for (int i = 1; i < list.length; i++) 
        {
            int key = list[i];
            int j = i - 1;
            while (j >= 0 && list[j] > key) 
            {
                list[j + 1] = list[j];
                j--;
            }
            list[j + 1] = key;
        }
    }
    
    /** bogoSort */ 
    public static void bogoSort(ArrayList<Integer> list)
    {
        int randomizations = 0;
        while (!isSorted(list)) 
        {
            Collections.shuffle(list);
            randomizations++;
        }
        System.out.println("BogoSort finished after " + randomizations + " randomizations.");
    }

    /** merge helper for mergeSort */ 
    private static ArrayList<Integer> merge(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < list1.size() && j < list2.size()) 
        {
            if (list1.get(i) <= list2.get(j)) 
            {
                result.add(list1.get(i));
                i++;
            } 
            else 
            {
                result.add(list2.get(j));
                j++;
            }
        }
        while (i < list1.size()) 
        {
            result.add(list1.get(i));
            i++;
        }
        while (j < list2.size()) 
        {
            result.add(list2.get(j));
            j++;
        }
        return result;
    }

    /** mergeSort */ 
    public static ArrayList<Integer> mergeSort(ArrayList<Integer> list)
    {
        if (list.size() <= 1) 
        {
            return list;
        }
        int mid = list.size() / 2;
        ArrayList<Integer> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Integer> right = new ArrayList<>(list.subList(mid, list.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    /** quickSort */ 
    public static ArrayList<Integer> quickSort(ArrayList<Integer> list)
    {
        if (list.size() <= 1) 
        {
            return list;
        }
        int pivot = list.get(list.size() / 2);
        ArrayList<Integer> less = new ArrayList<>();
        ArrayList<Integer> equal = new ArrayList<>();
        ArrayList<Integer> greater = new ArrayList<>();

        for (int num : list) 
        {
            if (num < pivot) 
            {
                less.add(num);
            } 
            else if (num == pivot) 
            {
                equal.add(num);
            } 
            else 
            {
                greater.add(num);
            }
        }

        ArrayList<Integer> sorted = new ArrayList<>();
        sorted.addAll(quickSort(less));
        sorted.addAll(equal);
        sorted.addAll(quickSort(greater));

        return sorted;
    }
}
