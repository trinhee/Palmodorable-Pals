import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PetsArray {
    String fileName;
    List<Pet> pets; 

    public PetsArray() {
        this.fileName = "data_handling/pets_data.csv";
        this.pets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
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

                pets.add(pet);            

            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    public List<Pet> getPets() {
        return this.pets;
    }

    public void displayPets() {
        for (Pet pet : pets) {
            System.out.println("Name: " + pet.getName());
            System.out.println("Health: " + pet.getHealth());
            System.out.println("Sleep: " + pet.getSleep());
            System.out.println("Fullness: " + pet.getFullness());
            System.out.println("Happiness: " + pet.getHappiness());
            System.out.println("Sleep Effectiveness: " + pet.getSleepEffectiveness());
            System.out.println("Play Effectiveness: " + pet.getPlayEffectiveness());
            System.out.println("-------------------------");
        }
    }
}

