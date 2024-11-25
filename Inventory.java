import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Inventory {

    private Map<Item, Integer> inventory;

    /**
     * Constructor for Inventory. Initializes an empty inventory.
     */
    public Inventory() {
        inventory = new HashMap<>();
    }

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

    public void loadInventory(String petName) {
        String csvFilePath = "data_handling/pets_data.csv";
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
                if (name.equalsIgnoreCase(petName)) {
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
                                inventoryItem = new Item("Basic Food", "food", 10); // Customize as needed
                                break;
                            case "gift":
                                inventoryItem = new Item("Basic Gift", "gift", 10); // Customize as needed
                                break;
                            default:
                                System.err.println("Unknown item type: " + itemType);
                                continue;
                        }

                        // Add the item and quantity to the inventory
                        this.addItem(inventoryItem, quantity);
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
     * Adds a single item to the inventory. If the item already exists, increments its quantity.
     *
     * @param item The item to add to the inventory.
     */
    public void addItem(Item item) {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
    }

    /**
     * Adds multiple quantities of an item to the inventory. If the item already exists, increments its quantity.
     *
     * @param item     The item to add to the inventory.
     * @param quantity The number of items to add.
     */
    public void addItem(Item item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }

    /**
     * Removes an item from the inventory. If the item's quantity is greater than 1, decrements it.
     * If the quantity reaches 0, removes the item from the inventory.
     *
     * @param item The item to remove from the inventory.
     */
    public void removeItem(Item item) {
        if (inventory.containsKey(item)) {
            int currentCount = inventory.get(item);
            if (currentCount > 1) {
                inventory.put(item, currentCount - 1);
            } else {
                inventory.remove(item);
            }
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    /**
     * Gets a list of all items in the inventory.
     *
     * @return A list of items in the inventory.
     */
    public List<Item> getItemsList() {
        return new ArrayList<>(inventory.keySet());
    }

    /**
     * Finds and returns an item from the inventory by its name.
     *
     * @param name The name of the item to find.
     * @return The item with the given name, or null if not found.
     */
    public Item getItem(String name) {
        for (Item item : inventory.keySet()) {
            if (item.getType().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Gets the quantity of a specific item in the inventory.
     *
     * @param item The item whose quantity to retrieve.
     * @return The quantity of the item, or 0 if the item is not in the inventory.
     */
    public int getItemCount(Item item) {
        return inventory.getOrDefault(item, 0);
    }

    /**
     * Prints the inventory for debugging or display purposes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.getKey().getName());
            sb.append(": ");
            sb.append(entry.getValue());
            first = false;
        }
        return sb.toString();
    }
}
