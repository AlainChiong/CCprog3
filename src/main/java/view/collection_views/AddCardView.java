package main.java.view.collection_views;

import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;
import main.java.utilities.ViewUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The `AddCardView` class represents a JPanel that serves as a dialog for users
 * to input details when adding a new card to their collection.
 * It provides fields for card name, rarity, variant, and base value.
 * The variant selection is dynamically enabled/disabled based on the chosen rarity.
 */
public class AddCardView extends JPanel {

    /**
     * Text field for entering the name of the card.
     */
    private JTextField cardNameField;
    /**
     * Combo box for selecting the rarity of the card from `Rarity` enum values.
     */
    private JComboBox<Rarity> rarityComboBox;
    /**
     * Combo box for selecting the variant of the card from `Variant` enum values.
     * This component's enabled state depends on the selected rarity.
     */
    private JComboBox<Variant> variantComboBox;
    /**
     * Text field for entering the base monetary value of the card.
     */
    private JTextField valueField;

    /**
     * Constructs a new `AddCardView`.
     * Initializes all UI components and arranges them using a `GridBagLayout`.
     * Also sets up initial styling and listeners.
     */
    public AddCardView() {
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initializes the Swing components (text fields, combo boxes) and applies basic styling.
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

    /**
     * Lays out the initialized components using `GridBagLayout` and sets the panel's background and border.
     * It also sets up the listener for rarity changes to update variant availability.
     */
    private void layoutComponents() {
        JLabel nameLabel = new JLabel("Card Name:");
        JLabel rarityLabel = new JLabel("Rarity:");
        JLabel variantLabel = new JLabel("Variant:");
        JLabel baseValueLabel = new JLabel("Base Value:");

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Card Name
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(nameLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(cardNameField, gridBagConstraints);
        row++;

        // Rarity
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(rarityLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(rarityComboBox, gridBagConstraints);
        row++;

        // Variant
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(variantLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(variantComboBox, gridBagConstraints);
        row++;

        // Base Value
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = row;
        add(baseValueLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(valueField, gridBagConstraints);
        row++;

        //Adds some padding around the whole panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(60, 60, 90));

        addRarityListener();
        updateVariantAvailability(); // Initial update based on default rarity
    }

    /**
     * Retrieves the text entered in the card name field.
     *
     * @return The trimmed string value of the card name.
     */
    public String getCardName() {
        return cardNameField.getText().trim();
    }

    /**
     * Retrieves the selected rarity from the rarity combo box.
     *
     * @return The `Rarity` enum value currently selected.
     */
    public Rarity getRaritySelection() {
        return (Rarity) rarityComboBox.getSelectedItem();
    }

    /**
     * Retrieves the selected variant from the variant combo box.
     *
     * @return The `Variant` enum value currently selected.
     */
    public Variant getVariantSelection() {
        return (Variant) variantComboBox.getSelectedItem();
    }

    /**
     * Retrieves the text entered in the base value field.
     *
     * @return The trimmed string value of the base value.
     */
    public String getValueText() {
        return valueField.getText().trim();
    }

    /**
     * Adds an `ItemListener` to the rarity combo box.
     * This listener calls `updateVariantAvailability()` whenever the selected rarity changes,
     * ensuring the variant combo box's state is consistent.
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
     * Updates the enabled state of the `variantComboBox` based on the currently selected rarity.
     * The variant combo box is only enabled if the selected rarity is {@link Rarity#RARE} or {@link Rarity#LEGENDARY}.
     * If variant selection is not allowed, it is disabled and its selected item is forced to {@link Variant#INVALID}.
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

    /**
     * Clears all input fields and resets combo boxes to their default (first) selection.
     */
    public void clearFields() {
        cardNameField.setText("");
        rarityComboBox.setSelectedIndex(0);
        variantComboBox.setSelectedIndex(0);
        valueField.setText("");
    }
}