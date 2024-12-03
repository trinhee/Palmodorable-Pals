package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class InventoryScreen extends JPanel {
    public InventoryScreen(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout()); // Use GridBagLayout to center components
        this.setBackground(new Color(139, 69, 19)); //set to brown
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
    
        // Row for Food Buttons
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 5, 20); // Add small padding between buttons and labels

        JButton treatButton = createImageButton("resources/sleep.png");
        add(treatButton, gbc);
        
        JButton snackButton = createImageButton("resources/sleep.png");
        add(snackButton, gbc);

        JButton mealButton = createImageButton("resources/sleep.png");
        add(mealButton, gbc);

        gbc.gridy++;

        JLabel treatLabel = createLabel("Treat");
        add(treatLabel, gbc);

        JLabel snackLabel = createLabel("Snack");
        add(snackLabel, gbc);

        JLabel mealLabel = createLabel("Meal");
        add(mealLabel, gbc);

        // Add extra space between Food and Gift buttons by creating an empty row
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 20, 5, 20); // Increase top padding
    
        // Add an empty row to create more space between the food buttons and gift buttons
        JPanel emptyPanel = new JPanel();
        add(emptyPanel, gbc);
    
        // Create and add Gift Buttons for each item in giftNames array
        gbc.gridy = 3; // Start the Gift Buttons row
    
        JButton plushyButton = createImageButton("resources/sleep.png");
        add(plushyButton, gbc);

        JButton ballButton = createImageButton("resources/sleep.png");
        add(ballButton, gbc);

        JButton rattleButton = createImageButton("resources/sleep.png");
        add(rattleButton, gbc);

        gbc.gridy++;

        JLabel plushyLabel = createLabel("Plushy");
        add(plushyLabel, gbc);

        JLabel ballLabel = createLabel("Ball");
        add(ballLabel, gbc);

        JLabel rattleLabel = createLabel("Rattle");
        add(rattleLabel, gbc);
    
        PanelUtils.moveBack(this, "Game", cardLayout, mainPanel);
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

        // Set the button as an image
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