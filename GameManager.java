/**
 * Manages the creation and updating of pets. Also controls logic behind 
 * the starting of the game, the study sessions, as well as the reward system
 * @author Cameron Solomway
 * @version 1.0
 */

/** ArrayList implementation for save list */
import java.util.ArrayList;  

/** For Save List interface */
import java.util.List;   

public class GameManager {
    private Pet currentPet;
    private SaveManager saveManager;

    /* Background Game Mechanics */

    /**
     * This constructor initializes the SaveManager and is responsible for handling 
     * both new game starts and loading of existing saves.
     * @version 1.0
     */
    public GameManager() {
        /** Initialize SaveManager should activate loadAllSaves() to display the possible loads */
        this.saveManager = new SaveManager(); 
        
        //Maybe something like
        
        //selectSlot() //to choose what save file is chosen

        //If User Chooses New Game
        //User input for pet name and type
        
        //Create New Pet
        //this.currentPet = createPet(name, id);
        //startGame();

        //If User Chooses Existing Pet
        //User selects save slot via gui
        //loadGame(slot); //Takes status values from save file and sets currentPet to have data from csv file
        //startGame();
    }

    /** 
    * Creates a new instance of the pet with their max values set to 100, or loads an existing pet from save.
    * This function is used to initialize a new pet based on the pet's name and type (petID), or to load a 
    * previously saved pet from the save system via SaveManager.
    * @version 1.0
    * @param petName The name of the pet entered by the user.
    * @param petID The type of pet (determined by an ID - for example, 0=Cat, 1=Dog, 2=Bird).
    */
    public void createPet(String petName, int petID) {
        Pet pet = null;
        /** Create empty inventory (can change so they start with items too!) */
        List<Item> inventory = new ArrayList<>(); /** Inv will update in SaveManager if existing pet. */

        /** Create new cat with default max values and special traits */
        if (petID == 0) {
            pet = new Pet(petName, petID, 100, 100, 100, 100, 15, 50, inventory);
        }

        /** Create new dog with default max values and special traits */
        if (petID == 1) {
            pet = new Pet(petName, petID, 100, 100, 100, 100, 25, 25), inventory;
        }

        /** Create new bird with default max values and special traits */
        if (petID == 2) {
            pet = new Pet(petName, petID, 100, 100, 100, 100, 50, 15, inventory);
        }
        if (pet != null) {
            this.currentPet = pet;
            System.out.println("Game started with " + pet.getName());
        } 
        else {
            System.out.println("Invalid pet ID.");
        }
    }

    /**
    * Saves the current game status into the selected save slot
    * This method saves the current game state into the save file in the specified slot.
    * @version 1.0
    * @param slot The save slot index where the pet's current state will be saved.
    */
    public void saveGame(int slot) {
        if (currentPet != null) {
            // Save the current pet's state
            saveManager.saveGame(currentPet, slot); 
        }
    }

    /**
     * Loads the pet based on the save slot the user picks.
     * This method loads a pet from a save file and sets the pet as the current pet in the game.
     * @version 1.0
     * @param slot The save slot index selected by the user to load a saved pet.
     *** May change to petName rather than slot***
     */
    public void loadGame(int slot) {
        Pet loadedPet = saveManager.loadSave(slot);
        if (loadedPet != null) {
            this.currentPet = loadedPet;
            System.out.println("Game loaded with " + currentPet.getName);
        }
    }

    /**
     * Updates the pet's stats based on their care (e.g., hunger, happiness).
     * This method will affect pet stats over time, simulating the impact of neglect or care.
     * The pet's hunger decreases, and the pet's happiness and health may decrease 
     * depending on the current hunger level.
     *** PROB WILL NEED TO UPDATE THE LOGIC ***
     * @version 1.0
     */
    public void updateStatus() {
        int hunger = currentPet.getFullness(); 
        int happiness = currentPet.getHappiness();
        currentPet.setFullness(hunger - 2);
        currentPet.setSleep(currentPet.getSleep() - 1);

        if (hunger >= 40 && hunger < 70){
            currentPet.setHappiness(happiness - 1);
        }

        /* If Pet is has less than 40% hunger left the pet begins taking damage and feeling unhappy */
        else if (hunger >= 20 && hunger < 40){
            currentPet.setHealth(currentPet.getHealth() - 1);
            currentPet.setHappiness(happiness - 2);
        }

        /* If Pet is has less than 20% hunger left the pet quickly begins taking damage and feeling unhappy */
        else if (hunger < 20){
            currentPet.setHealth(currentPet.getHealth() - 2); 
            currentPet.setHappiness(happiness - 3);
        }

        /* Pet is starving! */
        else if (hunger == 0){
            currentPet.setHealth(currentPet.getHealth() - 3); /*values can change to inc or dec difficulty*/
            currentPet.setHappiness(happiness - 5);
        }

        System.out.println("Pet status updated.");
    }



    /* Study Session Logic */


    /**
     * Simulates a study session and creates rewards which are added to the pet's inventory.
     * After completing a study session, the pet earns rewards based on how long the session lasts.
     * 
     * @version 1.0
     * @param studyTime The duration of the study session in minutes.
     */
    public void completeStudySession(int studyTime) {
        System.out.println("Study session completed for " + studyTime + " minutes.");

        /** Generate reward item based on study time */
        Item reward = generateReward(studyTime);

        if (reward != null) {
            System.out.println("You earned a reward: " + reward.getName());
            /** Add the reward to the pet's inventory */
            currentPet.addToInventory(reward); /** may need to change name once added */
        } 
        else {
            System.out.println("No rewards this time. Try studying for a little longer!");
        }
    }

    /**
     * Generates an item reward based on the duration of the study session.
     * The reward type and value are determined by how long the user studies.
     * 
     * @version 1.0
     * @param studyTime The duration of the study session in minutes.
     * @return The rewarded item. Returns null if the session is too short (less than 15 minutes).
     *** WILL NEED TO UPDATE THE LOGIC TO WORK WITH STATISTICS ***
     */
    private Item generateReward(int studyTime) {
        /** If user studies 15 - 29 mins */
        if (studyTime >= 15 && studyTime < 30) {
            return new Item("Treat", "food", 15); 
        } 
        /** If user studies 30 - 59 mins */
        else if (studyTime >= 30 && studyTime < 60) {
            return new Item("Ball", "gift", 20);
        } 
        /** If user studies 60+ mins */
        else if (studyTime >= 60) {
            return new Item("Meal", "food", 40);
        }
        /** If User studies less than 15 mins they get nothing */
        return null;
    }
}
