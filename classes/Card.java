package classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Card {
    private String name;
    private String rarity; 
    private String variant; 
    private double value;
    private double amount;

    /**
     *
     * @param name
     * @param rarity
     * @param variant
     * @param value
     */
    public Card(String name, String rarity, String variant, double value) {
        this.name = name;
        this.rarity = rarity;
        this.variant = variant;
        this.value = value;
        this.amount = 1;
    }

    //A public static method called when creating a card
    //Ret
    public static Card createCard(Scanner scanner) {
        String name;
        String rarity = "";
        String variant = "";
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
            try {
                System.out.println("1 - Common");
                System.out.println("2 - Uncommon");
                System.out.println("3 - Rare");
                System.out.println("4 - Legendary");

                System.out.print("Choose a rarity (1-4): ");
                rarityInput = scanner.nextInt();

                if (rarityInput >= 1 && rarityInput <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between 1 and 4.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a whole number.\n");
                scanner.nextLine(); 
            }
        }
        switch (rarityInput) {
            case 1: 
                rarity = "Common";
                break;
            case 2: 
                rarity = "Uncommon";
                break;
            case 3: 
                rarity = "Rare";
                break;
            case 4: 
                rarity = "Legendary";
                break;
            default:
                break;
        }
        if (rarityInput == 3 || rarityInput == 4) {
            System.out.println("1 - Normal");
            System.out.println("2 - Extended-art");
            System.out.println("3 - Full-art");
            System.out.println("4 - Alt-art");
            System.out.print("Choose a variant (1-4): ");
            variantInput = scanner.nextInt();

            switch (variantInput) {
                case 1: 
                    variant = "Normal";
                    break;
                case 2:
                    variant = "Extended-art";
                    value = value * 1.5;
                    break;
                case 3:
                    variant = "Full-art";
                    value = value * 2;
                    break;
                case 4:
                    variant = "Alt-art";
                    value = value * 3;
                    break;
                default:
                break;
            }
        }
        return new Card(name, rarity, variant, value);
    }

    public boolean matches(Card other) {
    return this.name.equalsIgnoreCase(other.name) && this.rarity.equalsIgnoreCase(other.rarity) && this.variant.equalsIgnoreCase(other.variant);
    }

    //Setters and Getters For Name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    //Setters and Getters For Rarity
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getRarity() {
        return this.rarity;
    }

    //Setters and Getters For Variant
    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return this.variant;
    }

    //Setters and Getters For Value
    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    //Setters and Getters For Amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }
    
}