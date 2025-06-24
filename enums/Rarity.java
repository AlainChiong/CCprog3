package enums;

/**
 * Defines the possible rarity levels for a collectible card.
 * These rarities determine if a card can have different visual variants.
 */
public enum Rarity {
    /**
     * Common rarity.
     */
    COMMON,
    /**
     * Uncommon rarity.
     */
    UNCOMMON,
    /**
     * Rare rarity. Cards of this rarity can have variants that influence their value.
     */
    RARE,
    /**
     * Legendary rarity. The highest rarity, cards of this type can also have variants
     * that significantly influence their value.
     */
    LEGENDARY;

    /**
     * Retrieves a `Rarity` enum constant based on a numerical input.
     * This is useful for user selection in console applications.
     *
     * @param input An integer representing the rarity choice (1 for Common, 2 for Uncommon, etc.).
     * @return The corresponding `Rarity` enum constant, or `null` if the input is invalid.
     */
    public static Rarity fromInt(int input) {
        switch (input) {
            case 1:
                return COMMON;
            case 2: 
                return UNCOMMON;
            case 3:
                return RARE;
            case 4:
                return LEGENDARY;
            default:
                return null;
        }
    }
}
