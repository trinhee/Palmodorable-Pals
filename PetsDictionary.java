import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PetsDictionary {
    private String fileName;
    private Map<String, Pet> pets;

    public PetsDictionary() {
        this.fileName = "data_handling/pets_data.csv";
        this.pets = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip header row
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

    public Map<String, Pet> getPets() {
        return this.pets;
    }

    public Pet getPetByName(String name) {
        return this.pets.get(name);
    }

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
