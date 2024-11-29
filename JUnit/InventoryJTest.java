package com.mycompany.statisticstrackertest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Unit tests for the {@code Inventory} class.
 * @author Cameron Solomway
 */
public class InventoryJTest {
    
    private Inventory instance;
     private static final String FILE_NAME = "pets_data.csv";
    
    @BeforeEach
    public void setUpClass() throws IOException {
        // Initialize instance
        instance = new Inventory();

        File testFile = new File(FILE_NAME);
        if (testFile.getParentFile() != null) {
            testFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("name,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory\n");
            writer.write("Buddy,100,100,10,20,10,15,\"Gift: 0, Food: 3\"\n");
        }
    }



    @AfterEach
    public void tearDownClass() {
        // Clean up test file after all tests
        File testFile = new File(FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    /**
     * Test of saveToFile method, of class Inventory.
     */
    @Test
    public void testSaveToFile() {
        
        Item item = new Item("Food", "food", 10);
        instance.addItem(item, 5);
        instance.saveToFile("Buddy", FILE_NAME);
        
        Inventory loadedInstance = new Inventory();
        loadedInstance.loadInventory("Buddy", FILE_NAME);
        
        assertEquals(5, loadedInstance.getItemCount(item), "Loaded inventory should match saved inventory.");
    }

    /**
     * Test of loadInventory method, of class Inventory.
     */
    @Test
    public void testLoadInventory() {
        
        Item food = new Item("Food", "food", 10);
        
        Inventory newInventory = new Inventory();
        newInventory.loadInventory("Buddy", FILE_NAME);
        
        assertEquals(3, newInventory.getItemCount(food), "Loaded item count should match saved data.");
    }

    /**
     * Test of addItem method, of class Inventory.
     */
    @Test
    public void testAddItem_Object() {
        System.out.println("addItem with item");
        Item item = new Item("Gift", "gift", 15);
        instance.addItem(item);
        
        assertEquals(1, instance.getItemCount(item), "Item count should be 1 after adding a single item.");
    }

    /**
     * Test of addItem method with quantity, of class Inventory.
     */
    @Test
    public void testAddItem_Object_int() {
        Item item = new Item("Food", "food", 10);
        int quantity = 5;
        instance.addItem(item, quantity);
        
        assertEquals(5, instance.getItemCount(item), "The item quantity should match the added quantity.");
    }

    /**
     * Test of removeItem method, of class Inventory.
     */
    @Test
    public void testRemoveItem() {
        Item item = new Item("Gift", "gift", 15);
        instance.addItem(item, 3);
        
        instance.removeItem(item);
        assertEquals(2, instance.getItemCount(item), "Item should be removed completely.");
    }

    /**
     * Test of getItemsList method, of class Inventory.
     */
    @Test
    public void testGetItemsList() {
        Item item1 = new Item("Food", "food", 10);
        Item item2 = new Item("Toy", "toy", 20);
        
        instance.addItem(item1, 2);
        instance.addItem(item2, 1);
        
        List<Item> result = instance.getItemsList();
        assertTrue(result.contains(item1) && result.contains(item2), "Items list should contain both added items.");
    }

    /**
     * Test of getItem method, of class Inventory.
     */
    @Test
    public void testGetItem() {
        Item item = new Item("Food", "food", 10);
        instance.addItem(item, 3);
        
        Item result = instance.getItem("Food");
        assertEquals(item, result, "getItem should return the correct item by name.");
    }

    /**
     * Test of getItemCount method, of class Inventory.
     */
    @Test
    public void testGetItemCount() {
        Item item = new Item("Food", "food", 10);
        instance.addItem(item, 4);
        
        int result = instance.getItemCount(item);
        assertEquals(4, result, "Item count should be correct for added items.");
    }

    /**
     * Test of toString method, of class Inventory.
     */
    @Test
    public void testToString() {
        Item item = new Item("Food", "food", 10);
        instance.addItem(item, 2);
        
        String result = instance.toString();
        assertTrue(result.contains("Food"), "Inventory string should contain item details.");
    }
}
