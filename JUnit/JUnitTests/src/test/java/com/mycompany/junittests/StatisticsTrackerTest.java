package com.mycompany.junittests;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@code StatisticsTracker} class.
 * Tests various functionalities including reading, updating, and saving statistics.
 */
public class StatisticsTrackerTest {

    private static final String TEST_CSV_PATH = "statistics_tracker.csv";

    @BeforeEach
    public void setUp() throws IOException {
        // Create a test CSV file with initial data for testing
        Files.write(Paths.get(TEST_CSV_PATH),
            java.util.Collections.singletonList(
                "Name,LastLogin,LastLogout,DayStart,DayEnd,TotalStudyTime\n" +
                "TestPet,2024-11-25 10:00 AM,2024-11-25 11:00 AM,08:00,18:00,60"
            )
        );
    }
 
    @AfterEach
    public void tearDown() throws IOException {
        // Delete the test CSV file after all tests are complete
        Files.deleteIfExists(Paths.get(TEST_CSV_PATH));
    }
    

    @Test
    public void testGetName() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        assertEquals("TestPet", tracker.getName(), "Name should match the initialized value.");
    }

    @Test
    public void testSetName() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        tracker.setName("NewPet");
        assertEquals("NewPet", tracker.getName(), "Name should be updated to 'NewPet'.");
    }


    @Test
    public void testFormatLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 11, 25, 10, 0);
        String expected = "2024-11-25 10:00 AM";
        assertEquals(expected, StatisticsTracker.formatLocalDateTime(dateTime),
            "Formatted date should match '2024-11-25 10:00 AM'.");
    }

    @Test
    public void testToString() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        String result = tracker.toString();
        assertTrue(result.contains("Name: TestPet"), "toString should include 'Name: TestPet'.");
        assertTrue(result.contains("Total Study Time: 60 minutes"), "toString should include 'Total Study Time: 60 minutes'.");
    }

    @Test
    public void testGetLastStudySession() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        assertEquals("2024-11-25 10:00 AM", tracker.getLastStudySession(),
            "Last study session should be '2024-11-25 10:00 AM'.");
    }

    @Test
    public void testSetLastStudySession() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        tracker.setLastStudySession("2024-11-26 11:00 AM");
        assertEquals("2024-11-26 11:00 AM", tracker.getLastStudySession(),
            "Last study session should be updated to '2024-11-26 11:00 AM'.");
    }

    @Test
    public void testGetTotalStudyTime() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        assertEquals(60, tracker.getTotalStudyTime(), "Initial total study time should be 60.");
    }

    @Test
    public void testSetTotalStudyTime() {
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        tracker.setTotalStudyTime(200);
        assertEquals(200, tracker.getTotalStudyTime(), "Total study time should be updated to 200.");
    }
    
    @Test
    public void testSaveToFile() throws IOException {
        // Initialize the tracker and set the total study time
        StatisticsTracker tracker = new StatisticsTracker("TestPet",TEST_CSV_PATH);
        tracker.setTotalStudyTime(120);

        // Save the data to the CSV file
        tracker.saveToFile(TEST_CSV_PATH);

        // Read the file and verify the updated total study time
        try (BufferedReader br = new BufferedReader(new FileReader(TEST_CSV_PATH))) {
            br.readLine();
            String line = br.readLine(); // Read the single line
            if (line != null) {
                String[] data = line.split(",");
                assertEquals("120", data[5], "Total study time should be updated in the file.");
            } else {
                fail("No data line found in the CSV file.");
            }
        }
    }
}
