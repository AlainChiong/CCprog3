package main.java.view;

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

import main.java.controller.CardController;
import main.java.model.classes.CardModel;
import main.java.utilities.ViewUtilities;

public class ManageCollectionView extends JPanel {

    // Action Buttons
    private JButton addCardButton;
    private JButton modifyCardCountButton; 
    private JButton viewCardDetailsButton; 
    private JButton sellCardButton;      
    private JButton backButton;

    // Panel to display the cards
    private JPanel cardsContainerPanel;
    private List<CardController> cardControllers;
    private CardModel currentSelectedCardModel = null;


    public ManageCollectionView() {
        this.cardControllers = new ArrayList<>();

        setLayout(new BorderLayout(10, 10)); 
        setBackground(new Color(50, 50, 80)); 


        JLabel titleLabel = new JLabel("Manage Collection", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE); 
        add(titleLabel, BorderLayout.NORTH);

 
        JPanel buttonPanel = new JPanel(new GridBagLayout()); 
        buttonPanel.setBackground(new Color(50, 50, 80)); 

        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); 
        gridBagConstraints.gridx = 0; 
        gridBagConstraints.gridy = 0; 
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL; 
        gridBagConstraints.weightx = 1.0; 


        addCardButton = new JButton("Add New Card");
        ViewUtilities.styleActionButton(addCardButton); 
        buttonPanel.add(addCardButton, gridBagConstraints); 

        
        gridBagConstraints.gridy++;
        modifyCardCountButton = new JButton("Modify Card Amount");
        ViewUtilities.styleActionButton(modifyCardCountButton);
        buttonPanel.add(modifyCardCountButton, gridBagConstraints);

        
        gridBagConstraints.gridy++;
        viewCardDetailsButton = new JButton("View Card Details");
        ViewUtilities.styleActionButton(viewCardDetailsButton);
        buttonPanel.add(viewCardDetailsButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        sellCardButton = new JButton("Sell Card"); 
        ViewUtilities.styleActionButton(sellCardButton);
        buttonPanel.add(sellCardButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.insets = new Insets(30, 10, 10, 10); 
        backButton = new JButton("Back to Main Menu");
        ViewUtilities.styleBackButton(backButton);
        buttonPanel.add(backButton, gridBagConstraints);


        add(buttonPanel, BorderLayout.EAST);


        cardsContainerPanel = new JPanel();
        cardsContainerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        cardsContainerPanel.setBackground(new Color(50, 50, 80));
        // Scroll Pane for cards (crucial for many cards)
        JScrollPane scrollPane = new JScrollPane(cardsContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(50, 50, 80));
        scrollPane.getViewport().setBackground(new Color(50, 50, 80));

        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the Center
    }


    public void setAddCardButtonActionListener(ActionListener listener) {
        addCardButton.addActionListener(listener);
    }

    public void setModifyCardCountButtonActionListener(ActionListener listener) {
        modifyCardCountButton.addActionListener(listener);
    }

    public void setViewCardDetailsButtonActionListener(ActionListener listener) {
        viewCardDetailsButton.addActionListener(listener);
    }

    public void setSellCardButtonActionListener(ActionListener listener) { 
        sellCardButton.addActionListener(listener);
    }

    public void setBackButtonActionListener(ActionListener listener) { 
        backButton.addActionListener(listener);
    }

 /**
     * Displays a list of CardModels by creating CardView and CardController instances for each.
     * This method clears the current display and repopulates it.
     *
     * @param cards The list of CardModels to display.
     * @param cardSelectionListener The ActionListener (from ManageCollectionController)
     * to be notified when an individual CardView is clicked for selection.
     */
    public void displayCards(List<CardModel> cards, ActionListener cardSelectionListener) {
        cardsContainerPanel.removeAll(); // Clear existing card panels
        cardControllers.clear();       // Clear existing controllers
        currentSelectedCardModel = null; // Clear previous selection

        if (cards != null && !cards.isEmpty()) {
            for (CardModel card : cards) {
                CardView cardView = new CardView(card);

                CardController cardController = new CardController(card, cardView, cardSelectionListener);


                cardControllers.add(cardController);

                cardsContainerPanel.add(cardView);
            }
        } else {
            // Display a message if there are no cards
            JLabel noCardsLabel = new JLabel("No cards in your collection. Add some!");
            noCardsLabel.setForeground(Color.LIGHT_GRAY); // Style for the message
            noCardsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            cardsContainerPanel.add(noCardsLabel);
        }

        // Revalidate and repaint to ensure the new components are displayed correctly
        cardsContainerPanel.revalidate();
        cardsContainerPanel.repaint();
    }

    /**
     * Updates the selection state across all displayed CardViews.
     * This method is called by the ManageCollectionController whenever a card's selection changes.
     *
     * @param selectedModel The CardModel that is currently selected (or null if no card is selected).
     */
    public void setSelectedCard(CardModel selectedModel) {
        this.currentSelectedCardModel = selectedModel; 

        for (CardController controller : cardControllers) {

            controller.setSelected(controller.getCardModel().equals(selectedModel));
        }
    }

    /**
     * Returns the CardModel of the currently selected card.
     * @return The currently selected CardModel, or null if no card is selected.
     */
    public CardModel getSelectedCard() {
        return currentSelectedCardModel;
    }
}
