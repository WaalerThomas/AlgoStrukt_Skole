import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Author: Thomas Waaler
 * Subject:  ITF20006-1 23V Algoritmer og datastrukturer
 * Task:   Obligatorisk oppgave 3: Test av sorteringsmetoder
 **/

class SortingMethods
{
    int n;  // NOTE: Do I need this here?
    int arr[];

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

        // Cleanup
        s.close();
    }

    public static void main(String[] args)
    {
        SortingMethods sort = new SortingMethods();
    }
}
