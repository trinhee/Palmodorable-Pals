public class Main {
    public static void main(String[] args) {
        // Testing the Pet class
        System.out.println("Testing the Pet class:");
        Pet genericPet = new Pet("Fluffy", 80, 40, 60, 70, 8, 12);

        System.out.println("Pet Name: " + genericPet.getName());
        System.out.println("Initial Health: " + genericPet.getHealth());
        System.out.println("Initial Sleep: " + genericPet.getSleep());
        System.out.println("Initial Fullness: " + genericPet.getFullness());
        System.out.println("Initial Happiness: " + genericPet.getHappiness());

        // Simulate actions on the Pet
        genericPet.sleep();
        System.out.println("After sleep, Sleep level: " + genericPet.getSleep());

        genericPet.play();
        System.out.println("After play, Happiness level: " + genericPet.getHappiness());

        // Testing the Dog class
        System.out.println("\nTesting the Dog class:");
        Dog buddy = new Dog("Buddy", "Golden Retriever");

        System.out.println("Dog Name: " + buddy.getName());
        System.out.println("Breed: " + buddy.getBreed());
        System.out.println("Initial Health: " + buddy.getHealth());
        System.out.println("Initial Sleep: " + buddy.getSleep());
        System.out.println("Initial Fullness: " + buddy.getFullness());
        System.out.println("Initial Happiness: " + buddy.getHappiness());

        // Simulate Dog-specific actions
        System.out.println(buddy.bark());
        buddy.play();
        System.out.println("After play, Happiness level: " + buddy.getHappiness());
        buddy.wagTail();
        System.out.println("After wagging tail, Happiness level: " + buddy.getHappiness());

        buddy.fetch();
        System.out.println("After fetch, Sleep level: " + buddy.getSleep());
        System.out.println("After fetch, Happiness level: " + buddy.getHappiness());

        // Take the dog to the vet
        buddy.setHealth(50); // Simulate damage
        System.out.println("Health after taking damage: " + buddy.getHealth());
        buddy.takeToVet();
        System.out.println("After visiting the vet, Health: " + buddy.getHealth());
    }
}
