package com.mycompany.junittests;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Settings} class manages the game settings associated with a specific pet.
 * Settings include study time, break time, target study time, background music, and parent mode.
 * The class provides functionality to load settings from and save settings to a CSV file.
 */
public class Settings {
    private static Settings instance;

    private int studyTime; // The study time in minutes
    private int breakTime; // The break time in minutes
    private boolean backgroundMusic; // Indicates if background music is enabled

    private static final String FILE_PATH = "../data_handling/game_settings.csv"; // The settings file path


    public Settings() {
        loadSettings(FILE_PATH);
    }

    public Settings(String filePath) {
        loadSettings(filePath);
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private void loadSettings() {
        loadSettings(this.FILE_PATH);
    }

    private void loadSettings(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the header row
                }

                String[] values = line.split(",");

                this.backgroundMusic = parseBoolean(values[0]);
                this.studyTime = Integer.parseInt(values[1]);
                this.breakTime = Integer.parseInt(values[2]);
                break;
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Saves the current settings for the pet to the CSV file.
     * If the pet's settings exist, they are updated; otherwise, an exception is thrown.
     */

    public void saveToFile() {
        saveToFile(this.FILE_PATH); 
    }
    public void saveToFile(String filePath) {
        List<String> lines = new ArrayList<>();
        boolean isFirstRow = true;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] values = line.split(",", -1);

                if (values[0].length() == 1){
                    
                    line = String.format("%d,%d,%d",
                    this.backgroundMusic ? 1 : 0,
                    this.studyTime,
                    this.breakTime);

                }

                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }

            System.out.println("Settings successfully saved");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    /**
     * Converts a string value to a boolean.
     *
     * @param value The string value to parse (1 = true, 0 = false).
     * @return The parsed boolean value.
     */
    private boolean parseBoolean(String value) {
        return "1".equals(value);
    }

    // Getter and Setter Methods

    /**
     * Sets the study time.
     *
     * @param time The new study time in minutes.
     */
    public void setStudyTime(int time) {
        this.studyTime = time;
    }

    /**
     * Retrieves the study time.
     *
     * @return The study time in minutes.
     */
    public int getStudyTime() {
        return this.studyTime;
    }

    /**
     * Sets the break time.
     *
     * @param time The new break time in minutes.
     */
    public void setBreakTime(int time) {
        this.breakTime = time;
    }

    /**
     * Retrieves the break time.
     *
     * @return The break time in minutes.
     */
    public int getBreakTime() {
        return this.breakTime;
    }

    /**
     * Retrieves whether background music is enabled.
     *
     * @return {@code true} if background music is enabled; {@code false} otherwise.
     */
    public boolean getBackgroundMusic() {
        return this.backgroundMusic;
    }

    /**
     * Sets whether background music is enabled.
     *
     * @param backgroundMusic {@code true} to enable background music; {@code false} to disable it.
     */
    public void setBackgroundMusic(boolean backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    /**
     * Returns a string representation of the settings for the pet.
     *
     * @return A formatted string describing the settings.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Settings for game: \n");
        sb.append("Background Music: ").append(this.backgroundMusic ? "On" : "Off").append("\n");
        sb.append("Study Time: ").append(this.studyTime).append(" minutes\n");
        sb.append("Break Time: ").append(this.breakTime).append(" minutes\n");
        return sb.toString();
    }
}
