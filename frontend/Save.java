package frontend;

import javax.swing.*;

import backend.*;


import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Save extends JPanel {
    private PetsDictionary petsDictionary;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JFrame parentFrame;
    private Image background;

    public Save(CardLayout cardLayout, JPanel mainPanel) {
        this.petsDictionary = new PetsDictionary();
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout()); // Use GridBagLayout for better centering

        // Load background image
        try {
            URL bgUrl = getClass().getResource("resources/save_background.png");
            if (bgUrl == null) {
                throw new RuntimeException("Resource not found: /save_background.jpg");
            }
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Add padding between components
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel title = new JLabel("Save/Load");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, gbc);

        // Save Button
        gbc.gridy++;
        JButton saveGameButton = createButton("Save Game");
        saveGameButton.addActionListener(e -> {
            GameManager.getInstance().saveGame();
            System.out.println("Save Game button clicked");
        });
        add(saveGameButton, gbc);

        // Load Button
        gbc.gridy++;
        JButton loadPreviousStateButton = createButton("Load Previous State");
        loadPreviousStateButton.addActionListener(e -> showPopUp2("resources/pop_up.png", "Pet Name:"));
        add(loadPreviousStateButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu")); // Return to the main menu
        add(backButton, gbc);

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50)); // Set button size
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

  
   
    private void showPopUp2(String imagePath, String placeholder) {
        PopUp popup = new PopUp(parentFrame, imagePath, placeholder, e -> {
            String input = e.getActionCommand();
            Pet pet = petsDictionary.getPetByName(input);
            int type = pet.getPetType();
            GameManager gameManager;


            switch (pet.getPetType()) {
                case 0:
                    gameManager = new GameManager(input, type);
                    GameManager.setInstance(gameManager);
                    break;
                case 1:
                    gameManager = new GameManager(input, type);
                    GameManager.setInstance(gameManager);

                    break;
                case 2:
                    gameManager = new GameManager(input, type);
                    GameManager.setInstance(gameManager);
                    break;
                default:
                    System.err.println("Invalid pet type!");
                    return;
            }
            GameScreen gameScreen = new GameScreen(cardLayout, mainPanel);
            mainPanel.add(gameScreen, "Game");
            cardLayout.show(mainPanel, "Game");
        });
        popup.show();
    }

}
