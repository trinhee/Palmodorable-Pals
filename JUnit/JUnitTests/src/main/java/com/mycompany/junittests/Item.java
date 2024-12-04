package com.mycompany.junittests;
import java.util.Objects;

/**
 * The {@code Item} class represents an item that can be used to affect a pet's attributes.
 * Each item has a name, type, and an effectiveness value.
 */
public class Item {

    private String name; // The name of the item.
    private String type; // The type of the item (e.g., "food", "gift").
    private int effectiveness; // The effectiveness value of the item.

    /**
     * Constructs an {@code Item} with the specified name, type, and effectiveness.
     *
     * @param name         the name of the item.
     * @param type         the type of the item (e.g., "food", "gift").
     * @param effectiveness the effectiveness value of the item.
     */
    public Item(String name, String type, int effectiveness) {
        this.name = name;
        this.type = type;
        this.effectiveness = effectiveness;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the new name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type of the item.
     *
     * @param type the new type of the item (e.g., "food", "gift").
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the effectiveness value of the item.
     *
     * @param effectiveness the new effectiveness value of the item.
     */
    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }

    /**
     * Retrieves the name of the item.
     *
     * @return the name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the type of the item.
     *
     * @return the type of the item (e.g., "food", "gift").
     */
    public String getType() {
        return this.type;
    }

    /**
     * Retrieves the effectiveness value of the item.
     *
     * @return the effectiveness value of the item.
     */
    public int getEffectiveness() {
        return this.effectiveness;
    }

    /**
     * Applies the item's effect to the specified pet. The effect depends on the item's type:
     * <ul>
     *   <li>If the type is "gift", it increases the pet's happiness.</li>
     *   <li>If the type is "food", it increases the pet's fullness.</li>
     * </ul>
     * If the pet's happiness or fullness exceeds its maximum value, it is capped at the maximum.
     *
     * @param pet the pet to which the item's effect will be applied.
     */
    public void applyEffect(Pet pet) {
        int result;
        try {
            if (this.type.equalsIgnoreCase("gift")) {
                result = pet.getHappiness() + this.effectiveness;
                pet.setHappiness(Math.min(result, pet.getMaxHappiness()));
            } else if (this.type.equalsIgnoreCase("food")) {
                result = pet.getFullness() + this.effectiveness;
                pet.setFullness(Math.min(result, pet.getMaxFullness()));
            } else {
                throw new IllegalArgumentException("Invalid item type: " + this.type);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error applying item effect: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Checks if this {@code Item} is equal to another object. Two items are considered equal
     * if their name, type, and effectiveness are the same.
     *
     * @param obj the object to compare to.
     * @return {@code true} if the items are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Item other = (Item) obj;
        return Objects.equals(this.name, other.name) &&
               Objects.equals(this.type, other.type) &&
               this.effectiveness == other.effectiveness;
    }

    /**
     * Computes the hash code for this {@code Item}.
     * The hash code is based on the name, type, and effectiveness.
     *
     * @return the hash code for this item.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.type, this.effectiveness);
    }
}