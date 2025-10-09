import java.util.*;

public class CSPMapColoring {
    static final int MAX = 100;
    static int[] color = new int[MAX];
    static List<List<Integer>> graph = new ArrayList<>(MAX);
    static int numRegions, numEdges, numColors;
    static List<String> colorNames = Arrays.asList("Red", "Green", "Blue", "Yellow", "Orange");

    // Initialize adjacency list
    static {
        for (int i = 0; i < MAX; i++) {
            graph.add(new ArrayList<>());
        }
    }

    // Check if it is safe to assign color 'c' to node
    static boolean isSafe(int node, int c) {
        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == c)
                return false;
        }
        return true;
    }

    // Backtracking CSP Solver
    static boolean solveCSP(int node) {
        if (node == numRegions)
            return true;

        for (int c = 1; c <= numColors; c++) {
            if (isSafe(node, c)) {
                color[node] = c;
                if (solveCSP(node + 1))
                    return true;
                color[node] = 0; // Backtrack
            }
        }
        return false;
    }

    // Input graph from user
    static void inputGraph(Scanner sc) {
        System.out.print("Enter number of regions: ");
        numRegions = sc.nextInt();
        System.out.print("Enter number of edges (adjacent regions): ");
        numEdges = sc.nextInt();

        for (int i = 0; i < numEdges; i++) {
            System.out.print("Enter edge " + (i + 1) + " (format: u v): ");
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        System.out.print("Enter number of colors (max 5 supported): ");
        numColors = sc.nextInt();
    }

    // Print final color assignment
    static void printColorAssignment() {
        System.out.println("\nRegion to Color Assignment:");
        for (int i = 0; i < numRegions; i++) {
            if (color[i] > 0)
                System.out.println("Region " + i + " => " + colorNames.get(color[i] - 1));
            else
                System.out.println("Region " + i + " => Not Assigned");
        }
    }

    // Main Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Constraint Satisfaction Problem ---");
            System.out.println("1. Input Graph (Regions & Adjacency)");
            System.out.println("2. Solve using Backtracking");
            System.out.println("3. Show Color Assignment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    inputGraph(sc);
                    break;
                case 2:
                    if (solveCSP(0))
                        System.out.println("CSP Solved Successfully!");
                    else
                        System.out.println("No solution exists.");
                    break;
                case 3:
                    printColorAssignment();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        sc.close();
    }
}
