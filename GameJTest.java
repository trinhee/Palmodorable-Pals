import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class GameJTest {
   
    @Test
    void testGetPet() {

        Pet fido = new Pet("Fluffy", 20, 25);

        //fido.saveToFile();

        Pet Buddy = new Pet("Fluffy", 20, 25);

        Game test1 = new Game("Fluffy");

        Pet actual = test1.getPet();

        assertEquals(Buddy, fido);
    }

    @Test
    void testGetSettings() {

        Game test = new Game("Buddy");

        Settings actual = test.getSettings();

        Settings settings = new Settings("Buddy");

        settings.setBackgroundMusic(true);
        settings.setStudyTime(0);
        settings.setBreakTime(40);
        settings.setTargetStudyTime(10);

        assertEquals(settings, actual);

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
