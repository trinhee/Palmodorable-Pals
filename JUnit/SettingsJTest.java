package com.mycompany.statisticstrackertest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsJTest {

    private static final String TEST_FILE_NAME = "test_game_settings.csv";
    
    @BeforeAll
    public static void setUpClass() throws IOException {
        File testFile = new File(TEST_FILE_NAME);

        if (testFile.getParentFile() != null) {
            testFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("petName,backgroundMusic,isParent,studyTime,breakTime,targetStudyTime\n");
            writer.write("TestPet,1,0,30,10,5\n");
        }
    }


    @AfterAll
    public static void tearDownClass() {
        // Clean up test file after all tests
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLoadSettings() {
        System.out.println("loadSettings");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);

        assertEquals("TestPet", instance.getPetName());
        assertTrue(instance.getBackgroundMusic());
        assertFalse(instance.isParent());
        assertEquals(30, instance.getStudyTime());
        assertEquals(10, instance.getBreakTime());
        assertEquals(5, instance.getTargetStudyTime());
    }

    @Test
    public void testSaveToFile() {
        System.out.println("saveToFile");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        instance.setBreakTime(20);
        instance.saveToFile(TEST_FILE_NAME);

        // Reload settings to verify changes persisted
        Settings reloaded = new Settings("TestPet",TEST_FILE_NAME);
        assertEquals(20, reloaded.getBreakTime());
    }

    @Test
    public void testSetAndGetStudyTime() {
        System.out.println("setStudyTime and getStudyTime");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        instance.setStudyTime(45);
        assertEquals(45, instance.getStudyTime());
    }

    @Test
    public void testSetAndGetBreakTime() {
        System.out.println("setBreakTime and getBreakTime");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        instance.setBreakTime(15);
        assertEquals(15, instance.getBreakTime());
    }

    @Test
    public void testSetAndGetTargetStudyTime() {
        System.out.println("setTargetStudyTime and getTargetStudyTime");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        instance.setTargetStudyTime(8);
        assertEquals(8, instance.getTargetStudyTime());
    }

    @Test
    public void testSetAndGetBackgroundMusic() {
        System.out.println("setBackgroundMusic and getBackgroundMusic");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        instance.setBackgroundMusic(false);
        assertFalse(instance.getBackgroundMusic());
    }

    @Test
    public void testIsParent() {
        System.out.println("isParent");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        assertFalse(instance.isParent());
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Settings instance = new Settings("TestPet",TEST_FILE_NAME);
        String expected = "Settings for Pet: TestPet\n" +
                "Background Music: On\n" +
                "Is Parent: No\n" +
                "Study Time: 30 minutes\n" +
                "Break Time: 10 minutes\n" +
                "Target Study Time: 5 sessions\n";
        assertEquals(expected, instance.toString());
    }
}
