public class InventoryTest {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager("Buddy");
        Pet pet = gameManager.getCurrentPet();

        
        



        // Add items to the inventory
        pet.getInventory().addItem(new Item("Basic Food", "food", 5));
        pet.getInventory().addItem(new Item("Basic Gift", "gift", 3));

        System.out.println(pet.getInventory()); // Outputs: Food: 5, Gift: 3
        System.out.println(pet);

        pet.saveToFile();

    }
}

