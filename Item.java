import java.util.Objects;

/**
 * Generic item class for food and gift
 * Author: Kaegan Walker Fulton
 */
public class Item {

    /** The item's name */
    private String name;
    /** The item's type */
    private String type;
    /** The item's effectiveness (how much it will increase stat) */
    private int effectiveness;

    /**
     * Item constructor. Creates an instance of an Item object. Sets initial values to given values.
     *
     * @param name          Name of item
     * @param type          Type of item. "gift" for Gift, "food" for Food.
     * @param effectiveness Increase in stat. Happiness for Gift, fullness for Food.
     */
    public Item(String name, String type, int effectiveness) {
        this.name = name;
        this.type = type;
        this.effectiveness = effectiveness;
    }

    // Getters and Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public int getEffectiveness() {
        return this.effectiveness;
    }

    /**
     * Updates pet stat (fullness for food, happiness for gift).
     *
     * @param pet The pet in the game
     */
    public void applyEffect(Pet pet) {
        try {
            if (this.type.equalsIgnoreCase("gift")) {
                if (pet.getHappiness() + this.effectiveness <= pet.getMaxHappiness()) {
                    pet.setHappiness(pet.getHappiness() + this.effectiveness);
                } else {
                    pet.setHappiness(pet.getMaxHappiness());
                }
            } else if (this.type.equalsIgnoreCase("food")) { // Fixed from "type" to "food"
                if (pet.getFullness() + this.effectiveness <= pet.getMaxFullness()) {
                    pet.setFullness(pet.getFullness() + this.effectiveness);
                } else {
                    pet.setFullness(pet.getMaxFullness()); // Fixed setting fullness instead of happiness
                }
            } else {
                throw new Exception("Invalid type in item class");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Override equals and hashCode for proper functioning in HashMap
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        return Objects.equals(name, other.name) &&
               Objects.equals(type, other.type) &&
               effectiveness == other.effectiveness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, effectiveness);
    }
}
