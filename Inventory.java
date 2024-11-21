import java.util.HashMap;
import java.util.List;
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
            if (item.getName().equalsIgnoreCase(name)) {
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
    public void printInventory() {
        for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
            System.out.println("Item: " + entry.getKey().getName() + ", Quantity: " + entry.getValue());
        }
    }
}
