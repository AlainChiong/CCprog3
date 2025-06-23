package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Deck {
    private String name;
    private ArrayList<Card> cards;

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean addCard(Card card) {
        if (cards.size() >= 10) {
            System.out.println("Deck is full (max 10 unique cards).");
            return false;
        }
        for (Card c : cards) {
            if (c.getName().equalsIgnoreCase(card.getName())) {
                System.out.println("Only one copy of this card is allowed in a deck.");
                return false;
            }
        }
        cards.add(card);
        System.out.println(card.getName() + " added to deck.");
        return true;
    }

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

    public void viewDeck() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty.");
            return;
        }
        System.out.println("=== " + name + "'s Deck ===");
        Collections.sort(cards, Comparator.comparing(Card::getName));
        for (Card card : cards) {
            System.out.printf("%s (%s, %s) - $%.2f\n", card.getName(), card.getRarity(), card.getVariant(), card.getValue());
        }
    }
    public void viewCardDetails(String name) {
        for (Card card : cards) {
            if (card.getName().equalsIgnoreCase(name)) {
                System.out.printf("Name: %s\nRarity: %s\nVariant: %s\nValue: $%.2f\n", card.getName(), card.getRarity(), card.getVariant(), card.getValue());
                return;
            }
        }
        System.out.println("Card not found.");
    }

    public static void manageDecks(Scanner scanner, ArrayList<Deck> decks, Collection collection) {
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
            if (input.isEmpty()) continue;
            char choice = input.charAt(0);

            switch (choice) {
                case '1':
                    System.out.print("Enter deck name: ");
                    String deckName = scanner.nextLine();
                    decks.add(new Deck(deckName));
                    System.out.println("Deck created.");
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
                    for (Deck d : decks) {
                        System.out.println("- " + d.getName());
                        d.viewDeck();
                    }
                    break;
                case '4':
                    System.out.print("Enter deck name: ");
                    String targetDeck = scanner.nextLine();
                    Deck deck = findDeck(decks, targetDeck);
                    if (deck == null) {
                        System.out.println("Deck not found.");
                        break;
                    }
                    collection.displayCollection();
                    System.out.print("Enter card name to add: ");
                    String cardName = scanner.nextLine();
                    Card cardToAdd = Collection.findCardInCollection(collection, cardName);
                    if (cardToAdd != null && cardToAdd.getAmount() >= 1) {
                        if (deck.addCard(cardToAdd)) {
                            cardToAdd.setAmount(cardToAdd.getAmount() - 1);
                        }
                    } else {
                        System.out.println("Card not found or no copies left.");
                    }
                    break;
                case '5':
                    System.out.print("Enter deck name: ");
                    String dName = scanner.nextLine();
                    Deck d = findDeck(decks, dName);
                    if (d == null) {
                        System.out.println("Deck not found.");
                        break;
                    }
                    d.viewDeck();
                    System.out.print("Enter card name to remove: ");
                    String removeCard = scanner.nextLine();
                    if (d.removeCard(removeCard)) {
                        Card dummy = Collection.findCardInCollection(collection, removeCard);
                        if (dummy != null) {
                            dummy.setAmount(dummy.getAmount() + 1);
                        }
                    }
                    break;
                case '6':
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    public static Deck findDeck(ArrayList<Deck> decks, String name) {
        for (Deck d : decks) {
            if (d.getName().equalsIgnoreCase(name)){
                return d;
            }
        }
        return null;    
    }

    
}
