package JUnit.JUnitTests.src.test.java.com.mycompany.junittests;

import org.junit.jupiter.api.Test;

import backend.Game;
import backend.GameManager;
import backend.Inventory;
import backend.Item;
import backend.Pet;
import backend.Settings;
import backend.StatisticsTracker;

/*import Game;
import GameManager;
import Inventory;
import Item;
import Pet;
import Settings;
import StatisticsTracker;
*/

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
public class GameManagerTest {

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
            writer.write("backgroundMusic,studyTime,breakTime\n");
            writer.write("1,30,10\n");
        }

        // Write stats tracker data for testing
        try (FileWriter writer2 = new FileWriter(testFile3)){
            Files.write(Paths.get(TEST3_FILE_NAME), 
                java.util.Collections.singletonList("Name,last_study_session,last_logout,day_start,day_end,total_study_time\n" + 
                "Buddy,2024-11-23 02:00 PM,2024-11-22 12:00 PM,9:00,18:00,60"));
        }

        // Write pet data for testing
        try (FileWriter writer3 = new FileWriter(testFile2)) {
            writer3.write("name,pettype,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory");
            writer3.write("Buddy,0,100,100,10,20,10,15,\"Gift: 0, Food: 0\"\n");
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

        GameManager test = new GameManager("Buddy", 0);

        test.getCurrentPet().setHappiness(75);

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

        GameManager test = new GameManager("Buddy", 0);

        Game trial = test.getCurrentGame();

        // Tests for accurate pet information from the game
        assertEquals("Buddy", trial.getPet().getName());
        assertEquals(0, trial.getPet().getPetType());
        assertEquals(50, trial.getPet().getSleepEffectiveness());

        // Tests for accurate settings information from the game
        assertEquals(false, trial.getSettings().getBackgroundMusic());

    }

    /**
     * Test the getCurrentInventory method
     */
    @Test
    void testGetCurrentInventory() {

        GameManager test = new GameManager("Buddy", 0);

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

        GameManager test = new GameManager("Buddy", 0);

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

        GameManager test = new GameManager("Buddy", 0);

        Settings trial = test.getCurrentSettings();

        assertEquals(false, trial.getBackgroundMusic());
    }

    /**
     * Test the getCurrentStatisticsTracker method
     */
    @Test
    void testGetCurrentStatisticsTracker() {

        GameManager test = new GameManager("Buddy", 0);

        StatisticsTracker trial = test.getCurrentStatisticsTracker();

        assertEquals("18:00",trial.getDayEnd());
        assertEquals("2024-11-22 12:00 PM", trial.getLastLogout());

    }

    /**
     * Test the getInstance method
     */
    @Test
    void testGetInstance() {

        GameManager.getInstance();

        assertEquals("Fluffy", GameManager.getInstance().getCurrentPet().getName());
    }

    /**
     * Test the setInstance method
     */
    @Test
    void testSetInstance() {

        GameManager fido = new GameManager("fido", 0);

        GameManager.setInstance(fido);

        assertEquals("fido", GameManager.getInstance().getCurrentPet().getName());

    }

    /**
     * Test the setPet method
     */
    @Test
    void testSetPet() {

        GameManager fido = new GameManager("fido", 0);

        Pet test = new Pet("Flaffy", 2, 10, 10);

        fido.setCurrentPet(test);

        assertEquals("Flaffy", fido.getCurrentPet().getName());

    }

    

    /**
     * Test the visitVet method
     */
    @Test
    void testVisitVet() {

        GameManager fido = new GameManager("fido", 0);

        fido.getCurrentPet().setHealth(40);

        fido.visitVet();

        assertEquals(100, fido.getCurrentPet().getHealth());
    }

    /**
     * Test the excersisePet method
     */
    @Test
    void testExcersisePet() {

        GameManager fido = new GameManager("fido", 0);

        fido.getCurrentPet().setHappiness(20);

        fido.exercisePet();

        assertEquals(25, fido.getCurrentPet().getHappiness());

    }

    /**
     * Test the sleepPet method
     */
    @Test
    void testSleepPet() {

        GameManager fido = new GameManager("fido", 0);

        fido.getCurrentPet().setSleep(20);

        fido.sleepPet();

        assertEquals(25, fido.getCurrentPet().getSleep());
    }

    /**
     * Test the givePet method
     */
    @Test
    void testGivePet() {

        GameManager test = new GameManager("Buddy", 0);

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

        GameManager test = new GameManager("Buddy", 0);

        test.getCurrentPet().setFullness(49);

        test.exitGame();

        GameManager test2 = new GameManager("Buddy", 0);

        Pet reloaded = test2.getCurrentPet();

        assertNotEquals(reloaded.getHappiness(), 100);

    }

    /**
     * Test the startGame method
     */
    @Test
    void testStartGame() {

        GameManager test = new GameManager("Buddy", 0);

        //Check debug consol, printed line is correct
        //startgame has no return value and does no updating, just a confirmation of pet name
        test.startGame();

    }

    /**
     * Test the startStudySession method
     */
    @Test
    void testStartStudySession() {

        GameManager test = new GameManager("Buddy", 0);

        test.startStudySession();

        assertFalse(test.getCurrentInventory().getItemsList().isEmpty());

    }

    /**
     * Test the toString method
     */
    @Test
    void testToString() {

        GameManager test = new GameManager("Buddy", 0);
        
        String result = test.toString();

        assertTrue(result.contains("Game Information"));
        assertTrue(result.contains("Pet Information:"));
        assertTrue(result.contains("Health:"));
        assertTrue(result.contains("Health:"));
        assertTrue(result.contains("Statistics Tracker Information:"));
        assertTrue(result.contains("Day End:"));
        assertTrue(result.contains("Last Study Session"));
    }

    /**
     * Test the updateStatsContinuous method
     */
    @Test
    void testUpdateStatsContinuous() {

        GameManager test = new GameManager("Buddy", 0);

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

        GameManager test = new GameManager("Buddy", 0);

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

        GameManager test = new GameManager("Buddy", 0);

        assertEquals(100, test.getCurrentPet().getSleep());
        assertEquals(100, test.getCurrentPet().getFullness());

        test.updateStatsNextDayDemo();

        assertNotEquals(100, test.getCurrentPet().getSleep());
        assertNotEquals(100, test.getCurrentPet().getFullness());

    }
}


