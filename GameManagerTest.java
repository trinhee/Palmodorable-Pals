public class GameManagerTest {
    public static void main(String[] args) {

        String petName = "Buddy"; 

        GameManager gameManager = new GameManager(petName);

        Game game = gameManager.getCurrentGame();
        Pet pet = gameManager.getCurrentPet();
        Settings settings = gameManager.getCurrentSettings();
        Inventory inventory = gameManager.getCurrentInventory();

        // Print Game Details
        System.out.println("\n--- Game Details ---");
        System.out.println(game);

        // Print Pet Details
        System.out.println("\n--- Pet Details ---");
        System.out.println(pet);

        // Print Settings Details
        System.out.println("\n--- Settings Details ---");
        System.out.println(settings);

        // Print Inventory Details
        System.out.println("\n--- Inventory Details ---");
        inventory.printInventory();
    }
}
