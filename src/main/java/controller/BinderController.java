package main.java.controller;

import main.java.model.classes.BinderModel;
import main.java.view.BinderView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * {@code BinderController} manages the interaction between a single {@link BinderModel}
 * and its corresponding {@link BinderView}.
 * <p>
 * It handles user interactions (e.g., mouse clicks) on the view and notifies a parent controller
 * through an {@link ActionListener} callback when the binder is selected.
 */
public class BinderController {

    /**
     * The data model representing a binder.
     */
    private final BinderModel binderModel;

    /**
     * The visual representation of the binder.
     */
    private final BinderView binderView;

    /**
     * The listener that is notified when the binder is selected.
     */
    private final ActionListener selectionListener;

    /**
     * Constructs a {@code BinderController} with the specified model, view, and selection listener.
     *
     * @param binderModel        the data model for the binder
     * @param binderView         the UI view for the binder
     * @param selectionListener  the listener to notify on selection events
     */
    public BinderController(BinderModel binderModel, BinderView binderView, ActionListener selectionListener) {
        this.binderModel = binderModel;
        this.binderView = binderView;
        this.selectionListener = selectionListener;
        initViewListeners();
    }

    /**
     * Initializes listeners for the view. Specifically, listens for mouse clicks to notify
     * the {@code selectionListener} that this binder was selected.
     */
    private void initViewListeners() {
        binderView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectionListener != null) {
                    selectionListener.actionPerformed(
                        new ActionEvent(binderModel, ActionEvent.ACTION_PERFORMED, binderModel.getName())
                    );
                }
            }
        });
    }

    /**
     * Returns the binder model associated with this controller.
     *
     * @return the binder model
     */
    public BinderModel getBinderModel() {
        return binderModel;
    }

    /**
     * Returns the binder view associated with this controller.
     *
     * @return the binder view
     */
    public BinderView getBinderView() {
        return binderView;
    }

    /**
     * Sets the selection state of the binder view.
     *
     * @param selected {@code true} to mark the view as selected; {@code false} otherwise
     */
    public void setSelected(boolean selected) {
        binderView.setSelected(selected);
    }
}
