package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.net.URL;

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
            String[] buttonPaths = {"resources/new_button.png", "resources/load_button.png", "resources/tutorial_button.png", "resources/parent_button.png", "resources/quit_button.png"};
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

    private void initializeButtons() {
        String[] buttonNames = {"New", "Load", "Tutorial", "Parent", "Quit"};
        for (int i = 0; i < buttons.length; i++) {
            int index = i; // Capture index for lambda and listeners
            buttons[i] = new JButton(new ImageIcon(buttonImages[i]));
            buttons[i].setFocusPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);

            // Add mouse listener for hover effect
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Reduce opacity by 25% when hovered
                    buttons[index].setIcon(new ImageIcon(createTransparentImage(buttonImages[index], 0.75f)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Restore full opacity when mouse exits
                    buttons[index].setIcon(new ImageIcon(buttonImages[index]));
                }
            });

            // Add action listeners
            if (buttonNames[index].equals("Quit")) {
                buttons[i].addActionListener(e -> System.exit(0)); // Quit the application
            } else {
                buttons[i].addActionListener(e -> {
                    if (buttonNames[index].equals("New")) {
                        cardLayout.show(mainPanel, "ChoosePet");
                    } else if (buttonNames[index].equals("Tutorial")) {
                        cardLayout.show(mainPanel, "Tutorial");
                    } else if (buttonNames[index].equals("Load")) {
                        cardLayout.show(mainPanel, "Save");
                    } else if (buttonNames[index].equals("Parent")) {
                        cardLayout.show(mainPanel, "Password");
                    }
                    // System.out.println(buttonNames[index] + " button clicked");
                });
            }
            add(buttons[i]);
        }
    }

    public Image createTransparentImage(Image originalImage, float opacity) {
        int width = originalImage.getWidth(null);
        int height = originalImage.getHeight(null);

        BufferedImage transparentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = transparentImage.createGraphics();

        // Set the opacity
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return transparentImage;
    }

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

    private void showButtons() {
        if (!buttonsVisible) {
            for (JButton button : buttons) {
                button.setVisible(true);
            }
            buttonsVisible = true;
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Dark gray background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Fade-in background
        if (backgroundImage != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeOpacity));
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            g2d.setComposite(originalComposite);
        }

        // Center buttons in a 2x2 grid with Quit button below
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

        // Draw title GIF when the background is fully visible
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
