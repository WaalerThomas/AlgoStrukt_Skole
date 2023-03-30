
// Translated "hashLinear.java" by Jan
class Hashing
{
    private int hashLenght;
    private String hashTable[];
    private int n;               // Number of elements in the table

    public Hashing(int length)
    {
        // TODO: Check that the given length is good
        hashLenght = length;
        hashTable = new String[length];
        n = 0;
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}
