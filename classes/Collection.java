package classes;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> Cards;
    private int cardCount;

    public Collection() {
        cardCount = 0;
    }

    public int getCardCount() {
        return cardCount;
    }
}
