import java.util.Scanner;

import classes.*;

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Collection collection = new Collection();

        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Trading Card Inventory System!");
            System.out.println("1 - Add Card");
            System.out.println("2 - Increase/Decrease Card Count");
            System.out.println("3 - Create a new binder");
            System.out.println("4 - Create a new deck");

            String input = scanner.next();
            char choice = input.charAt(0);

            switch (choice) {
                case '1':
                    break;
                case '2':
                    if (collection.getCardCount() > 0) {
                        
                    }
                    break;
                case '3':
                    break;
                case '4':
                    break;
                default:
                    break;
            }
        }
    }
}