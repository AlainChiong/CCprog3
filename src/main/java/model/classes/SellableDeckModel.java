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
        this.type = "Sell";
    }
    /*
     * 
     */
    public void SellDeck(ArrayList<DeckModel> decks){

        double dValue = 0.0;

        for (CardModel card : this.getCards()) {
            dValue += card.getValue();
        }

        this.getCards().clear();

        decks.remove(this);

        System.out.printf("Deck \"%s\" has been sold for $%.2f. All cards are now gone.\n", this.getName(), dValue);
    }
}
