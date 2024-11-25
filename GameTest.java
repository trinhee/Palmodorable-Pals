public class GameTest {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Initialize a game with a specific pet
        GameManager gameManager = new GameManager("Buddy");

        // Display game information
        gameManager.givePet("food");
        gameManager.givePet("gift");

        gameManager.saveGame();
    }
}