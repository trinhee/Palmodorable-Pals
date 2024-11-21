public class SettingsTest {
    public static void main(String[] args) {
        // Load settings for pet "Buddy"
        Settings buddySettings = new Settings("Buddy");
        buddySettings.displaySettings();

        // Load settings for pet "Max"
        Settings maxSettings = new Settings("Max");
        maxSettings.displaySettings();
    }
}