// File: src/main/java/controller/CardController.java
package main.java.controller;

import main.java.model.classes.CardModel;
import main.java.view.CardView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * CardController acts as the bridge between a single CardModel (data) and its CardView (display).
 * It handles user interaction with the CardView, specifically card selection,
 * and updates the CardView's display based on the CardModel's state or selection status.
 */
public class CardController {

    private CardModel cardModel; // Renamed from 'model'
    private CardView cardView;   // Renamed from 'view'
    private ActionListener selectionListener; // Listener from ManageCollectionController for card selection

    /**
     * Constructs a CardController.
     *
     * @param cardModel The CardModel this controller manages.
     * @param cardView The CardView this controller manages.
     * @param selectionListener An ActionListener (typically from ManageCollectionController)
     * to notify when this card is clicked for selection.
     */
    public CardController(CardModel cardModel, CardView cardView, ActionListener selectionListener) {
        this.cardModel = cardModel;
        this.cardView = cardView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    /**
     * Initializes mouse listeners on the CardView to detect clicks for selection.
     */
    private void initViewListeners() {
        cardView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectionListener != null) {
                    selectionListener.actionPerformed(new ActionEvent(cardModel, ActionEvent.ACTION_PERFORMED, cardModel.getName()));
                }
            }
        });
    }

    /**
     * Retrieves the CardModel managed by this controller.
     * @return The CardModel.
     */
    public CardModel getCardModel() { 
        return cardModel;
    }

    /**
     * Retrieves the CardView managed by this controller.
     * @return The CardView.
     */
    public CardView getCardView() { 
        return cardView;
    }

    /**
     * Sets the selection state of the card.
     * This method is typically called by the ManageCollectionController.
     * It updates the visual representation of the CardView (e.g., changes border).
     *
     * @param selected True if the card should be marked as selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        cardView.setSelected(selected);
    }
}