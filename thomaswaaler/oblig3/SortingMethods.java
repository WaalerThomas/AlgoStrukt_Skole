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
    // bigger than an ints' max value. With some safety margin
    private static int MAX_ARRAY_LENGTH = 1000000000;
    private static int MAX_SEQUENTIAL = 100000;

    int n;
    int arr[];

    boolean isRunning; // NOTE: Do I need this?

    public SortingMethods()
    {
        isRunning = true;

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

        while (isRunning)
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

            System.out.println("[STATUS]: Generating array with length " + n);
            GenerateRandomArray();
            System.out.println("[STATUS]: DONE");

            System.out.println("\nSelect sorting method:");
            System.out.println("\t1. Insertion Sort\n\t2. Quicksort\n\t3. Merge Sort\n\t4. Radix Sort");
            System.out.print("> ");
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
            System.out.print("> ");
            int testType = s.nextInt();
            System.out.println(""); // For spacing the output

            // Time Sorting
            if (testType == 1)
            {
                long time = System.currentTimeMillis();

                switch (sortMethod)
                {
                case 1:
                    System.out.println("[STATUS]: Starting Insertion sorting...");
                    InsertionSort();
                    break;
                case 2:
                    System.out.println("[STATUS]: Starting Quick sorting...");
                    QuickSort();
                    break;
                case 3:
                    System.out.println("[STATUS]: Starting Merge sorting...");
                    MergeSort();
                    break;
                case 4:
                    System.out.println("[STATUS]: Starting Radix sorting...");
                    RadixSort();
                    break;
                default:
                    System.err.println("[ERROR]: Should never be able to get here");
                    System.exit(-1);
                }

                time = System.currentTimeMillis() - time;
                System.out.println("[STATUS]: DONE. Time taken: " + time + " ms    " + (time / 1000) + " s");

                if (n <= 25)
                    System.out.println("\nArray: " + Arrays.toString(arr));
            }
            // Estimate constant C
            else if (testType == 2)
            {
                long totalTime = 0, time;
                float avgTime;

                System.out.println("[STATUS]: Calculating average runtime...");
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

                avgTime = totalTime / 10.0f;
                System.out.println("[STATUS]: DONE. Average runtime: " + String.format("%.2f", avgTime) + " ms    " + String.format("%.2f", avgTime / 1000));

                float c = 0.0f;
                switch (sortMethod)
                {
                    case 1: // Insertion
                        c = avgTime / (n * n);
                        break;
                    case 2: // Quick
                        c = avgTime / (float)(n * Math.log(n));
                        break;
                    case 3: // Merge
                        c = avgTime / (float)(n * Math.log(n));
                        break;
                    case 4: // Radix
                        c = avgTime / n;
                        break;
                    default:
                        System.err.println("[ERROR]: Should never be able to get here");
                        System.exit(-1);
                }
                System.out.println("[STATUS]: Estimated value for C: " + String.format("%.2f", c));
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
                isRunning = false;
            }
        }

        // Cleanup
        s.close();
    }

    /** Helper function to generate random values in the one dimensional array */
    private void GenerateRandomArray()
    {
        Random r = new Random(System.currentTimeMillis());
        arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = r.nextInt(0, 2 * n);
    }

    // TODO: Comment the code
    private void InsertionSort()
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

    // Function to call the recursive function QuickSort
    private void QuickSort()
    {
        QuickSort(0, n - 1);
    }

    // TODO: Comment the code
    private void QuickSort(int min, int max)
    {
        int partitionIndex;

        if (max - min > 0)
        {
            partitionIndex = FindPartition(min, max);
            // Sort the left partition
            QuickSort(min, partitionIndex - 1);
            // Sort the right partition
            QuickSort(partitionIndex + 1, max);
        }
    }

    // TODO: Comment the code
    private int FindPartition(int min, int max)
    {
        int left, right;
        int tempElement, partitionElement;

        partitionElement = arr[min];
        left = min;
        right = max;

        while (left < right)
        {
            while (arr[left] <= partitionElement && left < right)
                left++;

            while (arr[right] > partitionElement)
                right--;

            if (left < right)
            {
                tempElement = arr[left];
                arr[left] = arr[right];
                arr[right] = tempElement;
            }
        }

        tempElement = arr[min];
        arr[min] = arr[right];
        arr[right] = tempElement;

        return right;
    }

    private void MergeSort()
    {
        MergeSort(0, arr.length - 1);
    }

    // TODO: Comment the code
    private void MergeSort(int min, int max)
    {
        if (min == max)
            return;

        int mid = (min + max) / 2;

        MergeSort(min, mid);
        MergeSort(mid + 1, max);
        Merge(min, mid, max);
    }

    // TODO: Comment the code
    private void Merge(int min, int mid, int max)
    {
        int tempArr[] = new int[arr.length];

        for (int i = min; i <= max; i++)
            tempArr[i] = arr[i];

        int left = min;
        int right = mid + 1;
        for (int i = min; i <= max; i++)
        {
            if (right <= max)
            {
                if (left <= mid)
                {
                    if (tempArr[left] > tempArr[right])
                        arr[i] = tempArr[right++];
                    else
                        arr[i] = tempArr[left++];
                }
                else
                    arr[i] = tempArr[right++];
            }
            else
                arr[i] = tempArr[left++];
        }
    }

    // TODO: Comment the code
    private void RadixSort()
    {
        int maxDigits = String.valueOf(2 * n).length();
        int digitIndex = 1;

        Queue<Integer>[] queues = (Queue<Integer>[])(new Queue[10]);
        for (int i = 0; i < 10; i++)
            queues[i] = new LinkedList<>();

        for (int x = 0; x < maxDigits; x++)
        {
            for (int i = 0; i < n; i++)
            {
                int digit = (arr[i] / digitIndex) % 10;
                queues[digit].add(arr[i]);
            }

            int j = 0;
            for (int i = 0; i < 10; i++)
                while (!queues[i].isEmpty())
                    arr[j++] = (int)queues[i].remove();

            digitIndex *= 10;
        }
    }

    public static void main(String[] args)
    {
        SortingMethods sort = new SortingMethods();
    }
}
