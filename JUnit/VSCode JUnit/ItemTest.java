package JUnit.JUnitTests.src.test.java.com.mycompany.junittests;


import org.junit.jupiter.api.Test;

import backend.Item;
import backend.Pet;

//import Item;
//import Pet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing for the Item class
 * @author Julian Sharpe
 */
public class ItemTest {
    
    public ItemTest() {
    }

    /**
     * Test of applyEffect method of Item class
     */
    @Test
    void testApplyEffect() {

        Item test = new Item("t1", "gift", 20);
        Item test2 = new Item("t2", "food", 25);

        Pet fido = new Pet("Fido", 0, 100, 100);

        fido.setHappiness(50);
        fido.setFullness(45);

        test.applyEffect(fido);
        test2.applyEffect(fido);

        int expResult1 = 70;

        assertEquals(expResult1, fido.getHappiness());
        assertEquals(expResult1, fido.getFullness());

    }

    /**
     * Test of the Equals method
     */
    @Test
    void testEquals(){

        Item test = new Item("t1", "gift", 24);
        Item test2 = new Item("t1", "gift", 24);
        Item test3 = new Item("t2", "food", 24);
        
        boolean result = test.equals(test2);
        boolean result2 = test.equals(test3);
        
        assertTrue(result);
        assertFalse(result2);

    }

    /**
     * Test of getEffectivness method
     */
    @Test
    void testGetEffectiveness() {

        System.out.println("getEffectivness");

        int expResult1 = 20;
        int expResult2 = 25;

        Item test = new Item("t1", "gift", 20);
        Item test2 = new Item("t2", "food", 25);

        int result1 = test.getEffectiveness();
        int result2 = test2.getEffectiveness();

        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getName method
     */
    @Test
    void testGetName() {

        Item test = new Item("t1", "gift", 20);

        String result = "t1";

        assertEquals(result, test.getName());
    }

    /**
     * Test of the getType method
     */
    @Test
    void testGetType() {

        Item test = new Item("t1", "gift", 20);

        String result = "gift";

        assertEquals(result, test.getType());

    }

    /**
     * Test of the setEffectivness method
     */
    @Test
    void testSetEffectiveness() {

        Item test = new Item("t1", "gift", 0);

        int result = 20;

        test.setEffectiveness(20);

        assertEquals(result, test.getEffectiveness());

    }

    /**
     * Test of setName method
     */
    @Test
    void testSetName() {

        Item test = new Item("t1", "gift", 0);

        String result = "fido";

        test.setName("fido");

        assertEquals(result, test.getName());

    }

    /**
     * Test of setType method
     */
    @Test
    void testSetType() {

        Item test = new Item("t1", "gift", 0);

        String result = "food";

        test.setType("food");

        assertEquals(result, test.getType());

    }
    
}

