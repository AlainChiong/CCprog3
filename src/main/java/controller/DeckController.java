package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.model.classes.DeckModel;
import main.java.view.DeckView;

/**
 * The `DeckController` manages the interaction between a single {@link DeckModel}
 * and its corresponding {@link DeckView}. It handles user input (e.g., clicks)
 * on the `DeckView` and updates the view's visual state based on model changes.
 * This controller also notifies a higher-level controller (via an `ActionListener`)
 * when the deck view is selected by the user.
 */
public class DeckController {

    /**
     * The {@link DeckModel} instance managed by this controller.
     * It holds the data pertaining to a specific deck.
     */
    private DeckModel deckModel;
    /**
     * The {@link DeckView} instance managed by this controller.
     * It represents the graphical display of a specific deck.
     */
    private DeckView deckView;
    /**
     * An `ActionListener` that is typically the parent controller (e.g., `ManageDeckController`).
     * It is notified when this `DeckView` is clicked by the user, indicating selection.
     */
    private ActionListener selectionListener;

    /**
     * Constructs a `DeckController`.
     * Initializes the controller with a specific model, view, and a listener
     * to notify upon selection. It also sets up the necessary mouse listeners
     * on the `DeckView`.
     *
     * @param deckModel The {@link DeckModel} this controller manages.
     * @param deckView The {@link DeckView} this controller manages.
     * @param selectionListener An `ActionListener` (typically from `ManageDeckController`)
     * to notify when this deck's view is clicked for selection.
     */
    public DeckController(DeckModel deckModel, DeckView deckView, ActionListener selectionListener) {
        this.deckModel = deckModel;
        this.deckView = deckView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    /**
     * Initializes and adds mouse listeners to the `DeckView`.
     * This listener detects when the deck view component is clicked and
     * dispatches an `ActionEvent` to the `selectionListener`, using the
     * `DeckModel` as the source and its name as the command.
     */
    private void initViewListeners() {
        deckView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Notify the selection listener if it exists
                if (selectionListener != null) {
                    // Create an ActionEvent with the DeckModel as the source and its name as the command
                    selectionListener.actionPerformed(new ActionEvent(deckModel, ActionEvent.ACTION_PERFORMED, deckModel.getName()));
                }
            }
        });
    }

    /**
     * Returns the {@link DeckModel} instance managed by this controller.
     *
     * @return The {@link DeckModel} associated with this controller.
     */
    public DeckModel getDeckModel() {
        return deckModel;
    }

    /**
     * Returns the {@link DeckView} instance managed by this controller.
     *
     * @return The {@link DeckView} associated with this controller.
     */
    public DeckView getDeckView() {
        return deckView;
    }

    /**
     * Updates the selection state of the associated `DeckView`.
     * When `selected` is `true`, the view should visually indicate its selection
     * (e.g., by adding a border); otherwise, it should remove any selection indication.
     *
     * @param selected `true` if the deck view should be marked as selected; `false` otherwise.
     */
    public void setSelected(boolean selected) {
        deckView.setSelected(selected);
    }
}