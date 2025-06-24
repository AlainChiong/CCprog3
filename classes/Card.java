package classes;

import java.util.InputMismatchException;
import java.util.Scanner;

import enums.Rarity;
import enums.Variant;

/**
 * The `Card` class represents a collectible card with attributes such as name, rarity,
 * variant, value, and the amount/quantity of the card.
 * It provides methods for creating new cards interactively and for managing
 * card properties.
 */
public class Card {

    /**
     * The name of the card.
     */
    private String name;

    /**
     * The rarity of the card (Common, Uncommon, Rare, Legendary).
     */
    private Rarity rarity;

    /**
     * The variant of the card (e.g., Normal, Extended-art, Full-art, Alt-art).
     */
    private Variant variant;

    /**
     * The monetary value of a single card. This value can be adjusted based on the variant.
     */
    private double value;

    /**
     * The quantity or amount of this specific card. Differs on each collection, binder, and deck
     * Defaults to 1 when a new card is created.
     */
    private double amount;

    /**
     * Constructs a new `Card` object with specified properties.
     * The amount of the card is initialized to 1 by default.
     *
     * @param name    The name of the card.
     * @param rarity  The rarity of the card.
     * @param variant The variant of the card.
     * @param value   The monetary value of the card.
     */
    public Card(String name, Rarity rarity, Variant variant, double value) {
        this.name = name;
        this.rarity = rarity;
        this.variant = variant;
        this.value = value;
        this.amount = 1;
    }

    /**
     * Interactively creates a new `Card` object by prompting the user for input
     * via the provided `Scanner` object.
     * The method guides the user through selecting rarity and, if applicable, variant,
     * which can affect the card's final value. It handles invalid input for numeric selections.
     *
     * @param scanner The `Scanner` object used to read user input from the console.
     * @return A newly created `Card` object based on user input.
     * @throws InputMismatchException if the user enters non-integer input for rarity or variant selection.
     */
    public static Card createCard(Scanner scanner) {
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
        return new Card(name, rarity, variant, value);
    }

    /**
     * Checks if this `Card` object matches another `Card` object based on
     * its name, rarity, and variant (case-insensitive).
     *
     * @param other The other `Card` object to compare against.
     * @return `true` if the name, rarity, and variant of both cards are the same (ignoring case),
     * `false` otherwise.
     */
    public boolean matches(Card other) {
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
     * Sets the monetary value of the card.
     *
     * @param value The new monetary value for the card.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Returns the monetary value of the card.
     *
     * @return The current monetary value of the card.
     */
    public double getValue() {
        return this.value;
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
    
}