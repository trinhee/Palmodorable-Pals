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

    private int studyTime; // The study time in minutes
    private int breakTime; // The break time in minutes
    private int targetStudyTime; // The target number of study sessions
    private boolean backgroundMusic; // Indicates if background music is enabled
    private boolean isParent; // Indicates if the player is in parent mode
    private String petName; // The name of the associated pet

    private static final String FILE_PATH = "data_handling/game_settings.csv"; // The settings file path

    /**
     * Constructs a {@code Settings} object for a specific pet and loads its settings from the CSV file.
     *
     * @param petName The name of the pet whose settings will be loaded.
     */
    public Settings(String petName) {
        loadSettings(petName, FILE_PATH);
    }

    public Settings(String petName, String filePath) {
        loadSettings(petName, filePath);
    }


    /**
     * Loads the settings for the specified pet from the CSV file.
     *
     * @param petName The name of the pet whose settings will be loaded.
     */
    private void loadSettings(String petName) {
        loadSettings(petName, this.FILE_PATH);
    }

    private void loadSettings(String petName, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the header row
                }

                String[] values = line.split(",");

                if (values[0].equalsIgnoreCase(petName)) {
                    this.petName = values[0];
                    this.backgroundMusic = parseBoolean(values[1]);
                    this.isParent = parseBoolean(values[2]);
                    this.studyTime = Integer.parseInt(values[3]);
                    this.breakTime = Integer.parseInt(values[4]);
                    this.targetStudyTime = Integer.parseInt(values[5]);
                    break;
                }
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
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);

                if (values[0].equalsIgnoreCase(this.petName)) {
                    line = String.format("%s,%d,%d,%d,%d,%d",
                            this.petName,
                            this.backgroundMusic ? 1 : 0,
                            this.isParent ? 1 : 0,
                            this.studyTime,
                            this.breakTime,
                            this.targetStudyTime);
                    updated = true;
                }

                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        if (!updated) {
            throw new IllegalArgumentException("Pet not found in the file: " + this.petName);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }

            System.out.println("Settings successfully saved for pet: " + this.petName);
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
     * Sets the target study time.
     *
     * @param time The new target number of study sessions.
     */
    public void setTargetStudyTime(int time) {
        this.targetStudyTime = time;
    }

    /**
     * Retrieves the target study time.
     *
     * @return The target number of study sessions.
     */
    public int getTargetStudyTime() {
        return this.targetStudyTime;
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
     * Checks if the player is in parent mode.
     *
     * @return {@code true} if the player is in parent mode; {@code false} otherwise.
     */
    public boolean isParent() {
        return this.isParent;
    }

    /**
     * Retrieves the name of the associated pet.
     *
     * @return The pet's name.
     */
    public String getPetName() {
        return this.petName;
    }

    /**
     * Returns a string representation of the settings for the pet.
     *
     * @return A formatted string describing the settings.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Settings for Pet: ").append(this.petName).append("\n");
        sb.append("Background Music: ").append(this.backgroundMusic ? "On" : "Off").append("\n");
        sb.append("Is Parent: ").append(this.isParent ? "Yes" : "No").append("\n");
        sb.append("Study Time: ").append(this.studyTime).append(" minutes\n");
        sb.append("Break Time: ").append(this.breakTime).append(" minutes\n");
        sb.append("Target Study Time: ").append(this.targetStudyTime).append(" sessions\n");
        return sb.toString();
    }
}
