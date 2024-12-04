package com.mycompany.junittests;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The {@code Inventory} class represents an inventory system that stores and manages items for a pet.
 * It allows adding, removing, and retrieving items, as well as loading inventory data from a CSV file.
 */
public class Inventory {
    private static final String FILE_PATH = "../data_handling/pets_data.csv"; // The inventory file path
    private Map<Item, Integer> inventory; // A map of items and their quantities in the inventory.

    /**
     * Constructor for {@code Inventory}.
     * Initializes an empty inventory.
     */
    public Inventory() {
        inventory = new HashMap<>();
    }

    /**
     * Parses a CSV line into an array of values, handling commas inside quoted strings.
     *
     * @param line The CSV line to parse.
     * @return An array of strings representing the values in the CSV line.
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
     * Loads inventory data for a specific pet from a CSV file.
     * The CSV file must have the columns: name, health, sleep, fullness, happiness, sleepEffectiveness, 
     * playEffectiveness, and inventory. The inventory column should be in the format "Food: X, Gift: Y".
     *
     * @param petName The name of the pet whose inventory to load.
     */
    public void saveToFile(String petName) {
        saveToFile(petName, FILE_PATH);
    }

    public void saveToFile(String petName, String filePath) {
        String fileName = filePath;
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length < 8) {
                    System.err.println("Invalid data format: " + line);
                    continue; // Skip invalid lines
                }

                if (data[0].trim().equalsIgnoreCase(petName)) {
                    // Update the pet's inventory
                    line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"",
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim(),
                            this.toString()); // Use Inventory's toString() method
                    updated = true;
                }

                lines.add(line);
            }

            if (!updated) {
                System.err.println("Pet with name " + petName + " not found in the file.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Pet data successfully saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    
    /** 
     * @param petName
     */
    public void loadInventory(String petName) {
        loadInventory(petName, FILE_PATH);
    }
    public void loadInventory(String petName, String filePath) {
        String csvFilePath = filePath;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header row
                }

                String[] data = parseCSVLine(line);
                if (data.length < 9) {
                    System.err.println("Invalid CSV format.");
                    continue;
                }

                String name = data[0].trim();
                if (name.equalsIgnoreCase(petName)) {
                    String inventoryData = data[8].trim(); // Assuming inventory is at column index 8

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

                        switch (itemType) {
                            case "food":
                                inventoryItem = new Item("Food", "food", 10);
                                break;
                            case "gift":
                                inventoryItem = new Item("Gift", "gift", 10);
                                break;
                            default:
                                System.err.println("Unknown item type: " + itemType);
                                continue;
                        }

                        this.addItem(inventoryItem, quantity);
                    }

                    System.out.println("Inventory loaded for " + name);
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Inventory file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the inventory file: " + e.getMessage());
        }
    }

    /**
     * Adds a single item to the inventory. If the item already exists, its quantity is incremented by one.
     *
     * @param item The item to add to the inventory.
     */
    public void addItem(Item item) {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
    }

    /**
     * Adds a specified quantity of an item to the inventory. If the item already exists, its quantity is updated.
     *
     * @param item     The item to add to the inventory.
     * @param quantity The quantity of the item to add.
     */
    public void addItem(Item item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }

    /**
     * Removes one instance of an item from the inventory. If the item's quantity is greater than one, it decrements
     * the quantity. If the quantity reaches zero, the item is removed from the inventory.
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
     * Gets a list of all items currently in the inventory.
     *
     * @return A list of items in the inventory.
     */
    public List<Item> getItemsList() {
        return new ArrayList<>(inventory.keySet());
    }

    /**
     * Retrieves an item from the inventory by its type.
     *
     * @param name The name or type of the item to find.
     * @return The item if found, or {@code null} if not found.
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
     * @param item The item whose quantity is to be retrieved.
     * @return The quantity of the item, or 0 if the item is not found.
     */
    public int getItemCount(Item item) {
        return inventory.getOrDefault(item, 0);
    }

    /**
     * Returns a string representation of the inventory.
     * The format is "ItemName: Quantity, ItemName: Quantity, ...".
     *
     * @return A string representation of the inventory contents.
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
