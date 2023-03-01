/**
 * Author:  Thomas Waaler
 * Subject: ITF20006-1 23V Algoritmer og datastrukturer
 * Task:    Obligatorisk oppgave 3: Test av sorteringsmetoder
 **/


import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


class SortingMethods
{
    int n;  // NOTE: Do I need this here?
    int arr[];
    SORTING selectedSorting;

    enum SORTING
    {
        INSERTION,
        QUICK,
        MERGE,
        RADIX;
    }

    public SortingMethods()
    {
        Random r = new Random(System.currentTimeMillis());
        Scanner s = new Scanner(System.in);

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("              Sorting-Methods Tester");
        System.out.println("                By Thomas Waaler");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        // TODO: Add some limits of what's allowed to choose
        System.out.print("What's the length of the array? ");
        n = s.nextInt();
        arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = r.nextInt(0, 2 * n);

        System.out.println("\nSelect sorting method:");
        System.out.println("\t1. Insertion Sort\n\t2. Quicksort\n\t3. Merge Sort\n\t4. Radix Sort");
        System.out.print("> ");
        int sortMethod = s.nextInt();
        switch (sortMethod)
        {
            case 1: selectedSorting = SORTING.INSERTION; break;
            case 2: selectedSorting = SORTING.QUICK; break;
            case 3: selectedSorting = SORTING.MERGE; break;
            case 4: selectedSorting = SORTING.RADIX; break;
            default:
                System.err.println("Only accepts values from 1 to 4");
                System.exit(-1);
        }

        System.out.println("\nSelect type of test:");
        System.out.println("\t1. Time sorting\n\t2. Estimate constant C");
        System.out.print("> ");
        int testType = s.nextInt();

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        if (testType == 1)
        {
            long time = System.currentTimeMillis();

            switch (sortMethod)
            {
            case 1:
                System.out.println("Starting Insertion sorting...");
                insertionSort();
                break;
            case 2:
                System.out.println("Starting Quick sorting...");
                quickSort();
                break;
            case 3:
                System.out.println("Starting Merge sorting...");
                mergeSort();
                break;
            case 4:
                System.out.println("Starting Radix sorting...");
                radixSort();
                break;
            }

            time = System.currentTimeMillis() - time;

            System.out.println("Time taken: " + time + " ms");
        }
        else if (testType == 2)
        {
        }
        else
        {
            System.out.println("Only accepts values from 1 to 2");
            System.exit(-1);
        }

        System.out.println("Array Length: " + n);
        //System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Sorting Method: " + selectedSorting);
        System.out.println("Test type: " + testType);

        // Cleanup
        s.close();
    }

    // TODO: Comment the code
    /** Implementation taken from "seguentialSorting.java" by Jan Høiberg */
    private void insertionSort()
    {
        int current;

        for (int i = 0; i < n; i++)
        {
            current = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] > current)
            {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = current;
        }
    }

    private void quickSort()
    {
        System.out.println("NOT IMPLEMENTED: quickSort()");
        System.exit(-1);
    }

    private void mergeSort()
    {
        System.out.println("NOT IMPLEMENTED: mergeSort()");
        System.exit(-1);
    }

    private void radixSort()
    {
        System.out.println("NOT IMPLEMENTED: radixSort()");
        System.exit(-1);
    }

    public static void main(String[] args)
    {
        SortingMethods sort = new SortingMethods();
    }
}
