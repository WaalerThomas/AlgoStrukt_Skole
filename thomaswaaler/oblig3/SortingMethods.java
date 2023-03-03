/**
 * Author:  Thomas Waaler
 * Subject: ITF20006-1 23V Algoritmer og datastrukturer
 * Task:    Obligatorisk oppgave 3: Test av sorteringsmetoder
 **/

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

class SortingMethods
{
    // Since we generate random values from 0 to n*2 I can't have an n that will result in n*2 being
    // bigger than an ints' max value. With some safety margin.
    private static int MAX_ARRAY_LENGTH = 1000000000;
    private static int MAX_SEQUENTIAL = 100000;         // Insertionsort takes too long for values over this

    int n;
    int arr[];
    int mergeTempArr[];  // Secondary array used in merge sorting

    public SortingMethods()
    {
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("              Sorting-Methods Tester");
        System.out.println("                By Thomas Waaler");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        // Starts the application
        ApplicationLoop();
    }

    private void ApplicationLoop()
    {
        Scanner s = new Scanner(System.in);

        while (true)
        {
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

            System.out.print("What's the length of the array? ");
            n = s.nextInt();
            if (n <= 0)
            {
                System.err.println("[ERROR]: The length cannot be 0 or less");
                continue;
            }
            else if (n > MAX_ARRAY_LENGTH)
            {
                System.err.println("[ERROR]: Array length cannot be longer than " + MAX_ARRAY_LENGTH);
                continue;
            }

            System.out.print("[STATUS]: Generating array with length " + n + "... ");
            GenerateRandomArray();
            System.out.println("DONE");

            System.out.println("\nSelect sorting method:");
            System.out.println("\t1. Insertion Sort\n\t2. Quicksort\n\t3. Merge Sort\n\t4. Radix Sort");
            System.out.print("INPUT> ");
            int sortMethod = s.nextInt();
            if (sortMethod <= 0 || sortMethod > 4)
            {
                System.err.println("[ERROR]: Only accepts values from 1 to 4");
                continue;
            }
            else if (sortMethod == 1 && n > MAX_SEQUENTIAL)
            {
                System.out.println("[ERROR]: Sequential methods cannot sort arrays longer than " + MAX_SEQUENTIAL + "\n\n");
                continue;
            }

            System.out.println("\nSelect type of test:");
            System.out.println("\t1. Time sorting\n\t2. Estimate constant C");
            System.out.print("INPUT> ");
            int testType = s.nextInt();
            System.out.println(""); // For spacing the output

            // Time Sorting
            if (testType == 1)
            {
                long time = System.currentTimeMillis();

                switch (sortMethod)
                {
                case 1:
                    System.out.print("[STATUS]: Starting Insertion sorting... ");
                    InsertionSort();
                    break;
                case 2:
                    System.out.print("[STATUS]: Starting Quick sorting... ");
                    QuickSort();
                    break;
                case 3:
                    System.out.print("[STATUS]: Starting Merge sorting... ");
                    MergeSort();
                    break;
                case 4:
                    System.out.print("[STATUS]: Starting Radix sorting... ");
                    RadixSort();
                    break;
                default:
                    System.err.println("[ERROR]: Should never be able to get here");
                    System.exit(-1);
                }

                time = System.currentTimeMillis() - time;
                System.out.println("DONE");
                System.out.println("[STATUS]: Time taken\t" + time + " ms\t" + (time / 1000.0) + " s");

                // Print out the sorted array for small arrays
                if (n <= 25)
                    System.out.println("\nArray: " + Arrays.toString(arr));
            }

            // Estimate constant C
            else if (testType == 2)
            {
                long totalTime = 0, time;
                double avgTime;

                System.out.print("[STATUS]: Calculating average runtime... ");
                for (int i = 0; i < 10; i++)
                {
                    GenerateRandomArray();

                    time = System.currentTimeMillis();
                    switch (sortMethod)
                    {
                        case 1: { InsertionSort(); } break;
                        case 2: { QuickSort(); } break;
                        case 3: { MergeSort(); } break;
                        case 4: { RadixSort(); } break;
                        default:
                            System.err.println("[ERROR]: Should never be able to get here");
                            System.exit(-1);
                    }

                    time = System.currentTimeMillis() - time;
                    totalTime += time;
                }

                avgTime = totalTime / 10.0d;
                System.out.println("DONE");
                System.out.println("[STATUS]: Time taken     \t" + String.format("%6d", totalTime) + " ms\t" + String.format("%.2f", totalTime / 1000.0) + " s");
                System.out.println("[STATUS]: Average runtime\t" + String.format("%6.2f", avgTime) + " ms\t" + String.format("%.2f", avgTime / 1000.0) + " s");

                double c = 0.0d;
                switch (sortMethod)
                {
                    // Insertion
                    case 1: { c = (double)avgTime / (n * n); } break;

                    // Quick
                    case 2: { c = (double)avgTime / (n * Math.log(n)); } break;

                    // Merge
                    case 3: { c = (double)avgTime / (n * Math.log(n)); } break;

                    // Radix
                    case 4: { c = (double)avgTime / n; } break;

                    default:
                        System.err.println("[ERROR]: Should never be able to get here");
                        System.exit(-1);
                }

                // Printing the value of C with scientific notation since the numbers will be quite low
                // otherwise we would just get 0 in return
                System.out.println("[STATUS]: Estimated value for C\t" + c);
            }
            else
            {
                System.out.println("[ERROR]: Only accepts values from 1 to 2");
                continue;
            }

            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

            System.out.print("Again? [y/n] ");
            if (s.next().toLowerCase().compareTo("n") == 0)
            {
                System.out.println("Bye!");
                break;
            }
        }

        // Cleanup
        s.close();
    }

    // Helper function to generate random values in the one dimensional array
    private void GenerateRandomArray()
    {
        Random r = new Random(System.currentTimeMillis());
        arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = r.nextInt(0, 2 * n);
    }

    private void InsertionSort()
    {
        int current;

        // This loops goes through each element in the array
        for (int i = 0; i < n; i++)
        {
            current = arr[i];

            // This loops goes backwards through the numbers we have already sorted and finds a spot to put the current element.
            // It will swap the current element with the one to the left if the left element is bigger than the current one.
            // This will move the current element into the correct spot whilst moving all the big numbers to the right.
            int j = i;
            while (j > 0 && arr[j - 1] > current)
            {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = current;
        }
    }

    // Function to call the recursive function QuickSort
    private void QuickSort()
    {
        QuickSort(0, n - 1);
    }

    // Gotten from "logaritmicSorting.java" by Jan
    private void QuickSort(int min, int max)
    {
        int partitionIndex;

        // End of recursion when min_index has surpassed max_index
        if (max - min > 0)
        {
            // Partition the array and return the middle element in the partition
            partitionIndex = FindPartition(min, max);

            QuickSort(min, partitionIndex - 1);   // Sort the left partition
            QuickSort(partitionIndex + 1, max);   // Sort the right partition
        }
    }

    // Gotten from "logaritmicSorting.java" by Jan
    private int FindPartition(int min, int max)
    {
        int left, right;
        int tempElement, partitionElement;

        partitionElement = arr[min];
        left = min;
        right = max;

        while (left < right)
        {
            // Increment left until it finds a number that is bigger than the partitionElement
            while (arr[left] <= partitionElement && left < right)
                left++;

            // Increment right until it find a number that is smaller than the partitionElement
            while (arr[right] > partitionElement)
                right--;

            // Swap the left and right element only if they haven't reached eachother or surpassed
            if (left < right)
            {
                tempElement = arr[left];
                arr[left] = arr[right];
                arr[right] = tempElement;
            }
        }

        // Swap the partitionElement with the last element that is smaller than the partitionElement
        tempElement = arr[min];
        arr[min] = arr[right];
        arr[right] = tempElement;

        // Return the index of the partitionElement
        return right;
    }

    // Function to call the recursive function MergeSort()
    private void MergeSort()
    {
        mergeTempArr = new int[arr.length];
        MergeSort(0, arr.length - 1);
    }

    // Gotten from "logarithmicSorting.java" by Jan
    private void MergeSort(int min, int max)
    {
        // Bottom of the recursion. When there is only one element left
        if (min == max)
            return;

        // Find the middlepoint inbetween min and max
        int mid = (min + max) / 2;

        MergeSort(min, mid);       // MergeSort recursively the left side of the list
        MergeSort(mid + 1, max);   // MergeSort recursively the right side of the list
        Merge(min, mid, max);      // Merge the left side list with the right side list in ascending order
    }

    // Gottem from "logaritmicSorting.java" by Jan
    private void Merge(int min, int mid, int max)
    {
        // Copy the parts of the list into a temporary array
        for (int i = min; i <= max; i++)
            mergeTempArr[i] = arr[i];

        // Merging work by looking at the first index of both lists and compare to see which number is the smallest.
        // It then takes the smallest number and moves that into our actual array and increments the pointer in that list.
        // Loops that until both lists are empty, and that has now merged the two lists together in ascending order.
        int left = min;
        int right = mid + 1;
        for (int i = min; i <= max; i++)
        {
            if (right <= max)
            {
                if (left <= mid)
                {
                    if (mergeTempArr[left] > mergeTempArr[right])
                        arr[i] = mergeTempArr[right++];
                    else
                        arr[i] = mergeTempArr[left++];
                }
                else
                    arr[i] = mergeTempArr[right++];
            }
            else
                arr[i] = mergeTempArr[left++];
        }
    }

    // Gotten from "radixSort2.java" by Jan
    private void RadixSort()
    {
        // Figures out the max amount of digits we will be operating on. Find this by converting the highest number we can get
        // to a string and then read of the length.
        int maxDigits = String.valueOf(2 * n).length();
        int digitIndex = 1;

        // Create a list of 10 empty queues. One queue for each digit from 0 to 9.
        Queue<Integer>[] queues = (Queue<Integer>[])(new Queue[10]);
        for (int i = 0; i < 10; i++)
            queues[i] = new LinkedList<>();

        // Loop over every digit index of all the numbers
        for (int x = 0; x < maxDigits; x++)
        {
            // Find the correct queue to add the number in. Numbers that have a 4 in the current digitIndex will go into the
            // 4th queue and so on.
            for (int i = 0; i < n; i++)
            {
                int digit = (arr[i] / digitIndex) % 10;
                queues[digit].add(arr[i]);
            }

            // Empty the queues into the array, now more sorted than the last time.
            int j = 0;
            for (int i = 0; i < 10; i++)
                while (!queues[i].isEmpty())
                    arr[j++] = (int)queues[i].remove();

            // Move the index to the next 10th place digit
            digitIndex *= 10;
        }
    }

    public static void main(String[] args)
    {
        SortingMethods sort = new SortingMethods();
    }
}
