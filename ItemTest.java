public class ItemTest {

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Test Item creation

        Game game = new Game("Buddy");
        Pet pet = game.getPet();
        System.out.println("Testing Item creation...");
        Item item1 = new Item("Bone", "gift", 10);
        System.out.println("Name: " + item1.getName()); // Expected: Bone
        System.out.println("Type: " + item1.getType()); // Expected: gift
        System.out.println("Effectiveness: " + item1.getEffectiveness()); // Expected: 10
        System.out.println();

        // Test setters
        System.out.println("Testing setters...");
        item1.setName("Apple");
        item1.setType("food");
        item1.setEffectiveness(15);
        System.out.println("Name: " + item1.getName()); // Expected: Apple
        System.out.println("Type: " + item1.getType()); // Expected: food
        System.out.println("Effectiveness: " + item1.getEffectiveness()); // Expected: 15
        System.out.println();

        // Test applyEffect with a mock Pet object
        System.out.println("Testing applyEffect...");
        System.out.println("Pet initial happiness: " + pet.getHappiness()); // Expected: 50
        System.out.println("Pet initial fullness: " + pet.getFullness()); // Expected: 30

        // Test gift effect
        Item gift = new Item("Toy", "gift", 20);
        gift.applyEffect(pet);
        System.out.println("Pet happiness after gift: " + pet.getHappiness()); // Expected: 70

        // Test food effect
        Item food = new Item("Bone", "food", 50);
        food.applyEffect(pet);
        System.out.println("Pet fullness after food: " + pet.getFullness()); // Expected: 80
        System.out.println();

        // Test equals and hashCode
        System.out.println("Testing equals and hashCode...");
        Item item2 = new Item("Toy", "gift", 20);
        Item item3 = new Item("Apple", "food", 15);
        System.out.println("Item1 equals Item2: " + gift.equals(item2)); // Expected: true
        System.out.println("Item1 equals Item3: " + gift.equals(item3)); // Expected: false
        System.out.println("HashCode Item1: " + gift.hashCode());
        System.out.println("HashCode Item2: " + item2.hashCode());
        System.out.println();

        // Final Test Summary
        System.out.println("All tests completed.");
    }
}
