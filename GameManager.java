import java.io.*;
import java.util.*;

public class GameManager {

    private Game currentGame;
    private Pet currentPet;
    private Inventory currentInventory;
    private Settings currentSettings;
    private String csvFilePath;

    /**
     * Constructor for GameManager.
     *
     * @param petName     The name of the pet.
     * @param csvFilePath The path to the CSV file.
     */
    public GameManager(String petName, String csvFilePath) {
        this.currentGame = new Game(petName);
        this.currentPet = currentGame.getPet();
        this.currentSettings = currentGame.getSettings();
        this.currentInventory = new Inventory(); // Initialize Inventory
        this.csvFilePath = csvFilePath;

        loadInventory(); // Load inventory upon game start
        startGame();
    }

    /**
     * Loads the inventory for the current pet from a CSV file.
     * The CSV file should have columns: name,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory
     * The inventory column should be in the format: "Food: X, Gift: Y"
     */
    public void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // Skip the header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Handle potential commas within quoted strings
                String[] data = parseCSVLine(line);
                if (data.length < 8) {
                    System.err.println("Invalid CSV format.");
                    continue;
                }

                String name = data[0].trim();
                if (name.equalsIgnoreCase(currentPet.getName())) {
                    String inventoryData = data[7].trim(); // Assuming inventory is at column index 7

                    // Remove surrounding quotes if present
                    if (inventoryData.startsWith("\"") && inventoryData.endsWith("\"")) {
                        inventoryData = inventoryData.substring(1, inventoryData.length() - 1);
                    }

                    String[] inventoryItems = inventoryData.split(",");

                    for (String item : inventoryItems) {
                        String[] parts = item.split(":");
                        if (parts.length != 2) {
                            System.err.println("Invalid inventory item format: " + item);
                            continue;
                        }

                        String itemType = parts[0].trim().toLowerCase();
                        int quantity;
                        try {
                            quantity = Integer.parseInt(parts[1].trim());
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid quantity for item: " + item);
                            continue;
                        }

                        Item inventoryItem = null;

                        // Define default items based on type
                        switch (itemType) {
                            case "food":
                                inventoryItem = new Item("Basic Food", "food", 20); // Customize as needed
                                break;
                            case "gift":
                                inventoryItem = new Item("Basic Gift", "gift", 15); // Customize as needed
                                break;
                            default:
                                System.err.println("Unknown item type: " + itemType);
                                continue;
                        }

                        // Add the item and quantity to the inventory
                        currentInventory.addItem(inventoryItem, quantity);
                    }

                    System.out.println("Inventory loaded for " + name);
                    break; // Exit after loading the current pet's inventory
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Inventory file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the inventory file: " + e.getMessage());
        }
    }

    /**
     * Parses a CSV line, considering quoted commas.
     *
     * @param line The CSV line to parse.
     * @return An array of values.
     */
    private String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char ch : line.toCharArray()) {
            if (ch == '\"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                values.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }
        values.add(sb.toString());

        return values.toArray(new String[0]);
    }

    /**
     * Starts the game and displays the loaded inventory.
     */
    public void startGame() {
        System.out.println("Starting game with pet: " + currentPet.getName());
        currentInventory.printInventory(); // Optional: Display loaded inventory
    }

    /**
     * Saves the game state to a file.
     */
    public void saveGame() {
        // Implement saving logic here
    }

    // Getter methods for accessing private fields

    public Game getCurrentGame() {
        return currentGame;
    }

    public Pet getCurrentPet() {
        return currentPet;
    }

    public Settings getCurrentSettings() {
        return currentSettings;
    }

    public Inventory getCurrentInventory() {
        return currentInventory;
    }
}
