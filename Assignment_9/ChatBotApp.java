import java.util.*;

public class SimpleChatbot {

    // Function to simulate chatbot response
    static String getResponse(String userInput) {

        userInput = userInput.toLowerCase();

        // FAQs using rule-based responses
        if (userInput.contains("hello") || userInput.contains("hi") || userInput.contains("hey")) {
            return "Hello! 👋 How can I assist you today?";
        }
        else if (userInput.contains("order status") || userInput.contains("track order")) {
            return "Sure! Please provide your Order ID (e.g., ORD1234).";
        }
        else if (userInput.contains("ord")) {
            return "Your order is currently *In Transit* and will arrive in 2–3 days.";
        }
        else if (userInput.contains("refund")) {
            return "To request a refund, go to *My Orders → Select Item → Request Refund*.";
        }
        else if (userInput.contains("return")) {
            return "You can return a product within 7 days of delivery. Need help initiating a return?";
        }
        else if (userInput.contains("offers") || userInput.contains("discount")) {
            return "Today's Offers: 20% off on electronics and 10% cashback on prepaid payments!";
        }
        else if (userInput.contains("bye") || userInput.contains("exit") || userInput.contains("quit")) {
            return "Goodbye! 😊 Have a great day!";
        }
        else {
            return "Sorry, I didn't understand that. Can you rephrase?";
        }
    }

    // Main menu-driven chatbot
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("=======================================");
        System.out.println("      CUSTOMER SUPPORT CHATBOT 🤖");
        System.out.println("=======================================\n");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Start Chat");
            System.out.println("2. Help");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Clear buffer

            switch (choice) {

                case 1:
                    System.out.println("\nChatbot: Hello! I am your virtual assistant.");
                    System.out.println("Type 'bye' to end the conversation.\n");

                    while (true) {
                        System.out.print("You: ");
                        String userInput = sc.nextLine();

                        String botReply = getResponse(userInput);
                        System.out.println("Chatbot: " + botReply);

                        if (userInput.toLowerCase().contains("bye"))
                            break;
                    }
                    break;

                case 2:
                    System.out.println("\n📌 Chatbot Help");
                    System.out.println("- Ask about order status");
                    System.out.println("- Ask about refunds or returns");
                    System.out.println("- Ask about offers/discounts");
                    System.out.println("- Say 'bye' to exit chat\n");
                    break;

                case 3:
                    System.out.println("Exiting chatbot... Goodbye! 👋");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 3);

        sc.close();
    }
}
