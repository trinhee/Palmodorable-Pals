package JUnit;
import org.junit.jupiter.api.Test;

import Game;
import GameManager;
import Inventory;
import Item;
import Pet;
import Settings;
import StatisticsTracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Test class for testing the game manager class.
 * @author Julian Sharpe
 */
public class GameManagerJTest {

    private static final String TEST1_FILE_NAME = "test_game_manager_settings.csv";
    private static final String TEST2_FILE_NAME = "test_game_manager_petdata.csv";
    private static final String TEST3_FILE_NAME = "test_game_manager_statsTracker.csv";
    
    /**
     * Sets up test files before every test
     * @throws IOException
     */
    @BeforeAll
    public static void setUpClass() throws IOException {

        File testFile = new File(TEST1_FILE_NAME);
        File testFile2 = new File(TEST2_FILE_NAME);
        File testFile3 = new File(TEST3_FILE_NAME);

        // write settings data for testing
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("petName,backgroundMusic,isParent,studyTime,breakTime,targetStudyTime\n");
            writer.write("Buddy,1,0,30,10,5\n");
        }

        // Write stats tracker data for testing
        try (FileWriter writer2 = new FileWriter(testFile3)){
            Files.write(Paths.get(TEST3_FILE_NAME), 
                java.util.Collections.singletonList("Name,LastLogin,LastLogout,DayStart,DayEnd,TotalStudyTime\n" + 
                "Buddy,2024-11-25 10:00 AM,2024-11-25 11:00 AM,08:00,18:00,60"));
        }

        // Write pet data for testing
        try (FileWriter writer3 = new FileWriter(testFile2)) {
            writer3.write("name,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory");
            writer3.write("Buddy,100,100,10,20,10,15,\"Gift: 0, Food: 0\"\n");
        }
    }

    /**
     * Deletes all the test files after testing
     */
    @AfterAll
    public static void tearDownClass() {

        // Clean up test files after all tests
        File testFile = new File(TEST1_FILE_NAME);
        File testFile2 = new File(TEST2_FILE_NAME);
        File tesstFile3 = new File(TEST3_FILE_NAME);

        if (testFile.exists()) {
            testFile.delete();
        }
        if (testFile2.exists()) {
            testFile2.delete();
        }
        if (tesstFile3.exists()) {
            tesstFile3.delete();
        }
    }


    /**
     * Test the exit game method
     */
    @Test
    void testExitGame() {

        GameManager test = new GameManager("Buddy");

        test.updateStatsContinuous(test.getCurrentPet(), 4);

        assertNotEquals(test.getCurrentPet().getHappiness(), 100);

        test.exitGame();

        Pet reloaded = test.getCurrentPet();

        assertNotEquals(reloaded.getHappiness(), 100);
    }

    /**
     * Test the getCurrentGame method
     */
    @Test
    void testGetCurrentGame() {

        GameManager test = new GameManager("Buddy");

        Game trial = test.getCurrentGame();

        // Tests for accurate pet information from the game
        assertEquals("Buddy", trial.getPet().getName());
        assertEquals(20, trial.getPet().getHappiness());
        assertEquals(10, trial.getPet().getSleepEffectiveness());

        // Tests for accurate settings information from the game
        assertEquals(true, trial.getSettings().getBackgroundMusic());
        assertEquals(false, trial.getSettings().isParent());

    }

    /**
     * Test the getCurrentInventory method
     */
    @Test
    void testGetCurrentInventory() {

        GameManager test = new GameManager("Buddy");

        Inventory trial = test.getCurrentInventory();

        System.out.println(test.getCurrentInventory().getItemsList());

        Item Gift = new Item("gift", "gift", 20);

        trial.addItem(Gift, 2);

        test.saveGame();

        Inventory reload = test.getCurrentInventory();

        assertEquals(Gift, reload.getItem("gift"));

    }

    /**
     * Test the getCurrentPet method
     */
    @Test
    void testGetCurrentPet() {

        GameManager test = new GameManager("Buddy");

        Pet trial = test.getCurrentPet();

        assertEquals("Buddy", trial.getName());
        assertEquals(100, trial.getHealth());
        assertEquals(100, trial.getSleep());
    }

    /**
     * Test the getCurrentSettings method
     */
    @Test
    void testGetCurrentSettings() {

        GameManager test = new GameManager("Buddy");

        Settings trial = test.getCurrentSettings();

        assertEquals(true, trial.getBackgroundMusic());
        assertEquals(false, trial.isParent());
    }

    /**
     * Test the getCurrentStatisticsTracker method
     */
    @Test
    void testGetCurrentStatisticsTracker() {

        GameManager test = new GameManager("Buddy");

        StatisticsTracker trial = test.getCurrentStatisticsTracker();

        assertEquals("18:00",trial.getDayEnd());
        assertEquals("2024-11-25 11:00 AM", trial.getLastLogout());

    }

    /**
     * Test the givePet method
     */
    @Test
    void testGivePet() {

        GameManager test = new GameManager("Buddy");

        test.getCurrentPet().setHappiness(30);

        Item trial = new Item("gift", "gift", 70);
        test.getCurrentInventory().addItem(trial, 2);

        test.givePet("gift");

        assertEquals(100, test.getCurrentPet().getHappiness());

        assertEquals(1, test.getCurrentInventory().getItemCount(trial));

    }

    /**
     * Test the saveGame method
     */
    @Test
    void testSaveGame() {

        GameManager test = new GameManager("Buddy");

        test.updateStatsContinuous(test.getCurrentPet(), 4);

        assertNotEquals(test.getCurrentPet().getHappiness(), 100);

        test.exitGame();

        GameManager test2 = new GameManager("Buddy");

        Pet reloaded = test2.getCurrentPet();

        assertNotEquals(reloaded.getHappiness(), 100);

    }

    /**
     * Test the startGame method
     */
    @Test
    void testStartGame() {

        GameManager test = new GameManager("Buddy");

        //Check debug consol, printed line is correct
        //startgame has no return value and does no updating, just a confirmation of pet name
        test.startGame();

    }

    /**
     * Test the startStudySession method
     */
    @Test
    void testStartStudySession() {

        GameManager test = new GameManager("Buddy");

        test.startStudySession();

        assertFalse(test.getCurrentInventory().getItemsList().isEmpty());

    }

    /**
     * Test the toString method
     */
    @Test
    void testToString() {

        GameManager test = new GameManager("Buddy");
        
        String result = test.toString();

        assertTrue(result.contains("Game Information"));
        assertTrue(result.contains("Pet Information:"));
        assertTrue(result.contains("Health:"));
        assertTrue(result.contains("Health:"));
        assertTrue(result.contains("Target Study"));
        assertTrue(result.contains("Statistics Tracker Information:"));
        assertTrue(result.contains("Day End:"));
        assertTrue(result.contains("Last Study Session"));
    }

    /**
     * Test the updateStatsContinuous method
     */
    @Test
    void testUpdateStatsContinuous() {

        GameManager test = new GameManager("Buddy");

        assertEquals(100, test.getCurrentPet().getSleep());

        test.updateStatsContinuous(test.getCurrentPet(), 4);

        assertNotEquals(100, test.getCurrentPet().getSleep());
        assertNotEquals(20, test.getCurrentPet().getHappiness());
    }

    /**
     * Test the updateStatsLogin method
     */
    @Test
    void testUpdateStatsLogin() {

        GameManager test = new GameManager("Buddy");

        assertEquals(100, test.getCurrentPet().getSleep());
        assertEquals(100, test.getCurrentPet().getHealth());

        test.updateStatsLogin(test.getCurrentStatisticsTracker(), test.getCurrentPet());

        assertNotEquals(100, test.getCurrentPet().getSleep());
        assertNotEquals(100, test.getCurrentPet().getHealth());

    }

    /**
     * Test the updateStatsNextDayDemo
     */
    @Test
    void testUpdateStatsNextDayDemo() {

        GameManager test = new GameManager("Buddy");

        assertEquals(100, test.getCurrentPet().getSleep());
        assertEquals(100, test.getCurrentPet().getHealth());

        test.updateStatsNextDayDemo();

        assertNotEquals(100, test.getCurrentPet().getSleep());
        assertNotEquals(100, test.getCurrentPet().getHealth());

    }
}
