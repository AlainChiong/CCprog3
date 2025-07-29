package main.java.view;

import main.java.model.classes.DeckModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * DeckView is a JPanel subclass representing the visual display of a single deck.
 * It shows the deck's name, type, number of cards, and total value,
 * and provides visual feedback for its selection state.
 */
public class DeckView extends JPanel {

    private static final Dimension DECK_SIZE = new Dimension(200, 150);

    private final Border defaultBorder;
    private final Border selectedBorder;

    private boolean isSelected = false;

    /**
     * Constructs a DeckView for the given DeckModel.
     * 
     * @param deckModel The deck model containing the deck data.
     */
    public DeckView(DeckModel deckModel) {
        setPreferredSize(DECK_SIZE);
        setMinimumSize(DECK_SIZE);
        setMaximumSize(DECK_SIZE);
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(50, 50, 80));

        // --- Borders ---
        defaultBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        selectedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 165, 0), 3), // Orange border
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        );
        setBorder(defaultBorder);

        // --- Deck Name ---
        JLabel nameLabel = new JLabel("<html><center>" + deckModel.getName() + "</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(70, 70, 100));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(nameLabel, BorderLayout.NORTH);

        // --- Deck Details ---
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1, 0, 4));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel typeLabel = new JLabel("Type: " + deckModel.getType().toString());
        styleDetailLabel(typeLabel);
        detailsPanel.add(typeLabel);

        JLabel countLabel = new JLabel("Cards: " + deckModel.getCards().size());
        styleDetailLabel(countLabel);
        detailsPanel.add(countLabel);

        JLabel valueLabel = new JLabel(String.format("Value: $%.2f", deckModel.getTotalValue()));
        styleDetailLabel(valueLabel);
        detailsPanel.add(valueLabel);

        add(detailsPanel, BorderLayout.CENTER);

        // Set default selection state
        setSelected(false);
    }

    /**
     * Applies common styling to labels.
     * 
     * @param label JLabel to style.
     */
    private void styleDetailLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.LIGHT_GRAY);
    }

    /**
     * Updates visual selection state.
     * 
     * @param selected True if selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        setBorder(selected ? selectedBorder : defaultBorder);
        repaint();
    }

    /**
     * Returns whether the deck is currently selected.
     * 
     * @return True if selected.
     */
    public boolean isSelected() {
        return isSelected;
    }
}
