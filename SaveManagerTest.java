package com.mycompany.studenttesting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SaveManager. 
 * This class tests the saving and loading functionality of the SaveManager class.
 * 
 * @author Cameron Solomway
 */
public class SaveManagerTest {

    private SaveManager saveManager;
    private Pet pet1;
    private Pet pet2;

    /**
     * Set up the test environment before each test.
     * Initializes SaveManager and creates a couple of pets for testing.
     * It also ensures that the save file is cleared before each test to prevent data contamination.
     */
    @BeforeEach
    public void setUp() {
        /** Prepare the SaveManager and some pets for testing */
        saveManager = new SaveManager();
        
        /** Create a couple of pets with sample data */
        pet1 = new Pet("Fluffy", 1, 50, 80, 60, 100, List.of(new Item("Treat", "food", 15) , new Item("Ball", "gift", 20)));
        pet2 = new Pet("Spot", 2, 70, 90, 80, 100, List.of(new Item("Treat", "food", 15) , new Item("Meal", "food", 40)));
        
        /** Clear the save file before each test to avoid test data contamination */
        File file = new File(SaveManager.SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test the saving of a pet to a valid slot.
     * Verifies that the saved pet data can be loaded correctly from the save slot.
     */
    @Test
    public void testSaveGame() {
        // Save pet1 into slot 0
        saveManager.saveGame(pet1, 0);

        // Load the saved pet from slot 0
        Pet loadedPet = saveManager.loadSave(0);
        
        // Assert the loaded pet data matches the saved pet data
        assertNotNull(loadedPet, "Loaded pet should not be null");
        assertEquals(pet1.getName(), loadedPet.getName(), "Pet names should match");
        assertEquals(pet1.getPetID(), loadedPet.getPetID(), "Pet IDs should match");
        assertEquals(pet1.getFullness(), loadedPet.getFullness(), "Fullness should match");
        assertEquals(pet1.getHappiness(), loadedPet.getHappiness(), "Happiness should match");
        assertEquals(pet1.getSleep(), loadedPet.getSleep(), "Sleep should match");
        assertEquals(pet1.getHealth(), loadedPet.getHealth(), "Health should match");
        assertEquals(pet1.getInventory().size(), loadedPet.getInventory().size(), "Inventory size should match");
    }

    /**
     * Test saving the game to an invalid save slot.
     * Verifies that no save data is stored in invalid slots and loading from those slots returns null.
     */
    @Test
    public void testSaveGameInvalidSlot() {
        // Try saving a pet into an invalid slot (-1)
        saveManager.saveGame(pet1, -1);
        // Try saving into an invalid slot (3, which is out of bounds)
        saveManager.saveGame(pet1, 3);

        // The save file should not contain any invalid data
        Pet loadedPet = saveManager.loadSave(0); // Load from valid slot
        assertNull(loadedPet, "There should be no pet saved in the invalid slots");
    }

    /**
     * Test loading the game from valid save slots.
     * Verifies that pets saved in different slots can be loaded correctly.
     */
    @Test
    public void testLoadSave() {
        // Save pet1 into slot 0 and pet2 into slot 1
        saveManager.saveGame(pet1, 0);
        saveManager.saveGame(pet2, 1);

        // Load and check both pets
        Pet loadedPet1 = saveManager.loadSave(0);
        Pet loadedPet2 = saveManager.loadSave(1);

        // Assert the loaded pets match the saved ones
        assertNotNull(loadedPet1, "Pet1 should be loaded successfully");
        assertNotNull(loadedPet2, "Pet2 should be loaded successfully");

        assertEquals(pet1.getName(), loadedPet1.getName(), "Pet1 name should match");
        assertEquals(pet2.getName(), loadedPet2.getName(), "Pet2 name should match");
    }

    /**
     * Test loading from an empty save slot.
     * Verifies that loading from an empty save slot returns null.
     */
    @Test
    public void testLoadSaveNoPetInSlot() {
        // Try to load from an empty save slot (slot 2)
        Pet loadedPet = saveManager.loadSave(2);

        // There should be no pet saved in slot 2
        assertNull(loadedPet, "No pet should be loaded from an empty save slot");
    }

    /**
     * Test loading all saves from the save file.
     * Verifies that multiple pets can be saved and then loaded correctly from different slots.
     */
    @Test
    public void testLoadAllSaves() {
        // Save pets into multiple slots
        saveManager.saveGame(pet1, 0);
        saveManager.saveGame(pet2, 1);

        // Reload all saves
        saveManager.loadAllSaves();

        // Load and verify each pet from the save slots
        Pet loadedPet1 = saveManager.loadSave(0);
        Pet loadedPet2 = saveManager.loadSave(1);

        assertNotNull(loadedPet1, "Pet1 should be loaded from slot 0");
        assertNotNull(loadedPet2, "Pet2 should be loaded from slot 1");
        assertEquals(pet1.getName(), loadedPet1.getName(), "Pet1 name should match");
        assertEquals(pet2.getName(), loadedPet2.getName(), "Pet2 name should match");
    }
}
