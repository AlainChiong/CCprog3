package main.java.view;

import main.java.model.classes.BinderModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * BinderView is a JPanel representing a binder visually.
 * It shows the name, type, number of cards, and total value,
 * and changes appearance when selected.
 */
public class BinderView extends JPanel {

    private static final Dimension BINDER_SIZE = new Dimension(200, 150);

    private final Border defaultBorder;
    private final Border selectedBorder;

    private boolean isSelected = false;

    public BinderView(BinderModel binderModel) {
        setPreferredSize(BINDER_SIZE);
        setMinimumSize(BINDER_SIZE);
        setMaximumSize(BINDER_SIZE);
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(50, 50, 80));

        // Borders
        defaultBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        selectedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 180, 255), 3), // Light blue for binders
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        );
        setBorder(defaultBorder);

        // Name
        JLabel nameLabel = new JLabel("<html><center>" + binderModel.getName() + "</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(70, 70, 100));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(nameLabel, BorderLayout.NORTH);

        // Details
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 0, 4));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel typeLabel = new JLabel("Type: " + binderModel.getTypeName());
        styleDetailLabel(typeLabel);
        detailsPanel.add(typeLabel);

        JLabel countLabel = new JLabel("Cards: " + binderModel.getCards().size());
        styleDetailLabel(countLabel);
        detailsPanel.add(countLabel);

        JLabel valueLabel = new JLabel(String.format("Value: $%.2f", binderModel.getTotalValue()));
        styleDetailLabel(valueLabel);
        detailsPanel.add(valueLabel);

        add(detailsPanel, BorderLayout.CENTER);

        setSelected(false);
    }

    private void styleDetailLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.LIGHT_GRAY);
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        setBorder(selected ? selectedBorder : defaultBorder);
        repaint();
    }

    public boolean isSelected() {
        return isSelected;
    }
}
