package main.java.view.collection_views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets; // Import for Insets

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel; // Import for SpinnerNumberModel

/**
 * The `ModifyCardAmountView` class provides a user interface for inputting a
 * change in the amount of a card. It typically appears as a dialog
 * and allows users to specify a positive or negative integer value to add or subtract
 * from a card's current quantity.
 */
public class ModifyCardAmountView extends JPanel {
    /**
     * Stores the negative of the current card amount, representing the minimum
     * possible value for the spinner (to ensure amount doesn't go below zero).
     */
    private double minimumChangeAllowed; // Renamed for clarity on its purpose
    /**
     * A `JSpinner` component allowing the user to select an integer value
     * for the amount to add or subtract. Its range is dynamically set
     * based on the current card amount to prevent negative total quantities.
     */
    private JSpinner spinner;

    /**
     * Constructs a new `ModifyCardAmountView`.
     * Initializes the spinner with a model that allows changes from `-currentAmount`
     * (to reduce to zero) up to a very high positive number, with an initial value of 0.
     *
     * @param currentAmount The current amount of the card being modified. This is used
     * to set the lower bound for the spinner's value.
     */
    public ModifyCardAmountView(double currentAmount) {
        // The minimum change allowed is negative of current amount to allow reducing to 0
        this.minimumChangeAllowed = -currentAmount;

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5); // Add some padding

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        JLabel label = new JLabel("Input the amount you want to add or subtract from the card.");
        add(label, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        // Set up SpinnerNumberModel: initial value, min, max, step
        // Max is set to a large arbitrary number for adding cards
        spinner = new JSpinner(new SpinnerNumberModel(0.0, minimumChangeAllowed, Double.POSITIVE_INFINITY, 1.0));
        add(spinner, gridBagConstraints); // Use gridBagConstraints for spinner too
    }

    /**
     * Retrieves the value currently selected in the spinner.
     * This value represents the intended change (addition or subtraction) to the card's amount.
     * The value returned is directly from the spinner model, which is already constrained.
     *
     * @return The `double` value representing the change in card amount.
     */
    public double getNewValue() {
        // The SpinnerNumberModel already enforces the minimum and maximum ranges.
        return ((Double) spinner.getValue()).doubleValue();
    }
}