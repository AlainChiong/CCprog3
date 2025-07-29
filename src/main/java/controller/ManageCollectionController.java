package main.java.controller;

import javax.swing.JOptionPane;

import java.awt.Label;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import java.util.List; 

import main.java.model.MainModel; 
import main.java.model.classes.CardModel; 
import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;

import main.java.view.MainView;
import main.java.view.collection_views.AddCardView;
import main.java.view.collection_views.ManageCollectionView;
import main.java.view.collection_views.ModifyCardAmountView;
import main.java.view.collection_views.SellCardView; 

/**
 * ManageCollectionController is a sub-controller responsible for managing all interactions
 * and logic related to the "Manage Collection" screen.
 * It mediates between the ManageCollectionView and the relevant
 * parts of the MainModel, specifically the user's card Collection.
 */
public class ManageCollectionController {

    /*
     * Reference to the MainModel
     */
    private final MainModel mainModel;
    /*
     * Reference to the MainView
     */
    private final MainView mainView;
    /*
     * Reference to the MainController
     */
    private final MainController mainController;
    /*
     * Reference to the ManageCollectionView, the view that this controller is responsible of.
     */
    private final ManageCollectionView manageCollectionView; 

    /**
     * Constructor for ManageCollectionController.
     * @param mainModel The main application model.
     * @param mainView The main application view (JFrame).
     */
    public ManageCollectionController(MainModel mainModel, MainView mainView ,MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;
        this.manageCollectionView = mainView.getManageCollectionView();

        setupListeners();
    }

    /**
     * Sets up ActionListeners for buttons specifically on the ManageCollectionView.
     * This method is internal to ManageCollectionController as it's responsible for its view's interactions.
     */
    private void setupListeners() {
        // Listener for the "Add New Card" button
        manageCollectionView.setAddCardButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCardButtonPressed();
            }
        });

        // Listener for the "Modify Card Amount" button
        manageCollectionView.setModifyCardCountButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyCardCountButtonPressed();
            }
        });

        // Listener for the "View Card Details" button
        manageCollectionView.setViewCardDetailsButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCardDetailsButtonPressed();
            }
        });

        //Listener for the "Sell Card" button
        manageCollectionView.setSellCardButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellCardButtonPressed();
            }
        });

        // The "Back to Main Menu" button listener is handled directly in MainView's constructor,
        // which calls MainController.showMainMenu(). This keeps navigation centralized at MainController.
    }

    /**
     * Refreshes the display of cards in the ManageCollectionView.
     * This method is called by the MainController when the "Manage Collection" screen is shown,
     * and also by this controller after any operation that changes the collection data (add, modify).
     */
    public void refreshCardDisplay() {

        List<CardModel> currentCards = mainModel.getCollectionModel().getCardsSortedByName();

        manageCollectionView.displayCards(currentCards, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CardModel clickedCard = (CardModel) e.getSource();
 
                manageCollectionView.setSelectedCard(clickedCard);
            }
        });
        boolean isCollectionEmpty = currentCards.isEmpty();
        manageCollectionView.setEnableActionButtons(!isCollectionEmpty);
    }

    // --- Action methods for ManageCollectionView buttons ---

    /**
     * Handles the action when the "Add New Card" button is pressed.
     * Prompts the user for card details and adds the card to the collection.
     */
    private void addCardButtonPressed() {
        System.out.println("ManageCollectionController: 'Add Card' action received.");

        AddCardView addCardView = new AddCardView();

        int result = JOptionPane.showConfirmDialog(
            manageCollectionView, 
            addCardView,         
            "Add New Card",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = addCardView.getCardName();
            Rarity rarity = addCardView.getRaritySelection();
            Variant variant = addCardView.getVariantSelection();
            String valueStr = addCardView.getValueText();

            if (name.isEmpty() || valueStr.isEmpty()) {
                JOptionPane.showMessageDialog(manageCollectionView, "Card Name and Base Value are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double value;
            try {
                value = Double.parseDouble(valueStr);
                if (value <= 0) {
                    JOptionPane.showMessageDialog(manageCollectionView, "Base Value must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(manageCollectionView, "Invalid Base Value. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CardModel newCard = new CardModel(name, rarity, variant, value);

            boolean newEntry = mainModel.getCollectionModel().addCard(newCard);

            if (newEntry) {
                JOptionPane.showMessageDialog(manageCollectionView, "New card added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(manageCollectionView, "Existing card amount updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshCardDisplay();
        } else {
            System.out.println("ManageCollectionController: Add Card operation cancelled.");
        }
    }

    /**
     * Handles the action when the "Modify Card Amount" button is pressed.
     * Prompts the user for an amount change for the selected card.
     */
    private void modifyCardCountButtonPressed() {
        System.out.println("ManageCollectionController: 'Modify Card Count' action received.");
        // Get the currently selected CardModel from the ManageCollectionView
        CardModel selectedCard = manageCollectionView.getSelectedCard();
        if (selectedCard == null) {
            JOptionPane.showMessageDialog(manageCollectionView, "Please select a card to modify amount.", "No Card Selected", JOptionPane.WARNING_MESSAGE);
        }
        else {
            ModifyCardAmountView modifyCardAmountView = new ModifyCardAmountView(selectedCard.getAmount());

            int result = JOptionPane.showConfirmDialog(
                manageCollectionView,
                modifyCardAmountView,
                "Modify Card Count",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                System.out.println("ManageCollectionController: Card count value modified");
                selectedCard.setAmount( selectedCard.getAmount() + modifyCardAmountView.getNewValue());
                if (selectedCard.getAmount() <= 0) {
                    mainModel.getCollectionModel().removeCard(selectedCard);
                    JOptionPane.showMessageDialog(manageCollectionView, "Card removed from collection as amount reached 0.", "Card Removed", JOptionPane.INFORMATION_MESSAGE);
                }
                refreshCardDisplay();
            }
            else {
                System.out.println("ManageCollectionController: Modify card count cancelled");
            }
        }
    }

    /**
     * Handles the action when the "View Card Details" button is pressed.
     * Displays a dialog with detailed information about the selected card.
     */
    private void viewCardDetailsButtonPressed() {
        System.out.println("ManageCollectionController: 'View Card Details' action received.");
        // Get the currently selected CardModel from the ManageCollectionView
        CardModel selectedCard = manageCollectionView.getSelectedCard();
        if (selectedCard == null) {
            JOptionPane.showMessageDialog(manageCollectionView, "Please select a card to view details.", "No Card Selected", JOptionPane.WARNING_MESSAGE);
        }
        else {
            // Format the card details for display
            String details = String.format(
                "=== Card Details ===\n" +
                "Name    : %s\n" +
                "Rarity  : %s\n" +
                "Variant : %s\n" +
                "Value   : $%.2f\n" +
                "Amount  : %.0f",
                selectedCard.getName(),
                selectedCard.getRarity(),
                selectedCard.getVariant(),
                selectedCard.getValue(),
                selectedCard.getAmount()
            );
            JOptionPane.showMessageDialog(manageCollectionView, details, "Card Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void sellCardButtonPressed() {
        System.out.println("ManageCollectionController: 'Sell Card' action received.");

        CardModel selectedCard = manageCollectionView.getSelectedCard();

        if (selectedCard == null) {
            JOptionPane.showMessageDialog(manageCollectionView, "Please select a card to sell.", "No Card Selected", JOptionPane.WARNING_MESSAGE);
        }
        else {

            SellCardView sellCardView = new SellCardView(selectedCard);

            int result = JOptionPane.showConfirmDialog(
                manageCollectionView,
                sellCardView,
                "Sell Card",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                mainController.setMoney(mainModel.getMoney() + selectedCard.getTotalPrice(sellCardView.getNewValue()));
                selectedCard.setAmount(selectedCard.getAmount() - sellCardView.getNewValue());
                if (selectedCard.getAmount() == 0) {
                    mainModel.getCollectionModel().removeCard(selectedCard);
                    refreshCardDisplay();
                    System.out.println("ManageCollectionController: Card Removed.");
                }
            }
            else {
                System.out.println("ManageCollectionController: Sell Card Cancelled.");
            }
        }
    }
}