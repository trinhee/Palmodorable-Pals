package backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code StatisticsTracker} class is responsible for managing study statistics
 * for a specific entity (e.g., a pet). It handles loading and saving data to a CSV file,
 * and tracks details such as the last study session, daily start and end times, and total study time.
 */
public class StatisticsTracker {
    private static StatisticsTracker instance; // Singleton instance
    private String name; // The name of the entity (e.g., pet or user)
    private String lastStudySession; // The timestamp of the last study session
    private String lastLogout; // The timestamp of the last logout
    private String dayStart; // The start time for the current day
    private String dayEnd; // The end time for the current day
    private int totalStudyTime; // The total study time in minutes
    private static final String FILE_PATH = "../data_handling/statistics_tracker.csv"; // The statistics file path
    /**
     * Constructs a {@code StatisticsTracker} instance and loads statistics for the specified name.
     *
     * @param dogName The name of the entity whose statistics are being tracked.
     */

     public static StatisticsTracker getInstance(String name) {
        if (instance == null) {
            instance = new StatisticsTracker(name);
        }
        return instance;
    }

    /**
     * Updates the singleton instance of the {@code StatisticsTracker}.
     *
     * @param tracker The new {@code StatisticsTracker} instance to set.
     */
    public static void setInstance(StatisticsTracker tracker) {
        instance = tracker;
    }

    public StatisticsTracker(String dogName) {
        this(dogName, FILE_PATH);
    }
    
    public StatisticsTracker(String dogName, String filePath) {
        boolean found = false;
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read all lines to handle file manipulation later
            while ((line = br.readLine()) != null) {
                lines.add(line);
                String[] data = line.split(","); // Split by comma

                if (!line.equals(lines.get(0)) && data[0].equalsIgnoreCase(dogName)) {
                    this.name = data[0];
                    this.lastStudySession = data[1];
                    this.lastLogout = data[2];
                    this.dayStart = data[3];
                    this.dayEnd = data[4];
                    this.totalStudyTime = Integer.parseInt(data[5]);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer values in the CSV file: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.println("Dog name not found in the CSV file: " + dogName);

            // Default values for the new dog
            this.name = dogName;
            this.lastStudySession = StatisticsTracker.formatLocalDateTime(LocalDateTime.now());
            this.lastLogout = StatisticsTracker.formatLocalDateTime(LocalDateTime.now());;
            this.dayStart = StatisticsTracker.formatLocalDateTime(LocalDateTime.now());;
            this.dayEnd = StatisticsTracker.formatLocalDateTime(LocalDateTime.now().plusDays(1));
            this.totalStudyTime = 0;

            // Append the new dog entry to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
                if (lines.isEmpty()) {
                    // Add header if file is empty
                    bw.write("Name,LastStudySession,LastLogout,DayStart,DayEnd,TotalStudyTime");
                    bw.newLine();
                }
                bw.write(this.name + "," + this.lastStudySession + "," + this.lastLogout + "," +
                        this.dayStart + "," + this.dayEnd + "," + this.totalStudyTime);
                bw.newLine();
                System.out.println("Added new dog to the file: " + this.name);
            } catch (IOException e) {
                System.err.println("Error writing to CSV file: " + e.getMessage());
            }
        }
    }

    /**
     * Saves the current statistics to the CSV file. Updates the corresponding entry if it exists.
     */
    public void saveToFile() {
        String fileName = "../data_handling/statistics_tracker.csv";
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Check if the line corresponds to the current name
                if (data[0].equalsIgnoreCase(name)) {
                    // Update the row with new values
                    line = String.format("%s,%s,%s,%s,%s,%d",
                            name,
                            lastStudySession,
                            lastLogout,
                            dayStart,
                            dayEnd,
                            totalStudyTime);
                    updated = true;
                }

                lines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // If the name was not found in the file, print an error
        if (!updated) {
            System.err.println("Name not found in the file: " + name);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write back all lines to the file
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Data successfully updated in file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    
    /**
     * For Testing
     * @param fileName name of file.
     */
    public void saveToFile(String fileName) {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Check if the line corresponds to the current name
                if (data[0].equalsIgnoreCase(name)) {
                    // Update the row with new values
                    line = String.format("%s,%s,%s,%s,%s,%d",
                            name,
                            lastStudySession,
                            lastLogout,
                            dayStart,
                            dayEnd,
                            totalStudyTime);
                    updated = true;
                }

                lines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // If the name was not found in the file, print an error
        if (!updated) {
            System.err.println("Name not found in the file: " + name);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write back all lines to the file
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Data successfully updated in file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    // Getters and Setters

    /**
     * Retrieves the name associated with the statistics tracker.
     *
     * @return The name of the entity being tracked.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the statistics tracker.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the timestamp of the last study session.
     *
     * @return The last study session timestamp.
     */
    public String getLastStudySession() {
        return lastStudySession;
    }

    /**
     * Sets the timestamp of the last study session.
     *
     * @param lastStudySession The new timestamp to set.
     */
    public void setLastStudySession(String lastStudySession) {
        this.lastStudySession = lastStudySession;
    }

    /**
     * Retrieves the start time for the current day.
     *
     * @return The start time for the day.
     */
    public String getDayStart() {
        return dayStart;
    }

    /**
     * Sets the start time for the current day.
     *
     * @param dayStart The new start time to set.
     */
    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    /**
     * Retrieves the end time for the current day.
     *
     * @return The end time for the day.
     */
    public String getDayEnd() {
        return dayEnd;
    }

    public String getLastLogout() {return this.lastLogout;}

    /**
     * Sets the end time for the current day.
     *
     * @param dayEnd The new end time to set.
     */
    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    /**
     * Retrieves the total study time in minutes.
     *
     * @return The total study time.
     */
    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    /**
     * Sets the total study time in minutes.
     *
     * @param totalStudyTime The new total study time to set.
     */
    public void setTotalStudyTime(int totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }

    /**
     * Formats a {@code LocalDateTime} object into a human-readable string.
     *
     * @param dateTime The {@code LocalDateTime} object to format.
     * @return A formatted date-time string in "yyyy-MM-dd hh:mm a" format.
     * @throws IllegalArgumentException if the {@code dateTime} is null.
     */
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("The LocalDateTime object cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        String formatted = dateTime.format(formatter);
        formatted = formatted.replace("a.m.", "AM").replace("p.m.", "PM");

        return formatted;
    }

    public static LocalDateTime formatStringToLocalDateTime(String dateTime) {
        try {
            // Normalize the input string to match the expected format
            dateTime = dateTime.replace("AM", "a.m.");
            dateTime = dateTime.replace("PM", "p.m.");
            System.out.println(dateTime);
            // Define the formatter for the normalized string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

            // Parse the normalized string into a LocalDateTime
            return LocalDateTime.parse(dateTime, formatter);

        } catch (DateTimeParseException e) {
            // Print the error message and rethrow the exception
            System.err.println("Error parsing date-time string: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Returns a string representation of the statistics.
     *
     * @return A formatted string containing the tracked statistics.
     */
    @Override
    public String toString() {
        return "Statistics Tracker Information:\n" +
               "Name: " + name + "\n" +
               "Last Study Session: " + lastStudySession + "\n" +
               "Day Start: " + dayStart + "\n" +
               "Day End: " + dayEnd + "\n" +
               "Total Study Time: " + totalStudyTime + " minutes";
    }

    public static void main(String[] args) {
        StatisticsTracker tracker = new StatisticsTracker("Buddy");
        System.out.println(StatisticsTracker.formatStringToLocalDateTime(tracker.getLastStudySession()));

    }
}
