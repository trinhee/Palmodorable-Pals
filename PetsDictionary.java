import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code PetsDictionary} class is responsible for loading and managing a collection of pets
 * from a CSV file. It provides functionality to retrieve pets by their name or access all pets as a map.
 */
public class PetsDictionary {

    private String fileName; // Path to the CSV file containing pet data.
    private Map<String, Pet> pets; // A map of pet names to their respective Pet objects.

    /**
     * Constructs a {@code PetsDictionary} instance and loads pet data from a CSV file.
     * Each row in the file represents a pet, with attributes such as name, health, sleep, fullness, happiness,
     * sleep effectiveness, and play effectiveness.
     */
    public PetsDictionary() {
        this.fileName = "data_handling/pets_data.csv";
        this.pets = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the header row
                }

                String[] values = line.split(",");

                String name = values[0];
                int health = Integer.parseInt(values[1]);
                int sleep = Integer.parseInt(values[2]);
                int fullness = Integer.parseInt(values[3]);
                int happiness = Integer.parseInt(values[4]);
                int sleepEffectiveness = Integer.parseInt(values[5]);
                int playEffectiveness = Integer.parseInt(values[6]);

                Pet pet = new Pet(name, sleepEffectiveness, playEffectiveness);
                pet.setHealth(health);
                pet.setSleep(sleep);
                pet.setFullness(fullness);
                pet.setHappiness(happiness);

                pets.put(name, pet);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the map of all pets.
     *
     * @return A map where the keys are pet names and the values are {@code Pet} objects.
     */
    public Map<String, Pet> getPets() {
        return this.pets;
    }

    /**
     * Retrieves a pet by its name.
     *
     * @param name The name of the pet to retrieve.
     * @return The {@code Pet} object corresponding to the given name, or {@code null} if not found.
     */
    public Pet getPetByName(String name) {
        return this.pets.get(name);
    }

    /**
     * Returns a string representation of all the pets in the dictionary.
     * The details for each pet include its name, health, sleep, fullness, happiness,
     * sleep effectiveness, and play effectiveness.
     *
     * @return A formatted string containing the details of all pets in the dictionary.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Pet> entry : pets.entrySet()) {
            Pet pet = entry.getValue();
            sb.append("Name: ").append(pet.getName()).append("\n")
              .append("Health: ").append(pet.getHealth()).append("\n")
              .append("Sleep: ").append(pet.getSleep()).append("\n")
              .append("Fullness: ").append(pet.getFullness()).append("\n")
              .append("Happiness: ").append(pet.getHappiness()).append("\n")
              .append("Sleep Effectiveness: ").append(pet.getSleepEffectiveness()).append("\n")
              .append("Play Effectiveness: ").append(pet.getPlayEffectiveness()).append("\n")
              .append("-------------------------\n");
        }
        return sb.toString();
    }
}
