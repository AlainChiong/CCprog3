// File: src/main/java/view/AddCardView.java
package main.java.view;

import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;
import main.java.utilities.ViewUtilities; 

import javax.swing.*;
import java.awt.*;

public class AddCardView extends JPanel {

    private JTextField cardNameField;
    private JComboBox<Rarity> rarityComboBox;
    private JComboBox<Variant> variantComboBox;
    private JTextField valueField;

    public AddCardView() {
        initializeComponents();
        layoutComponents();
    }

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
        setLayout(new GridBagLayout()); // Using GridBagLayout for flexible form layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Components fill their cell horizontally

        int row = 0;

        // Card Name
        gbc.gridx = 0;
        gbc.gridy = row;
        add(ViewUtilities.createStyledLabel("Card Name:"), gbc);
        gbc.gridx = 1;
        add(cardNameField, gbc);
        row++;

        // Rarity
        gbc.gridx = 0;
        gbc.gridy = row;
        add(ViewUtilities.createStyledLabel("Rarity:"), gbc);
        gbc.gridx = 1;
        add(rarityComboBox, gbc);
        row++;

        // Variant
        gbc.gridx = 0;
        gbc.gridy = row;
        add(ViewUtilities.createStyledLabel("Variant:"), gbc);
        gbc.gridx = 1;
        add(variantComboBox, gbc);
        row++;

        // Base Value
        gbc.gridx = 0;
        gbc.gridy = row;
        add(ViewUtilities.createStyledLabel("Base Value:"), gbc);
        gbc.gridx = 1;
        add(valueField, gbc);
        row++;


        //Adds some padding around the whole panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(60, 60, 90));
    }

    // --- Getters for input values ---

    public String getCardName() {
        return cardNameField.getText().trim();
    }

    public Rarity getRaritySelection() {
        return (Rarity) rarityComboBox.getSelectedItem();
    }

    public Variant getVariantSelection() {
        return (Variant) variantComboBox.getSelectedItem();
    }

    public String getValueText() {
        return valueField.getText().trim();
    }


    public void clearFields() {
        cardNameField.setText("");
        rarityComboBox.setSelectedIndex(0); // Select first rarity
        variantComboBox.setSelectedIndex(0); // Select first variant
        valueField.setText("");
    }
}