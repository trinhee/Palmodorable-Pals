import java.util.Scanner;

public class GameManagerTest {

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Initialize GameManager with a sample pet
        GameManager gameManager = new GameManager("Buddy");

        // Print the current game state
        System.out.println(gameManager);

        // Scanner for user interaction
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Start Study Session");
            System.out.println("2. Give Item to Pet");
            System.out.println("3. View Current Game State");
            System.out.println("4. Exit");

            String choice = scanner.next(); // Use next() to ensure precise input

            switch (choice) {
                case "1":
                    // Test startStudySession
                    gameManager.startStudySession();
                    break;

                case "2":
                    // Test giveItem
                    System.out.println("Current Inventory:");
                    System.out.println(gameManager.getCurrentInventory());
                    System.out.println("Enter the type of the item to give (e.g., 'food', 'gift'): ");
                    String itemName = scanner.next(); // Use next() to ensure exact input (no spaces)

                    // Retrieve the item from the inventory

                    switch (itemName) {
                        case "food":
                            gameManager.givePet("food");
                            break;
                        case "gift":
                            gameManager.givePet("gift");
                            break;
                    
                        default:
                            System.out.println("Unknown item type: " + itemName);
                            break;
                    }
                    break;

                case "3":
                    // View the current game state
                    Pet pet = gameManager.getCurrentPet();
                    System.out.println(gameManager);
                    System.out.println(pet);
                    System.out.println(gameManager.getCurrentInventory());
                    break;

                case "4":
                    // Exit the test
                    gameManager.saveGame();
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Test finished.");
    }
}
