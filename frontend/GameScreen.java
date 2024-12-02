package frontend;

import backend.*;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import javax.swing.event.AncestorEvent;

public class GameScreen extends JPanel {
    private BufferedImage background;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Timer fadeTimer;
    private float fadeOpacity;
    private JButton startButton;
    private JLabel countdownLabel;
    private Timer countdownTimer;
    private BufferedImage[] petFrames;
    private Timer animationTimer;
    private int currentFrame = 0; // Added currentFrame for animation
    private int petType;
    private Settings settings;
    private JButton sleepButton;
    private JButton vetButton;
    private JButton exerciseButton;
    private JButton inventoryButton;
    private JProgressBar healthBar;
    private JProgressBar sleepBar;
    private JProgressBar hungerBar;
    private JProgressBar happinessBar;

    public GameScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.settings = Settings.getInstance();
        this.petType = GameManager.getInstance().getCurrentPet().getPetType();

        // Load background image
        try {
            URL bgUrl = getClass().getResource("resources/game_background.png");
            if (bgUrl == null) {
                throw new RuntimeException("Resource not found: /game_background.png");
            }
            background = ImageIO.read(bgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Set layout
        setLayout(null);

        // Enable escape key to return to the menu
        PanelUtils.moveBack(this, "Menu", cardLayout, mainPanel);

        initializeStartButton();
        initializeSleepButton();
        initializeInventoryButton();
        initializeVetButton();
        initializeExerciseButton();
        initializePetSprite(petType);
        initializeStatusBars();

        // Detect when the panel is shown
        addAncestorListener(new AncestorListenerAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                startFadeIn(); // Trigger fade-in when the panel is shown
            }
        });
    }

    private void initializeStartButton() {
        startButton = new JButton();
        startButton.setFocusPainted(false);

        try {
            URL startButtonUrl = getClass().getResource("resources/start_button.png");
            if (startButtonUrl == null) {
                throw new RuntimeException("Resource not found: /start_button.png");
            }
            BufferedImage image = ImageIO.read(startButtonUrl);
            Image scaledImage = image.getScaledInstance(200,200, Image.SCALE_SMOOTH);
            startButton.setIcon(new ImageIcon(scaledImage));;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default position and size
        startButton.setBounds(755, 492, 300, 100); // on the screen
        startButton.addActionListener(e -> {
            System.out.println("Start button clicked!");
            Music.getInstance().stop();
            Music.getInstance().play("resources/lofi.wav");

            startButton.setVisible(false); // Make the button invisible

            int studyTime = settings.getStudyTime();
            int hours = studyTime / 60;
            int minutes = studyTime % 60;

            initializeCountdown(hours, minutes);
            revalidate();
            repaint();
        });

        add(startButton);
    }

    private void initializeCountdown(int hours, int minutes) {
        countdownLabel = new JLabel(formatTime(hours, minutes, 0), SwingConstants.CENTER);
        countdownLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        countdownLabel.setForeground(Color.RED);
        countdownLabel.setBounds(755, 492, 300, 100); // on the screen

        add(countdownLabel);

        // Countdown logic
        int[] remainingTime = {hours * 3600 + minutes * 60}; // Convert to total seconds
        countdownTimer = new Timer(1000, e -> {
            remainingTime[0]--;
            if (remainingTime[0] > 0) {
                int h = remainingTime[0] / 3600;
                int m = (remainingTime[0] % 3600) / 60;
                int s = remainingTime[0] % 60;
                countdownLabel.setText(formatTime(h, m, s));
            } else {
                countdownTimer.stop();
                remove(countdownLabel);
                revalidate();
                repaint();
                System.out.println("Countdown finished!");
                // Trigger game logic here
            }
        });
        countdownTimer.start();
    }

    private String formatTime(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void startFadeIn() {
        fadeOpacity = 0.0f; // Reset fade opacity
        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }

        // Timer to handle background fade-in
        fadeTimer = new Timer(30, e -> { // Slower fade with smaller increments
            fadeOpacity = Math.min(fadeOpacity + 0.007f, 1.0f); // Gradually increase opacity
            repaint();

            if (fadeOpacity >= 1.0f) { // Stop timer once fade-in is complete
                fadeTimer.stop();
            }
        });
        fadeTimer.start();
    }

    private void initializePetSprite(int petType) {
        String resourcePath;
        int frameWidth, frameHeight;

        // Determine the resource path and dimensions based on pet type
        switch (petType) {
            case 0:
                resourcePath = "resources/dog_idle.png";
                frameWidth = 48;
                frameHeight = 48;
                break;
            case 1:
                resourcePath = "resources/cat_idle.png";
                frameWidth = 48;
                frameHeight = 48;
                break;
            case 2:
                resourcePath = "resources/bird_idle.png";
                frameWidth = 32;
                frameHeight = 32;
                break;
            default:
                throw new IllegalArgumentException("Invalid pet type: " + petType);
        }

        // Load the sprite sheet
        petFrames = loadSpriteSheet(resourcePath, frameWidth, frameHeight);

        // Start animation timer
        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % petFrames.length;
            repaint();
        });
        animationTimer.start();
    }

    private BufferedImage[] loadSpriteSheet(String resourcePath, int frameWidth, int frameHeight) {
        try {
            URL spriteSheetURL = getClass().getResource(resourcePath);
            if (spriteSheetURL == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            BufferedImage spriteSheet = ImageIO.read(spriteSheetURL);

            int frameCount = spriteSheet.getWidth() / frameWidth;

            if (spriteSheet.getHeight() < frameHeight) {
                throw new RuntimeException("Sprite sheet height is smaller than expected: " + resourcePath);
            }

            BufferedImage[] frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
            return frames;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private void initializeStatusBars() {
        // Health Bar
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100); // Example value
        healthBar.setForeground(Color.RED);
        healthBar.setBounds(20, 850, 150, 20);
        add(healthBar);

        // Sleep Bar
        sleepBar = new JProgressBar(0, 100);
        sleepBar.setValue(100); // Example value
        sleepBar.setForeground(Color.BLUE);
        sleepBar.setBounds(20, 880, 150, 20);
        add(sleepBar);

        // Hunger Bar
        hungerBar = new JProgressBar(0, 100);
        hungerBar.setValue(100); // Example value
        hungerBar.setForeground(Color.ORANGE);
        hungerBar.setBounds(20, 910, 150, 20);
        add(hungerBar);

        // Happiness Bar
        happinessBar = new JProgressBar(0, 100);
        happinessBar.setValue(100); // Example value
        happinessBar.setForeground(Color.GREEN);
        happinessBar.setBounds(20, 940, 150, 20);
        add(happinessBar);
    }

    private void initializeInventoryButton() {
        inventoryButton = new JButton();
        inventoryButton.setFocusPainted(false);
        inventoryButton.setBorderPainted(false);
        inventoryButton.setContentAreaFilled(false);

        // Set the button as an image
        try {
            URL imageUrl = getClass().getResource("resources/inventory.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /inventory.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            inventoryButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default position and size
        inventoryButton.setBounds(1500, 100, 200, 200); // on the screen
        inventoryButton.addActionListener(e -> {
            System.out.println("Inventory button clicked!");
            cardLayout.show(mainPanel, "Inventory");

            revalidate();
            repaint();
        });
        add(inventoryButton);
    }

    private void initializeSleepButton() {
        sleepButton = new JButton();
        sleepButton.setFocusPainted(false);
        sleepButton.setBorderPainted(false);
        sleepButton.setContentAreaFilled(false);

        // Set the button as an image
        try {
            URL imageUrl = getClass().getResource("resources/sleep.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /sleep.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            sleepButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default position and size
        sleepButton.setBounds(1100, 100, 200, 200); // on the screen
        sleepButton.addActionListener(e -> {
            System.out.println("Sleep button clicked!");

            revalidate();
            repaint();
        });
        add(sleepButton);
    }

    private void initializeVetButton() {
        vetButton = new JButton();
        vetButton.setFocusPainted(false);
        vetButton.setBorderPainted(false);
        vetButton.setContentAreaFilled(false);

        // Set the button as an image
        try {
            URL imageUrl = getClass().getResource("resources/vet.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /vet.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            vetButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default position and size
        vetButton.setBounds(700, 100, 200, 200); // on the screen
        vetButton.addActionListener(e -> {
            System.out.println("Vet button clicked!");

            revalidate();
            repaint();
        });
        add(vetButton);
    }


    private void initializeExerciseButton() {
        exerciseButton = new JButton();
        exerciseButton.setFocusPainted(false);
        exerciseButton.setBorderPainted(false);
        exerciseButton.setContentAreaFilled(false);

        // Set the button as an image
        try {
            URL imageUrl = getClass().getResource("resources/exercise.png");
            if (imageUrl == null) {
                throw new RuntimeException("Resource not found: /exercise.png");
            }
            BufferedImage image = ImageIO.read(imageUrl);
            Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            exerciseButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default position and size
        exerciseButton.setBounds(300, 100, 200, 200); // on the screen
        exerciseButton.addActionListener(e -> {
            System.out.println("Exercise button clicked!");

            revalidate();
            repaint();
        });
        add(exerciseButton);
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw black background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the background image with fade effect
        if (background != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeOpacity));
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g2d.setComposite(originalComposite);
        }

        // Draw the animated pet sprite
        if (petFrames != null && petFrames.length > 0) {
            int x, y, scaledWidth, scaledHeight;
            switch (petType) {
                case 0: // Dog
                    x = 720;
                    y = 635;
                    scaledWidth = 384;
                    scaledHeight = 384;
                    break;
                case 1: // Cat
                    x = 720;
                    y = 635;
                    scaledWidth = 384;
                    scaledHeight = 384;
                    break;
                case 2: // Bird
                    x = 750;
                    y = 720;
                    scaledWidth = 256;
                    scaledHeight = 256;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid pet type: " + petType);
            }
            g2d.drawImage(petFrames[currentFrame], x, y, scaledWidth, scaledHeight, this);
        }

        g2d.dispose();
    }
}
