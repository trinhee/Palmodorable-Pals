package JUnit.JUnitTests.src.test.java.com.mycompany.junittests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import backend.Game;
import backend.Pet;
import backend.Settings;

/*import Game;
import Pet;
import Settings;*/

public class GameTest {
   
    @Test
    void testGetPet() {

        Pet fido = new Pet("Smokey", 0, 20, 25);

        fido.saveToFile();

        Game test1 = new Game("Smokey", 0);

        Pet actual = test1.getPet();

        assertEquals("Smokey", actual.getName());
        assertEquals(100, actual.getHealth());
        assertEquals(100, actual.getSleep());
        assertEquals(100, actual.getFullness());
        assertEquals(100, actual.getHappiness());
        assertEquals(50, actual.getSleepEffectiveness());
        assertEquals(50, actual.getPlayEffectiveness());
    }

    @Test
    void testGetSettings() {

        Game test = new Game("Buddy", 9);

        Settings actual = test.getSettings();

        assertFalse(actual.getBackgroundMusic());
        assertEquals(40, actual.getBreakTime());
        assertEquals(0, actual.getStudyTime());

    }

    @Test
    void testToString() {

        Game buddy = new Game("Buddy",0);

        String result = buddy.toString();

        assertTrue(result.contains("=== Game Information ==="));
        assertTrue(result.contains("Pet Information:"));
        assertTrue(result.contains("Name: Buddy"));
        assertTrue(result.contains("Health: 100"));
        assertTrue(result.contains("Sleep: 100"));
        assertTrue(result.contains("Fullness: 10"));
        assertTrue(result.contains("Happiness: 20"));
        assertTrue(result.contains("Settings for Pet: Buddy"));
        assertTrue(result.contains("Background Music: On"));
        assertTrue(result.contains("Study Time: 0 minutes"));
        assertTrue(result.contains("Break Time: 40 minutes"));

    }
}

