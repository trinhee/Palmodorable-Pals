package com.mycompany.junittests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private static final String TEMP_FILE = "test_pets.csv";
    private Inventory inventory;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary CSV file with test data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE))) {
            writer.write("name,type,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory\n");
            writer.write("Buddy,0,100,100,40,0,10,15,\"Gift: 1, Food: 5\"\n");
            writer.write("Fluffy,2,100,0,0,0,20,25,\"Gift: 2, Food: 4\"\n");
        }

        // Initialize inventory
        inventory = new Inventory();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up temporary test data
        Files.deleteIfExists(Path.of(TEMP_FILE));
    }

     /**
     * Test of saveToFile method, of class Inventory.
     */
    @Test
    public void testSaveToFile() {
        Inventory inv = new Inventory();
        Item item = new Item("Food", "food", 10);
        inv.addItem(item, 5);
        inv.saveToFile("Buddy", TEMP_FILE);
        
        Inventory loadedInstance = new Inventory();
        loadedInstance.loadInventory("Buddy", TEMP_FILE);
        
        assertEquals(5, loadedInstance.getItemCount(item), "Loaded inventory should match saved inventory.");
    }


    @Test
    public void testLoadInventoryList() {
        inventory.loadInventory("Buddy", TEMP_FILE);
        assertEquals(2, inventory.getItemsList().size(), "Buddy should have 2 items.");
    }

    /**
     * Test of loadInventory method, of class Inventory.
     */
    @Test
    public void testLoadInventory() {
        
        Item food = new Item("Food", "food", 10);
        
        Inventory newInventory = new Inventory();
        newInventory.loadInventory("Buddy", TEMP_FILE);
        
        assertEquals(5, newInventory.getItemCount(food), "Loaded item count should match saved data.");
    }

    @Test
    public void testAddItemFood() {
        Item item = new Item("Treat","food", 3);
        inventory.addItem(item);

        assertEquals(1, inventory.getItemsList().size(), "Inventory should contain 1 item");
        assertEquals("Treat", inventory.getItemsList().get(0).getName());
    }

    @Test
    public void testAddItemGift() {
        Item item = new Item("Toy","gift", 3);
        inventory.addItem(item, 5);

        Item addedItem = inventory.getItemsList().stream().filter(i -> i.getName().equals("Toy")).findFirst().orElse(null);
        assertNotNull(addedItem);
        assertEquals(5, inventory.getItemCount(item), "Total quantity of Toy should be 5");
    }

    @Test
    public void testRemoveItem() {
        Item item = new Item("Toy","gift", 1);
        inventory.addItem(item);
        inventory.removeItem(item);

        assertEquals(0, inventory.getItemsList().size(), "Inventory should be empty after removing the item.");
    }

    @Test
    public void testGetItemsList() {
        inventory.loadInventory("Buddy", TEMP_FILE);
        List<Item> items = inventory.getItemsList();
        assertEquals(2, items.size(), "Buddy should have 2 items");
    }

    @Test
    public void testGetItem() {
        Item item = new Item("Gift","gift", 1);
        inventory.loadInventory("Buddy", TEMP_FILE);

        Item result = inventory.getItem("Gift");
        assertNotNull(item);
        assertEquals("Gift", result.getName(), "Item name should be Gift.");
    }

    @Test
    public void testGetItemCount() {
        System.out.println("getItemCount");
        inventory.loadInventory("Buddy", TEMP_FILE);

        Item item = inventory.getItem("Food");
        int count = inventory.getItemCount(item);

        assertEquals(5, count, "Food count should be 5");
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        inventory.loadInventory("Buddy", TEMP_FILE);

        String result = inventory.toString();
        assertTrue(result.contains("Gift: 1"), "Output should include Gift.");
        assertTrue(result.contains("Food: 5"), "Output should include Food.");
    }
}
