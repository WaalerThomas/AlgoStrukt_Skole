import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/** Helper class to read a textfile word by word. Taken from "OrdLeser.java" by Jan */
class WordReader
{
    StreamTokenizer sT;

    public WordReader(String filename)
    {
        // Initialize the tokenizer. Will exit program if file is not found
        try
        {
            sT = new StreamTokenizer(
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(filename))));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("[ERROR]: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        };
    }

    /** Return the next word from the file */
    public String NextWord()
    {
        // Have the tokenizer "point" to the next word
        try
        {
            int x = sT.nextToken();
            while (sT.ttype != StreamTokenizer.TT_WORD)
            {
                // Return null if it has reached the end of the file
                if (x == StreamTokenizer.TT_EOF)
                    return null;

                x = sT.nextToken();
            }
        }
        catch (IOException e)
        {
            System.out.println("[ERROR]: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Change the word to all uppercase and try to remove trailing . and -
        // NOTE: Have noticed that the test data has words that end in multiple trailing . and -. Need a better way to remove those
        String word = sT.sval.toUpperCase();
        if (word.charAt(word.length() - 1) == '.' ||
            word.charAt(word.length() - 1) == '-')
            word = word.substring(0, word.length() - 1);

        return word;
    }
}

/** Simple binary tree class implementation */
class TW_BinTree
{
    // Root node of the tree
    private Node root;

    /** Inner dataclass representing a node in the binary tree */
    private class Node
    {
        int freqCount;  // Keeps a count of times this nodes' word has showed up
        String word;    // The word this node is representing
        Node leftNode;  // Reference to the left node
        Node rightNode; // Reference to the right node

        public Node(String word)
        {
            this.word = word;
            freqCount = 1;
            leftNode = null;
            rightNode = null;
        }
    }

    public TW_BinTree()
    {
        root = null;
    }

    /** Insert a word into the binary tree. Inserts iteratively */
    public void Insert(String word)
    {
        // Sets the node as root if there isn't any
        if (root == null)
        {
            root = new Node(word);
            return;
        }

        Node currentNode = root;
        boolean isFinished = false;

        // Will iterate down the tree until the same word is found or an available spot for the word is found
        while (!isFinished)
        {
            // Word is smaller than the currentNodes word. Go left
            if (currentNode.word.compareTo(word) > 0)
            {
                if (currentNode.leftNode == null)
                {
                    currentNode.leftNode = new Node(word);
                    isFinished = true;
                }
                else
                    currentNode = currentNode.leftNode;
            }

            // Word is greater than the currentNodes word. Go right
            else if (currentNode.word.compareTo(word) < 0)
            {
                if (currentNode.rightNode == null)
                {
                    currentNode.rightNode = new Node(word);
                    isFinished = true;
                }
                else
                    currentNode = currentNode.rightNode;
            }

            // Similar word. Increment the counter
            else
            {
                currentNode.freqCount++;
                isFinished = true;
            }
        }
    }

    /** Return the number of nodes in the binary tree. Used for printing out information. Calls the recursive method GetNodeCount */
    public int GetNodeCount()
    {
        return GetNodeCount(root);
    }

    /** Recursive method to count the number of nodes in the tree */
    private int GetNodeCount(Node root)
    {
        // Bottom of the recursion
        if (root == null)
            return 0;

        // Return the number of nodes from the leftNode and rightNode, then increment for current node
        return GetNodeCount(root.leftNode) + GetNodeCount(root.rightNode) + 1;
    }

    /** Print out the binary tree with in-order traversion. Creates a table to make it easier to read */
    public void PrintTree()
    {
        System.out.println("┌────────────────────────┬───────────┐");
        System.out.println("│ Word                   │ Frequency │");
        System.out.println("├────────────────────────┼───────────┤");

        // Calls the recursive method to print out each node
        PrintTree(root);

        System.out.println("└────────────────────────┴───────────┘");
        System.out.println("Number of nodes: " + GetNodeCount());
    }

    /** Recursive method to print out each node with in-order traversion */
    private void PrintTree(Node root)
    {
        // Bottom of the recursion
        if (root == null)
            return;

        // First print out the leftNode, then print out the root node. Lastly print out the rightNode
        PrintTree(root.leftNode);
        System.out.format("│ %-22s │ %-9d │%n", root.word, root.freqCount);
        PrintTree(root.rightNode);
    }
}

/** The main class of this prosject */
class TekstAnalyse
{
    public static void main(String[] args)
    {
        long insertionTime; // For keeping count of how long it took to populate the tree
        Scanner s = new Scanner(System.in);

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("      Text Analyzer With Binary Search Tree");
        System.out.println("                By Thomas Waaler");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        // Get input from the user
        System.out.print("Filename? ");
        String filename = s.nextLine();

        // Initialize the wordreader and an empty binary tree
        WordReader wordReader = new WordReader(filename);
        TW_BinTree binTree = new TW_BinTree();

        // Go through each word in the file and insert them into the binary tree
        insertionTime = System.currentTimeMillis();
        String word = wordReader.NextWord();
        while (word != null)
        {
            binTree.Insert(word);
            word = wordReader.NextWord();
        }
        insertionTime = System.currentTimeMillis() - insertionTime;

        // Print out the whole tree, and some aditional information
        binTree.PrintTree();
        System.out.println("Binary Tree Creation Time: " + insertionTime + " ms");

        s.close();
    }
}
