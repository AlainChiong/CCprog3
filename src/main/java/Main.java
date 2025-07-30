package main.java;

import main.java.controller.MainController;

import javax.swing.SwingUtilities;
// lol
public class Main {

    public static void main(String args[]) {

        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    new MainController();
                }
            }
        );

        /*
        Scanner scanner = new Scanner(System.in);
        Collection collection = new Collection();
        ArrayList<Binder> binders = new ArrayList<>();
        ArrayList<Deck> decks = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Trading Card Inventory System ===");
            System.out.println("1 - Add Card");
            System.out.println("2 - Increase/Decrease Card Count");
            System.out.println("3 - Manage Binders");
            System.out.println("4 - Manage Decks");
            System.out.println("5 - Display Collection");
            System.out.println("6 - View Card Details");
            System.out.println("7 - Exit");

            System.out.print("Enter choice: ");
            String input = scanner.next();
            char choice = input.charAt(0);
            scanner.nextLine();

            switch (choice) {
                case '1':
                    Card newCard = Card.createCard(scanner);
                    collection.addCardC(newCard);
                    break;
                case '2':
                    collection.modifyCardCount(scanner);
                    break;
                case '3':
                    Binder.manageBinders(scanner, binders, collection);
                    break;
                case '4':
                    Deck.manageDecks(scanner, decks, collection);
                    break;
                case '5':
                    collection.displayCollection();
                    break;
                case '6':
                    collection.displayCollection();
                    System.out.print("Enter card name to view: ");
                    String search = scanner.nextLine();
                    collection.viewCardDetails(search);
                    break;
                case '7':
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    c
            }
        }
        scanner.close(); */
    }     
    
}