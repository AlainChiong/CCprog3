// File: src/main/java/view/AddCardView.java
package main.java.view.collection_views;

import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;
import main.java.utilities.ViewUtilities; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddCardView extends JPanel {

    /*
     * 
     */
    private JTextField cardNameField;
    /*
     * 
     */
    private JComboBox<Rarity> rarityComboBox;
    /*
     * 
     */
    private JComboBox<Variant> variantComboBox;
    /*
     * 
     */
    private JTextField valueField;

    /*
     * 
     */
    public AddCardView() {
        initializeComponents();
        layoutComponents();
    }

    /*
     * 
     */
    private void initializeComponents() {
        cardNameField = new JTextField(20);
        rarityComboBox = new JComboBox<>(Rarity.values());
        variantComboBox = new JComboBox<>(Variant.values());
        valueField = new JTextField(10);

        ViewUtilities.styleTextField(cardNameField);
        ViewUtilities.styleComboBox(rarityComboBox);
        ViewUtilities.styleComboBox(variantComboBox);
        ViewUtilities.styleTextField(valueField);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Card Name
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(ViewUtilities.createStyledLabel("Card Name:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(cardNameField, gridBagConstraints);
        row++;

        // Rarity
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(ViewUtilities.createStyledLabel("Rarity:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(rarityComboBox, gridBagConstraints);
        row++;

        // Variant
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(ViewUtilities.createStyledLabel("Variant:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(variantComboBox, gridBagConstraints);
        row++;

        // Base Value
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(ViewUtilities.createStyledLabel("Base Value:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(valueField, gridBagConstraints);
        row++;


        //Adds some padding around the whole panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(60, 60, 90));

        addRarityListener();
        updateVariantAvailability();
    }

    /*
     * 
     */
    public String getCardName() {
        return cardNameField.getText().trim();
    }

    /*
     * 
     */
    public Rarity getRaritySelection() {
        return (Rarity) rarityComboBox.getSelectedItem();
    }

    /*
     * 
     */
    public Variant getVariantSelection() {
        return (Variant) variantComboBox.getSelectedItem();
    }

    /*
     * 
     */
    public String getValueText() {
        return valueField.getText().trim();
    }

    /*
     * 
     */
    private void addRarityListener() {
        // Listener for Rarity ComboBox to control Variant ComboBox
        rarityComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateVariantAvailability();
                }
            }
        });
    }

    /**
     * Updates the enabled state of the variantComboBox based on the selected rarity.
     * Only RARE and LEGENDARY rarities allow a specific variant.
     */
    private void updateVariantAvailability() {
        Rarity selectedRarity = (Rarity) rarityComboBox.getSelectedItem();
        boolean variantAllowed = (selectedRarity == Rarity.RARE || selectedRarity == Rarity.LEGENDARY);

        variantComboBox.setEnabled(variantAllowed);
        if (!variantAllowed) {
            // If variant is not allowed, force selection to INVALID
            variantComboBox.setSelectedItem(Variant.INVALID); 
        }
    }

    /*
     * 
     */
    public void clearFields() {
        cardNameField.setText("");
        rarityComboBox.setSelectedIndex(0);
        variantComboBox.setSelectedIndex(0);
        valueField.setText("");
    }
}