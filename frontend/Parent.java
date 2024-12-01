import javax.swing.*;

import java.awt.*;

public class Parent extends JPanel {
    private CardLayout parentCardLayout;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JFrame parentFrame;
    private static Settings globalSettings;

    static {
        globalSettings = new Settings("GlobalGameSettings");
    }

    public static Settings getGlobalSettings() {
        return globalSettings;
    }

    public Parent(CardLayout appCardLayout, JPanel mainPanel, CardLayout cardLayout, JFrame parentFrame) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.parentFrame = parentFrame;
        parentCardLayout = new CardLayout();

        
        setLayout(parentCardLayout); // CardLayout for switching between subpanels

        // Create subpanels
        JPanel parentControlsPanel = createParentControlsPanel(appCardLayout, mainPanel);

        // Add the main panel for parent controls
        add(parentControlsPanel, "ParentControls");

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
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
        JButton setBreakTimeButton = createButton("Set Break Time");
        JButton setStudyTimeButton = createButton("Set Study Time");

        // Slider for music volume
        JLabel volumeLabel = new JLabel("Music Volume:");
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        volumeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSlider volumeSlider = new JSlider(0, 100, 50); // Min 0, Max 100, Default 50
        volumeSlider.setMaximumSize(new Dimension(300, 50));
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            System.out.println("Music Volume set to: " + volume);
            Music.getInstance().setVolume(volume / 100f); // Assuming `setVolume(float)` in Music class
        });

        // Button actions
        revivePetButton.addActionListener(e -> System.out.println("Revive Pet button clicked"));
        viewStatsButton.addActionListener(e -> System.out.println("View Stats button clicked"));
        setBreakTimeButton.addActionListener(e -> showPopUp("/pop_up.png", "Break Time: ", "break", globalSettings));
        setStudyTimeButton.addActionListener(e -> showPopUp("/pop_up.png", "Study Time: ", "study", globalSettings));

        // Add components to the panel
        panel.add(Box.createVerticalStrut(20)); // Add spacing between title and buttons
        panel.add(revivePetButton);
        panel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        panel.add(viewStatsButton);
        panel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        panel.add(setBreakTimeButton);
        panel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        panel.add(setStudyTimeButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(volumeLabel);
        panel.add(volumeSlider);

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

    private void showPopUp(String imagePath, String placeholder, String type, Settings settings) {
        PopUp popup = new PopUp(parentFrame, imagePath, placeholder, e -> {
            String input = e.getActionCommand().trim(); // Trim whitespace
            System.out.println("Raw Input: " + input); // Log raw input for debugging
            
            try {
                // Validate if the input is numeric
                if (!input.matches("\\d+")) { // Check if the input contains only digits
                    throw new NumberFormatException("Input is not a valid positive integer.");
                }
    
                int timeInMinutes = Integer.parseInt(input); // Parse the input as an integer
    
                // Update the appropriate setting based on the type
                switch (type.toLowerCase()) {
                    case "study":
                        settings.setStudyTime(timeInMinutes);
                        System.out.println("Study time set to: " + timeInMinutes + " minutes");
                        break;
                    case "break":
                        settings.setBreakTime(timeInMinutes);
                        System.out.println("Break time set to: " + timeInMinutes + " minutes");
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid type: " + type);
                }
    
                // Save settings to the file
                settings.saveToFile();
            } catch (NumberFormatException ex) {
                // Handle invalid input
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid positive integer for time.", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                // Handle invalid type
                System.err.println("Error: " + ex.getMessage());
            }
        });
        popup.show();
    }
    
    
    

}
