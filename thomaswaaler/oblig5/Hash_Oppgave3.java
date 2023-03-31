import java.util.Scanner;
import java.io.File;

// Changes note: Translated and cleaned up so that it fits more my programming style

class Hash_Oppgave3
{
    class HashNode
    {
        String data;
        HashNode next;

        public HashNode(String s, HashNode n)
        {
            data = s;
            next = n;
        }
    }

    private int hashLength;
    private HashNode hashTable[];
    private int n;                 // Number of elements in the hashtable
    private int collisionCount;

    public Hash_Oppgave3(int length)
    {
        hashLength = length;
        hashTable = new HashNode[length];
        n = 0;
        collisionCount = 0;
    }

    // Returns the loadfactor
    public float loadFactor()
    {
        return ((float) n / hashLength);
    }

    // Returns the amount of elements in the hashtable
    public int dataCount()
    {
        return n;
    }

    // Returns the amount of collision during insertion
    public int collisionCount()
    {
        return collisionCount;
    }

    // Hashfunction
    private int hash(String s)
    {
        int h = Math.abs(s.hashCode());
        return h % hashLength;
    }

    // Insertion of strings with chaining
    public void insert(String s)
    {
        // Calculate the hashvalue
        int h = hash(s);

        // Increment data count
        n++;

        // Check for collision
        if (hashTable[h] != null)
            collisionCount++;

        // Inserts a new node first in the list
        hashTable[h] = new HashNode(s, hashTable[h]);
    }

// ADDED CODE START ================================================================================
    public boolean remove(String s)
    {
        // Hashvalue
        int h = hash(s);

        // Find the list where the node would reside in
        HashNode hNode = hashTable[h];
        HashNode parentNode = null;

        // Going through the list of elements
        while (hNode != null)
        {
            // Found the element
            if (hNode.data.compareTo(s) == 0)
            {
                // Check if the node is the first in the list
                if (parentNode == null)
                {
                    // Set the nextNode as the hashvalue root node
                    hashTable[h] = hNode.next;
                    return true;
                }

                parentNode.next = hNode.next;
                return true;
            }

            // Trying the next node
            parentNode = hNode;
            hNode = hNode.next;
        }

        // Could not find a node with given string
        return false;
    }
// ADDED CODE END ==================================================================================

    public static void main(String[] args)
    {
        // Get the hashlength from the user
        Scanner in = new Scanner(System.in);
        System.out.print("Hashlength? ");
        int hashLength = in.nextInt();

        // Initialize the hashtable
        Hash_Oppgave3 hash = new Hash_Oppgave3(hashLength);

        // Read datafile and hash each line
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
            System.out.println("\nHashLength      : " + hashLength);
            System.out.println("Element Count   : " + hash.dataCount());
            System.out.println("Loadfactor      : " + hash.loadFactor());
            System.out.println("Collision Count : " + hash.collisionCount());
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }

// ADDED CODE START ================================================================================
        // As the user if they want to try and remove some elements from the hashtable
        System.out.print("\nDo you wish to remove elements? [y/N] ");
        String strAnswer = in.next();
        if (strAnswer.toLowerCase().compareTo("y") != 0)
            System.exit(0);

        // Consume newline that is left over
        in.nextLine();

        // Infinite loop until the user tells us to quit
        System.out.print("[DISCLAMER]: Removing is case sensitive. To QUIT press enter without typing anything in");
        while (true)
        {
            System.out.print("\nElement String? ");
            String element = in.nextLine();

            if (element.isEmpty())
                System.exit(0);

            // Check the status from removing the element
            boolean res = hash.remove(element);
            if (res)
                System.out.println("[STATUS]: Removed element '" + element + "' successfully");
            else
                System.out.println("[STATUS]: Cannot find element '" + element + "'");
        }
// ADDED CODE END ==================================================================================
    }
}
