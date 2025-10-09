import java.util.*;

class Rule {
    List<String> premises; // IF part
    String conclusion;     // THEN part

    Rule() {
        premises = new ArrayList<>();
    }
}

class ForwardChaining {
    private List<Rule> rules = new ArrayList<>();
    private Set<String> facts = new HashSet<>();
    private String goal;

    // Add a new rule
    public void addRule(Scanner sc) {
        System.out.print("Enter number of premises: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        Rule r = new Rule();
        System.out.println("Enter premises:");
        for (int i = 0; i < n; i++) {
            String p = sc.nextLine().trim();
            r.premises.add(p);
        }

        System.out.print("Enter conclusion: ");
        r.conclusion = sc.nextLine().trim();

        rules.add(r);
        System.out.println("Rule added successfully!");
    }

    // Add a fact
    public void addFact(Scanner sc) {
        System.out.print("Enter fact: ");
        String f = sc.nextLine().trim();
        facts.add(f);
        System.out.println("Fact added successfully!");
    }

    // Set goal
    public void setGoal(Scanner sc) {
        System.out.print("Enter goal: ");
        goal = sc.nextLine().trim();
    }

    // Display rules and facts
    public void display() {
        System.out.println("\n--- Rules ---");
        for (int i = 0; i < rules.size(); i++) {
            Rule r = rules.get(i);
            System.out.print("R" + (i + 1) + ": ");
            for (int j = 0; j < r.premises.size(); j++) {
                System.out.print(r.premises.get(j));
                if (j != r.premises.size() - 1)
                    System.out.print(" AND ");
            }
            System.out.println(" -> " + r.conclusion);
        }

        System.out.println("\n--- Facts ---");
        for (String f : facts)
            System.out.print(f + " ");
        System.out.println("\nGoal: " + goal);
    }

    // Forward chaining algorithm
    public void forwardChaining() {
        System.out.println("\nApplying Forward Chaining...");

        boolean newFactAdded = true;
        Set<String> derivedFacts = new HashSet<>(facts);

        while (newFactAdded) {
            newFactAdded = false;

            for (Rule r : rules) {
                boolean allPremisesTrue = true;

                for (String p : r.premises) {
                    if (!derivedFacts.contains(p)) {
                        allPremisesTrue = false;
                        break;
                    }
                }

                if (allPremisesTrue && !derivedFacts.contains(r.conclusion)) {
                    System.out.println("Derived new fact: " + r.conclusion);
                    derivedFacts.add(r.conclusion);
                    newFactAdded = true;
                }

                if (derivedFacts.contains(goal)) {
                    System.out.println("\n✅ Goal '" + goal + "' is reached!");
                    return;
                }
            }
        }

        System.out.println("\n❌ Goal '" + goal + "' cannot be derived.");
    }
}

public class ForwardChainingMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ForwardChaining fc = new ForwardChaining();
        int choice;

        do {
            System.out.println("\n--- Forward Chaining Menu ---");
            System.out.println("1. Add Rule");
            System.out.println("2. Add Fact");
            System.out.println("3. Set Goal");
            System.out.println("4. Display Knowledge Base");
            System.out.println("5. Apply Forward Chaining");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    fc.addRule(sc);
                    break;
                case 2:
                    fc.addFact(sc);
                    break;
                case 3:
                    fc.setGoal(sc);
                    break;
                case 4:
                    fc.display();
                    break;
                case 5:
                    fc.forwardChaining();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 6);

        sc.close();
    }
}
