package main.java.model.classes;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;

/**
 * The `Card` class represents a collectible card with attributes such as name, rarity,
 * variant, monetary value, and the amount/quantity of the card.
 * It provides methods for creating new cards interactively and for managing
 * card properties. The value of the card is influenced by its variant, which is
 * defined by the {@link Variant} enum.
 */
public class CardModel {

    /**
     * The name of the card.
     */
    private String name;

    /**
     * The rarity of the card (e.g., Common, Uncommon, Rare, Legendary),
     * represented by the {@link Rarity} enum.
     */
    private Rarity rarity;

    /**
     * The variant of the card (e.g., Normal, Extended-art, Full-art, Alt-art),
     * represented by the {@link Variant} enum. This attribute influences the card's final monetary value.
     */
    private Variant variant;

    /**
     * The monetary base value of a single card. This value is before any
     * variant-specific multipliers are applied by {@link #getValue()}.
     */
    private double value;

    /**
     * The quantity or amount of this specific card. This amount can differ across
     * collections, binders, and decks. Defaults to 1 when a new card is created.
     */
    private double amount;

    /**
     * Constructs a new `Card` object with specified properties.
     * The amount of the card is initialized to 1 by default.
     *
     * @param name    The name of the card.
     * @param rarity  The {@link Rarity} of the card.
     * @param variant The {@link Variant} of the card.
     * @param value   The monetary base value of the card. The final value will be
     * calculated using this base value and the variant's multiplier.
     */
    public CardModel(String name, Rarity rarity, Variant variant, double value) {
        this.name = name;
        this.rarity = rarity;
        this.variant = variant;
        this.value = value;
        this.amount = 1;
    }

     /**
     * Interactively creates a new `Card` object by prompting the user for input
     * via the provided `Scanner` object.
     * The method guides the user through entering the card's name and base value,
     * then selecting its rarity. If the rarity is {@link Rarity#RARE} or {@link Rarity#LEGENDARY},
     * the user is prompted to choose a variant. The chosen {@link Variant}'s multiplier
     * is then applied to the card's base value, affecting the value returned by {@link #getValue()}.
     * It handles invalid input for numeric selections.
     *
     * @param scanner The `Scanner` object used to read user input from the console.
     * @return A newly created `Card` object based on user input. The card's final value
     * will be calculated based on the base value and selected variant.
     */
    public static CardModel createCard(Scanner scanner) {
        String name;
        Rarity rarity = null;
        Variant variant = null;
        double value;
        int rarityInput=0;
        int variantInput=0;
        boolean validInput = false;

        System.out.print("Name of card: ");
        name = scanner.nextLine();

        System.out.print("Value of card($): ");
        value = scanner.nextDouble();
        scanner.nextLine();

        while (!validInput) {
            System.out.println("1 - Common");
            System.out.println("2 - Uncommon");
            System.out.println("3 - Rare");
            System.out.println("4 - Legendary");

            try {
                System.out.print("Choose the rarity(1-4) of this card: ");
                rarityInput = scanner.nextInt();

                if ((rarity = Rarity.fromInt(rarityInput)) != null) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between 1 and 4.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a whole number.\n");
                scanner.nextLine(); 
            }
        }

        validInput = false;

        if (rarityInput == 3 || rarityInput == 4) {

            while (!validInput) {
                System.out.println("This card is a " + rarity.toString() + " card and qualifies for a variant.");
                System.out.println("1 - Normal");
                System.out.println("2 - Extended-art");
                System.out.println("3 - Full-art");
                System.out.println("4 - Alt-art");

                try {
                    System.out.print("Choose a variant (1-4): ");
                    variantInput = scanner.nextInt();

                    if ((variant = Variant.fromInt(variantInput)) != null) {
                        validInput = true;
                    } else {
                        System.out.println("Please enter a number between 1 and 4.\n");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a whole number.\n");
                    scanner.nextLine(); 
                }
            }
        }

        else {
            variant = Variant.INVALID;
        }
        return new CardModel(name, rarity, variant, value);
    }

    /**
     * Checks if this `Card` object matches another `Card` object based on
     * its name, rarity, and variant (case-insensitive).
     *
     * @param other The other `Card` object to compare against.
     * @return `true` if the name, rarity, and variant of both cards are the same (ignoring case),
     * `false` otherwise.
     */
    public boolean matches(CardModel other) {
        return this.name.equalsIgnoreCase(other.name) && this.rarity.toString().equalsIgnoreCase(other.rarity.toString()) && this.variant.toString().equalsIgnoreCase(other.variant.toString());
    }

    /**
     * Sets the name of the card.
     *
     * @param name The new name for the card.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the card.
     *
     * @return The current name of the card.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the rarity of the card.
     *
     * @param rarity The new rarity for the card (e.g., "Common","Uncommon","Rare","Legendary").
     */
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    /**
     * Returns the rarity of the card.
     *
     * @return The current rarity of the card.
     */
    public Rarity getRarity() {
        return this.rarity;
    }

    /**
     * Sets the variant of the card.
     *
     * @param variant The new variant for the card ("Normal","Extended-art","Full-art","Alt-art").
     */
    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    /**
     * Returns the variant of the card.
     *
     * @return The current variant of the card.
     */
    public Variant getVariant() {
        return this.variant;
    }

    /**
     * Sets the monetary base value of the card.
     * This value does not include any multipliers from the card's variant.
     *
     * @param value The new monetary base value for the card.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Returns the monetary value of the card
     *
     * @return The current monetary value of the card.
     */
    public double getBaseValue() {
        return this.value;
    }

    /**
     * Returns the monetary value of the card, calculated by applying the
     * {@link Variant}'s multiplier to the card's base value.
     *
     * @return The current monetary value of the card, including variant multiplier.
     */
    public double getValue() {
        return this.value * variant.getMultiplier();
    }

    /**
     * Sets the amount (quantity) of this specific card.
     *
     * @param amount The new amount/quantity of the card.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the amount (quantity) of this specific card.
     *
     * @return The current amount/quantity of the card.
     */
    public double getAmount() {
        return this.amount;
    }
    
    public double getTotalPrice(double amount) {
        return getValue() * amount;
    }
}