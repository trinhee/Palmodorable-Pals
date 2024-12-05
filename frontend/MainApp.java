package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;

/**
 * The MainApp class represents the main entry point for the Pomodorable Pals application.
 * It sets up the main application window, initializes different screens, and uses a CardLayout
 * to switch between the panels.
 */
public class MainApp {

    /**
     * The main method serves as the entry point for the application.
     * It creates the main application window and adds different panels using CardLayout.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main application window
            JFrame window = new JFrame("Pomodorable Pals");
            window.setUndecorated(true); // Removes the title bar at the top of the window
            window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes the window in fullscreen
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // CardLayout for managing different panels/screens
            CardLayout cardLayout = new CardLayout();
            JPanel mainPanel = new JPanel(cardLayout);

            // Create and add individual screens
            Loading loadingScreen = new Loading(); // Loading screen with animation

            Menu menuScreen = new Menu(cardLayout, mainPanel);
            ChoosePet choosePetScreen = new ChoosePet(cardLayout, mainPanel, menuScreen, window);
            Tutorial tutorialScreen = new Tutorial(cardLayout, mainPanel);
            Save saveScreen = new Save(cardLayout, mainPanel);
            Parent parentScreen = new Parent(cardLayout, mainPanel, cardLayout, window);
            InventoryScreen inventoryScreen = new InventoryScreen(cardLayout, mainPanel);
            PasswordScreen passwordScreen = new PasswordScreen(cardLayout, mainPanel);

            // Add all screens to the main panel using CardLayout
            mainPanel.add(loadingScreen, "Loading");
            mainPanel.add(menuScreen, "Menu");
            mainPanel.add(passwordScreen, "Password");
            mainPanel.add(choosePetScreen, "ChoosePet");
            mainPanel.add(tutorialScreen, "Tutorial");
            mainPanel.add(saveScreen, "Save");
            mainPanel.add(parentScreen, "Parent");
            mainPanel.add(inventoryScreen, "Inventory");

            // Show the loading screen initially
            cardLayout.show(mainPanel, "Loading");

            // Add the main panel to the window and make it visible
            window.add(mainPanel);
            window.setVisible(true);

            // Timer to check if loading is complete and switch to the Menu screen
            Timer checkloadingComplete = new Timer(100, e -> {
                if (loadingScreen.isLoadingComplete()) {
                    ((Timer) e.getSource()).stop(); // Stop the timer once loading is complete
                    // System.out.println("Loading complete");
                    cardLayout.show(mainPanel, "Menu"); // Show the Menu screen
                }
            });
            checkloadingComplete.start();
        });
    }
}
