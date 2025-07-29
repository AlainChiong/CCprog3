package main.java.model.classes;

import java.util.ArrayList;

/*
 * 
 */
public class SellableDeckModel extends DeckModel {
    
    /*
     * 
     */
    public SellableDeckModel(String name){
        super(name);
        this.typeName = "Sellable Deck";
        this.type = "sell";
    }
    /*
     * 
     */
    public void SellDeck(CollectionModel collection){

        if (cards.isEmpty()) {
            System.out.println("Sellable deck is empty. Nothing to sell.");
            return;
        }

        double dValue = 0.0;
        ArrayList<CardModel> cardsCopy = new ArrayList<>(cards);

        for (CardModel deckCard : cardsCopy) {
            dValue += deckCard.getValue();

            this.removeCard(deckCard.getName());
            collection.removeCardByName(deckCard.getName());
        }
        System.out.printf("All cards in deck \"%s\" sold. Total value: $%.2f%n", this.getName(), dValue);
    }

    @Override
    public boolean isSellable() {
        return true;
    }
}
