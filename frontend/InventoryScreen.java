package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class InventoryScreen extends JPanel {
    private GameManager gameManager; // Declare GameManager instance

    public InventoryScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.gameManager = GameManager.getInstance();
        setLayout(new GridBagLayout()); // Use GridBagLayout to center components
        this.setBackground(new Color(139, 69, 19)); // set to brown
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
    
        // Row for Food Buttons
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 5, 20); // Add small padding between buttons and labels

        gbc.gridx++;
        initializeTreatButton(gbc);
        gbc.gridx++;
        initializeSnackButton(gbc);
        gbc.gridx++;
        initializeMealButton(gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;

        initializeFoodLabels(gbc);

        // Add extra space between Food and Gift buttons by creating an empty row
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 20, 5, 20); // Increase top padding
    
        JPanel emptyPanel = new JPanel();
        add(emptyPanel, gbc);
    
        gbc.gridy = 3; 
        gbc.gridx = 0;
    
        initializePlushyButton(gbc);
        gbc.gridx++;
        initializeBallButton(gbc);
        gbc.gridx++;
        initializeBellButton(gbc);
        gbc.gridx++;

        gbc.gridy++;
        gbc.gridx = 0;

        initializeGiftLabels(gbc);
    
        PanelUtils.moveBack(this, "Game", cardLayout, mainPanel);
    }

    private void initializeTreatButton(GridBagConstraints gbc) {
        JButton treatButton = createImageButton("resources/treat.png");
        treatButton.addActionListener(e -> {
            Item treat = new Item("Treat", "Food", 10);
            gameManager.getCurrentPet().useItem(treat);
        });
        add(treatButton, gbc);
    }

    private void initializeSnackButton(GridBagConstraints gbc) {
        JButton snackButton = createImageButton("resources/snack.png");
        snackButton.addActionListener(e -> {
            Item snack = new Item("Snack", "Food", 20);
            gameManager.getCurrentPet().useItem(snack);
        });
        add(snackButton, gbc);
    }

    private void initializeMealButton(GridBagConstraints gbc) {
        JButton mealButton = createImageButton("resources/meal.png");
        mealButton.addActionListener(e -> {
            Item meal = new Item("Meal", "Food", 30);
            gameManager.getCurrentPet().useItem(meal);
        });
        add(mealButton, gbc);
    }

    private void initializePlushyButton(GridBagConstraints gbc) {
        JButton plushyButton = createImageButton("resources/plushy.png");
        plushyButton.addActionListener(e -> {
            Item plushy = new Item("Plushy", "Gift", 15);
            gameManager.getCurrentPet().useItem(plushy);
        });
        add(plushyButton, gbc);
    }

    private void initializeBallButton(GridBagConstraints gbc) {
        JButton ballButton = createImageButton("resources/ball.png");
        ballButton.addActionListener(e -> {
            Item ball = new Item("Ball", "Gift", 20);
            gameManager.getCurrentPet().useItem(ball);
        });
        add(ballButton, gbc);
    }

    private void initializeBellButton(GridBagConstraints gbc) {
        JButton bellButton = createImageButton("resources/bell.png");
        bellButton.addActionListener(e -> {
            Item Bell = new Item("Bell", "Gift", 25);
            gameManager.getCurrentPet().useItem(Bell);
        });
        add(bellButton, gbc);
    }

    private void initializeFoodLabels(GridBagConstraints gbc) {
        JLabel treatLabel = createLabel("Treat");
        add(treatLabel, gbc);
        gbc.gridx++;

        JLabel snackLabel = createLabel("Snack");
        add(snackLabel, gbc);
        gbc.gridx++;

        JLabel mealLabel = createLabel("Meal");
        add(mealLabel, gbc);
        gbc.gridx++;
    }

    private void initializeGiftLabels(GridBagConstraints gbc) {
        JLabel plushyLabel = createLabel("Plushy");
        add(plushyLabel, gbc);
        gbc.gridx++;

        JLabel ballLabel = createLabel("Ball");
        add(ballLabel, gbc);
        gbc.gridx++;

        JLabel BellLabel = createLabel("Bell");
        add(BellLabel, gbc);
        gbc.gridx++;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JButton createImageButton(String imagePath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(200, 200)); // Set button size to make it large
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: " + imagePath);
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return button;
    }
}
