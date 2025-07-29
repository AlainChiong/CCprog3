package main.java.view.collection_views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/*
 * 
 */
public class ModifyCardAmountView extends JPanel{
    /*
     * 
     */
    double minimum;
    /*
     * 
     */
    JSpinner spinner;

/*
 * 
 */
    public ModifyCardAmountView(double minimum) {
        this.minimum = -minimum;
        
        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        gridBagConstraints.gridy = 0;
        JLabel label = new JLabel("Input the amount you want to add or subtract from the card.");
        add(label, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        spinner = new JSpinner();
        add(spinner);
    }

    public double getNewValue() {
        double value = ((Integer) spinner.getValue()).doubleValue();
        if (value < minimum) {
            value = minimum;
        }
        return value;
    }
}
