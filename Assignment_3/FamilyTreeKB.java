import java.util.*;

public class FamilyTreeKB {

    // Knowledge base using HashMaps
    static Map<String, List<String>> parentToChildren = new HashMap<>(); // Parent → list of children
    static Map<String, String> childToParent = new HashMap<>();          // Child → parent

    // Function to add a relationship to knowledge base
    static void addRelation(String parent, String child) {
        parentToChildren.putIfAbsent(parent, new ArrayList<>());
        parentToChildren.get(parent).add(child);
        childToParent.put(child, parent);
    }

    // Function to display children of a given person
    static void showChildren(String parent) {
        if (parentToChildren.containsKey(parent)) {
            System.out.print("Children of " + parent + ": ");
            for (String child : parentToChildren.get(parent)) {
                System.out.print(child + " ");
            }
            System.out.println();
        } else {
            System.out.println(parent + " has no recorded children in the knowledge base.");
        }
    }

    // Function to display parent of a given person
    static void showParent(String child) {
        if (childToParent.containsKey(child)) {
            System.out.println("Parent of " + child + " is " + childToParent.get(child));
        } else {
            System.out.println("Parent of " + child + " not found in the knowledge base.");
        }
    }

    // Menu-driven main program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        String parent, child, name;

        System.out.println("===== Family Tree Parser Using Knowledge Base =====");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add parent-child relation");
            System.out.println("2. Show children of a person");
            System.out.println("3. Show parent of a person");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter parent name: ");
                    parent = sc.next();
                    System.out.print("Enter child name: ");
                    child = sc.next();
                    addRelation(parent, child);
                    System.out.println("Relation added!");
                    break;

                case 2:
                    System.out.print("Enter name to find children: ");
                    name = sc.next();
                    showChildren(name);
                    break;

                case 3:
                    System.out.print("Enter name to find parent: ");
                    name = sc.next();
                    showParent(name);
                    break;

                case 4:
                    System.out.println("Exiting program. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}
