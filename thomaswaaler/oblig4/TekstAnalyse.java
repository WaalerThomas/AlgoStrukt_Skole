import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class WordReader
{
    StreamTokenizer sT;

    public WordReader(String filename)
    {
        try
        {
            sT = new StreamTokenizer(
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(filename))));
        }
        catch (FileNotFoundException e)
        {
            // TODO: Handle FileNotFoundException
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        };
    }

    public String nextWord()
    {
        try
        {
            int x = sT.nextToken();
            while (sT.ttype != StreamTokenizer.TT_WORD)
            {
                if (x == StreamTokenizer.TT_EOF)
                    return null;
                x = sT.nextToken();
            }
        }
        catch (IOException e)
        {
            // TODO: Handle IOException
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        String word = sT.sval.toUpperCase();
        if (word.charAt(word.length() - 1) == '.')
            word = word.substring(0, word.length() - 1);

        return word;
    }
}


class TW_BinTree
{
    private Node root;

    private class Node
    {
        int freqCount;
        String word;
        Node leftNode;
        Node rightNode;

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
        if (root == null)
        {
            root = new Node(word);
            return;
        }

        Node currentNode = root;
        boolean isFinished = false;

        while (!isFinished)
        {
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
            else
            {
                currentNode.freqCount++;
                isFinished = true;
            }
        }
    }

    /** Print out the binary tree with in-order traversion */
    public void PrintTree()
    {
        System.out.println("┌────────────────────────┬───────────┐");
        System.out.println("│ Word                   │ Frequency │");
        System.out.println("├────────────────────────┼───────────┤");

        PrintTree(root);

        System.out.println("└────────────────────────┴───────────┘");
    }

    private void PrintTree(Node root)
    {
        if (root == null)
            return;

        PrintTree(root.leftNode);
        System.out.format("│ %-22s │ %-9d │%n", root.word, root.freqCount);
        PrintTree(root.rightNode);
    }
}


class TekstAnalyse
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("      Text Analyzer With Binary Search Tree");
        System.out.println("                By Thomas Waaler");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        System.out.print("Filename? ");
        String filename = s.nextLine();

        // TODO: Handle if the file doesn't exists
        WordReader wordReader = new WordReader(filename);
        TW_BinTree binTree = new TW_BinTree();

        String word = wordReader.nextWord();
        while (word != null)
        {
            binTree.Insert(word);
            word = wordReader.nextWord();
        }

        binTree.PrintTree();
        s.close();
    }
}
