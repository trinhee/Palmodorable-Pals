public class Game {

    private Pet pet;
    private Settings settings;

    public Game(String petName) {
        // Initialize pet from PetsDictionary
        PetsDictionary petsDictionary = new PetsDictionary();
        this.pet = petsDictionary.getPetByName(petName);
        
        if (this.pet == null) {
            System.err.println("Error: Pet with name '" + petName + "' not found.");
            return;
        }

        // Initialize settings for the given pet
        this.settings = new Settings(petName);

        System.out.println("Game initialized with pet: " + this.pet.getName());
        System.out.println("Game settings loaded for pet: " + this.pet.getName());
    }

    public Pet getPet() {
        return pet;
    }

    public Settings getSettings() {
        return settings;
    }

    public void displayGameInfo() {
        System.out.println("=== Game Information ===");
        System.out.println("Pet Information:");
        System.out.println("Name: " + pet.getName());
        System.out.println("Health: " + pet.getHealth());
        System.out.println("Sleep: " + pet.getSleep());
        System.out.println("Fullness: " + pet.getFullness());
        System.out.println("Happiness: " + pet.getHappiness());
        System.out.println("Settings:");
        settings.displaySettings();
    }
}