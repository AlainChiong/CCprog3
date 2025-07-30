package main.java.model.classes;

import java.util.ArrayList;

/**
 * The `SellableDeckModel` class extends {@link DeckModel} to represent a special type of deck
 * that can be sold for its combined card value. When a sellable deck is sold,
 * its cards are permanently removed from the game (both from the deck and potentially
 * from the main {@link CollectionModel} if they were the last copies).
 */
public class SellableDeckModel extends DeckModel {
    
    /**
     * Constructs a new `SellableDeckModel` with the specified name.
     * Initializes the deck using the constructor of the superclass {@link DeckModel}.
     *
     * @param name The name of the sellable deck.
     */
    public SellableDeckModel(String name){
        super(name);
    }
    /**
     * Sells all cards within this sellable deck, permanently removing them from the game
     * and calculating their total value. The total value is then reported to the console.
     * This method iterates through a copy of the deck's cards to avoid `ConcurrentModificationException`
     * during removal. Each card's value is added to a running total, and then the card is
     * removed from both the deck itself and, if applicable, from the main {@link CollectionModel}
     * (e.g., if it was the last copy in the player's overall inventory).
     *
     * @param collection The main {@link CollectionModel} of the player, which may need to be updated
     * if cards from this deck were also present in the main collection and are now being sold.
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

    /**
     * Overrides the {@link DeckModel#isSellable()} method to indicate that this
     * type of deck is indeed sellable.
     *
     * @return `true`, indicating that this deck can be sold.
     */
    @Override
    public boolean isSellable() {
        return true;
    }

    /**
     * Overrides the {@link DeckModel#getTypeName()} method to provide a specific
     * human-readable name for this type of deck.
     *
     * @return The string "Sellable Deck".
     */
    @Override
    public String getTypeName() {
        return "Sellable Deck";
    }

    /**
     * Overrides the {@link DeckModel#getType()} method to provide a specific
     * string identifier for this type of deck, typically for internal logic or comparison.
     *
     * @return The string "sellable".
     */
    @Override
    public String getType() {
        return "sellable";
    }   
}
