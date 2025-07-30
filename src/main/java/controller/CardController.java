package main.java.controller;

import main.java.model.classes.CardModel;
import main.java.view.CardView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@code CardController} acts as the bridge between a single {@link CardModel} (data)
 * and its corresponding {@link CardView} (display).
 * <p>
 * It handles user interaction with the CardView, specifically detecting selection via mouse clicks,
 * and notifies the parent controller via an {@link ActionListener}.
 */
public class CardController {

    /**
     * The data model for the card.
     */
    private final CardModel cardModel;

    /**
     * The view component for the card.
     */
    private final CardView cardView;

    /**
     * Listener to notify when the card is selected.
     * Typically provided by the parent controller (e.g., ManageCollectionController).
     */
    private final ActionListener selectionListener;

    /**
     * Constructs a {@code CardController} to manage a card's model and view.
     *
     * @param cardModel          the model representing the card's data
     * @param cardView           the view representing the card visually
     * @param selectionListener  the listener to notify on card selection events
     */
    public CardController(CardModel cardModel, CardView cardView, ActionListener selectionListener) {
        this.cardModel = cardModel;
        this.cardView = cardView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    /**
     * Initializes mouse listeners on the {@code CardView}.
     * On mouse click, this notifies the {@code selectionListener} that the card was selected.
     */
    private void initViewListeners() {
        cardView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectionListener != null) {
                    selectionListener.actionPerformed(
                        new ActionEvent(cardModel, ActionEvent.ACTION_PERFORMED, cardModel.getName())
                    );
                }
            }
        });
    }

    /**
     * Returns the {@code CardModel} managed by this controller.
     *
     * @return the associated card model
     */
    public CardModel getCardModel() {
        return cardModel;
    }

    /**
     * Returns the {@code CardView} managed by this controller.
     *
     * @return the associated card view
     */
    public CardView getCardView() {
        return cardView;
    }

    /**
     * Updates the selection state of the card's view.
     * This affects how the card is displayed (e.g., highlighted when selected).
     *
     * @param selected {@code true} if the card is selected; {@code false} otherwise
     */
    public void setSelected(boolean selected) {
        cardView.setSelected(selected);
    }
}