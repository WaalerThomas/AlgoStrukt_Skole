import java.util.Scanner;

public class SpringerProblemet
{
    int n;
    int board[][];
    int stepCount;

    // Possible moves in x and y directions
    //int dX[] = { -2, -1, 1, 2, 2, 1, -1, -2 };
    //int dY[] = { -1, -2, -2, -1, 1, 2, 2, 1 };
    int dX[] = { -1,  1,  2,  2,  1, -1, -2, -2 };
    int dY[] = { -2, -2, -1,  1,  2,  2,  1, -1 };

    public SpringerProblemet()
    {
        Scanner s = new Scanner(System.in);
        int startX, startY, result;
        stepCount = 0;

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("              Knight's Tour Solver               ");
        System.out.println("               By Thomas Waaler                  ");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        // Gather parameters from the user
        System.out.print("How wide is the board? ");
        n = s.nextInt();
        board = new int[n][n];

        System.out.print("Start position X? (1 to " + n + ") ");
        startX = s.nextInt();

        System.out.print("Start position Y? (1 to " + n + ") ");
        startY = s.nextInt();

        // Check to see if user has given a position out of range
        if (startX <= 0 || startX > n || startY <= 0 || startY > n) {
            System.err.println("ERROR: Given position (" + startX + ", " + startY + ") is out of range");
            System.exit(-1);
        }

        // Ask if the user want to use Recursive solution or Warnsdorf solution
        System.out.println("\nWhich solving style?");
        System.out.println("\t0. Recursive");
        System.out.println("\t1. Warnsdorf");
        System.out.print("Style> ");
        result = s.nextInt();
        System.out.println("");

        switch (result)
        {
            // Recursive
            case 0: {
                // Don't allow an n that is higher than 7
                if (n >= 8) {
                    System.out.println("Can't solve for boards bigger than 7x7");
                    System.exit(-1);
                }

                // Subtract 1 from x and y since the code uses position coordinates between 0 and n-1
                if (findStep(startX - 1, startY - 1)) {
                    System.out.println("Found a solution!");
                    printBoard();
                } else
                    System.out.println("Couldn't find a solution!");
            } break;

            // Warnsdorf
            case 1: {
                // Subtract 1 from x and y since the code uses position coordinates between 0 and n-1
                if (warnsdorf(startX - 1, startY - 1)) {
                    System.out.println("Found a solution!");

                    if (n <= 20)
                        printBoard();
                    else {
                        // TODO: Print out to a file instead?
                        System.out.println("Board is too big to be printed in the terminal");
                    }
                } else
                    System.out.println("Couldn't find a solution!");
            } break;

            default:
                System.out.println("Number given is out of range. Need a number between 0 and 1");
                System.exit(-1);
        }

        // Cleanup
        s.close();
    }

    public boolean findStep(int x, int y)
    {
        // Increment step count and save into current cell on the board
        board[y][x] = ++stepCount;

        // Check for base case in the recursion
        // If our current step count is the same as n squared, then we have reached the bottom
        if (stepCount == n * n)
            return true;

        // Go through every 8 moves a knight can take from current cell
        for (int i = 0; i < 8; i++)
        {
            // The next cells x and y coordinated
            int newX = x + dX[i];
            int newY = y + dY[i];

            // Check for bounderies and if the next cell is available, free spaces are a 0
            if (newX >= 0 && newX < n && newY >= 0 && newY < n && board[newY][newX] == 0) {
                // Find the next step from new cell.
                if (findStep(newX, newY)) {
                    return true;
                }
            }
        }

        // No available cells found in the for loop. Need to backtrack
        board[y][x] = 0;
        stepCount--;
        return false;
    }

    /** Helper function to count the available cells around a given cell */
    private int getCellAvailableSteps(int x, int y)
    {
        int count = 0;

        for (int i = 0; i < 8; i++)
        {
            int newX = x + dX[i];
            int newY = y + dY[i];

            if (newX >= 0 && newX < n && newY >= 0 && newY < n && board[newY][newX] == 0)
                count++;
        }

        return count;
    }

    public boolean warnsdorf(int x, int y)
    {
        // Increment step count and save into current cell on the board
        board[y][x] = ++stepCount;

        // Check for base case in the recursion
        // If our current step count is the same as n squared, then we have reached the bottom
        if (stepCount == n * n)
            return true;

        // Populate a list with the amount of available cells it has. Index into that array
        // corresponds with the index into dX and dY arrays
        int cellsAvailableCount[] = new int[8];
        for (int i = 0; i < 8; i++)
        {
            // The next cells x and y coordinates
            int newX = x + dX[i];
            int newY = y + dY[i];

            // Check for bounderies and if the next cell is available
            if (newX >= 0 && newX < n && newY >= 0 && newY < n && board[newY][newX] == 0) {
                cellsAvailableCount[i] = getCellAvailableSteps(newX, newY);
            }
        }

        // For finding the smallest count 8 times
        // NOTE: Could be better to create a Cell class and make it comparable so I can use Arrays.sort on the array. Think it will be faster
        for (int i = 0; i < 8; i++)
        {
            int smallestCount = 9;
            int smallestCountIndex = 0;
            for (int j = 0; j < 8; j++) {
                if (cellsAvailableCount[j] < smallestCount && cellsAvailableCount[j] != 0)
                {
                    smallestCountIndex = j;
                    smallestCount = cellsAvailableCount[j];
                }
            }

            // TODO: Can smallest count ever end up as 9?

            if (cellsAvailableCount[smallestCountIndex] == 0)
                return false;

            int newX = x + dX[smallestCountIndex];
            int newY = y + dY[smallestCountIndex];

            // Found the smallest cell. Time to recursivly go into it?
            if (warnsdorf(newX, newY))
                return true;
        }

        // Couldn't find any next steps
        board[y][x] = 0;
        stepCount--;
        return false;
    }

    /* Function to help printing out the board in a pretty way */
    public void printBoard()
    {
        // Find the printing offset for numbers to align them properly
        int offset = String.valueOf(n * n).length();

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                System.out.format("%" + offset + "d ", board[y][x]);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        SpringerProblemet sp = new SpringerProblemet();
    }
}
