import java.io.*;
import java.util.*;
import java.time.*;
public class GameManager {

    private Game currentGame;
    private Pet currentPet;
    private Inventory currentInventory;
    private Settings currentSettings;
    private String csvFilePath;
    private StatisticsTracker currentStatisticsTracker;
    /**
     * Constructor for GameManager.
     *
     * @param petName     The name of the pet.
     * @param csvFilePath The path to the CSV file.
     */
    public GameManager(String petName) {
        this.currentGame = new Game(petName);
        this.currentPet = currentGame.getPet();
        this.currentSettings = currentGame.getSettings();
        this.currentInventory = new Inventory(); // Initialize Inventory
        this.csvFilePath = "data_handling/pets_data.csv"; // Default inventory file path
        this.currentStatisticsTracker = new StatisticsTracker(petName);
        this.currentInventory.loadInventory(petName);

        startGame();
    }

    /**
     * Loads the inventory for the current pet from a CSV file.
     * The CSV file should have columns: name,health,sleep,fullness,happiness,sleepEffectiveness,playEffectiveness,inventory
     * The inventory column should be in the format: "Food: X, Gift: Y"
     */


    /**
     * Parses a CSV line, considering quoted commas.
     *
     * @param line The CSV line to parse.
     * @return An array of values.
     */

    /**
     * Starts the game and displays the loaded inventory.
     */
    public void startGame() {
        System.out.println("Starting game with pet: " + currentPet.getName());
        System.out.println(currentInventory); // Optional: Display loaded inventory
    }

    /**
     * Saves the game state to a file.
     */
    public void saveGame() {
        // Save the inventory to the CSV file
        currentPet.saveToFile();
        currentSettings.saveToFile();
        System.out.println("Game saved successfully."); 
    }

    // Getter methods for accessing private fields

    public Game getCurrentGame() {
        return currentGame;
    }

    public Pet getCurrentPet() {
        return currentPet;
    }

    public Settings getCurrentSettings() {
        return currentSettings;
    }

    public Inventory getCurrentInventory() {
        return currentInventory;
    }

    public StatisticsTracker getCurrentStatisticsTracker() {
        return currentStatisticsTracker;
    }

    public void givePet(String itemType) {
        Item inventoryItem = this.currentInventory.getItem(itemType);
        if (inventoryItem != null) {
            this.currentInventory.removeItem(inventoryItem);
            this.currentPet.useItem(inventoryItem);
        }
        else {
            System.out.println("Item not found in inventory.");
        }
    }


    public void startStudySession() {
        int studyTime = currentSettings.getStudyTime(); // in minutes
        int breakTime = currentSettings.getBreakTime(); // in minutes
        int totalStudyTime = currentStatisticsTracker.getTotalStudyTime();
    
        int totalStudySeconds = studyTime; // Convert minutes to seconds for countdown
        int totalBreakSeconds = breakTime; // Convert break time to seconds
    
        System.out.println("Study session started! Study time: " + studyTime + " minutes.");
        System.out.println("Press 'q' at any time to break the study session.");
    
        // Progress bar setup
        int barLength = 50; // Length of the progress bar
    
        // Study session countdown
        int actualStudySeconds = countDownWithProgressBar(totalStudySeconds, barLength, "Study");
        int actualStudyMinutes = actualStudySeconds; // Convert back to minutes
    
        // Update the study time based on actual time studied
        totalStudyTime += actualStudyMinutes;
        currentStatisticsTracker.setTotalStudyTime(totalStudyTime);
        LocalDateTime now = LocalDateTime.now();
        String time = StatisticsTracker.formatLocalDateTime(now);
        currentStatisticsTracker.setLastStudySession(now.toString());
        System.out.println("\nTotal study time updated to: " + currentStatisticsTracker.getTotalStudyTime() + " minutes.");
        System.out.println("Last study session: " + time);
    
        // If interrupted during study, exit early
        if (actualStudySeconds < totalStudySeconds) {
            System.out.println("\nStudy session interrupted early.");
            return;
        }
    
        // After study time, take a break
        System.out.println("\nTime for a break! Break time: " + breakTime + " minutes.");
        int actualBreakSeconds = countDownWithProgressBar(totalBreakSeconds, barLength, "Break");
    
        if (actualBreakSeconds < totalBreakSeconds) {
            System.out.println("\nBreak interrupted early.");
        } else {
            System.out.println("\nBreak time is over. Study session completed!");
        }
    }
    
    private int countDownWithProgressBar(int totalSeconds, int barLength, String phase) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < totalSeconds; i++) {
            // Display the progress bar
            int progress = (i * barLength) / totalSeconds;
            System.out.print("\r" + phase + " Progress: [");
            for (int j = 0; j < barLength; j++) {
                if (j <= progress) {
                    System.out.print("=");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("] " + (totalSeconds - i) + "s remaining");
    
            // Check for user interruption
            try {
                if (System.in.available() > 0 && scanner.nextLine().equalsIgnoreCase("q")) {
                    return i; // Return the number of seconds completed
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            // Wait for 1 second
            try {
                Thread.sleep(1000); // 1000ms = 1 second
            } catch (InterruptedException e) {
                System.err.println("Timer interrupted: " + e.getMessage());
            }
        }
        return totalSeconds; // Return the full duration if completed
    }

    public void exitGame() {
        saveGame();
        System.out.println("Exiting game. Goodbye!");
    }
    



    @Override
    public String toString() {
        return "GameManager {" +
               "\n" + currentGame +
               "Inventory: " + currentInventory +
                "\n" + currentStatisticsTracker +
               "\n}";
    }

}
