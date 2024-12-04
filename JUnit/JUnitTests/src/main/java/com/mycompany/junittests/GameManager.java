package com.mycompany.junittests;
import java.io.*;
import java.time.*;
import java.util.*;


/**
 * The {@code GameManager} class handles the management of the game, including the pet, inventory, settings,
 * and statistics tracking. It provides functionalities to start the game, save the game state, manage inventory,
 * track study sessions, and exit the game.
 */
public class GameManager {

    private static GameManager instance;
    private Game currentGame; // The current game instance.
    private Pet currentPet; // The current pet being managed in the game.
    private Inventory currentInventory; // The inventory associated with the current pet.
    private Settings currentSettings; // The settings for the current game.
    private String csvFilePath; // Path to the CSV file for storing inventory data.
    private StatisticsTracker currentStatisticsTracker; // Tracks statistics for the current pet.


    /**
     * Constructor for {@code GameManager}.
     * Initializes the game with the given pet name and loads relevant settings, inventory, and statistics.
     *
     * @param petName The name of the pet.
     */
    public GameManager(String petName, int petType) {
        this.currentGame = new Game(petName, petType);
        this.currentPet = currentGame.getPet();
        this.currentSettings = currentGame.getSettings();
        this.currentInventory = new Inventory(); // Initialize Inventory
        this.csvFilePath = "../data_handling/pets_data.csv"; // Default inventory file path
        this.currentStatisticsTracker = new StatisticsTracker(petName);
        this.currentInventory.loadInventory(petName);


        startGame();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager("Fluffy", 0); // Default pet or initialize as required
        }
        return instance;
    }
    

    public static void setInstance(GameManager newInstance) {
        instance = newInstance;
    }
    
    public void exercisePet(){
        this.currentPet.setHappiness(Math.min(this.currentPet.getHappiness() + 5, 100));
    }

    public void sleepPet(){
        this.currentPet.setSleep(Math.min(this.currentPet.getSleep() + 5, 100));
    }

    /**
     * Starts the game by displaying the loaded pet and inventory information.
     */
    public void startGame() {
        System.out.println("Starting game with pet: " + currentPet.getName());
        
        System.out.println(currentInventory); // Optional: Display loaded inventory
    }


    /**
     * Saves the current game state, including pet data, settings, and statistics.
     */
    public void saveGame() {
        currentPet.saveToFile();
        currentSettings.saveToFile();
        currentStatisticsTracker.saveToFile();
        currentInventory.saveToFile(this.currentPet.getName());
        System.out.println("Game saved successfully.");
    }


    /**
     * Retrieves the current {@code Game} instance.
     *
     * @return The current game instance.
     */
    public Game getCurrentGame() {
        return currentGame;
    }


    /**
     * Retrieves the current pet.
     *
     * @return The pet being managed in the game.
     */
    public Pet getCurrentPet() {
        return currentPet;
    }


    /**
     * Sets the current pet.
     *
     * @return The pet being managed in the game.
     */
    public void setCurrentPet(Pet pet) {
        this.currentPet = pet;
    }




    /**
     * Retrieves the current settings.
     *
     * @return The settings associated with the game.
     */
    public Settings getCurrentSettings() {
        return currentSettings;
    }


    /**
     * Retrieves the current inventory.
     *
     * @return The inventory of the current pet.
     */
    public Inventory getCurrentInventory() {
        return currentInventory;
    }


    /**
     * Retrieves the current statistics tracker.
     *
     * @return The statistics tracker associated with the current pet.
     */
    public StatisticsTracker getCurrentStatisticsTracker() {
        return currentStatisticsTracker;
    }

    public void visitVet(){
        this.currentPet.setHealth(100);
    }
    
    /**
     * Gives an item from the inventory to the pet. The item type must match an item in the inventory.
     *
     * @param itemType The type of item to give (e.g., "Food").
     */
    public void givePet(String itemName) {
        Item inventoryItem = this.currentInventory.getItem(itemName);
        if (inventoryItem != null) {
            this.currentInventory.removeItem(inventoryItem);
            this.currentPet.useItem(inventoryItem);
        } else {
            System.out.println("Item not found in inventory.");
        }
    }


    /**
     * Starts a study session, including study time and break time.
     * Tracks the study progress, handles interruptions, and updates statistics accordingly.
     * If study session is completed, gives a random food or gift.
     */
    public void startStudySession() {
        int studyTime = currentSettings.getStudyTime(); // in minutes
        int breakTime = currentSettings.getBreakTime(); // in minutes
        int totalStudyTime = currentStatisticsTracker.getTotalStudyTime();


        int totalStudySeconds = studyTime; // Convert minutes to seconds for countdown
        int totalBreakSeconds = breakTime; // Convert break time to seconds


        System.out.println("Study session started! Study time: " + studyTime + " minutes.");
        System.out.println("Press 'q' at any time to break the study session.");


        int barLength = 50; // Length of the progress bar


        // Study session countdown
        int actualStudySeconds = countDownWithProgressBar(totalStudySeconds, barLength, "Study");
        int actualStudyMinutes = actualStudySeconds;


        totalStudyTime += actualStudyMinutes;
        currentStatisticsTracker.setTotalStudyTime(totalStudyTime);
        LocalDateTime now = LocalDateTime.now();
        String time = StatisticsTracker.formatLocalDateTime(now);
        currentStatisticsTracker.setLastStudySession(now.toString());
        System.out.println("\nTotal study time updated to: " + currentStatisticsTracker.getTotalStudyTime() + " minutes.");
        System.out.println("Last study session: " + time);


        if (actualStudySeconds < totalStudySeconds) {
            System.out.println("\nStudy session interrupted early.");
            return;
        }


        System.out.println("\nTime for a break! Break time: " + breakTime + " minutes.");
        int actualBreakSeconds = countDownWithProgressBar(totalBreakSeconds, barLength, "Break");


        if (actualBreakSeconds < totalBreakSeconds) {
            System.out.println("\nBreak interrupted early.");
        } else {
            long foodOrGift = Math.round(Math.random());
            System.out.println("\nBreak time is over. Study session completed!");


            if(foodOrGift == 0){
                Item foodArray[] = new Item[3];
                foodArray[0] = new Item("Treat", "food", 10);
                foodArray[1] = new Item("Snack", "food", 25);
                foodArray[2] = new Item("Meal", "food", 50);
                Random rand = new Random();
                int randNum = rand.nextInt(3);
                System.out.println("Food given: " + foodArray[randNum].getName());
                this.getCurrentInventory().addItem(foodArray[randNum]);
            }


            if(foodOrGift == 1){
                Item giftArray[] = new Item[3];
                giftArray[0] = new Item("Plushy", "gift", 10);
                giftArray[1] = new Item("Ball", "gift", 25);
                giftArray[2] = new Item("Bell", "gift", 50);
                Random rand = new Random();
                int randNum = rand.nextInt(3);
                System.out.println("Gift given: " + giftArray[randNum].getName());
                this.getCurrentInventory().addItem(giftArray[randNum]);
            }
        }
    }


    /**
     * Handles a countdown timer with a progress bar.
     *
     * @param totalSeconds The total duration of the countdown in seconds.
     * @param barLength    The length of the progress bar.
     * @param phase        The current phase of the timer (e.g., "Study" or "Break").
     * @return The number of seconds completed before interruption.
     */
    private int countDownWithProgressBar(int totalSeconds, int barLength, String phase) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < totalSeconds; i++) {
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


            try {
                if (System.in.available() > 0 && scanner.nextLine().equalsIgnoreCase("q")) {
                    return i;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Timer interrupted: " + e.getMessage());
            }
        }


        return totalSeconds;
    }


    /**
     * Exits the game after saving the current state.
     */
    public void exitGame() {
        saveGame();
        System.out.println("Exiting game. Goodbye!");
    }


    /**
     * Decreases Health by 1 point per hour if happiness or fullness is at 0 or below. Decreases by 2 if both.
     * Does the same for happiness with 25.
     * @param tracker the StatisticsTracker instance of the game
     * @param pet the pet to have points deducted from
     */
    public void updateStatsLogin(StatisticsTracker tracker, Pet pet) {
        long timeElapsed;
        LocalDateTime prevDateTime = StatisticsTracker.formatStringToLocalDateTime(tracker.getLastLogout());
        LocalDateTime currDateTime = LocalDateTime.now();
        Duration duration = Duration.between(currDateTime, prevDateTime);
        timeElapsed = Math.abs(duration.toHours());
        for(int i = (int)timeElapsed; i > 0; i--) {
            if ((pet.getSleep() <= 25 || pet.getFullness() <= 25) && !(pet.getSleep() <= 25 && pet.getFullness() <= 25)) {




                if (pet.getHappiness() - 1 <= 0) {
                    pet.setHappiness(0);
                } else {
                    pet.setHappiness(pet.getHappiness() - 1);
                }


            }


            if (pet.getSleep() <= 25 && pet.getFullness() <= 25) {


                if (pet.getHappiness() - 2 <= 0) {


                    pet.setHappiness(0);
                } else {


                    pet.setHappiness(pet.getHappiness() - 2);


                }
            }


            if ((pet.getSleep() == 0 || pet.getFullness() == 0) && !(pet.getSleep() == 0 && pet.getFullness() == 0)) {




                if (pet.getHealth() - 1 <= 0) {
                    pet.setHealth(0);
                } else {
                    pet.setHealth(pet.getHealth() - 1);
                }


            }




            if (pet.getSleep() == 0 && pet.getFullness() == 0) {
                pet.setHealth(Math.max(0, pet.getHealth() - 2));
            }






            pet.setSleep(Math.max(0,pet.getSleep() -1));
            pet.setFullness(Math.max(0,pet.getFullness() -1));
        }
    }




    /**
     * Decreases the Happiness if an hour has passed in game by 1 if sleep or fullness less than or equal to 25.
     * Decreases by two if both.
     * Does the same with Health and the value of 0.
     *
     * @param pet The pet to be acted upon
     * @param lastHour The last hour when health was deducted
     *
     * @return lastHour last hour passed.
     */
    public int updateStatsContinuous(Pet pet, int lastHour){
        LocalDateTime currDateTime = LocalDateTime.now();




        if(currDateTime.getHour() != lastHour){
            if((pet.getSleep() <= 25 || pet.getFullness() <= 25) && !(pet.getSleep() <= 25 && pet.getFullness() <=25)){




                if(pet.getHappiness() - 1 <= 0){
                    pet.setHappiness(0);
                }


                else{
                    pet.setHappiness(pet.getHappiness() - 1);
                }


            }


            if(pet.getSleep() <= 25 && pet.getFullness()  <= 25){


                if(pet.getHappiness() - 2 <= 0){


                    pet.setHappiness(0);
                }


                else{


                    pet.setHappiness(pet.getHappiness() - 2);


                }
            }


            if((pet.getSleep() == 0 || pet.getFullness() == 0) && !(pet.getSleep() == 0 && pet.getFullness() == 0)){




                if(pet.getHealth() - 1 <= 0){
                    pet.setHealth(0);
                }


                else{
                    pet.setHealth(pet.getHealth() - 1);
                }


            }


            if(pet.getSleep() == 0 && pet.getFullness()  == 0){


                if(pet.getHealth() - 2 <= 0){
                    pet.setHealth(0);
                }
                else{


                    pet.setHealth(pet.getHealth() - 2);
                }




            }
            pet.setSleep(Math.max(0,pet.getSleep() -1));
            pet.setFullness(Math.max(0,pet.getFullness() -1));
        }




        return currDateTime.getHour();
    }




    /**
     * Adds 24 hours to the time and updates stats.
     */
    public void updateStatsNextDayDemo(){


        LocalDateTime currDateTime = LocalDateTime.now();
        LocalDateTime nextDateTime = LocalDateTime.now().plusDays(1);
        Duration duration = Duration.between(currDateTime, nextDateTime);
        long timeElapsed = Math.abs(duration.toHours());
        for(int i = (int)timeElapsed; i > 0; i--) {
            if ((this.getCurrentPet().getSleep() <= 25 || this.getCurrentPet().getFullness() <= 25) && !(this.getCurrentPet().getSleep() <= 25 && this.getCurrentPet().getFullness() <= 25)) {




                if (this.getCurrentPet().getHappiness() - 1 <= 0) {
                    this.getCurrentPet().setHappiness(0);
                } else {
                    this.getCurrentPet().setHappiness(this.getCurrentPet().getHappiness() - 1);
                }


            }


            if (this.getCurrentPet().getSleep() <= 25 && this.getCurrentPet().getFullness() <= 25) {


                if (this.getCurrentPet().getHappiness() - 2 <= 0) {


                    this.getCurrentPet().setHappiness(0);
                } else {


                    this.getCurrentPet().setHappiness(this.getCurrentPet().getHappiness() - 2);


                }
            }


            if ((this.getCurrentPet().getSleep() == 0 || this.getCurrentPet().getFullness() == 0) && !(this.getCurrentPet().getSleep() == 0 && this.getCurrentPet().getFullness() == 0)) {




                if (this.getCurrentPet().getHealth() - 1 <= 0) {
                    this.getCurrentPet().setHealth(0);
                } else {
                    this.getCurrentPet().setHealth(this.getCurrentPet().getHealth() - 1);
                }


            }




            if (this.getCurrentPet().getSleep() == 0 && this.getCurrentPet().getFullness() == 0) {
                this.getCurrentPet().setHealth(Math.max(0, this.getCurrentPet().getHealth() - 2));
            }






            this.getCurrentPet().setSleep(Math.max(0,this.getCurrentPet().getSleep() -1));
            this.getCurrentPet().setFullness(Math.max(0,this.getCurrentPet().getFullness() -1));
        }


    }


    /**
     * Provides a string representation of the {@code GameManager} object, including game details, inventory,
     * and statistics tracker.
     *
     * @return A formatted string representation of the {@code GameManager}.
     */
    @Override
    public String toString() {
        return
                "\n" + currentGame +
                        "Inventory: " + currentInventory +
                        "\n" + currentStatisticsTracker +
                        "\n}";


    }


}