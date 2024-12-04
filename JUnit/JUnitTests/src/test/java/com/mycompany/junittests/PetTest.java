package JUnit.JUnitTests.src.test.java.com.mycompany.junittests;


import org.junit.jupiter.api.Test;

import backend.Item;
import backend.Pet;

//import Item;
//import Pet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing for the Pet class
 * @author Julian Sharpe
 */
public class PetTest {

    @Test
    void testSaveToFile() {

        Pet fido = new Pet("fido", 0, 40, 40);

        fido.saveToFile();
        
    }

    /**
     * Test for the getFullness method
     */
    @Test
    void testGetFullness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;
        int result2 = 50;

        assertEquals(result, fido.getFullness());

        fido.setFullness(result2);

        assertEquals(result2, fido.getFullness());

    }

    /**
     * Test for the getHappiness method
     */
    @Test
    void testGetHappiness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getHappiness());
    }

    /**
     * Test for the getHealth method
     */
    @Test
    void testGetHealth() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getHealth());

    }

    /**
     * Test for the getMaxFullness method
     */
    @Test
    void testGetMaxFullness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getMaxFullness());

    }

    /**
     * Test for the getMaxHappiness method
     */
    @Test
    void testGetMaxHappiness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getMaxHappiness());

    }

    /**
     * Test for the getMaxHealth method
     */
    @Test
    void testGetMaxHealth() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getMaxHealth());

    }

    /**
     * Test for the getMaxSleep method
     */
    @Test
    void testGetMaxSleep() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getMaxSleep());

    }

    /**
     * Test the getName method
     */
    @Test
    void testGetName() {
    
        Pet fido = new Pet("fido", 0, 100, 100);

        String result = "fido";

        assertEquals(result, fido.getName());

    }

    /**
     * Test the getPlayEffectivness method
     */
    @Test
    void testGetPlayEffectiveness() {

        Pet fido = new Pet("fido", 0, 100, 0);

        int result = 0;

        assertEquals(result, fido.getPlayEffectiveness());

    }

    /**
     * Test the getSleep method
     */
    @Test
    void testGetSleep() {
    
        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        assertEquals(result, fido.getSleep());

    }

    /**
     * Test the getSleepEffectivness
     */
    @Test
    void testGetSleepEffectiveness() {

        Pet fido = new Pet("fido", 0, 0, 100);

        int result = 0;

        assertEquals(result, fido.getSleepEffectiveness());

    }

    @Test
    void testGetPetType() {

        Pet fido = new Pet("fido", 0, 0, 100);

        int result = 0;

        assertEquals(result, fido.getPetType());
    }

    /**
     * Test the Play method
     */
    @Test
    void testPlay() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 100;

        fido.setHappiness(70);

        fido.play();

        assertEquals(result, fido.getHappiness());

    }

    /**
     * Test the setFullness method
     */
    @Test
    void testSetFullness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setFullness(70);

        assertEquals(result, fido.getFullness());

    }

    /**
     * Test for the setHappiness method
     */
    @Test
    void testSetHappiness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setHappiness(70);

        assertEquals(result, fido.getHappiness());

    }

    /**
     * Test for the setHealth method
     */
    @Test
    void testSetHealth() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setHealth(70);

        assertEquals(result, fido.getHealth());

    }

    /**
     * Test the setMaxFullness method
     */
    @Test
    void testSetMaxFullness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setMaxFullness(70);

        assertEquals(result, fido.getMaxFullness());

    }

    /**
     * Test the setMaxHappiness method
     */
    @Test
    void testSetMaxHappiness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setMaxHappiness(70);

        assertEquals(result, fido.getMaxHappiness());

    }

    /**
     * Test the setMaxHealth method
     */
    @Test
    void testSetMaxHealth() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setMaxHealth(70);

        assertEquals(result, fido.getMaxHealth());

    }

    /**
     * Test the setMaxSleep method
     */
    @Test
    void testSetMaxSleep() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setMaxSleep(70);

        assertEquals(result, fido.getMaxSleep());

    }

    /**
     * Test the setName method
     */
    @Test
    void testSetName() {

        Pet fido = new Pet("fido", 0, 100, 100);

        String result = "buddy";

        fido.setName("buddy");

        assertEquals(result, fido.getName());

    }

    /**
     * Test the setPlayEffectiveness method
     */
    @Test
    void testSetPlayEffectiveness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setPlayEffectiveness(70);

        assertEquals(result, fido.getPlayEffectiveness());

    }

    /**
     * Test setSleep
     */
    @Test
    void testSetSleep() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setSleep(70);

        assertEquals(result, fido.getSleep());

    }

    /**
     * Test the setPetType method
     */
    @Test
    void testSetPetType() {
        
        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 1;

        fido.setPetType(1);;

        assertEquals(result, fido.getPetType());
    }

    /**
     * Test the setSleepEffectiveness method
     */
    @Test
    void testSetSleepEffectiveness() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 70;

        fido.setSleepEffectiveness(70);

        assertEquals(result, fido.getSleepEffectiveness());

    }

    /**
     * Test the sleep method
     */
    @Test
    void testSleep() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = 80;

        fido.setSleep(50);

        fido.setSleepEffectiveness(30);

        fido.sleep();

        assertEquals(result, fido.getSleep());

        result = 100;

        fido.sleep();

        assertEquals(result, fido.getSleep());

    }

    /**
     * Test the takeToVet method
     */
    @Test
    void testTakeToVet() {

        Pet fido = new Pet("fido", 0, 100, 100);

        int result = fido.getMaxHealth();

        fido.setHealth(20);

        fido.takeToVet();

        assertEquals(result, fido.getHealth());

    }

    /**
     * Test the useItem method
     */
    @Test
    void testUseItem() {

        Pet fido = new Pet("fido", 0, 100, 100);

        Item ball = new Item("ball", "gift", 40);
        Item steak = new Item("steak", "food", 40);

        int result = 70;

        fido.setFullness(30);
        fido.setHappiness(30);

        fido.useItem(steak);
        fido.useItem(ball);

        assertEquals(result, fido.getFullness());
        assertEquals(result, fido.getHappiness());

        fido.useItem(steak);
        fido.useItem(ball);

        result = 100;

        assertEquals(result, fido.getFullness());
        assertEquals(result, fido.getHappiness());
    }
}

