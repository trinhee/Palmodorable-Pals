package backend;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Pet} class represents a virtual pet in the game.
 * The pet has attributes such as health, sleep, fullness, happiness, and their respective maximum values.
 * The pet can perform actions like sleeping, playing, and using items, which affect its stats.
 * 
 * This class includes functionality for saving pet data to a file.
 * 
 * @author Kaegan Walker Fulton
 */
public class Pet {

    /** Pet's name */
    private String name;
    /** Pet's current health */
    private int health;
    /** Pet's current sleep level */
    private int sleep;
    /** Pet's current fullness */
    private int fullness;
    /** Pet's current happiness */
    private int happiness;
    /** Pet's maximum health */
    private int maxHealth;
    /** Pet's maximum sleep level */
    private int maxSleep;
    /** Pet's maximum fullness */
    private int maxFullness;
    /** Pet's maximum happiness */
    private int maxHappiness;
    /** The effectiveness (stat increase) of sleep */
    private int sleepEffectiveness;
    /** The effectiveness (stat increase) of play */
    private int playEffectiveness;
    /**The type of the pet, 0 = dog, 1 = cat, 2 = bird*/
    private int petType;

    private static final String FILE_PATH = "../data_handling/pets_data.csv"; // The pet data file path

    /**
     * Constructs a {@code Pet} instance and initializes its attributes.
     * Current values are set to their maximums.
     * 
     * @param name               The pet's name.
     * @param sleepEffectiveness The effectiveness of sleep (stat increase).
     * @param playEffectiveness  The effectiveness of play (stat increase).
     */
    public Pet(String name, int petType, int sleepEffectiveness, int playEffectiveness) {
        this.name = name;
        this.maxHealth = 100;
        this.maxSleep = 100;
        this.maxFullness = 100;
        this.maxHappiness = 100;
        this.health = this.maxHealth;
        this.sleep = this.maxSleep;
        this.fullness = this.maxFullness;
        this.happiness = this.maxHappiness;
        this.sleepEffectiveness = sleepEffectiveness;
        this.playEffectiveness = playEffectiveness;
        this.petType = petType;
    }

    /**
     * Saves the pet's data to a CSV file.
     * If the pet already exists in the file, its data is updated; otherwise, a new record is added.
     */
/**
 * Saves the pet's data to a CSV file.
 * If the pet already exists in the file, its data is updated; otherwise, a new record is added.
 */
    public void saveToFile () {
        saveToFile(FILE_PATH);
    }
    
    public void saveToFile(String filePath) {
        String fileName = filePath;
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (data.length < 8) { // Adjusted to match the new format without inventory
                    System.err.println("Invalid data format: " + line);
                    continue; // Skip invalid lines
                }

                if (data[0].equalsIgnoreCase(name)) {
                    line = String.format("%s,%d,%d,%d,%d,%d,%d,%d",
                            name,
                            petType,
                            health,
                            sleep,
                            fullness,
                            happiness,
                            sleepEffectiveness,
                            playEffectiveness);
                    updated = true;
                }

                lines.add(line);
            }

            if (!updated) {
                lines.add(String.format("%s,%d,%d,%d,%d,%d,%d,%d,%s",
                        name,
                        petType,
                        health,
                        sleep,
                        fullness,
                        happiness,
                        sleepEffectiveness,
                        playEffectiveness,
                        '"' + "Food: 1, Gift: 1" + '"'));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line); 
                writer.newLine();
            }
            System.out.println("Pet data successfully saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    // Setter methods
    /** @param name Sets the pet's name. */
    public void setName(String name) { this.name = name; }

    /** @param health Sets the pet's health. */
    public void setHealth(int health) { this.health = health; }

    /** @param maxHealth Sets the pet's maximum health. */
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    /** @param sleep Sets the pet's sleep level. */
    public void setSleep(int sleep) { this.sleep = sleep; }

    /** @param maxSleep Sets the pet's maximum sleep level. */
    public void setMaxSleep(int maxSleep) { this.maxSleep = maxSleep; }

    /** @param fullness Sets the pet's fullness. */
    public void setFullness(int fullness) { this.fullness = fullness; }

    /** @param maxFullness Sets the pet's maximum fullness. */
    public void setMaxFullness(int maxFullness) { this.maxFullness = maxFullness; }

    /** @param happiness Sets the pet's happiness. */
    public void setHappiness(int happiness) { this.happiness = happiness; }

    /** @param maxHappiness Sets the pet's maximum happiness. */
    public void setMaxHappiness(int maxHappiness) { this.maxHappiness = maxHappiness; }

    /** @param sleepEffectiveness Sets the effectiveness of sleep. */
    public void setSleepEffectiveness(int sleepEffectiveness) { this.sleepEffectiveness = sleepEffectiveness; }

    /** @param playEffectiveness Sets the effectiveness of play. */
    public void setPlayEffectiveness(int playEffectiveness) { this.playEffectiveness = playEffectiveness; }

    /** @param type the type of the pet
     */
    public void setPetType(int type) { this.petType = type;}

    // Getter methods
    /** @return The pet's name. */
    public String getName() { return this.name; }

    /** @return The pet's health. */
    public int getHealth() { return this.health; }

    /** @return The pet's maximum health. */
    public int getMaxHealth() { return this.maxHealth; }

    /** @return The pet's sleep level. */
    public int getSleep() { return this.sleep; }

    /** @return The pet's maximum sleep level. */
    public int getMaxSleep() { return this.maxSleep; }

    /** @return The pet's fullness. */
    public int getFullness() { return this.fullness; }

    /** @return The pet's maximum fullness. */
    public int getMaxFullness() { return this.maxFullness; }

    /** @return The pet's happiness. */
    public int getHappiness() { return this.happiness; }

    /** @return The pet's maximum happiness. */
    public int getMaxHappiness() { return this.maxHappiness; }

    /** @return The effectiveness of sleep. */
    public int getSleepEffectiveness() { return this.sleepEffectiveness; }

    /** @return The effectiveness of play. */
    public int getPlayEffectiveness() { return this.playEffectiveness; }

    /** @return The type of the pet*/
    public int getPetType() { return this.petType; }

    /**
     * Increases the pet's sleep level by the sleep effectiveness value.
     * Caps sleep at the maximum sleep level.
     */
    public void sleep() {
        if (this.getSleep() + this.getSleepEffectiveness() < this.getMaxSleep()) {
            this.setSleep(this.getSleep() + this.getSleepEffectiveness());
        } else {
            this.setSleep(this.getMaxSleep());
        }
    }

    /**
     * Increases the pet's happiness by the play effectiveness value.
     * Caps happiness at the maximum happiness level.
     */
    public void play() {
        if (this.getHappiness() + this.getPlayEffectiveness() < this.getMaxHappiness()) {
            this.setHappiness(this.getHappiness() + this.getPlayEffectiveness());
        } else {
            this.setHappiness(this.getMaxHappiness());
        }
    }


    /**
     * Resets the pet's health to the maximum health level.
     */
    public void takeToVet() {
        this.setHealth(this.getMaxHealth());
    }

    /**
     * Uses the specified item on the pet, applying its effect.
     * 
     * @param item The item to be used.
     */
    public void useItem(Item item) {
        item.applyEffect(this);
        System.out.println("Pet " + name + " used item: " + item.getName() + 
                           " | Type: " + item.getType() + 
                           " | Effectiveness: " + item.getEffectiveness() + "\n");
    
        // Print the updated stats after using the item
        System.out.println("Updated Stats:\n" + this);
    }

    /**
     * Returns a string representation of the pet's current state.
     *
     * @return A formatted string containing the pet's details.
     */
    @Override
    public String toString() {
        return "=== Pet Information ===\n" +
            "Name: " + name + "\n" +
            "Pet type: " + petType + "\n" +
            "Health: " + health + "/" + maxHealth + "\n" +
            "Sleep: " + sleep + "/" + maxSleep + "\n" +
            "Fullness: " + fullness + "/" + maxFullness + "\n" +
            "Happiness: " + happiness + "/" + maxHappiness + "\n" +
            "Sleep Effectiveness: " + sleepEffectiveness + "\n" +
            "Play Effectiveness: " + playEffectiveness + "\n";
    }
}
