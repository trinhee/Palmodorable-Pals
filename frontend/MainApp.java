package frontend;

import backend.GameManager;
import backend.Pet;
import backend.Settings;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        JFrame window = new JFrame("Pomodorable Pals");
        window.setUndecorated(true); // removes bar at top of window
        window.setExtendedState(JFrame.MAXIMIZED_BOTH); // windowed fullscreen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // CardLayout for different panels
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Loading screen wit da cat
        Loading loadingScreen = new Loading();


        // Add panels to cardLayout
        Menu menuScreen = new Menu(cardLayout, mainPanel);
        ChoosePet choosePetScreen = new ChoosePet(cardLayout, mainPanel, menuScreen, window);
        Tutorial tutorialScreen = new Tutorial(cardLayout, mainPanel);
        Save saveScreen = new Save(cardLayout, mainPanel);
        Parent parentScreen = new Parent(cardLayout, mainPanel, cardLayout, window);
        GameScreen gameScreen = new GameScreen(cardLayout, mainPanel);

        mainPanel.add(loadingScreen, "Loading"); // add loading panel to main panel
        mainPanel.add(menuScreen, "Menu");
        mainPanel.add(choosePetScreen, "ChoosePet");
        mainPanel.add(tutorialScreen, "Tutorial");
        mainPanel.add(saveScreen, "Save");
        mainPanel.add(parentScreen, "Parent");
        mainPanel.add(gameScreen, "Game");


        cardLayout.show(mainPanel, "Loading");

        window.add(mainPanel);
        window.setVisible(true);

        Timer checkloadingComplete = new Timer(100, e -> {
            if (loadingScreen.isLoadingComplete()) {
                ((Timer) e.getSource()).stop();
                // cardLayout.show(mainPanel, "Menu");
                System.out.println("Loading complete");
                cardLayout.show(mainPanel, "Menu");
            }

        });
        checkloadingComplete.start();


    }
}
