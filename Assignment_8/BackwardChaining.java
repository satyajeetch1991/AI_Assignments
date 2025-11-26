import java.util.*;

public class BackwardChaining {

    // Knowledge Base: Rules stored as {conclusion : list of premises}
    static Map<String, List<List<String>>> rules = new HashMap<>();
    // Facts in knowledge base
    static Set<String> facts = new HashSet<>();

    // Function to check if a goal can be proven using backward chaining
    static boolean backwardChaining(String goal, Set<String> visited) {

        // If goal is already a known fact
        if (facts.contains(goal))
            return true;

        // Avoid infinite recursion
        if (visited.contains(goal))
            return false;
        visited.add(goal);

        // If rules exist that can conclude the goal
        if (rules.containsKey(goal)) {
            for (List<String> premises : rules.get(goal)) {
                boolean allTrue = true;

                for (String p : premises) {
                    if (!backwardChaining(p, visited)) {
                        allTrue = false;
                        break;
                    }
                }

                if (allTrue)
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        String goal;

        // ------------------------------
        // SAMPLE KNOWLEDGE BASE
        // ------------------------------
        // Rule: A & B → C
        if (!rules.containsKey("C"))
            rules.put("C", new ArrayList<List<String>>());
        rules.get("C").add(Arrays.asList("A", "B"));

        // Rule: D → B
        if (!rules.containsKey("B"))
            rules.put("B", new ArrayList<List<String>>());
        rules.get("B").add(Arrays.asList("D"));

        // Facts
        facts.add("A");
        facts.add("D");

        // ------------------------------
        // MENU
        // ------------------------------
        do {
            System.out.println("\n=== Backward Chaining Menu ===");
            System.out.println("1. Show Knowledge Base");
            System.out.println("2. Query (Check if a fact can be proven)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("\nFacts: ");
                    for (String f : facts)
                        System.out.print(f + " ");

                    System.out.println("\nRules:");
                    for (Map.Entry<String, List<List<String>>> entry : rules.entrySet()) {
                        String conclusion = entry.getKey();
                        for (List<String> premises : entry.getValue()) {
                            System.out.print("IF ");
                            for (int i = 0; i < premises.size(); i++) {
                                System.out.print(premises.get(i));
                                if (i != premises.size() - 1)
                                    System.out.print(" AND ");
                            }
                            System.out.println(" THEN " + conclusion);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter goal to prove: ");
                    goal = sc.next();
                    Set<String> visited = new HashSet<>();

                    if (backwardChaining(goal, visited))
                        System.out.println("Goal '" + goal + "' can be PROVEN.");
                    else
                        System.out.println("Goal '" + goal + "' CANNOT be proven.");
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);

        sc.close();
    }
}
