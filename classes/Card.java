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
        String rarity;
        String variant;
        double value;

        int rarityInput;

        name = scanner.nextLine();

        try {

            System.out.println("1 - Common");
            System.out.println("2 - Uncommon");
            System.out.println("3 - Rare");
            System.out.println("4 - Legendary");
            rarityInput = scanner.nextInt();
        } catch (InputMismatchException e) {
            // TODO: handle exception
        }

        switch (rarityInput) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        
            default:
                break;
        }

        if (rarityInput == 3 || rarityInput == 4) {
            
        }

        return new Card(name, rarity, variant, value);
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