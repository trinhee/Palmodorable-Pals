package backend;
/**
 * The {@code Game} class represents a game where a player interacts with a virtual pet.
 * It initializes the pet and game settings and provides methods to retrieve game details.
 */
public class Game {


    private Pet pet; // The pet being managed in the game.
    private Settings settings; // The settings specific to the game.


    /**
     * Constructs a {@code Game} instance with the specified pet name.
     * The constructor initializes the game with a pet fetched from the {@code PetsDictionary}.
     * If the pet name is not found, an error is logged, and the game will not be fully initialized.
     *
     * @param petName the name of the pet to initialize the game with.
     */
    public Game(String petName, int petType) {
        PetsDictionary petsDictionary = new PetsDictionary();
        this.pet = petsDictionary.getPetByName(petName);
       
        if (this.pet == null) {
            this.pet = new Pet(petName, petType, 50, 50); //Set 50 as base effectiveness.
        }


        // Initialize settings for the given pet
        this.settings = new Settings(petName);


        System.out.println("Game initialized with pet: " + this.pet.getName());
        System.out.println("Game settings loaded for pet: " + this.pet.getName());
    }


    /**
     * Retrieves the pet associated with this game.
     *
     * @return the pet being managed in the game.
     */
    public Pet getPet() {
        return pet;
    }


    /**
     * Retrieves the settings associated with this game.
     *
     * @return the settings specific to the game.
     */
    public Settings getSettings() {
        return settings;
    }


    /**
     * Returns a string representation of the game's details, including pet information and settings.
     *
     * @return a formatted string containing the game's details.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Game Information ===\n");
        sb.append("Pet Information:\n");
        sb.append("Name: ").append(pet.getName()).append("\n");
        sb.append("Type: ").append(pet.getPetType()).append("\n");
        sb.append("Health: ").append(pet.getHealth()).append("\n");
        sb.append("Sleep: ").append(pet.getSleep()).append("\n");
        sb.append("Fullness: ").append(pet.getFullness()).append("\n");
        sb.append("Happiness: ").append(pet.getHappiness()).append("\n");
        sb.append(settings.toString());
        return sb.toString();
    }
}


