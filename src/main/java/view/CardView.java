package main.java.view;

import main.java.model.classes.CardModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * CardView is a JPanel subclass representing the visual display of a single trading card.
 * It shows the card's name, rarity, variant, amount, and value, and provides visual feedback
 * only for its selection state.
 */
public class CardView extends JPanel {

    private static final Dimension CARD_SIZE = new Dimension(180, 250);

    // Borders for different states
    private final Border defaultBorder;
    private final Border selectedBorder;

    private boolean isSelected = false; 

    /**
     * Constructs a CardView for a given CardModel.
     *
     * @param cardModel The CardModel whose data will be displayed by this view.
     */
    public CardView(CardModel cardModel) {
        // --- Panel Setup ---
        setPreferredSize(CARD_SIZE);
        setMinimumSize(CARD_SIZE);
        setMaximumSize(CARD_SIZE);
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(60, 60, 90));

        // --- Border Definitions ---
        defaultBorder = BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(80, 80, 100), 2),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        );
        selectedBorder = BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(30, 144, 255), 3), // Dodger Blue
                            BorderFactory.createEmptyBorder(4, 4, 4, 4) // Adjust padding for thicker border
                        );

        setBorder(defaultBorder); // Set initial border

        // --- Card Details (Labels) ---
        // Name Label (Top)
        JLabel nameLabel = new JLabel("<html><center>" + cardModel.getName() + "</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(true); // Make it opaque to paint its background
        nameLabel.setBackground(new Color(70, 70, 100)); // Darker bar for name
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding
        add(nameLabel, BorderLayout.NORTH);

        // Details Panel (Center)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1, 0, 5));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding

        // Rarity
        JLabel rarityLabel = new JLabel("Rarity: " + cardModel.getRarity().toString());
        styleDetailLabel(rarityLabel);
        detailsPanel.add(rarityLabel);

        // Variant
        JLabel variantLabel = new JLabel("Variant: " + cardModel.getVariant().toString());
        styleDetailLabel(variantLabel);
        detailsPanel.add(variantLabel);

        // Amount
        JLabel amountLabel = new JLabel("Amount: " + (int) cardModel.getAmount());
        styleDetailLabel(amountLabel);
        detailsPanel.add(amountLabel);

        // Value (Formatted as currency)
        JLabel valueLabel = new JLabel(String.format("Value: $%.2f", cardModel.getValue()));
        styleDetailLabel(valueLabel);
        detailsPanel.add(valueLabel);

        add(detailsPanel, BorderLayout.CENTER); // Add details to the center

        // Set up the current status based on selection (for initial display)
        setSelected(false); // Default to not selected
    }

    /**
     * Applies common styling to detail labels within the card view.
     * @param label The JLabel to style.
     */
    private void styleDetailLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.LIGHT_GRAY);
    }

    /**
     * Sets the selection state of this CardView.
     * Changes the border to indicate if the card is selected or not.
     *
     * @param selected True to mark as selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            setBorder(selectedBorder);
        } else {
            setBorder(defaultBorder);
        }
        repaint(); 
    }

}