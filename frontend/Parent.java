package frontend;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import backend.Settings;

public class Parent extends JPanel {
    private CardLayout parentCardLayout;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JFrame parentFrame;
    private Image background;

    public Parent(CardLayout appCardLayout, JPanel mainPanel, CardLayout cardLayout, JFrame parentFrame) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.parentFrame = parentFrame;
        parentCardLayout = new CardLayout();

        // Load background image
        try {
            URL bgUrl = getClass().getResource("resources/tutorial_background.jpg");
            if (bgUrl == null) {
                throw new RuntimeException("Resource not found: /tutorial_background.jpg");
            }
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(parentCardLayout); // CardLayout for switching between subpanels

        // Create subpanels
        JPanel parentControlsPanel = createParentControlsPanel(appCardLayout, mainPanel);

        // Add the main panel for parent controls
        add(parentControlsPanel, "ParentControls");

        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);
    }

    private JPanel createParentControlsPanel(CardLayout appCardLayout, JPanel mainPanel) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel title = new JLabel("Parent Controls");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title, gbc);

        // Buttons
        JButton revivePetButton = createButton("Revive Pet");
        revivePetButton.addActionListener(e -> System.out.println("Revive Pet button clicked"));

        JButton viewStatsButton = createButton("View Stats");
        viewStatsButton.addActionListener(e -> {
            String stats = "Your Current Stats:\n"
                    + "Study Time: " + Settings.getInstance().getStudyTime() + " minutes\n"
                    + "Break Time: " + Settings.getInstance().getBreakTime() + " minutes\n"
                    + "Other Stats: TBD";

            JOptionPane.showMessageDialog(parentFrame,
                    stats,
                    "View Stats",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JButton setBreakTimeButton = createButton("Set Break Time");
        setBreakTimeButton.addActionListener(e -> showPopUp("resources/pop_up.png", "", "break"));

        JButton setStudyTimeButton = createButton("Set Study Time");
        setStudyTimeButton.addActionListener(e -> showPopUp("resources/pop_up.png", "", "study"));

        JButton updateStatsButton = createButton("Update Stats To Next Day");
        updateStatsButton.addActionListener(e -> {
            System.out.println("Updating stats to the next day...");
            JOptionPane.showMessageDialog(parentFrame,
                    "Stats have been updated to the next day!",
                    "Update Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Add buttons to the panel
        panel.add(revivePetButton, gbc);
        panel.add(viewStatsButton, gbc);
        panel.add(setBreakTimeButton, gbc);
        panel.add(setStudyTimeButton, gbc);
        panel.add(updateStatsButton, gbc);

        // Slider for music volume
        JLabel volumeLabel = new JLabel("Music Volume:");
        volumeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        volumeLabel.setForeground(Color.BLACK);
        volumeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(volumeLabel, gbc);

        JSlider volumeSlider = new JSlider(0, 100, 50); // Min 0, Max 100, Default 50
        volumeSlider.setMaximumSize(new Dimension(300, 50));
        volumeSlider.setPreferredSize(new Dimension(300, 50));
        volumeSlider.setUI(new BasicSliderUI(volumeSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(92, 64, 51)); // dark brown
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(196, 164, 132)); // light brown
                g2d.fillRoundRect(trackRect.x, trackRect.y + trackRect.height / 3, trackRect.width, trackRect.height / 3, 5, 5);
            }
        });
        volumeSlider.setOpaque(false);
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            Music.getInstance().setVolume(volume / 100f); // Assuming `setVolume(float)` in Music class
        });

        panel.add(volumeSlider, gbc);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(200, 200, 200)); // Light gray background to contrast with blue
        button.setForeground(Color.BLACK); // Black text for readability
        button.setPreferredSize(new Dimension(300, 50)); // Set uniform button size
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 180, 180)); // Darker gray on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 200, 200)); // Original color
            }
        });

        return button;
    }

    private void showPopUp(String imagePath, String placeholder, String type) {
        PopUp popup = new PopUp(parentFrame, imagePath, placeholder, e -> {
            String input = e.getActionCommand().trim(); // Trim whitespace
            int timeInMinutes;
            Settings settings = Settings.getInstance();
            try {
                input = input.replace(placeholder, "").trim();

                if (!input.matches("\\d+")) { // Check if the input contains only digits
                    throw new NumberFormatException("Input is not a valid positive integer.");
                }

                timeInMinutes = Integer.parseInt(input);

                if (timeInMinutes <= 0) { // Additional validation for positive values
                    throw new NumberFormatException("Time must be greater than zero.");
                }

                switch (type) {
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

                settings.saveToFile();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parentFrame,
                        "Please enter a valid positive integer for time.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });
        popup.show();
    }
}
