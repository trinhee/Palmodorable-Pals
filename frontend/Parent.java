import javax.swing.*;
import java.awt.*;

public class Parent extends JPanel {
    private CardLayout parentCardLayout;

    public Parent(CardLayout appCardLayout, JPanel mainPanel) {
        parentCardLayout = new CardLayout();
        setLayout(parentCardLayout); // CardLayout for switching between subpanels

        // Create subpanels
        JPanel parentControlsPanel = createParentControlsPanel(appCardLayout, mainPanel);
        JPanel setPasswordPanel = createSetPasswordPanel();

        // Add subpanels to the Parent CardLayout
        add(parentControlsPanel, "ParentControls");
        add(setPasswordPanel, "SetPassword");
    }

    private JPanel createParentControlsPanel(CardLayout appCardLayout, JPanel mainPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel title = new JLabel("Parent Controls");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(50)); // Add vertical spacing
        panel.add(title);

        // Buttons
        JButton revivePetButton = createButton("Revive Pet");
        JButton viewStatsButton = createButton("View Stats");
        JButton setTimeLimitButton = createButton("Set Time Limit");
        JButton setPasswordButton = createButton("Set Parental Password");

        // Button actions
        revivePetButton.addActionListener(e -> System.out.println("Revive Pet button clicked"));
        viewStatsButton.addActionListener(e -> System.out.println("View Stats button clicked"));
        setTimeLimitButton.addActionListener(e -> System.out.println("Set Time Limit button clicked"));
        setPasswordButton.addActionListener(e -> {
            System.out.println("Navigating to Set Password panel");
            parentCardLayout.show(this, "SetPassword"); // Switch to Set Password panel
        });

        panel.add(Box.createVerticalStrut(20)); // Add spacing between title and buttons
        panel.add(revivePetButton);
        panel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        panel.add(viewStatsButton);
        panel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        panel.add(setTimeLimitButton);
        panel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        panel.add(setPasswordButton);

        // Back Button
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> appCardLayout.show(mainPanel, "Menu")); // Return to the main menu
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);

        return panel;
    }

    private JPanel createSetPasswordPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel title = new JLabel("Set Parent Password");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(50)); // Add vertical spacing
        panel.add(title);

        // Text Field for password input
        JTextField passwordField = new JTextField(20);
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordField);

        // Add Enter key functionality
        passwordField.addActionListener(e -> {
            String enteredText = passwordField.getText();
            System.out.println("Password entered: " + enteredText);
            passwordField.setText(""); // Clear the text field
        });

        // Back Button
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> parentCardLayout.show(this, "ParentControls")); // Return to Parent Controls panel
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);

        return panel;
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
