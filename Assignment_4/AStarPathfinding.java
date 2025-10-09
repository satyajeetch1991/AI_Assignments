import java.util.*;

class Node {
    int x, y;       // Coordinates
    int g, h, f;    // g = cost so far, h = heuristic, f = total
    Node parent;    // To trace path

    Node(int x, int y, int g, int h, Node parent) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.parent = parent;
    }
}

class CompareNodes implements Comparator<Node> {
    public int compare(Node a, Node b) {
        return Integer.compare(a.f, b.f);
    }
}

public class AStarPathfinding {

    // Check valid cell
    static boolean isValid(int x, int y, int n, int m, int[][] grid) {
        return (x >= 0 && x < n && y >= 0 && y < m && grid[x][y] == 0);
    }

    // Manhattan distance heuristic
    static int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // A* Algorithm
    static void AStar(int[][] grid, int[] start, int[] goal) {
        int n = grid.length, m = grid[0].length;
        PriorityQueue<Node> openList = new PriorityQueue<>(new CompareNodes());
        boolean[][] closed = new boolean[n][m];

        Node startNode = new Node(start[0], start[1], 0,
                heuristic(start[0], start[1], goal[0], goal[1]), null);
        openList.add(startNode);

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}}; // 4 directions

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            int x = current.x, y = current.y;

            if (x == goal[0] && y == goal[1]) {
                System.out.println("✅ Path Found!");
                List<int[]> path = new ArrayList<>();
                while (current != null) {
                    path.add(new int[]{current.x, current.y});
                    current = current.parent;
                }
                Collections.reverse(path);
                System.out.print("Shortest Path: ");
                for (int[] p : path)
                    System.out.print("(" + p[0] + "," + p[1] + ") ");
                System.out.println();
                return;
            }

            closed[x][y] = true;

            for (int[] d : directions) {
                int nx = x + d[0];
                int ny = y + d[1];
                if (isValid(nx, ny, n, m, grid) && !closed[nx][ny]) {
                    Node neighbor = new Node(nx, ny, current.g + 1,
                            heuristic(nx, ny, goal[0], goal[1]), current);
                    openList.add(neighbor);
                }
            }
        }
        System.out.println("❌ No Path Found!");
    }

    // Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter grid size (rows cols): ");
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];

        System.out.println("Enter grid (0 = free, 1 = blocked):");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                grid[i][j] = sc.nextInt();

        int[] start = new int[2];
        int[] goal = new int[2];
        System.out.print("Enter Start (row col): ");
        start[0] = sc.nextInt();
        start[1] = sc.nextInt();
        System.out.print("Enter Goal (row col): ");
        goal[0] = sc.nextInt();
        goal[1] = sc.nextInt();

        int choice;
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Run A* Algorithm");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    AStar(grid, start, goal);
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 2);

        sc.close();
    }
}
