import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.print.DocFlavor.STRING;

public class StatisticsTracker {
    // Attributes
    private String name;
    private String lastStudySession;
    private String lastLogout;
    private String dayStart;
    private String dayEnd;
    private int totalStudyTime;

    // Constructor
    public StatisticsTracker(String dogName) {
        try (BufferedReader br = new BufferedReader(new FileReader("data_handling/statistics_tracker.csv"))) {
            String line;
            boolean found = false;

            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Split by comma
                if (data[0].equalsIgnoreCase(dogName)) {
                    this.name = data[0];
                    this.lastStudySession = data[1];
                    this.lastLogout = data[2];
                    this.dayStart = data[3];
                    this.dayEnd = data[4];
                    this.totalStudyTime = Integer.parseInt(data[5]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.err.println("Dog name not found in the CSV file: " + dogName);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer values in the CSV file: " + e.getMessage());
        }
    }


    public void saveToFile() {
        String fileName = "data_handling/statistics_tracker.csv";
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
                            lastLogout, // Assuming Last Logout is the same as Last Study Session
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastStudySession() {
        return lastStudySession;
    }

    public void setLastStudySession(String lastStudySession) {
        this.lastStudySession = lastStudySession;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(int totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("The LocalDateTime object cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        String formatted = dateTime.format(formatter);
        formatted = formatted.replace("a.m.", "AM").replace("p.m.", "PM");

        return formatted;
    }

    // Override toString method with formatted output
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
        // Example usage
        StatisticsTracker tracker = new StatisticsTracker("Buddy");
        System.out.println(tracker);
    
        // Update some data
        tracker.setLastStudySession("2024-11-23 02:00 PM");
        tracker.setTotalStudyTime(60);
    
        // Save to file
        tracker.saveToFile();
    }
}
