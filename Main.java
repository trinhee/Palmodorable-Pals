public class Main {
    public static void main(String[] args) {
        // Create an instance of GameManager
        GameManager gameManager = new GameManager();
        // Create an instance of StatisticsTracker
        StatisticsTracker statisticsTracker = new StatisticsTracker();
        // Create an instance of UserInterface
        UserInterface userInterface = new UserInterface();

        // Load the game
        gameManager.loadGame("pet1");
        // Update the pet state
        gameManager.updatePetState();
        // Track the session time
        statisticsTracker.trackSessionTime();
        // Display the screen
        userInterface.displayScreen("MainScreen");
        // Update the pet display
        userInterface.updatePetDisplay();
        // Save the game
        gameManager.saveGame();
        // Reset the statistics
        statisticsTracker.resetStatistics();
    }
}
