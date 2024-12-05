package com.mycompany.junittests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the use of the {@code PetsDictionary} Class.
 * @author cameronsolomway
 */
public class PetsDictionaryTest {
    
    public PetsDictionaryTest() {
    }
    private static final String FILE_NAME = "pets_data.csv";
    
    @BeforeEach
    public void setUpClass() throws IOException {

        File testFile = new File(FILE_NAME);
        if (testFile.getParentFile() != null) {
            testFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("name,petType,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory\n");
            writer.write("Buddy,1,100,100,10,20,10,15,\"Gift: 0, Food: 3\"\n");
            writer.write("Fluffy,2,100,100,100,100,20,25,\"Food: 4, Gift: 2\"\n");
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
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
     

    /**
     * Test of getPets method, of class PetsDictionary.
     */
    @Test
    public void testGetPets() {
        PetsDictionary instance = new PetsDictionary(FILE_NAME);
        Map<String, Pet> pets = instance.getPets();
        assertTrue(pets.containsKey("Buddy"));
        assertTrue(pets.containsKey("Fluffy"));
    }

    /**
     * Test of getPetByName method, of class PetsDictionary.
     */
    @Test
    public void testGetPetByName() {
        PetsDictionary instance = new PetsDictionary(FILE_NAME);
        Pet pet = instance.getPetByName("Fluffy");
        assertEquals("Fluffy", pet.getName(), "Expected pet name does not match.");
        assertEquals(100, pet.getHealth(), "Health does not match.");
        assertEquals(100, pet.getSleep(), "Sleep does not match.");
        assertEquals(100, pet.getFullness(), "Fullness does not match.");
        assertEquals(100, pet.getHappiness(), "Happiness does not match.");
        assertEquals(20, pet.getSleepEffectiveness(), "Sleep effectiveness does not match.");
        assertEquals(25, pet.getPlayEffectiveness(), "Play effectiveness does not match.");
    }

    /**
     * Test of toString method, of class PetsDictionary.
     */
    @Test
    public void testToString() {
        PetsDictionary instance = new PetsDictionary(FILE_NAME);
        String expResult = "Name: Fluffy\n" +
            "Pet type: 2\n" +
            "Health: 100\n" +
            "Sleep: 100\n" +
            "Fullness: 100\n" +
            "Happiness: 100\n" +
            "Sleep Effectiveness: 20\n" +
            "Play Effectiveness: 25\n" +
            "-------------------------\n" +
            "Name: Buddy\n" +
            "Pet type: 1\n" +
            "Health: 100\n" +
            "Sleep: 100\n" +
            "Fullness: 10\n" +
            "Happiness: 20\n" +
            "Sleep Effectiveness: 10\n" +
            "Play Effectiveness: 15\n" +
            "-------------------------\n";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
