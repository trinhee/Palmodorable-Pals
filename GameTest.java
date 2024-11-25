public class GameTest {
    public static void main(String[] args) {
        // Initialize a game with a specific pet
        Game game = new Game("Buddy");

        // Display game information
        if (game.getPet() != null) {
            System.err.println(game);
        }
    }
}