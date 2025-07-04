package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


/**
 * The `Binder` class represents a physical or digital binder used to store a collection of {@link Card} objects.
 * A binder has a name and a fixed capacity (maximum of 20 cards). Unlike a {@link Deck}, a binder
 * does not enforce uniqueness of card names; multiple copies of the same card (by name) can be added,
 * but only up to the maximum capacity. It also provides functionality for trading cards with other binders.
 */
public class Binder {

   /**
     * The name of the binder.
     */
    private String name;

    /**
     * An `ArrayList` to store the {@link Card} objects contained within this binder.
     */
    private ArrayList<Card> cards;

    /**
     * Constructs a new `Binder` with a specified name and initializes an empty list of cards.
     *
     * @param name The name of the binder.
     */
    public Binder(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    /**
     * Returns the name of the binder.
     *
     * @return The current name of the binder.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the `ArrayList` containing all {@link Card} objects in this binder.
     *
     * @return An `ArrayList` of cards currently in the binder.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Calculates and returns the total number of physical cards in the binder.
     * This sum includes the amounts of all distinct card types present.
     * For example, if a binder has "Goblin x5" and "Dragon x3", this method returns 8.
     *
     * @return The total count of all cards in the binder.
     */
    public int getTotalCardCount() {
        int sum = 0;
        for (Card c : cards) {
            sum += c.getAmount();
        }
        return sum;
    }

    /**
     * Attempts to add a {@link Card} to the binder.
     * The card is added based on its name, rarity, and variant. If an identical card
     * (matching name, rarity, and variant) already exists, its amount is incremented.
     * Otherwise, the card is added as a new distinct entry with an amount of 1.
     * The total number of cards in the binder cannot exceed 20.
     *
     * @param card The {@link Card} object to be added. Its {@link Card#getAmount() amount} is ignored;
     * it will be added as 1 if new, or increment an existing card's count by 1.
     * @return `true` if the card was successfully added or its amount was increased, `false` otherwise (binder is full).
     */
    public boolean addCardB(Card card) {
        int total = getTotalCardCount();
        if (total >= 20) {
            System.out.println("Binder is full (max 20 total cards).");
            return false;
        }
        for (Card c : cards) {
            if (c.matches(card)) { 
                int availableSpace = 20 - total;
                if (availableSpace <= 0) {
                    System.out.println("Cannot add more cards. Binder is full.");
                    return false;
                }

                c.setAmount(c.getAmount() + 1);
                System.out.println(card.getName() + " amount increased in binder.");
                return true;
            }
        }
        Card newCard = new Card(card.getName(), card.getRarity(), card.getVariant(), card.getValue());
        newCard.setAmount(1);
        cards.add(newCard);
        System.out.println(newCard.getName() + " added to binder.");
        return true;
    }

    /**
     * Attempts to remove one instance of a {@link Card} from the binder by its name.
     * If the card's amount is greater than 1, its amount is decremented.
     * If the card's amount is 1, the card entry is entirely removed from the binder.
     * The first card found with a matching name (case-insensitive) will be processed.
     *
     * @param name The name of the card to be removed or have its amount decremented.
     * @return `true` if the card was found and its amount was decremented or it was removed, `false` if the card was not found in the binder.
     */
    public boolean removeCard(String name) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equalsIgnoreCase(name)) {
                Card card = cards.get(i);
                if (card.getAmount() > 1) {
                    card.setAmount(card.getAmount() - 1);
                    System.out.println("Decreased amount of " + card.getName() + " in binder.");
                } else {
                    cards.remove(i);
                    System.out.println("Card removed from binder.");
                }
                return true;
            }
        }
        System.out.println("Card not found.");
        return false;
    }

   /**
     * Displays all cards currently in this binder to the console.
     * Cards are sorted alphabetically by name for consistent display.
     * Includes the card's name, its amount, rarity, variant, and value.
     * If the binder is empty, a corresponding message is printed.
     */
    public void viewBinder() {
        if (cards.isEmpty()) {
            System.out.println("Binder is empty.");
            return;
        }
        System.out.println("=== " + name + "'s Binder ===");
        Collections.sort(cards, Comparator.comparing(Card::getName));
        for (Card card : cards) {
            System.out.printf("%s x%.0f (%s, %s) - $%.2f\n",
                card.getName(), card.getAmount(), card.getRarity(), card.getVariant(), card.getValue());
        }
    }

    /**
     * Facilitates a trade operation within the application. This method allows the user
     * to trade a card from *this* binder for a *new* card created by the user during the trade.
     * It prompts the user to select a card from the current binder, then guides them through
     * creating a new card they wish to receive. A value difference is calculated and displayed,
     * and the user is prompted for confirmation if the difference is significant.
     * The selected card is removed from this binder and the new card is added to the main {@link Collection}
     * and then to this binder (after reducing its count in the collection).
     *
     * @param scanner   The `Scanner` object used to read user input for card selection and creation.
     * @param collection The main {@link Collection} of cards, used to manage cards being traded into/out of.
     */
    public void tradeWith(Scanner scanner, Collection collection) {
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
            }
        }
        if (myCard == null) {
            System.out.println("Card not found in this binder.");
            return;
        }
        
        System.out.println("Now, input the card you will receive in return.");
         Card newCard = Card.createCard(scanner);  // You create a new card directly

        double valueDiff = Math.abs(myCard.getValue() - newCard.getValue());
        if (valueDiff > 1.0) {
            System.out.printf("The value difference is $%.2f. Proceed with trade? (Yes/No): ", valueDiff);
            scanner.nextLine();
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("yes")) {
                System.out.println("Trade cancelled.");
                return;
            }
        }
        this.removeCard(myCard.getName());
        boolean stillInBinder = false;
        for (Card c : this.cards) {
            if (c.getName().equalsIgnoreCase(myCard.getName())) {
                stillInBinder = true;
            }
        }
        if(!stillInBinder){
            collection.removeCardByName(myCard.getName());
        }
        collection.addCardC(newCard);

        Card newCardC = Collection.findCardInCollection(collection, newCard.getName());
        newCardC.setAmount(newCardC.getAmount()-1);
        this.addCardB(newCardC);
    }

    /**
     * Provides a static menu-driven interface for managing a list of `Binder` objects.
     * This method allows users to create, delete, view, add cards to, remove cards from,
     * and initiate trades. It interacts with a {@link Collection} object
     * to retrieve cards for adding to binders and to return cards removed from binders.
     *
     * @param scanner A `Scanner` object used to read user input for menu choices and details.
     * @param binders An `ArrayList` representing the list of all binders being managed.
     * @param collection The main {@link Collection} of cards from which cards can be added to binders,
     * and to which cards are returned upon removal or moved during trades.
     */
    public static void manageBinders(Scanner scanner, ArrayList<Binder> binders, Collection collection) {
        while (true) {
            System.out.println("\n=== Binder Menu ===");
            System.out.println("1 - Create New Binder");
            System.out.println("2 - Delete Binder");
            System.out.println("3 - View Binders");
            System.out.println("4 - Add Card to Binder");
            System.out.println("5 - Remove Card from Binder");
            System.out.println("6 - Trade Cards");
            System.out.println("7 - Go Back");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine();
            System.out.print("\n");
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
                        binder.addCardB(cardToAdd);
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
                    System.out.print("Enter your binder name (trading from): ");
                    String fName = scanner.nextLine();
                    Binder fBinder = findBinder(binders, fName);

                    if (fBinder == null) {
                        System.out.println("Binder not found.");
                        break;
                    }
                    
                    fBinder.tradeWith(scanner, collection);
                    scanner.nextLine();
                    break;
                case '7':
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    /**
     * Static helper method to find a {@link Binder} within a list of binders by its name.
     * The search is case-insensitive.
     *
     * @param binders An `ArrayList` of {@link Binder} objects to search through.
     * @param name    The name of the binder to find.
     * @return The {@link Binder} object if found, otherwise `null`.
     */
    public static Binder findBinder(ArrayList<Binder> binders, String name) {
        for (Binder b : binders) {
            if (b.getName().equalsIgnoreCase(name)) return b;
        }
        return null;
    }
}
