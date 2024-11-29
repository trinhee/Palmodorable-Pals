import javax.swing.*;
import java.awt.*;

public class Save extends JPanel {
    public Save(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Stack elements vertically
        setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel title = new JLabel("Save/Load");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(50)); // Add vertical spacing
        add(title);

        // Buttons
        JButton saveGameButton = createButton("Save Game");
        JButton loadPreviousStateButton = createButton("Load Previous State");
        JButton loadAlternatePetButton = createButton("Load Alternate Pet");

        // Button actions
        saveGameButton.addActionListener(e -> System.out.println("Save Game button clicked"));
        loadPreviousStateButton.addActionListener(e -> System.out.println("Load Previous State button clicked"));
        loadAlternatePetButton.addActionListener(e -> System.out.println("Load Alternate Pet button clicked"));

        add(Box.createVerticalStrut(20)); // Add spacing between title and buttons
        add(saveGameButton);
        add(Box.createVerticalStrut(10)); // Spacing between buttons
        add(loadPreviousStateButton);
        add(Box.createVerticalStrut(10)); // Spacing between buttons
        add(loadAlternatePetButton);

        // Back Button
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu")); // Return to the main menu
        add(Box.createVerticalStrut(20));
        add(backButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40)); // Set button size
        return button;
    }
}
