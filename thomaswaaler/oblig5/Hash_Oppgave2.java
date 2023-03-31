import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

// Changes note: Translated and cleaned up so that it fits more my programming style

class Hash_Oppgave2
{
    private int hashLength;
    private String hashTable[];
    private int n;
    private int probeCount;

    public Hash_Oppgave2(int length)
    {
// ADDED CODE START ================================================================================
        if (length <= 0)
        {
            System.err.println("[ERROR]: Hashlength needs to be larger than 0");
            System.exit(1);
        }
// ADDED CODE END ==================================================================================

        hashLength = length;
        hashTable = new String[length];
        n = 0;
        probeCount = 0;
    }

// ADDED CODE START ================================================================================

    // Returns the hashTable array. Used for printing it at the end of the program
    public String[] getHashTable()
    {
        return hashTable;
    }

// ADDED CODE END ==================================================================================

    // Returns the loadfactor
    public float loadFactor()
    {
        return ((float) n / hashLength);
    }

    // Returns the amount of data in the table
    public int dataCount()
    {
        return n;
    }

    // Returns the amount of probes during insertion
    public int probeCount()
    {
        return probeCount;
    }

    // Inserting a string with linear probing
    // Cancels if there is no more available space
    public void insert(String s)
    {
        // Calculate the hashvalue
        int h = hash(s);

        int next = h;
        while (hashTable[next] != null)
        {
            // New probe
            probeCount++;

// ADDED CODE START ================================================================================

            // Calculate the amount that the element in the hashtable has moved away from its original hashindex.
            // Then do the same with the element we are trying to insert.
            int tableHash = hash(hashTable[next]);
            int deltaTableHash = (tableHash > next) ? (hashLength - tableHash + next) : (next - tableHash);
            int deltaH = (h > next) ? (hashLength - h + next) : (next - h);

            // Check if the element in the table is further away from its original hashindex
            if (deltaH > deltaTableHash)
            {
                // Alternative 1

                // Swap the element we are inserting with the element in the hashtable, and bring that with us
                // to the next spot in the hashtable. Resulting in moving the element one to the right.
                String tempElement = hashTable[next];
                hashTable[next] = s;
                s = tempElement;
                h = tableHash;
            }

// ADDED CODE END ==================================================================================

            next++;

            // Wrap-around
            if (next >= hashLength)
                next = 0;

            // If we get back to the start hashvalue then the table is full.
            // Will close the program instead of increasing the table size.
            if (next == h)
            {
                System.err.println("[ERROR]: Hashtable is full. Exiting!");
                System.exit(0);
            }
        }

        hashTable[next] = s;

        n++;
    }

    // Hashfunction
    private int hash(String s)
    {
        int h = Math.abs(s.hashCode());
        return h % hashLength;
    }

    public static void main(String[] args)
    {
        // Hash length from the user
        Scanner in = new Scanner(System.in);
        System.out.print("Hashlength? ");
        int hashLength = in.nextInt();

        // Initialize a new hashtable
        Hash_Oppgave2 hash = new Hash_Oppgave2(hashLength);

        // Read from a datafile and hash every line
        try
        {
            System.out.print("Datafile? ");
            String filename = in.next();

            Scanner fileInput = new Scanner(new File(filename));
            while (fileInput.hasNext())
            {
                hash.insert(fileInput.nextLine());
            }

            // Print out info
            System.out.println("\nHashLength    : " + hashLength);
            System.out.println("Element Count : " + hash.dataCount());
            System.out.println("Loadfactor    : " + hash.loadFactor());
            System.out.println("Probe Count   : " + hash.probeCount());

            // Print out the finished hashtable
            System.out.println("\nHashTable:");
            if (hashLength <= 50)
                System.out.println( Arrays.toString(hash.getHashTable()) );
            else
                System.out.println("[WARNING]: Only printing out hashtables with a length of 50 or smaller");
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
    }
}
