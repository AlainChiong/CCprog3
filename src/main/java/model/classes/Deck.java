package main.java.model.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The `Deck` class represents a collection of {@link CardModel} objects that form a playable deck.
 * A deck has a name and a limited capacity (maximum of 10 unique cards).
 * It also enforces a rule that only one copy of a specific card (by name) is allowed in the deck.
 * This class provides functionality for managing cards within a deck,
 * as well as a static method for overall deck management (creation, deletion, modification).
 */
public class Deck {
    /**
     * The name of the deck.
     */
    private String name;
    /**
     * An `ArrayList` to store the `Card` objects that are part of this deck.
     * Each card in the deck is considered a unique instance (only one copy per card name).
     */
    private ArrayList<CardModel> cards;

    /**
     * Constructs a new `Deck` with a specified name and initializes an empty list of cards.
     *
     * @param name The name of the deck.
     */
    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    /**
     * Returns the name of the deck.
     *
     * @return The current name of the deck.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the `ArrayList` containing all {@link CardModel} objects in this deck.
     *
     * @return An `ArrayList` of cards currently in the deck.
     */
    public ArrayList<CardModel> getCards() {
        return cards;
    }

    /**
     * Attempts to add a {@link CardModel} to the deck.
     * A card can only be added if the deck is not already full (max 10 unique cards)
     * and if a card with the same name does not already exist in the deck.
     *
     * @param card The `Card` object to be added.
     * @return `true` if the card was successfully added, `false` otherwise (deck full or duplicate card name).
     */
    public boolean addCard(CardModel card) {
        if (cards.size() >= 10) {
            System.out.println("Deck is full (max 10 unique cards).");
            return false;
        }
        for (CardModel c : cards) {
            // Check for unique card name, case-insensitive
            if (c.getName().equalsIgnoreCase(card.getName())) {
                System.out.println("Only one copy of this card is allowed in a deck.");
                return false;
            }
        }
        cards.add(card);
        System.out.println(card.getName() + " added to deck.");
        return true;
    }

    /**
     * Attempts to remove a {@link CardModel} from the deck by its name.
     * The first card found with a matching name (case-insensitive) will be removed.
     *
     * @param name The name of the card to be removed.
     * @return `true` if the card was found and removed, `false` if the card was not found in the deck.
     */
    public boolean removeCard(String name) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equalsIgnoreCase(name)) {
                cards.remove(i);
                System.out.println("Card removed from deck.");
                return true;
            }
        }
        System.out.println("Card not found.");
        return false;
    }

    /**
     * Displays all cards currently in this deck to the console.
     * Cards are sorted alphabetically by name for consistent display.
     * Includes the card's name, rarity, variant, and value.
     * If the deck is empty, a corresponding message is printed.
     */
    public void viewDeck() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty.");
            return;
        }
        System.out.println("=== " + name + "'s Deck ===");
        // Sort cards by name before displaying
        Collections.sort(cards, Comparator.comparing(CardModel::getName));
        for (CardModel card : cards) {
            // Displays card details including rarity, variant, and value
            System.out.printf("%s (%s, %s) - $%.2f\n", card.getName(), card.getRarity(), card.getVariant(), card.getValue());
        }
    }

    /**
     * Displays detailed information for a specific {@link CardModel} within this deck by its name.
     * If found, it prints the card's name, rarity, variant, and value.
     *
     * @param name The name of the card whose details are to be viewed. Case-insensitive.
     */
    public void viewCardDetails(String name) {
        for (CardModel card : cards) {
            if (card.getName().equalsIgnoreCase(name)) {
                System.out.printf("Name: %s\nRarity: %s\nVariant: %s\nValue: $%.2f\n", card.getName(), card.getRarity(), card.getVariant(), card.getValue());
                return;
            }
        }
        System.out.println("Card not found.");
    }

    /**
     * Provides a static menu-driven interface for managing a list of `Deck` objects.
     * This method allows users to create, delete, view, add cards to, and remove cards from decks.
     * It interacts with a {@link CollectionModel} object to retrieve cards for adding to decks.
     *
     * @param scanner    The `Scanner` object used to read user input for menu choices and details.
     * @param decks      An `ArrayList` representing the list of all decks being managed.
     * @param collection The main Collection of cards from which cards can be added to decks.
     */
    public static void manageDecks(Scanner scanner, ArrayList<Deck> decks, CollectionModel collection) {
        while (true) {
            System.out.println("\n=== Deck Menu ===");
            System.out.println("1 - Create New Deck");
            System.out.println("2 - Delete Deck");
            System.out.println("3 - View Decks");
            System.out.println("4 - Add Card to Deck");
            System.out.println("5 - Remove Card from Deck");
            System.out.println("6 - Go Back");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a choice.");
                continue; // Prompt again
            }
            char choice = input.charAt(0);

            switch (choice) {
                case '1':
                    System.out.print("Enter deck name: ");
                    String deckName = scanner.nextLine();
                    decks.add(new Deck(deckName));
                    System.out.println("Deck \"" + deckName + "\" created.");
                    break;
                case '2':
                    System.out.print("Enter deck name to delete: ");
                    String dDeck = scanner.nextLine();
                    Deck tDelete = findDeck(decks, dDeck);
                    if (tDelete != null) {
                        decks.remove(tDelete);
                        System.out.println("Deck \"" + dDeck + "\" deleted.");
                    } else {
                        System.out.println("Deck not found.");
                    }
                    break;
                case '3':
                    if (decks.isEmpty()) {
                        System.out.println("No decks yet.");
                        break;
                    }
                    // Loop through and view each deck's contents
                    for (Deck d : decks) {
                        d.viewDeck();
                    }
                    break;
                case '4':
                    System.out.print("Enter deck name to add card to: ");
                    String targetDeckName = scanner.nextLine();
                    Deck deck = findDeck(decks, targetDeckName);
                    if (deck == null) {
                        System.out.println("Deck not found.");
                        break;
                    }
                    // Display available cards from the main collection
                    collection.displayCollection();
                    System.out.print("Enter card name from collection to add to deck: ");
                    String cardName = scanner.nextLine();
                    CardModel cardToAdd = CollectionModel.findCardInCollection(collection, cardName);
                    
                    // Check if card exists in collection and if there's at least one copy
                    if (cardToAdd != null && cardToAdd.getAmount() >= 1) {
                        // Attempt to add card to deck. If successful, decrease amount in collection.
                        if (deck.addCard(cardToAdd)) {
                            cardToAdd.setAmount(cardToAdd.getAmount() - 1);
                            System.out.println("Card \"" + cardToAdd.getName() + "\" quantity in collection reduced by 1.");
                        }
                    } else {
                        System.out.println("Card not found in collection or no copies left.");
                    }
                    break;
                case '5':
                    System.out.print("Enter deck name to remove card from: ");
                    String dName = scanner.nextLine();
                    Deck d = findDeck(decks, dName);
                    if (d == null) {
                        System.out.println("Deck not found.");
                        break;
                    }
                    // Display cards in the selected deck
                    d.viewDeck();
                    System.out.print("Enter card name to remove from deck: ");
                    String removeCardName = scanner.nextLine();
                    // Attempt to remove card from deck. If successful, increase amount in main collection.
                    if (d.removeCard(removeCardName)) {
                        CardModel cardReturnedToCollection = CollectionModel.findCardInCollection(collection, removeCardName);
                        if (cardReturnedToCollection != null) {
                            cardReturnedToCollection.setAmount(cardReturnedToCollection.getAmount() + 1);
                            System.out.println("Card \"" + removeCardName + "\" returned to main collection.");
                        }
                    }
                    break;
                case '6':
                    return; // Exit the Deck Management menu
                default:
                    System.out.println("Invalid input. Please enter a number from 1 to 6.");
            }
        }
    }

    /**
     * Static helper method to find a {@link Deck} within a list of decks by its name.
     * The search is case-insensitive.
     *
     * @param decks An `ArrayList` of {@link Deck} objects to search through.
     * @param name  The name of the deck to find.
     * @return The {@link Deck} object if found, otherwise `null`.
     */
    public static Deck findDeck(ArrayList<Deck> decks, String name) {
        for (Deck d : decks) {
            if (d.getName().equalsIgnoreCase(name)){
                return d;
            }
        }
        return null;
    }
}
