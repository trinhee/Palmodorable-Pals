package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import javax.swing.event.AncestorEvent;

/**
 * The GameScreen class represents the main screen where the game is played.
 * It contains various controls and animations related to the pet's activities and health.
 */
public class GameScreen extends JPanel {
    private BufferedImage background;
    private GameManager gameManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Timer fadeTimer;
    private float fadeOpacity;
    private JButton startButton;
    private JLabel countdownLabel;
    private Timer countdownTimer;
    private BufferedImage[] petFrames;
    private Timer animationTimer;
    private int currentFrame = 0;
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
    private JLabel scoreLabel;

    /**
     * Constructs the GameScreen panel, initializing game elements and UI components.
     *
     * @param cardLayout The CardLayout used for navigating between different screens.
     * @param mainPanel  The main panel containing all the different screens.
     */
    public GameScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.settings = Settings.getInstance();
        this.petType = GameManager.getInstance().getCurrentPet().getPetType();
        this.gameManager = GameManager.getInstance();

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

        initializeScoreLabel(GameScreen.intScoreToString(GameManager.getInstance().getCurrentPet().getPetScore()));

        initializeStatusBars();

        // Update positions when the panel is resized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updatePositions();
            }
        });

        // Detect when the panel is shown
        addAncestorListener(new AncestorListenerAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                startFadeIn();
            }
        });
    }

    /**
     * Initializes the start button and its properties.
     */
    private void initializeStartButton() {
        startButton = new JButton();
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(468, 179));
        try {
            URL startButtonUrl = getClass().getResource("resources/start_button.png");
            if (startButtonUrl == null) {
                throw new RuntimeException("Resource not found: /start_button.png");
            }
            BufferedImage image = ImageIO.read(startButtonUrl);
            Image scaledImage = image.getScaledInstance(468, 179, Image.SCALE_SMOOTH);
            startButton.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startButton.addActionListener(e -> {
            Music.getInstance().stop();
            Music.getInstance().play("resources/lofi.wav");
            Music.getInstance().setVolume(50 / 100f);

            startButton.setVisible(false);

            int studyTime = settings.getStudyTime();
            int hours = studyTime / 60;
            int minutes = studyTime % 60;

            initializeCountdown(hours, minutes);
            revalidate();
            repaint();
        });

        PanelUtils.mouseRollOver(startButton, 0.85f);

        add(startButton);
    }

    /**
     * Initializes a countdown timer for the game.
     *
     * @param hours   The number of hours for the countdown.
     * @param minutes The number of minutes for the countdown.
     */
    private void initializeCountdown(int hours, int minutes) {
        countdownLabel = new JLabel(formatTime(hours, minutes, 0), SwingConstants.CENTER);
        countdownLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        countdownLabel.setForeground(Color.RED);
        countdownLabel.setBounds(755, 492, 300, 100);

        add(countdownLabel);

        int[] remainingTime = {hours * 3600 + minutes * 60};
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
            }
        });
        countdownTimer.start();
    }

    /**
     * Formats the given time into a string.
     *
     * @param hours   The number of hours.
     * @param minutes The number of minutes.
     * @param seconds The number of seconds.
     * @return A formatted string representing the time.
     */
    private String formatTime(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Starts the fade-in animation for the game screen.
     */
    private void startFadeIn() {
        fadeOpacity = 0.0f;
        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }

        fadeTimer = new Timer(30, e -> {
            fadeOpacity = Math.min(fadeOpacity + 0.007f, 1.0f);
            repaint();

            if (fadeOpacity >= 1.0f) {
                fadeTimer.stop();
            }
        });
        fadeTimer.start();
    }

    /**
     * Initializes the pet sprite for animation.
     *
     * @param petType The type of pet to initialize.
     */
    private void initializePetSprite(int petType) {
        String resourcePath;
        int frameWidth, frameHeight;

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

        petFrames = loadSpriteSheet(resourcePath, frameWidth, frameHeight);

        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % petFrames.length;
            repaint();
        });
        animationTimer.start();
    }

    /**
     * Loads the sprite sheet for pet animation.
     *
     * @param resourcePath The path to the sprite sheet resource.
     * @param frameWidth   The width of each frame in the sprite sheet.
     * @param frameHeight  The height of each frame in the sprite sheet.
     * @return An array of BufferedImage frames.
     */
    private BufferedImage[] loadSpriteSheet(String resourcePath, int frameWidth, int frameHeight) {
        try {
            URL spriteSheetURL = getClass().getResource(resourcePath);
            if (spriteSheetURL == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            BufferedImage spriteSheet = ImageIO.read(spriteSheetURL);

            int frameCount = spriteSheet.getWidth() / frameWidth;

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

    /**
     * Initializes the status bars for pet health, sleep, hunger, and happiness.
     */
    private void initializeStatusBars() {
        healthBar = new JProgressBar(0, 100);
        healthBar.setForeground(Color.RED);
        add(healthBar);

        sleepBar = new JProgressBar(0, 100);
        sleepBar.setForeground(Color.BLUE);
        add(sleepBar);

        hungerBar = new JProgressBar(0, 100);
        hungerBar.setForeground(Color.ORANGE);
        add(hungerBar);

        happinessBar = new JProgressBar(0, 100);
        happinessBar.setForeground(Color.GREEN);
        add(happinessBar);

        updateStatusBars();
    }

    /**
     * Updates the values of the status bars based on the pet's current status.
     */
    private void updateStatusBars() {
        healthBar.setValue(gameManager.getCurrentPet().getHealth());
        sleepBar.setValue(gameManager.getCurrentPet().getSleep());
        hungerBar.setValue(gameManager.getCurrentPet().getFullness());
        happinessBar.setValue(gameManager.getCurrentPet().getHappiness());
        String score = GameScreen.intScoreToString(GameManager.getInstance().getCurrentPet().getPetScore()); 
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Initializes the inventory button and its properties.
     */
    private void initializeInventoryButton() {
        inventoryButton = new JButton();
        inventoryButton.setFocusPainted(false);
        inventoryButton.setBorderPainted(false);
        inventoryButton.setContentAreaFilled(false);

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

        PanelUtils.mouseRollOver(inventoryButton, 0.75f);

        inventoryButton.addActionListener(e -> {
            System.out.println("Inventory button clicked!");
            cardLayout.show(mainPanel, "Inventory");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(inventoryButton);
    }

    /**
     * Initializes the sleep button and its properties.
     */
    private void initializeSleepButton() {
        sleepButton = new JButton();
        sleepButton.setFocusPainted(false);
        sleepButton.setBorderPainted(false);
        sleepButton.setContentAreaFilled(false);

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

        PanelUtils.mouseRollOver(sleepButton, 0.75f);

        sleepButton.addActionListener(e -> {
            gameManager.sleepPet();
            System.out.println("Sleep button clicked!");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(sleepButton);
    }

    /**
     * Initializes the vet button and its properties.
     */
    private void initializeVetButton() {
        vetButton = new JButton();
        vetButton.setFocusPainted(false);
        vetButton.setBorderPainted(false);
        vetButton.setContentAreaFilled(false);

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

        PanelUtils.mouseRollOver(vetButton, 0.75f);

        vetButton.addActionListener(e -> {
            gameManager.visitVet();
            System.out.println("Vet button clicked!");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(vetButton);
    }

    /**
     * Initializes the exercise button and its properties.
     */
    private void initializeExerciseButton() {
        exerciseButton = new JButton();
        exerciseButton.setFocusPainted(false);
        exerciseButton.setBorderPainted(false);
        exerciseButton.setContentAreaFilled(false);

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

        PanelUtils.mouseRollOver(exerciseButton, 0.75f);

        exerciseButton.addActionListener(e -> {
            gameManager.exercisePet();
            System.out.println("Exercise button clicked!");
            updateStatusBars();
            revalidate();
            repaint();
        });
        add(exerciseButton);
    }

    private static String intScoreToString(int score) {
        if (score < 10) {
            return "F";
        } else if (score < 20) {
            return "D";
        } else if (score < 30) {
            return "C";
        } else if (score < 40) {
            return "B";
        } else if (score < 50) {
            return "A";
        } else {
            return "A+";
        }
    }

    /**
     * Initializes the score label.
     *
     * @param score The score value to be displayed.
     */
    private void initializeScoreLabel(String score) {
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Dimension size = getSize();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (background != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeOpacity));
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            g2d.setComposite(originalComposite);
        }

        if (petFrames != null && petFrames.length > 0) {
            int x, y, scaledWidth, scaledHeight;
            switch (petType) {
                case 0:
                    x = (int) (0.48 * size.width - 384 / 2.0);
                    y = (int) (0.88 * size.height - 635 / 2.0);
                    scaledWidth = 384;
                    scaledHeight = 384;
                    break;
                case 1:
                    x = (int) (0.48 * size.width - 384 / 2.0);
                    y = (int) (0.88 * size.height - 635 / 2.0);
                    scaledWidth = 384;
                    scaledHeight = 384;
                    break;
                case 2:
                    x = (int) (0.48 * size.width - 256 / 2.0);
                    y = (int) (0.82 * size.height - 256 / 2.0);
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

    /**
     * Updates the positions of all components in the panel based on the current size.
     */
    private void updatePositions() {
        Dimension size = getSize();

        position(startButton, 0.47, 0.5, size, 450, 150);
        position(sleepButton, 0.2, 0.15, size, 200, 200);
        position(vetButton, 0.4, 0.15, size, 200, 200);
        position(exerciseButton, 0.6, 0.15, size, 200, 200);
        position(inventoryButton, 0.8, 0.15, size, 200, 200);
        position(healthBar, 0.05, 0.87, size, 150, 20);
        position(sleepBar, 0.05, 0.90, size, 150, 20);
        position(hungerBar, 0.05, 0.93, size, 150, 20);
        position(happinessBar, 0.05, 0.96, size, 150, 20);
        position(scoreLabel, 0.065, 0.83, size, 200, 200);
    }

    /**
     * Positions a component based on relative x and y values, as well as the component's width and height.
     *
     * @param component The component to position.
     * @param relativeX The relative x position (0.0 to 1.0) of the component.
     * @param relativeY The relative y position (0.0 to 1.0) of the component.
     * @param size      The size of the container in which the component is placed.
     * @param width     The width of the component.
     * @param height    The height of the component.
     */
    private void position(JComponent component, double relativeX, double relativeY, Dimension size, int width, int height) {
        int x = (int) (relativeX * size.width - width / 2.0);
        int y = (int) (relativeY * size.height - height / 2.0);
        component.setBounds(x, y, width, height);
    }
}
