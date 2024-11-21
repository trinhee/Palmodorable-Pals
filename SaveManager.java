/**
 * Handles the saving and loading of the game
 * This class is responsible for managing the save slots, saving the current pet's state to a file, 
 * and loading saved game data from the file. The save data is stored in a CSV file, and it supports 
 * multiple save slots.
 * 
 * @author Cameron Solomway
 * @version 1.0
 */

/** For writing to files */
import java.io.BufferedWriter;  
import java.io.FileWriter;      

/** For handling IO exceptions (errors) */
import java.io.IOException;

/** For reading from files */
import java.io.BufferedReader;  
import java.io.FileReader;   

/** ArrayList implementation for save list */
import java.util.ArrayList;  

/** For Save List interface */
import java.util.List;          



/**
 * Manages the saving and loading of pet data to and from CSV files.
 * This class provides methods to save and load the pet's state from a CSV file, 
 * allowing for persistent game data across sessions.
 * @version 1.0
 */
public class SaveManager {
    private Pet currentPet;
    private List<SaveFile> saveSlots;
    private static final String SAVE_FILE = "pet_saves.csv"; /** File to store save data in */
    private static final int MAX_SAVE_SLOTS = 3;             /** Limit to 3 save slots */

    /**
     * Constructor for SaveManager. Initializes saveSlots and loads existing saves from the file.
     * The saveSlots list is populated with empty slots (null) initially, and then the existing saves 
     * are loaded from the CSV file.
     * @version 1.0
     */
    public SaveManager() {
        /** Initialize saveSlots with 3 null slots */
        this.saveSlots = new ArrayList<>(MAX_SAVE_SLOTS);
        for (int i = 0; i < MAX_SAVE_SLOTS; i++) {
            saveSlots.add(null); /** Filling saveSlots will 3 nulls where the saves will go */
        }
        loadAllSaves(); /** Load existing saves into saveSlots */
    }

    /**
     * Sets the pet that is currently being played in the game.
     * 
     * @version 1.0
     * @param pet The pet to set as the current pet.
     */
    public void setCurrentPet(Pet pet) {
        this.currentPet = pet;
    }

    /**
     * Saves the current pet's state to the specified save slot.
     * This method writes the pet's data to the corresponding save slot in the saveSlots list 
     * and then saves all slots to the file.
     * 
     * @version 1.0
     * @param pet The pet whose data is being saved.
     * @param slot The index of the save slot where the pet's data will be stored.
     */
    public void saveGame(Pet pet, int slot) {
        if (pet == null) {
            System.out.println("No active pet to save.");
            return;
        }
        if (slot < 0 || slot >= MAX_SAVE_SLOTS) {
            System.out.println("Invalid slot index. Please select a valid slot (0 to 2).");
            return;
        }

        /** Create a SaveFile from the pet */
        SaveFile save = new SaveFile(pet);

        /** Save or overwrite the slot */
        saveSlots.set(slot, save);

        /** Write all saves to the file */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            for (SaveFile slotSave : saveSlots) {
                if (slotSave != null) {
                    writer.write(slotSave.toCSV());
                    writer.newLine();
                }
            }
            System.out.println("Game saved in slot " + slot + ".");
        } catch (IOException e) {
            System.err.println("Failed to save the game: " + e.getMessage());
        }
    }

    /**
     * Loads the pet data from the specified save slot.
     * If a valid save exists in the given slot, the pet data is loaded and applied to the current game.
     * 
     * @version 1.0
     * @param slot The index of the save slot to load data from.
     * @return The pet loaded from the save slot, or null if no valid save exists.
     */
    public Pet loadSave(int slot) {
        if (slot < 0 || slot >= MAX_SAVE_SLOTS) {
            System.out.println("Invalid slot number. Please select a valid slot (0 to 2).");
            return null;
        }

        SaveFile save = saveSlots.get(slot);
        if (save == null) {
            System.out.println("No save exists in slot " + slot + ".");
            return null;
        }

        /** Create a pet object from the save file */
        Pet loadedPet = new Pet(
            save.getPetName(),
            save.getPetID(),
            save.getFullness(),
            save.getHappiness(),
            save.getSleep(),
            save.getHealth(),
            save.getInventory()
        );
        setCurrentPet(loadedPet);
        System.out.println("Loaded pet from slot " + slot + ": " + loadedPet.getName());
        return loadedPet;
    }

    /**
     * Loads all the saved data from the save file and updates the saveSlots list.
     * This method reads the CSV file and fills the saveSlots with the pet data from each save slot.
     * If the file does not exist, it starts fresh with empty save slots.
     * @version 1.0
     */
    public void loadAllSaves() {
        File file = new File(SAVE_FILE);

        /** Clear existing saves and reset to 3 null slots */
        saveSlots.clear();
        for (int i = 0; i < MAX_SAVE_SLOTS; i++) {
            saveSlots.add(null);
        }

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int index = 0;
                while ((line = reader.readLine()) != null && index < MAX_SAVE_SLOTS) {
                    saveSlots.set(index, SaveFile.fromCSV(line));
                    index++;
                }
                System.out.println("Saves loaded from file.");
            } catch (IOException e) {
                System.err.println("Failed to load saves: " + e.getMessage());
            }
        } else {
            System.out.println("No save file found. Starting fresh.");
        }
    }


    /**
     * Represents a single save file containing the pet's data.
     * This inner class stores the pet's name, ID, stats (fullness, happiness, etc.), and inventory.
     * It also provides methods to convert the data to and from CSV format for easy storage and retrieval.
     * @version 1.0
     */
    public static class SaveFile {
        private String petName;
        private int petID;
        private int fullness;
        private int happiness;
        private int sleep;
        private int health;
        private List<Item> inventory;

        /**
         * Constructs a SaveFile object from a pet's data.
         * 
         * @param pet The pet whose data will be saved.
         * @version 1.0
         */
        public SaveFile(Pet pet) {
            this.petName = pet.getName();
            this.petID = pet.getPetID();
            this.fullness = pet.getFullness();
            this.happiness = pet.getHappiness();
            this.sleep = pet.getSleep();
            this.health = pet.getHealth();
            this.inventory = pet.getInventory();
        }

        /**
         * Converts the save file's data to CSV format.
         * The data includes the pet's name, ID, stats, and inventory, separated by commas.
         * 
         * @version 1.0
         * @return A CSV representation of the save file.
         */
        public String toCSV() {
            String inventoryString = inventory == null ? "" : String.join("|", inventory.stream().map(Item::getName).toArray(String[]::new));
            return String.join(",", petName, String.valueOf(petID), String.valueOf(fullness), String.valueOf(happiness), String.valueOf(sleep), String.valueOf(health), inventoryString);
        }

        /**
         * Converts a CSV string back into a SaveFile object.
         * This method parses a CSV string into a SaveFile object, extracting each piece of data for the pet.
         * 
         * @version 1.0
         * @param csv The CSV string containing the saved pet data.
         * @return The SaveFile object created from the CSV string.
         */
        public static SaveFile fromCSV(String csv) {
            String[] parts = csv.split(",", -1); /** Use -1 to include empty trailing values */
            String petName = parts[0];
            int petID = Integer.parseInt(parts[1]);
            int fullness = Integer.parseInt(parts[2]);
            int happiness = Integer.parseInt(parts[3]);
            int sleep = Integer.parseInt(parts[4]);
            int health = Integer.parseInt(parts[5]);

            List<Item> inventory = new ArrayList<>();
            if (parts.length > 6 && !parts[6].isEmpty()) {
                String[] items = parts[6].split("\\|");
                for (String itemName : items) {
                    inventory.add(new Item(itemName)); /** Assuming Item has a constructor taking the name */
                }
            }

            SaveFile save = new SaveFile(new Pet(petName, petID, fullness, happiness, sleep, health, inventory));
            return save;
        }

        /** Getters for SaveFile properties */
        public String getPetName() { return petName; }
        public int getPetID() { return petID; }
        public int getFullness() { return fullness; }
        public int getHappiness() { return happiness; }
        public int getSleep() { return sleep; }
        public int getHealth() { return health; }
        public List<Item> getInventory() { return inventory; }
    }
}
