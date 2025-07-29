package main.java.view.collection_views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets; // Import for Insets

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel; // Import for SpinnerNumberModel

import main.java.model.classes.CardModel;

/**
 * The `SellCardView` class provides a user interface for inputting the quantity
 * of a specific card a user wishes to sell. It typically appears as a dialog.
 * It ensures that the user can only select a positive amount up to the
 * currently available quantity of the card.
 */
public class SellCardView extends JPanel {
    /**
     * A `JSpinner` component allowing the user to select the quantity of cards to sell.
     * Its range is constrained from 1 up to the total amount of the card currently held.
     */
    private JSpinner spinner;
    /**
     * The `CardModel` instance representing the card being sold.
     * Used to determine the maximum sellable quantity.
     */
    private CardModel card;

    /**
     * Constructs a new `SellCardView`.
     * Initializes the spinner with a model that restricts selling quantity
     * from 1 up to the current amount of the `card` provided.
     *
     * @param card The {@link CardModel} representing the card the user intends to sell.
     */
    public SellCardView(CardModel card) {
        this.card = card;
        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5); // Add some padding

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        JLabel label = new JLabel("How many of this do you want to sell?");
        add(label, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        // Set up SpinnerNumberModel: initial value, min, max, step
        // Initial value defaults to 1, min is 1, max is the current card amount
        spinner = new JSpinner(new SpinnerNumberModel(1.0, 1.0, card.getAmount(), 1.0));
        add(spinner, gridBagConstraints); // Ensure gridBagConstraints is used
    }

    /**
     * Retrieves the quantity of cards to sell currently selected in the spinner.
     * The value returned is directly from the spinner model, which is already
     * constrained to be between 1 and the card's current amount (inclusive).
     * If the current amount is 0, the spinner model will likely constrain to 0 as max,
     * leading to 0 being returned.
     *
     * @return The `double` value representing the quantity of cards the user wishes to sell.
     */
    public double getNewValue() {
        // The SpinnerNumberModel already enforces the range (1 to card.getAmount()).
        // No additional clamping logic is needed here.
        return ((Double) spinner.getValue()).doubleValue();
    }
}