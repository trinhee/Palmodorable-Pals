public class PetsDictionaryTest {
    public static void main(String[] args) {
        // Create the PetsDictionary
        PetsDictionary petsDictionary = new PetsDictionary();

        // Display all pets
        petsDictionary.displayPets();

        // Retrieve a specific pet by name
        Pet buddy = petsDictionary.getPetByName("Buddy");
        if (buddy != null) {
            System.out.println("Retrieved Pet: ");
            System.out.println("Name: " + buddy.getName());
        } else {
            System.out.println("Pet not found!");
        }
    }
}
