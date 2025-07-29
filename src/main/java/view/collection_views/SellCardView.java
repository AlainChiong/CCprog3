package main.java.view.collection_views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import main.java.model.classes.CardModel;

public class SellCardView extends JPanel{
    /*
     * 
     */
    JSpinner spinner;
    /*
     * 
     */
    CardModel card;
    public SellCardView(CardModel card) {
        this.card = card;
        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        gridBagConstraints.gridy = 0;
        JLabel label = new JLabel("How many of this do you want to sell?.");
        add(label, gridBagConstraints);


        gridBagConstraints.gridy = 1;
        spinner = new JSpinner();
        add(spinner);
    }

    public double getNewValue() {
        double value = ((Integer) spinner.getValue()).doubleValue();
        if (value > card.getAmount()) {
            value = card.getAmount();
        }
        if (value < 1) {
            value = 0;
        }
        return value;
    }
}
