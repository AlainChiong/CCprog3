package main.java.view.deck_views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.java.controller.DeckController;
import main.java.model.classes.DeckModel;
import main.java.utilities.ViewUtilities;
import main.java.view.DeckView;

public class ManageDecksView extends JPanel {

    private JButton createDeckButton;
    private JButton deleteDeckButton;
    private JButton addRemoveCardButton;
    private JButton viewDeckButton;
    private JButton sellDeckButton;
    private JButton backButton;

    private JPanel decksContainerPanel;
    private List<DeckController> deckControllers;
    private DeckModel currentSelectedDeckModel = null;

    public ManageDecksView() {
        this.deckControllers = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(50, 50, 80));

        layoutComponents();
    }

    private void layoutComponents() {
        JLabel titleLabel = new JLabel("Manage Decks", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(50, 50, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        createDeckButton = new JButton("Create New Deck");
        ViewUtilities.styleActionButton(createDeckButton);
        buttonPanel.add(createDeckButton, gbc);

        gbc.gridy++;
        deleteDeckButton = new JButton("Delete Deck");
        ViewUtilities.styleActionButton(deleteDeckButton);
        buttonPanel.add(deleteDeckButton, gbc);

        gbc.gridy++;
        addRemoveCardButton = new JButton("Add/Remove Cards");
        ViewUtilities.styleActionButton(addRemoveCardButton);
        buttonPanel.add(addRemoveCardButton, gbc);

        gbc.gridy++;
        viewDeckButton = new JButton("View Deck");
        ViewUtilities.styleActionButton(viewDeckButton);
        buttonPanel.add(viewDeckButton, gbc);

        gbc.gridy++;
        sellDeckButton = new JButton("Sell Deck");
        ViewUtilities.styleActionButton(sellDeckButton);
        sellDeckButton.setEnabled(false); // Only enable when a sellable deck is selected
        buttonPanel.add(sellDeckButton, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        backButton = new JButton("Back to Main Menu");
        ViewUtilities.styleBackButton(backButton);
        buttonPanel.add(backButton, gbc);

        add(buttonPanel, BorderLayout.EAST);

        decksContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        decksContainerPanel.setBackground(new Color(50, 50, 80));

        JScrollPane scrollPane = new JScrollPane(decksContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(50, 50, 80));
        scrollPane.getViewport().setBackground(new Color(50, 50, 80));

        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayDecks(List<DeckModel> decks, ActionListener deckSelectionListener) {
        decksContainerPanel.removeAll();
        deckControllers.clear();
        currentSelectedDeckModel = null;

        if (decks != null && !decks.isEmpty()) {
            for (DeckModel deck : decks) {
                DeckView deckView = new DeckView(deck);
                DeckController controller = new DeckController(deck, deckView, deckSelectionListener);
                deckControllers.add(controller);
                decksContainerPanel.add(deckView);
            }
        } else {
            JLabel noDecksLabel = new JLabel("No decks in your collection.");
            noDecksLabel.setForeground(Color.LIGHT_GRAY);
            noDecksLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            decksContainerPanel.add(noDecksLabel);
        }

        decksContainerPanel.revalidate();
        decksContainerPanel.repaint();
    }

    public void setSelectedDeck(DeckModel selectedDeck) {
        this.currentSelectedDeckModel = selectedDeck;
        for (DeckController controller : deckControllers) {
            controller.setSelected(controller.getDeckModel().equals(selectedDeck));
        }
        // Enable sell button only if the deck is sellable
        sellDeckButton.setEnabled(selectedDeck != null && selectedDeck.isSellable());
    }

    public DeckModel getSelectedDeck() {
        return currentSelectedDeckModel;
    }

    public void setCreateDeckButtonActionListener(ActionListener listener) {
        createDeckButton.addActionListener(listener);
    }

    public void setDeleteDeckButtonActionListener(ActionListener listener) {
        deleteDeckButton.addActionListener(listener);
    }

    public void setAddRemoveCardButtonActionListener(ActionListener listener) {
        addRemoveCardButton.addActionListener(listener);
    }

    public void setViewDeckButtonActionListener(ActionListener listener) {
        viewDeckButton.addActionListener(listener);
    }

    public void setSellDeckButtonActionListener(ActionListener listener) {
        sellDeckButton.addActionListener(listener);
    }

    public void setBackButtonActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
