package enums;

/**
 * Defines the possible visual variants for collectible cards, particularly
 * for those of Rare or Legendary rarity. Each variant has an associated
 * multiplier that affects the card's monetary value.
 */
public enum Variant {
    /**
     * This variant applies no additional multiplier to the card's base value.
     */
    NORMAL(1.0),
    /**
     * This variant increases the card's base value by 50%.
     */
    EXTENDED_ART(1.5),
    /**
     * This variant increases the card's base value by 100%.
     */
    FULL_ART(2.0),
    /**
     * This variant significantly increases the card's base value by 200%.
     */
    ALT_ART(3.0);

    /**
     * The monetary value multiplier associated with this variant.
     * This multiplier is applied to the card's base value.
     */
    private final double multiplier;

    /**
     * Constructs a `Variant` enum constant with a specific value multiplier.
     *
     * @param multiplier The factor by which a card's base value is increased for this variant.
     */
    Variant(double multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Returns the monetary value multiplier associated with this card variant.
     *
     * @return The multiplier (e.g., 1.0 for Normal, 1.5 for Extended-art).
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * Retrieves a `Variant` enum constant based on a numerical input.
     *
     * @param input An integer representing the variant choice (1 for Normal, 2 for Extended-art, etc.).
     * @return The corresponding `Variant` enum constant, or `null` if the input is invalid.
     */
    public static Variant fromInt(int input) {
        switch (input) {
            case 1:
                return NORMAL;
            case 2:
                return EXTENDED_ART;
            case 3:
                return FULL_ART;
            case 4:
                return ALT_ART;
            default:
                return null;
        }
    }

    /**
     * Returns a user-friendly string representation of the variant level.
     * This method overrides the default `Object.toString()` method.
     *
     * @return The name of the rarity constant as a String.
     */
    @Override
    public String toString() {
        switch (this) {
            case NORMAL:
                return "Normal";
            case EXTENDED_ART:
                return "Extended Art";
            case FULL_ART:
                return "Full Art";
            case ALT_ART:
                return "Alt Art";
            default:
                return super.toString(); 
        }
    }
}
