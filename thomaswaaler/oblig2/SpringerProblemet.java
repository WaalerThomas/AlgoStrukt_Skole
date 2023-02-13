import java.util.Scanner;

public class SpringerProblemet
{
    int n;
    int board[][];
    int stepCount;

    // Possible moves in x and y directions
    int dX[] = { -2, -1, 1, 2, 2, 1, -1, -2 };
    int dY[] = { -1, -2, -2, -1, 1, 2, 2, 1 };

    public SpringerProblemet()
    {
        stepCount = 0;

        // Read in data from the user
        Scanner s = new Scanner(System.in);

        System.out.print("Size of board? ");
        n = s.nextInt();
        board = new int[n][n];

        System.out.print("Start X? ");
        int startX = s.nextInt();

        System.out.print("Start Y? ");
        int startY = s.nextInt();

        // Ask if the user want to use Recursive solution or Warnsdorf solution
        System.out.print("Which style? 0=Recursive 1=Warnsdorf ");
        int result = s.nextInt();
        if (result == 0)
        {
            // User chose Recursive
            if (findStep(startX, startY)) {
                System.out.println("Found a solution!");
                printBoard();
            } else
                System.out.println("Couldn't find a solution!");
        }
        else if (result == 1)
        {
            // User chose Warnsdorf
        }
        else {
            // User gave the wrong number, just terminate
            System.out.println("I asked for 0 or 1 ...");
            System.exit(-1);
        }

        s.close();
    }

    public boolean findStep(int x, int y)
    {
        board[y][x] = ++stepCount;

        // Bottom of recursion, no more available spots?
        if (stepCount == n * n) {
            return true;
        }

        for (int i = 0; i < 8; i++)
        {
            int newX = x + dX[i];
            int newY = y + dY[i];

            // Check for bounderies and if the cell is already visited
            if (newX >= 0 && newX < n && newY >= 0 && newY < n && board[newY][newX] == 0)
            {
                if (!findStep(newX, newY))
                    continue;

                return true;
            }
        }

        return false;
    }

    public void printBoard()
    {
        // Find the printing offset for numbers to align them properly
        int offset = String.valueOf(n * n - 1).length();

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
