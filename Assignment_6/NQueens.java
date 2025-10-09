import java.util.Scanner;

public class NQueens {
    static final int N = 8; // Chessboard size for 8-Queens

    // Function to print the board
    static void printBoard(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1)
                    System.out.print(" Q ");
                else
                    System.out.print(" . ");
            }
            System.out.println();
        }
    }

    // Check if a queen can be placed at board[row][col]
    static boolean isSafe(int[][] board, int row, int col) {
        // Check this row on left side
        for (int i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // Check upper diagonal on left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check lower diagonal on left side
        for (int i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    // Solve N Queen problem using Backtracking
    static boolean solveNQUtil(int[][] board, int col) {
        if (col >= N) // Base case: All queens placed
            return true;

        // Try placing this queen in all rows one by one
        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1; // Place queen

                if (solveNQUtil(board, col + 1)) // Recur for next column
                    return true;

                board[i][col] = 0; // BACKTRACK
            }
        }
        return false;
    }

    // Wrapper function
    static void solveNQ() {
        int[][] board = new int[N][N];

        if (!solveNQUtil(board, 0)) {
            System.out.println("Solution does not exist");
            return;
        }

        printBoard(board);
    }

    // Menu-driven main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== 8-Queens Problem (Backtracking) =====");
            System.out.println("1. Solve 8-Queens");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    solveNQ();
                    break;
                case 2:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 2);

        sc.close();
    }
}
