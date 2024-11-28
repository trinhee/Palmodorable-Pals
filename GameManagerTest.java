import java.time.LocalDateTime;
import java.util.Scanner;

public class GameManagerTest {


    public static void testUpdateStatsContinuous(){
        GameManager gameManager = new GameManager("Buddy");
        LocalDateTime currDateTime = LocalDateTime.now();
        int lastHour = currDateTime.getHour();
        System.out.println("Buddys Health Before (no increase): " + gameManager.getCurrentGame().getPet().getHealth());
        System.out.println("Buddys Fullness Before (no increase): " + gameManager.getCurrentGame().getPet().getFullness());
        System.out.println("Buddys Sleep Before (no increase): " + gameManager.getCurrentGame().getPet().getSleep());
        System.out.println("Buddys Happiness Before (no increase): " + gameManager.getCurrentGame().getPet().getHappiness());
        gameManager.updateStatsContinuous(gameManager.getCurrentPet(), lastHour);
        System.out.println("Buddys Health After (no increase): " + gameManager.getCurrentGame().getPet().getHealth());
        System.out.println("Buddys Fullness After (no increase): " + gameManager.getCurrentGame().getPet().getFullness());
        System.out.println("Buddys Sleep After (no increase): " + gameManager.getCurrentGame().getPet().getSleep());
        System.out.println("Buddys Happiness After (no increase): " + gameManager.getCurrentGame().getPet().getHappiness());
        lastHour++;
        System.out.println("Buddys Health Before (increase): " + gameManager.getCurrentGame().getPet().getHealth());
        System.out.println("Buddys Fullness Before (increase): " + gameManager.getCurrentGame().getPet().getFullness());
        System.out.println("Buddys Sleep Before (increase): " + gameManager.getCurrentGame().getPet().getSleep());
        System.out.println("Buddys Happiness Before (increase): " + gameManager.getCurrentGame().getPet().getHappiness());
        gameManager.updateStatsContinuous(gameManager.getCurrentPet(), lastHour);
        System.out.println("Buddys Health After (increase): " + gameManager.getCurrentGame().getPet().getHealth());
        System.out.println("Buddys Fullness After (increase): " + gameManager.getCurrentGame().getPet().getFullness());
        System.out.println("Buddys Sleep After (increase): " + gameManager.getCurrentGame().getPet().getSleep());
        System.out.println("Buddys Happiness After (increase): " + gameManager.getCurrentGame().getPet().getHappiness());

    }

    public static void testUpdateStatsLogin(){
        GameManager gameManager = new GameManager("Buddy");
        gameManager.getCurrentGame().getPet().setFullness(100);
        gameManager.getCurrentGame().getPet().setSleep(20);
        System.out.println("Buddys Health Before: " + gameManager.getCurrentGame().getPet().getHealth());
        System.out.println("Buddys Fullness Before: " + gameManager.getCurrentGame().getPet().getFullness());
        System.out.println("Buddys Sleep Before: " + gameManager.getCurrentGame().getPet().getSleep());
        System.out.println("Buddys Happiness Before: " + gameManager.getCurrentGame().getPet().getHappiness());
        gameManager.updateStatsLogin(gameManager.getCurrentStatisticsTracker(), gameManager.getCurrentPet());
        System.out.println("Buddys Health After: " + gameManager.getCurrentGame().getPet().getHealth());
        System.out.println("Buddys Fullness After: " + gameManager.getCurrentGame().getPet().getFullness());
        System.out.println("Buddys Sleep After: " + gameManager.getCurrentGame().getPet().getSleep());
        System.out.println("Buddys Happiness After: " + gameManager.getCurrentGame().getPet().getHappiness());

        GameManager gameManager2 = new GameManager("Fluffy");
        gameManager2.getCurrentGame().getPet().setFullness(0);

        System.out.println("Fluffys Health Before: " + gameManager2.getCurrentGame().getPet().getHealth());
        System.out.println("Fluffys Fullness Before: " + gameManager2.getCurrentGame().getPet().getFullness());
        System.out.println("Fluffys Sleep Before: " + gameManager2.getCurrentGame().getPet().getSleep());
        System.out.println("Fluffys Happiness Before: " + gameManager2.getCurrentGame().getPet().getHappiness());
        gameManager2.updateStatsLogin(gameManager2.getCurrentStatisticsTracker(), gameManager2.getCurrentPet());
        System.out.println("Fluffys Health After: " + gameManager2.getCurrentGame().getPet().getHealth());
        System.out.println("Fluffys Fullness After: " + gameManager2.getCurrentGame().getPet().getFullness());
        System.out.println("Fluffys Sleep After: " + gameManager2.getCurrentGame().getPet().getSleep());
        System.out.println("Fluffys Happiness After: " + gameManager2.getCurrentGame().getPet().getHappiness());

    }
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

        System.out.println("Update Stats Login Test:");
        GameManagerTest.testUpdateStatsLogin();
        System.out.println("Test finished");

        System.out.println("Update Stats Continuous Test:");
        GameManagerTest.testUpdateStatsContinuous();
        System.out.println("Test finished");
    }
}

