package com.mycompany.junittests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SettingsTest {

    private static final String TEST_FILE_NAME = "test_game_settings.csv";

    @BeforeAll
    public static void setUpClass() throws IOException {
        File testFile = new File(TEST_FILE_NAME);

        if (testFile.getParentFile() != null) {
            testFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("backgroundMusic,studyTime,breakTime\n");
            writer.write("1,30,10\n");
        }
    }

    @AfterAll
    public static void tearDownClass() {
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testSaveToFile() {
        System.out.println("saveToFile");

        // Create a Settings instance and modify some values
        Settings instance = new Settings(TEST_FILE_NAME);
        instance.setStudyTime(45); // Modify some setting
        instance.setBreakTime(20); // Modify another setting

        // Call saveToFile to save the settings to the file
        instance.saveToFile(TEST_FILE_NAME);

        // Reload settings from the file to verify the changes
        Settings reloaded = new Settings(TEST_FILE_NAME);

        // Assert that the saved settings match the modified settings
        assertEquals(45, reloaded.getStudyTime());
        assertEquals(20, reloaded.getBreakTime());
    }


    @Test
    public void testLoadSettings() {
        System.out.println("loadSettings");
        Settings instance = new Settings(TEST_FILE_NAME);
        assertTrue(instance.getBackgroundMusic());
        assertEquals(30, instance.getStudyTime());
        assertEquals(10, instance.getBreakTime());
    }

    @Test
    public void testSetAndGetStudyTime() {
        System.out.println("setStudyTime and getStudyTime");
        Settings instance = new Settings(TEST_FILE_NAME);
        instance.setStudyTime(45);
        assertEquals(45, instance.getStudyTime());
    }

    @Test
    public void testSetAndGetBreakTime() {
        System.out.println("setBreakTime and getBreakTime");
        Settings instance = new Settings(TEST_FILE_NAME);
        instance.setBreakTime(15);
        assertEquals(15, instance.getBreakTime());
    }

    @Test
    public void testSetAndGetBackgroundMusic() {
        System.out.println("setBackgroundMusic and getBackgroundMusic");
        Settings instance = new Settings(TEST_FILE_NAME);
        instance.setBackgroundMusic(false);
        assertFalse(instance.getBackgroundMusic());
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Settings instance = new Settings(TEST_FILE_NAME);
        String expected = "Settings for game: \n" +
                          "Background Music: On\n" +
                          "Study Time: 30 minutes\n" +
                          "Break Time: 10 minutes\n";
        assertEquals(expected, instance.toString());
    }
}
