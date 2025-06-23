package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Binder {
    private String name;
    private ArrayList<Card> cards;


    public Binder(String name) {
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
        if (cards.size() >= 20) {
            System.out.println("Binder is full (max 20 cards).");
            return false;
        }
        cards.add(card);
        System.out.println(card.getName() + " added to binder.");
        return true;
    }

    public boolean removeCard(String name) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equalsIgnoreCase(name)) {
                cards.remove(i);
                System.out.println("Card removed from binder.");
                return true;
            }
        }
        System.out.println("Card not found.");
        return false;
    }

    public void viewBinder() {
        if (cards.isEmpty()) {
            System.out.println("Binder is empty.");
            return;
        }
        System.out.println("=== " + name + "'s Binder ===");
        Collections.sort(cards, Comparator.comparing(Card::getName));
        for (Card card : cards) {
            System.out.printf("%s (%s, %s) - $%.2f\n",
                card.getName(), card.getRarity(), card.getVariant(), card.getValue());
        }
    }

    public void tradeWith(Binder otherBinder, Scanner scanner) {
        if (this == otherBinder) {
            System.out.println("You cannot trade with the same binder.");
            return;
        }
        if (this.cards.isEmpty()) {
            System.out.println("Your binder has no cards to trade.");
            return;
        }
        this.viewBinder();
        System.out.print("Enter the name of the card to trade from " + this.name + ": ");
        String cardName = scanner.nextLine();
        Card myCard = null;
        for (Card c : this.cards) {
            if (c.getName().equalsIgnoreCase(cardName)) {
                myCard = c;
                break;
            }
        }
        if (myCard == null) {
            System.out.println("Card not found in this binder.");
            return;
        }
        if (otherBinder.cards.size() >= 20) {
            System.out.println("Trade failed. The other binder is full.");
            return;
        }

        System.out.println("\n--- " + otherBinder.name + "'s Cards ---");
        otherBinder.viewBinder();
        System.out.print("Enter the name of the card to receive: ");
        String theirCardName = scanner.nextLine();

        Card theirCard = null;
        for (Card c : otherBinder.cards) {
            if (c.getName().equalsIgnoreCase(theirCardName)) {
                theirCard = c;
                break;
            }
        }

        if (theirCard == null) {
            System.out.println("Card not found in the other binder.");
            return;
        }
        this.cards.remove(myCard);
        otherBinder.cards.remove(theirCard);

        this.cards.add(theirCard);
        otherBinder.cards.add(myCard);
        System.out.println("Successfully traded " + myCard.getName() + " to " + theirCard.getName() + ".");
    }

    public static void manageBinders(Scanner scanner, ArrayList<Binder> binders, Collection collection) {
        while (true) {
            System.out.println("\n=== Binder Menu ===");
            System.out.println("1 - Create New Binder");
            System.out.println("2 - Delete Binder");
            System.out.println("3 - View Binders");
            System.out.println("4 - Add Card to Binder");
            System.out.println("5 - Remove Card from Binder");
            System.out.println("6 - Trade Cards Between Binders");
            System.out.println("7 - Go Back");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine();
            if (input.isEmpty()) continue;
            char choice = input.charAt(0);

            switch (choice) {
                case '1':
                    System.out.print("Enter binder name: ");
                    String binderName = scanner.nextLine();
                    binders.add(new Binder(binderName));
                    System.out.println("Binder created.");
                    break;
                case '2':
                    System.out.print("Enter binder name to delete: ");
                    String dBinder = scanner.nextLine();
                    Binder tDelete = findBinder(binders, dBinder);
                    if (tDelete != null) {
                        binders.remove(tDelete);
                        System.out.println("Binder \"" + dBinder + "\" deleted.");
                    } else {
                        System.out.println("Binder not found.");
                    }
                    break;
                case '3':
                    if (binders.isEmpty()) {
                        System.out.println("No binders yet.");
                        break;
                    }
                    for (Binder b : binders) {
                        System.out.println("- " + b.getName());
                        b.viewBinder();
                    }
                    break;
                case '4':
                    if (binders.isEmpty()) {
                        System.out.println("No binders yet.");
                        break;
                    }
                    System.out.print("Enter binder name: ");
                    String targetBinder = scanner.nextLine();
                    Binder binder = findBinder(binders, targetBinder);
                    if (binder == null) {
                        System.out.println("Binder not found.");
                        break;
                    }
                    collection.displayCollection();
                    System.out.print("Enter card name to add: ");
                    String cardName = scanner.nextLine();
                    Card cardToAdd = Collection.findCardInCollection(collection, cardName);
                    if (cardToAdd != null && cardToAdd.getAmount() >= 1) {
                        binder.addCard(cardToAdd);
                        cardToAdd.setAmount(cardToAdd.getAmount() - 1);
                    } else {
                        System.out.println("Card not found or no copies left.");
                    }
                    break;
                case '5':
                    System.out.print("Enter binder name: ");
                    String bName = scanner.nextLine();
                    Binder b = findBinder(binders, bName);
                    if (b == null) {
                        System.out.println("Binder not found.");
                        break;
                    }
                    b.viewBinder();
                    System.out.print("Enter card name to remove: ");
                    String removeCard = scanner.nextLine();
                    if (b.removeCard(removeCard)) {
                        Card dummy = Collection.findCardInCollection(collection, removeCard);
                        if (dummy != null) {
                            dummy.setAmount(dummy.getAmount() + 1);
                        }
                    }
                    break;
                case '6':
                    if (binders.size() < 2) {
                        System.out.println("Need at least 2 binders to trade.");
                        break;
                    }

                    System.out.print("Enter your binder name (trading from): ");
                    String fName = scanner.nextLine();
                    Binder fBinder = findBinder(binders, fName);

                    if (fBinder == null) {
                        System.out.println("Binder not found.");
                        break;
                    }

                    System.out.print("Enter the binder to trade with: ");
                    String tName = scanner.nextLine();
                    Binder tBinder = findBinder(binders, tName);

                    if (tBinder == null) {
                        System.out.println("Other binder not found.");
                        break;
                    }

                    fBinder.tradeWith(tBinder, scanner);
                    break;
                case '7':
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    public static Binder findBinder(ArrayList<Binder> binders, String name) {
        for (Binder b : binders) {
            if (b.getName().equalsIgnoreCase(name)) return b;
        }
        return null;
    }
}
