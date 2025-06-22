package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Collection {
    private ArrayList<Card> cards;

    public Collection() {
        cards = new ArrayList<>();
    }

    public int getCardCount() {
        return cards.size();
    }

    public void addCard(Card nCard) {
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

    public void displayCollection() {
        if (cards.isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }
        System.out.println("\nYour Collection:");
        Collections.sort(cards, Comparator.comparing(Card::getName));
        for (Card c : cards) {
            System.out.printf("%s x%.0f\n", c.getName(), c.getAmount());
        }
    }

    public void modifyCardCount(Scanner scanner) {
        if (cards.isEmpty()) {
            System.out.println("No cards to modify.");
            return;
        }

        System.out.println("Choose a card to modify:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.printf("%d - %s x%.0f\n", i + 1, cards.get(i).getName(), cards.get(i).getAmount());
        }

        int choice = scanner.nextInt() - 1;
        if (choice < 0 || choice >= cards.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Card selected = cards.get(choice);
        System.out.print("Enter amount to increase/decrease (+/-): ");
        double change = scanner.nextDouble();

        double newAmount = selected.getAmount() + change;
        if (newAmount < 0) {
            System.out.println("Amount cannot go below 0. Set to 0.");
            newAmount = 0;
        }
        selected.setAmount(newAmount);
        System.out.println("Updated amount: " + (int)newAmount);
    }

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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public static Card findCardInCollection(Collection collection, String name) {
        for (Card c : collection.getCards()) {
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }    
        }
        return null;
    }
}
