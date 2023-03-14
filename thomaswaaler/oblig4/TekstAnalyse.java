import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
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
    Node root;

    class Node
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

    public void AddWord(String word)
    {
        if (root == null)
        {
            root = new Node(word);
            return;
        }

        Node currentNode = root;
        while (currentNode != null)
        {
            if (currentNode.word.compareTo(word) == 0)
                currentNode = currentNode.leftNode;
        }
    }
}


class TekstAnalyse
{
    public static void main(String[] args)
    {
        TW_BinTree binTree = new TW_BinTree();
        WordReader wordReader = new WordReader("churchill.txt");

        String word = wordReader.nextWord();
        while (word != null)
        {
            System.out.println(word);
            word = wordReader.nextWord();
        }
    }
}
