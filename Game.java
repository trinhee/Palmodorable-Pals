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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Game Information ===\n");
        sb.append("Pet Information:\n");
        sb.append("Name: ").append(pet.getName()).append("\n");
        sb.append("Health: ").append(pet.getHealth()).append("\n");
        sb.append("Sleep: ").append(pet.getSleep()).append("\n");
        sb.append("Fullness: ").append(pet.getFullness()).append("\n");
        sb.append("Happiness: ").append(pet.getHappiness()).append("\n");
        sb.append("Settings:\n");
        sb.append(settings.toString());
        return sb.toString();
    }
}