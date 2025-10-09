import java.util.*;

public class EightPuzzleSolver {

    // Goal state
    private static final int[][] goal = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    // State class
    static class State {
        int[][] board;
        int x, y; // blank tile position
        String path;

        State(int[][] b, int i, int j, String p) {
            board = new int[3][3];
            for (int m = 0; m < 3; m++)
                board[m] = Arrays.copyOf(b[m], 3);
            x = i;
            y = j;
            path = p;
        }
    }

    // Check if current state is goal
    static boolean isGoal(int[][] b) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (b[i][j] != goal[i][j])
                    return false;
        return true;
    }

    // Convert board to string for hashing
    static String toStr(int[][] b) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : b)
            for (int val : row)
                sb.append(val);
        return sb.toString();
    }

    // Generate all next states from current
    static List<State> getNextStates(State s) {
        List<State> res = new ArrayList<>();
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        char[] move = {'U', 'D', 'L', 'R'};

        for (int i = 0; i < 4; i++) {
            int nx = s.x + dirs[i][0];
            int ny = s.y + dirs[i][1];
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int[][] nb = new int[3][3];
                for (int m = 0; m < 3; m++)
                    nb[m] = Arrays.copyOf(s.board[m], 3);
                int temp = nb[s.x][s.y];
                nb[s.x][s.y] = nb[nx][ny];
                nb[nx][ny] = temp;
                res.add(new State(nb, nx, ny, s.path + move[i]));
            }
        }
        return res;
    }

    // BFS Solver
    static void solveBFS(State start) {
        Queue<State> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        q.add(start);
        visited.add(toStr(start.board));

        while (!q.isEmpty()) {
            State curr = q.poll();

            if (isGoal(curr.board)) {
                System.out.println("\n✅ BFS: Goal Reached!");
                System.out.println("Moves to reach goal: " + curr.path);
                return;
            }

            for (State next : getNextStates(curr)) {
                String key = toStr(next.board);
                if (!visited.contains(key)) {
                    visited.add(key);
                    q.add(next);
                }
            }
        }
        System.out.println("\n❌ BFS: Goal not reachable.");
    }

    // DFS Solver with depth limit
    static boolean solveDFSUtil(State curr, Set<String> visited, int depth, int maxDepth, StringBuilder resultPath) {
        if (isGoal(curr.board)) {
            resultPath.append(curr.path);
            return true;
        }
        if (depth >= maxDepth) return false;

        visited.add(toStr(curr.board));

        for (State next : getNextStates(curr)) {
            String key = toStr(next.board);
            if (!visited.contains(key)) {
                if (solveDFSUtil(next, visited, depth + 1, maxDepth, resultPath))
                    return true;
            }
        }

        visited.remove(toStr(curr.board));
        return false;
    }

    static void solveDFS(State start, int maxDepth) {
        Set<String> visited = new HashSet<>();
        StringBuilder resultPath = new StringBuilder();

        if (solveDFSUtil(start, visited, 0, maxDepth, resultPath)) {
            System.out.println("\n✅ DFS: Goal Reached!");
            System.out.println("Moves to reach goal: " + resultPath);
        } else {
            System.out.println("\n❌ DFS: Goal not reachable within depth limit (" + maxDepth + ").");
        }
    }

    // Find blank position (0)
    static int[] findBlank(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return new int[]{i, j};
        return new int[]{-1, -1};
    }

    // Menu-driven main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] board = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };

        int choice;
        do {
            System.out.println("\n==== 8-Puzzle Solver Menu ====");
            System.out.println("1. Enter Initial Board");
            System.out.println("2. Solve using BFS");
            System.out.println("3. Solve using DFS");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter the board (3x3) row-wise (use 0 for blank):");
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++)
                            board[i][j] = sc.nextInt();
                    break;

                case 2: {
                    int[] blankPos = findBlank(board);
                    State start = new State(board, blankPos[0], blankPos[1], "");
                    solveBFS(start);
                    break;
                }

                case 3: {
                    int[] blankPos = findBlank(board);
                    State start = new State(board, blankPos[0], blankPos[1], "");
                    System.out.print("Enter DFS max depth (suggest 20-40): ");
                    int maxDepth = sc.nextInt();
                    solveDFS(start, maxDepth);
                    break;
                }

                case 4:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 4);

        sc.close();
    }
}
