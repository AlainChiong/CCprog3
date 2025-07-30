package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.model.classes.DeckModel;
import main.java.view.DeckView;

/**
 * {@code DeckController} manages the interaction between a single {@link DeckModel}
 * and its corresponding {@link DeckView}. It handles user input (e.g., clicks)
 * on the view and updates the view's visual state based on the model.
 * <p>
 * This controller also notifies a higher-level controller (via an {@link ActionListener})
 * when the deck view is selected by the user.
 */
public class DeckController {

    /**
     * The {@link DeckModel} instance managed by this controller.
     * It holds the data for a specific deck.
     */
    private final DeckModel deckModel;

    /**
     * The {@link DeckView} instance representing the deck visually.
     */
    private final DeckView deckView;

    /**
     * An {@link ActionListener} (typically from a parent controller like ManageDeckController)
     * that is notified when this deck view is clicked by the user.
     */
    private final ActionListener selectionListener;

    /**
     * Constructs a {@code DeckController}.
     * Initializes the controller with the given model, view, and selection listener.
     * Sets up mouse listeners for user interaction.
     *
     * @param deckModel          the deck model to manage
     * @param deckView           the view associated with the deck
     * @param selectionListener  the listener to notify upon selection
     */
    public DeckController(DeckModel deckModel, DeckView deckView, ActionListener selectionListener) {
        this.deckModel = deckModel;
        this.deckView = deckView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    /**
     * Initializes and registers mouse listeners on the {@code DeckView}.
     * When clicked, an {@link ActionEvent} is fired using the deck model as the source
     * and the deck's name as the action command.
     */
    private void initViewListeners() {
        deckView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectionListener != null) {
                    selectionListener.actionPerformed(
                        new ActionEvent(deckModel, ActionEvent.ACTION_PERFORMED, deckModel.getName())
                    );
                }
            }
        });
    }

    /**
     * Returns the {@link DeckModel} managed by this controller.
     *
     * @return the deck model
     */
    public DeckModel getDeckModel() {
        return deckModel;
    }

    /**
     * Returns the {@link DeckView} managed by this controller.
     *
     * @return the deck view
     */
    public DeckView getDeckView() {
        return deckView;
    }

    /**
     * Updates the selection state of the deck view.
     * When selected, the view may visually indicate selection (e.g., by a border).
     *
     * @param selected {@code true} to mark the view as selected; {@code false} otherwise
     */
    public void setSelected(boolean selected) {
        deckView.setSelected(selected);
    }
}