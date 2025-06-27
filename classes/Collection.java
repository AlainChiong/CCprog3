package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The `Collection` class manages a collection of {@link Card} objects.
 * It provides functionalities to add cards, display the collection,
 * modify card counts, view individual card details, and find specific cards.
 * {@link Card} are stored in an `ArrayList`.
 */
public class Collection {
    /**
     * An `ArrayList` to store the {@link Card} objects in this collection.
     */
    private ArrayList<Card> cards;

    /**
     * Constructs a new, empty `Collection` of cards.
     * Initializes the internal `ArrayList` to store cards.
     */
    public Collection() {
        cards = new ArrayList<>();
    }

    /**
     * Returns the total number of unique card types currently in the collection.
     * This represents the number of distinct `Card` objects, not the sum of their amounts.
     *
     * @return The number of distinct cards in the collection.
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Adds a new {@link Card} to the collection.
     * If a card with the same name, rarity, and variant already exists in the collection,
     * its amount is incremented by one. Otherwise, the new card is added as a distinct entry.
     *
     * @param nCard The {@link Card} object to be added to the collection.
     */
    public void addCardC(Card nCard) {
        for (Card card : cards) {
            if (card.matches(nCard)) {
                card.setAmount(card.getAmount() + 1);
                System.out.println("Duplicate found. Amount increased.");
                return;
            }
        }
        cards.add(nCard);
        System.out.println("New card added.");
    }

    /**
     * Displays all cards currently in the collection to the console.
     * {@link Card} are sorted alphabetically by name before being displayed.
     * If the collection is empty, a corresponding message is printed.
     * The output format is "CardName xAmount".
     */
    public void displayCollection() {
        if (cards.isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        System.out.println("\nYour Collection:");
        // Sort cards by name for consistent display
        Collections.sort(cards, Comparator.comparing(Card::getName));
        for (Card c : cards) {
            System.out.printf("%s x%.0f\n", c.getName(), c.getAmount());
        }
    }

    /**
     * Allows the user to modify the amount of a specific card within the collection.
     * The method prompts the user to choose a card from a numbered list and then
     * to enter an amount by which to increase or decrease its quantity.
     * The card's amount cannot be set to a negative value; if a decrease would result
     * in a negative amount, it is set to 0.
     *
     * @param scanner The `Scanner` object used to read user input from the console.
     */
    public void modifyCardCount(Scanner scanner) {
        if (cards.isEmpty()) {
            System.out.println("No cards to modify.");
            return;
        }

        System.out.println("Choose a card to modify:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.printf("%d - %s x%.0f\n", i + 1, cards.get(i).getName(), cards.get(i).getAmount());
        }

        int choice = scanner.nextInt() - 1; // Adjust for 0-based indexing
        if (choice < 0 || choice >= cards.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Card selected = cards.get(choice);
        System.out.print("Enter amount to increase/decrease (+/-): ");
        double change = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        double newAmount = selected.getAmount() + change;
        if (newAmount < 0) {
            System.out.println("Amount cannot go below 0. Set to 0.");
            newAmount = 0;
        }
        selected.setAmount(newAmount);
        System.out.println("Updated amount: " + (int)newAmount);
    }

    /**
     * Displays the detailed properties of a specific card found by its name.
     * If multiple cards have the same name but different rarities/variants, this method
     * will display details of the first one found.
     *
     * @param name The name of the card whose details are to be viewed. Case-insensitive.
     */
    public void viewCardDetails(String name) {
        for (Card card : cards) {
            if (card.getName().equalsIgnoreCase(name)) {
                System.out.println("=== Card Details ===");
                System.out.println("Name   : " + card.getName());
                System.out.println("Rarity : " + card.getRarity());
                System.out.println("Variant: " + card.getVariant());
                System.out.printf("Value  : $%.2f\n", card.getValue());
                System.out.println("Amount : " + (int)card.getAmount());
                return;
            }
        }
        System.out.println("Card not found in collection.");
    }

    /**
     * Returns the internal `ArrayList` of {@link Card} objects managed by this collection.
     * This provides direct access to the underlying list of cards.
     *
     * @return The `ArrayList` containing all cards in the collection.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Static helper method to find a specific card within a given collection by its name.
     * It iterates through the collection's cards and returns the first card that matches
     * the provided name (case-insensitive).
     *
     * @param collection The `Collection` object to search within.
     * @param name       The name of the card to find.
     * @return The `Card` object if found, otherwise `null`.
     */
    public static Card findCardInCollection(Collection collection, String name) {
        for (Card c : collection.getCards()) {
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * Reduces the amount of a specific card in the collection by one.
     * If the card's amount becomes 0 or less after reduction, the card is removed entirely from the collection.
     * A message is printed to the console indicating the outcome (amount reduced or card removed).
     *
     * @param name The name of the card to be removed or have its amount reduced. Case-insensitive.
     */    
    public void removeCardByName(String name) {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.getName().equalsIgnoreCase(name)) {
                double newAmount = card.getAmount() - 1;
                if (newAmount <= 0) {
                    cards.remove(i);
                    System.out.println("Card removed from collection (amount reached 0).");
                } else {
                    card.setAmount(newAmount);
                    System.out.println("Card amount reduced by 1.");
                }
            }
        }
    }
}