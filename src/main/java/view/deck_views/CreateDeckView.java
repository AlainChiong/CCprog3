package main.java.view.deck_views;

import javax.swing.*;
import java.awt.*;

import main.java.model.classes.DeckModel;
import main.java.model.classes.SellableDeckModel;
import main.java.utilities.ViewUtilities;

/**
 * The `CreateDeckView` class provides a panel used in dialogs for creating new decks.
 * It allows the user to input a name and choose a type (Normal or Sellable).
 */
public class CreateDeckView extends JPanel {

    /**
     * Text field for entering the name of the deck.
     */
    private JTextField deckNameField;

    /**
     * Combo box for selecting the deck type ("Normal" or "Sellable").
     */
    private JComboBox<String> deckTypeComboBox;

    /**
     * Constructs the CreateDeckView and lays out all components.
     */
    public CreateDeckView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        deckNameField = new JTextField(20);
        deckTypeComboBox = new JComboBox<>(new String[]{"Normal", "Sellable"});

        ViewUtilities.styleTextField(deckNameField);
        ViewUtilities.styleComboBox(deckTypeComboBox);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Deck Name:");
        JLabel typeLabel = new JLabel("Deck Type:");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        add(deckNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(typeLabel, gbc);

        gbc.gridx = 1;
        add(deckTypeComboBox, gbc);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(60, 60, 90));
    }

    /**
     * @return the trimmed name entered for the new deck.
     */
    public String getDeckName() {
        return deckNameField.getText().trim();
    }

    /**
     * @return the selected deck type as a string: "Normal" or "Sellable".
     */
    public DeckModel getDeckType() {
        String type = (String) deckTypeComboBox.getSelectedItem();
        if (type == "Sellable") {
            return new SellableDeckModel(getDeckName());
        }
        else {
            return new DeckModel(getDeckName());
        }
    }

    /**
     * Clears the fields for reuse.
     */
    public void clearFields() {
        deckNameField.setText("");
        deckTypeComboBox.setSelectedIndex(0);
    }
}
