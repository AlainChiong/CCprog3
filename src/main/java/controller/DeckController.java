package main.java.controller;

import main.java.model.classes.DeckModel;
import main.java.view.DeckView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeckController {

    private DeckModel deckModel;
    private DeckView deckView;
    private ActionListener selectionListener;

    /**
     * Constructs a DeckController.
     *
     * @param deckModel The DeckModel this controller manages.
     * @param deckView The DeckView this controller manages.
     * @param selectionListener An ActionListener (typically from ManageDeckController)
     *                          to notify when this deck is clicked for selection.
     */
    public DeckController(DeckModel deckModel, DeckView deckView, ActionListener selectionListener) {
        this.deckModel = deckModel;
        this.deckView = deckView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    /**
     * Adds mouse listener to detect when the deck is clicked.
     */
    private void initViewListeners() {
        deckView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectionListener != null) {
                    selectionListener.actionPerformed(new ActionEvent(deckModel, ActionEvent.ACTION_PERFORMED, deckModel.getName()));
                }
            }
        });
    }

    /**
     * Returns the DeckModel managed by this controller.
     * @return the DeckModel
     */
    public DeckModel getDeckModel() {
        return deckModel;
    }

    /**
     * Returns the DeckView managed by this controller.
     * @return the DeckView
     */
    public DeckView getDeckView() {
        return deckView;
    }

    /**
     * Updates the selection state visually.
     * @param selected true if selected, false otherwise
     */
    public void setSelected(boolean selected) {
        deckView.setSelected(selected);
    }
}
