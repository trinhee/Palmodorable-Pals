package frontend;

import javax.swing.*;
import java.awt.*;

public class InventoryScreen extends JPanel {
    public InventoryScreen(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout()); // Use GridBagLayout to center components
        setBackground(Color.WHITE); // Set background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Add padding between components
        gbc.anchor = GridBagConstraints.CENTER;

        // Create Food Button
        JButton foodButton = createButton("Food");
        add(foodButton, gbc);

        // Label for Food Button
        gbc.gridy++;
        JLabel foodLabel = createLabel("Food");
        add(foodLabel, gbc);

        // Create Gift Button
        gbc.gridy++;
        JButton giftButton = createButton("Gift");
        add(giftButton, gbc);

        // Label for Gift Button
        gbc.gridy++;
        JLabel giftLabel = createLabel("Gift");
        add(giftLabel, gbc);

        PanelUtils.moveBack(this, "Game", cardLayout, mainPanel);

    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(200, 100)); // Set button size to make it large
        button.setFocusPainted(false);
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

}
