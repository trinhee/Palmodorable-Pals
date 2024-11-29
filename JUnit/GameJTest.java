package JUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import Game;
import Pet;
import Settings;

public class GameJTest {
   
    @Test
    void testGetPet() {

        Pet fido = new Pet("Smokey", 20, 25);

        fido.saveToFile();

        Game test1 = new Game("Smokey");

        Pet actual = test1.getPet();

        assertEquals("Smokey", actual.getName());
        assertEquals(100, actual.getHealth());
        assertEquals(100, actual.getSleep());
        assertEquals(100, actual.getFullness());
        assertEquals(100, actual.getHappiness());
        assertEquals(20, actual.getSleepEffectiveness());
        assertEquals(25, actual.getPlayEffectiveness());
    }

    @Test
    void testGetSettings() {

        Game test = new Game("Buddy");

        Settings actual = test.getSettings();

        assertEquals("Buddy", actual.getPetName());
        assertFalse(actual.getBackgroundMusic());
        assertFalse(actual.isParent());
        assertEquals(10, actual.getBreakTime());
        assertEquals(40, actual.getStudyTime());
        assertEquals(10, actual.getTargetStudyTime());

    }

    @Test
    void testToString() {

        Game buddy = new Game("Buddy");

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
        assertTrue(result.contains("Is Parent: No"));
        assertTrue(result.contains("Study Time: 0 minutes"));
        assertTrue(result.contains("Break Time: 40 minutes"));
        assertTrue(result.contains("Target Study Time: 10 sessions"));

    }
}
