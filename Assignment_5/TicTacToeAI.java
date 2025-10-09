import java.util.*;

public class TicTacToeAI {
    static final char HUMAN = 'X';
    static final char AI = 'O';
    static final char EMPTY = '_';

    // Function to print board
    static void printBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check if any moves left
    static boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == EMPTY)
                    return true;
        return false;
    }

    // Evaluate board
    static int evaluate(char[][] b) {
        // Rows
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == AI) return +10;
                else if (b[row][0] == HUMAN) return -10;
            }
        }
        // Columns
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == AI) return +10;
                else if (b[0][col] == HUMAN) return -10;
            }
        }
        // Diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == AI) return +10;
            else if (b[0][0] == HUMAN) return -10;
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == AI) return +10;
            else if (b[0][2] == HUMAN) return -10;
        }
        return 0;
    }

    // Minimax Algorithm
    static int minimax(char[][] board, int depth, boolean isMax) {
        int score = evaluate(board);

        if (score == 10) return score - depth;   // prefer quick win
        if (score == -10) return score + depth;  // prefer delayed loss
        if (!isMovesLeft(board)) return 0;

        if (isMax) { // Maximizer (AI)
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        } else { // Minimizer (Human)
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        }
    }

    // Find best move for AI
    static int[] findBestMove(char[][] board) {
        int bestVal = -1000;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = AI;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = EMPTY;

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] board = new char[3][3];
        for (char[] row : board) Arrays.fill(row, EMPTY);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Play Tic Tac Toe (Human vs AI)");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                for (char[] row : board) Arrays.fill(row, EMPTY);
                System.out.println("\nGame Start!\nYou are X, Computer is O");
                printBoard(board);

                while (true) {
                    System.out.print("Enter your move (row and column: 0 1 2): ");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    if (x < 0 || x > 2 || y < 0 || y > 2 || board[x][y] != EMPTY) {
                        System.out.println("Invalid Move! Try again.");
                        continue;
                    }
                    board[x][y] = HUMAN;
                    printBoard(board);

                    if (evaluate(board) == -10) {
                        System.out.println("You Win!");
                        break;
                    }
                    if (!isMovesLeft(board)) {
                        System.out.println("Game Draw!");
                        break;
                    }

                    int[] aiMove = findBestMove(board);
                    board[aiMove[0]][aiMove[1]] = AI;
                    System.out.println("Computer placed O at (" + aiMove[0] + "," + aiMove[1] + ")");
                    printBoard(board);

                    if (evaluate(board) == 10) {
                        System.out.println("Computer Wins!");
                        break;
                    }
                    if (!isMovesLeft(board)) {
                        System.out.println("Game Draw!");
                        break;
                    }
                }
            } else if (choice == 2) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
