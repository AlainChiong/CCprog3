package main.java.model.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * The `CollectionModel` class manages a collection of {@link CardModel} objects.
 * It provides functionalities to add cards, display the collection,
 * modify card counts, view individual card details, find specific cards,
 * and remove cards.
 * {@link CardModel} instances are stored internally in an `ArrayList`.
 */
public class CollectionModel {
    /**
     * An `ArrayList` to store the {@link CardModel} objects in this collection.
     * Each {@link CardModel} represents a unique card type (name, rarity, variant)
     * and holds its current amount.
     */
    private List<CardModel> cards;

    /**
     * Constructs a new, empty `CollectionModel` of cards.
     * Initializes the internal `ArrayList` to store cards, ensuring the collection
     * starts with no cards.
     */
    public CollectionModel() {
        cards = new ArrayList<>();
    }

    /**
     * Returns the total number of unique card types currently in the collection.
     * This represents the number of distinct {@link CardModel} objects stored,
     * not the sum of the amounts of all cards.
     *
     * @return The number of distinct card types in the collection.
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Adds a new {@link CardModel} to the collection.
     * If a card with the same name, rarity, and variant already exists in the collection
     * (as determined by the `matches` method of {@link CardModel}),
     * its amount is incremented by one. Otherwise, the new card is added as a distinct entry
     * with an initial amount of one (assuming the `nCard` passed in has an amount of 1,
     * or its amount is set to 1 upon creation if it's a truly new card).
     *
     * @param nCard The {@link CardModel} object to be added to the collection.
     * @return `true` if the card was added as a new distinct entry, `false` if an
     * existing card's amount was incremented.
     */
    public boolean addCard(CardModel nCard) {
        for (CardModel card : cards) {
            if (card.matches(nCard)) {
                card.setAmount(card.getAmount() + 1);
                System.out.println("Duplicate found. Amount increased.");
                return false;
            }
        }
        cards.add(nCard);
        return true;
    }

    /**
     * Displays all cards currently in the collection to the console.
     * {@link CardModel} instances are sorted alphabetically by name before being displayed.
     * If the collection is empty, a corresponding message is printed.
     * The output format for each card is "CardName xAmount".
     */
    public void displayCollection() {
        if (cards.isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        System.out.println("\nYour Collection:");
        // Sort cards by name for consistent display
        Collections.sort(cards, Comparator.comparing(CardModel::getName));
        for (CardModel c : cards) {
            System.out.printf("%s x%.0f\n", c.getName(), c.getAmount());
        }
    }

    /**
     * Allows the user to modify the amount of a specific card within the collection
     * via console input. The method prompts the user to choose a card from a numbered list
     * and then to enter an amount by which to increase or decrease its quantity.
     * The card's amount cannot be set to a negative value; if a decrease would result
     * in a negative amount, it is set to 0.
     *
     * @param scanner The `Scanner` object used to read user input from the console.
     * @deprecated This method is primarily for console-based interaction and
     * is superseded by GUI-driven modification methods in the controller.
     */
    @Deprecated
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

        CardModel selected = cards.get(choice);
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
     * Displays the detailed properties of a specific card found by its name to the console.
     * It iterates through the collection and displays details of the first card found
     * that matches the given name (case-insensitive).
     *
     * @param name The name of the card whose details are to be viewed. Case-insensitive.
     * @deprecated This method is primarily for console-based interaction and
     * is superseded by GUI-driven detail display methods in the controller.
     */
    @Deprecated
    public void viewCardDetails(String name) {
        for (CardModel card : cards) {
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
     * Retrieves a list of all cards in the collection, sorted alphabetically by name.
     * The internal order of the collection (`cards` field) remains unchanged.
     *
     * @return A new, sorted `List` containing all {@link CardModel} objects from the collection.
     */
    public List<CardModel> getCardsSortedByName() {
        List<CardModel> sortedCards = new ArrayList<>(cards); // Create a new, mutable copy

        Collections.sort(sortedCards, new Comparator<CardModel>() {
            @Override
            public int compare(CardModel card1, CardModel card2) {
                // Compare card names case-insensitively
                return card1.getName().compareToIgnoreCase(card2.getName());
            }
        });

        return sortedCards;
    }

    /**
     * Returns the internal `ArrayList` of {@link CardModel} objects managed by this collection.
     * This provides direct access to the underlying list of cards.
     *
     * @return The `ArrayList` containing all cards in the collection.
     */
    public List<CardModel> getCards() {
        return cards;
    }

    /**
     * Static helper method to find a specific card within a given collection by its name.
     * It iterates through the collection's cards and returns the first card that matches
     * the provided name (case-insensitive).
     *
     * @param collection The `CollectionModel` object to search within.
     * @param name       The name of the card to find.
     * @return The {@link CardModel} object if found, otherwise `null`.
     */
    public static CardModel findCardInCollection(CollectionModel collection, String name) {
        for (CardModel c : collection.getCards()) {
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * Reduces the amount of a specific card in the collection by one, identified by its name.
     * If the card's amount becomes 0 or less after reduction, the card is removed entirely from the collection.
     * A message is printed to the console indicating the outcome (amount reduced or card removed).
     *
     * @param name The name of the card to be removed or have its amount reduced. Case-insensitive.
     * @deprecated This method is primarily for console-based interaction and
     * is superseded by GUI-driven removal methods in the controller.
     */
    @Deprecated
    public void removeCardByName(String name) {
        for (int i = 0; i < cards.size(); i++) {
            CardModel card = cards.get(i);
            if (card.getName().equalsIgnoreCase(name)) {
                double newAmount = card.getAmount() - 1;
                if (newAmount <= 0) {
                    cards.remove(i);
                    System.out.println("Card removed from collection (amount reached 0).");
                } else {
                    card.setAmount(newAmount);
                    System.out.println("Card amount reduced by 1.");
                }
                return; // Exit after finding and processing the first match
            }
        }
    }

    /**
     * Removes a specific {@link CardModel} instance entirely from the collection.
     * This method directly removes the object reference from the internal list.
     *
     * @param cardModel The {@link CardModel} object to be removed from the collection.
     */
    public void removeCard(CardModel cardModel) {
        cards.remove(cardModel);
    }

    /**
     * Finds and returns the card in the collection that matches the given card
     * (based on name, rarity, and variant).
     *
     * @param target The card to match against.
     * @return The matching card from the collection, or null if not found.
     */
    public CardModel getMatchingCard(CardModel target) {
        for (CardModel card : cards) {
            if (card.matches(target)) {
                return card;
            }
        }
        return null;
    }
}