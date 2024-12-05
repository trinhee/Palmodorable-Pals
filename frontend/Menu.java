package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.net.URL;

/**
 * The Menu class represents the main menu screen of the application.
 * It displays different options such as New Game, Load Game, Tutorial, Parent Controls, and Quit.
 * The menu includes animations such as a background fade-in effect and a title scaling effect.
 */
public class Menu extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BufferedImage backgroundImage;
    private ImageIcon titleIcon;
    private JButton[] buttons = new JButton[5]; // Updated to include the "Quit" button
    private Image[] buttonImages = new Image[5]; // Updated for 5 buttons
    private int buttonWidth = 522;
    private int buttonHeight = 208;
    private float fadeOpacity = 0.0f;
    private float gifScale = 0.1f;
    private Timer fadeTimer, titleTimer;
    private boolean buttonsVisible = false;
    private int spacing = 25; // Spacing between buttons

    /**
     * Constructs a new Menu panel that serves as the main menu for the application.
     * This panel allows the user to navigate to different parts of the application.
     *
     * @param cardLayout The CardLayout used to manage different screens.
     * @param mainPanel  The main panel containing all the screens.
     */
    public Menu(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        // Load resources
        loadResources();

        // Set layout to null for manual positioning
        setLayout(null);

        // Initialize buttons
        initializeButtons();

        // Timer to handle background fade-in
        fadeTimer = new Timer(50, e -> {
            fadeOpacity = clamp(fadeOpacity + 0.05f, 0.0f, 1.0f); // Clamp fadeOpacity
            repaint();
            if (fadeOpacity >= 1.0f) {
                fadeTimer.stop();
                startTitleAnimation();
            }
        });
        fadeTimer.start();
    }

    /**
     * Loads the resources required for the menu, such as the background image,
     * the title GIF, and the button images.
     */
    private void loadResources() {
        try {
            // Load background image
            URL bgUrl = getClass().getResource("resources/background.png");
            if (bgUrl == null) throw new RuntimeException("Resource not found: /background.png");
            backgroundImage = ImageIO.read(bgUrl);

            // Load title GIF
            URL titleUrl = getClass().getResource("resources/title.gif");
            if (titleUrl == null) throw new RuntimeException("Resource not found: /title.gif");
            titleIcon = new ImageIcon(titleUrl);

            // Load button images
            String[] buttonPaths = {
                    "resources/new_button.png",
                    "resources/load_button.png",
                    "resources/tutorial_button.png",
                    "resources/parent_button.png",
                    "resources/quit_button.png"
            };
            for (int i = 0; i < buttonPaths.length; i++) {
                URL buttonUrl = getClass().getResource(buttonPaths[i]);
                if (buttonUrl == null) throw new RuntimeException("Resource not found: " + buttonPaths[i]);
                buttonImages[i] = new ImageIcon(buttonUrl).getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            }
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Initializes the menu buttons and adds action listeners to each button
     * to navigate to the appropriate screen or perform an action.
     */
    private void initializeButtons() {
        String[] buttonNames = {"New", "Load", "Tutorial", "Parent", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            int index = i; // Capture index for lambda and listeners
            buttons[i] = new JButton(new ImageIcon(buttonImages[i]));
            buttons[i].setVerticalAlignment(SwingConstants.CENTER);
            buttons[i].setHorizontalAlignment(SwingConstants.CENTER);
            buttons[i].setFocusPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);

            // Add rollover effect
            PanelUtils.mouseRollOver(buttons[i], 0.75f);

            // Add action listeners
            if (buttonNames[index].equals("Quit")) {
                buttons[i].addActionListener(e -> System.exit(0)); // Quit the application
            } else {
                buttons[i].addActionListener(e -> {
                    switch (buttonNames[index]) {
                        case "New" -> cardLayout.show(mainPanel, "ChoosePet");
                        case "Tutorial" -> cardLayout.show(mainPanel, "Tutorial");
                        case "Load" -> cardLayout.show(mainPanel, "Save");
                        case "Parent" -> cardLayout.show(mainPanel, "Password");
                    }
                });
            }
            add(buttons[i]);
        }
    }

    /**
     * Starts the title animation where the title GIF gradually scales up.
     * Once the scaling is complete, the buttons are displayed.
     */
    private void startTitleAnimation() {
        titleTimer = new Timer(40, e -> {
            gifScale = clamp(gifScale + 0.05f, 0.0f, 2.0f); // Scale up to 2x
            repaint();
            if (gifScale >= 2.0f) {
                titleTimer.stop();
                showButtons();
            }
        });
        titleTimer.start();
    }

    /**
     * Displays the menu buttons once the title animation has completed.
     */
    private void showButtons() {
        if (!buttonsVisible) {
            for (JButton button : buttons) {
                button.setVisible(true);
            }
            buttonsVisible = true;
        }
    }

    /**
     * Clamps a given value between a minimum and maximum.
     *
     * @param value The value to clamp.
     * @param min   The minimum allowed value.
     * @param max   The maximum allowed value.
     * @return The clamped value.
     */
    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Paints the components of the menu, including the background, buttons, and title animation.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw dark gray background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw fade-in background
        if (backgroundImage != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeOpacity));
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            g2d.setComposite(originalComposite);
        }

        // Position the buttons in a 2x2 grid with the Quit button below
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int totalButtonWidth = (buttonWidth * 2) + spacing;
        int totalButtonHeight = (buttonHeight * 2) + spacing;
        int startX = (panelWidth - totalButtonWidth) / 2;
        int startY = (panelHeight - totalButtonHeight) / 2;

        // Position the first 4 buttons
        for (int i = 0; i < buttons.length - 1; i++) {
            int row = i / 2;
            int col = i % 2;
            int x = startX + (col * (buttonWidth + spacing));
            int y = startY + (row * (buttonHeight + spacing));
            buttons[i].setBounds(x, y, buttonWidth, buttonHeight);
        }

        // Position the Quit button
        JButton quitButton = buttons[4];
        int quitX = (panelWidth - buttonWidth) / 2;
        int quitY = startY + (buttonHeight * 2) + (spacing * 2); // Below the grid
        quitButton.setBounds(quitX, quitY, buttonWidth, buttonHeight);

        // Draw the title GIF when the background is fully visible
        if (fadeOpacity >= 1.0f && titleIcon != null) {
            int originalWidth = titleIcon.getIconWidth();
            int originalHeight = titleIcon.getIconHeight();
            int scaledWidth = (int) (originalWidth * gifScale);
            int scaledHeight = (int) (originalHeight * gifScale);
            int x = (getWidth() - scaledWidth) / 2;
            int y = 50 + (originalHeight - scaledHeight) / 2;

            g2d.translate(x + scaledWidth / 2, y + scaledHeight / 2);
            g2d.scale(gifScale, gifScale);
            g2d.translate(-originalWidth / 2, -originalHeight / 2);
            titleIcon.paintIcon(this, g2d, 0, 0);
        }

        g2d.dispose();
    }
}
