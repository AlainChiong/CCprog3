package main.java.controller;

import javax.swing.JOptionPane;

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
 * `ManageCollectionController` is a sub-controller responsible for managing all interactions
 * and logic related to the "Manage Collection" screen.
 * It mediates between the {@link ManageCollectionView} and the relevant
 * parts of the {@link MainModel}, specifically the user's card {@link main.java.model.classes.CollectionModel}.
 * This controller handles user input from the view, updates the model, and ensures the view
 * reflects the current state of the card collection.
 */
public class ManageCollectionController {

    /**
     * Reference to the main application model, which holds the card collection data.
     */
    private final MainModel mainModel;
    /**
     * Reference to the main application view (JFrame), used for displaying sub-views and dialogs.
     */
    private final MainView mainView;
    /**
     * Reference to the main application controller, used for general application
     * flow and state changes (e.g., updating user money or navigating back to main menu).
     */
    private final MainController mainController;
    /**
     * Reference to the {@link ManageCollectionView}, the specific view that this controller is responsible for.
     */
    private final ManageCollectionView manageCollectionView;

    /**
     * Constructs a new `ManageCollectionController`.
     * Initializes the controller with references to the main model, main view, and main controller.
     * It also obtains the specific `ManageCollectionView` instance from the `MainView`
     * and sets up all necessary action listeners for its buttons.
     *
     * @param mainModel The main application model.
     * @param mainView The main application view (JFrame).
     * @param mainController The main application controller.
     */
    public ManageCollectionController(MainModel mainModel, MainView mainView ,MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;
        this.manageCollectionView = mainView.getManageCollectionView();

        setupListeners();
    }

    /**
     * Sets up `ActionListener`s for all interactive buttons on the {@link ManageCollectionView}.
     * This method is internal to `ManageCollectionController` as it's solely responsible
     * for handling its view's interactions and delegating to appropriate action methods.
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

        // Listener for the "Sell Card" button
        manageCollectionView.setSellCardButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellCardButtonPressed();
            }
        });

        // The "Back to Main Menu" button listener is handled in MainController,
        // ensuring centralized navigation control.
    }

    /**
     * Refreshes the display of cards in the {@link ManageCollectionView}.
     * This method is called by the {@link MainController} when the "Manage Collection" screen is shown,
     * and also by this controller after any operation that changes the collection data (e.g., add, modify, sell).
     * It fetches the latest card data from the model, updates the view, and
     * enables/disables action buttons based on whether the collection is empty.
     */
    public void refreshCardDisplay() {
        List<CardModel> currentCards = mainModel.getCollectionModel().getCardsSortedByName();

        manageCollectionView.displayCards(currentCards, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When a card view is clicked, its CardModel is passed as the source
                CardModel clickedCard = (CardModel) e.getSource();
                manageCollectionView.setSelectedCard(clickedCard); // Update the selected card in the view
            }
        });
        // Enable/disable action buttons based on whether there are cards in the collection
        boolean isCollectionEmpty = currentCards.isEmpty();
        manageCollectionView.setEnableActionButtons(!isCollectionEmpty);
    }

    // --- Action methods for ManageCollectionView buttons ---

    /**
     * Handles the action when the "Add New Card" button is pressed.
     * This method prompts the user for new card details using an {@link AddCardView} dialog.
     * It validates the input, creates a new {@link CardModel}, and attempts to add it to the
     * user's collection via the {@link MainModel}. It provides feedback to the user
     * and refreshes the card display.
     */
    private void addCardButtonPressed() {
        System.out.println("ManageCollectionController: 'Add Card' action received.");

        AddCardView addCardView = new AddCardView(); // Create the dialog view

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

            // Input validation
            if (name.trim().isEmpty() || valueStr.trim().isEmpty()) {
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

            // Create new card model and attempt to add to collection
            CardModel newCard = new CardModel(name.trim(), rarity, variant, value);
            boolean newEntry = mainModel.getCollectionModel().addCard(newCard); // addCard updates amount if card exists

            if (newEntry) {
                JOptionPane.showMessageDialog(manageCollectionView, "New card added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(manageCollectionView, "Existing card amount updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshCardDisplay(); // Update the view to show changes
        } else {
            System.out.println("ManageCollectionController: Add Card operation cancelled.");
        }
    }

    /**
     * Handles the action when the "Modify Card Amount" button is pressed.
     * This method first checks if a card is selected in the view. If so, it displays
     * a {@link ModifyCardAmountView} dialog to allow the user to change the amount
     * of the selected card. It updates the card's amount in the model and, if the amount
     * drops to zero or less, removes the card entirely from the collection.
     * The card display is then refreshed.
     */
    private void modifyCardCountButtonPressed() {
        System.out.println("ManageCollectionController: 'Modify Card Count' action received.");
        CardModel selectedCard = manageCollectionView.getSelectedCard(); // Get the currently selected CardModel

        if (selectedCard == null) {
            JOptionPane.showMessageDialog(manageCollectionView, "Please select a card to modify amount.", "No Card Selected", JOptionPane.WARNING_MESSAGE);
        } else {
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
                // Update the amount of the selected card
                selectedCard.setAmount(selectedCard.getAmount() + modifyCardAmountView.getNewValue());

                // If amount drops to 0 or less, remove the card from the collection
                if (selectedCard.getAmount() <= 0) {
                    mainModel.getCollectionModel().removeCard(selectedCard);
                    JOptionPane.showMessageDialog(manageCollectionView, "Card removed from collection as amount reached 0 or less.", "Card Removed", JOptionPane.INFORMATION_MESSAGE);
                }
                refreshCardDisplay(); // Update the view to reflect changes
            } else {
                System.out.println("ManageCollectionController: Modify card count cancelled.");
            }
        }
    }

    /**
     * Handles the action when the "View Card Details" button is pressed.
     * This method first checks if a card is selected in the view. If so, it formats
     * the detailed information of the selected card (name, rarity, variant, value, amount)
     * into a string and displays it in an informational dialog to the user.
     */
    private void viewCardDetailsButtonPressed() {
        System.out.println("ManageCollectionController: 'View Card Details' action received.");
        CardModel selectedCard = manageCollectionView.getSelectedCard(); // Get the currently selected CardModel

        if (selectedCard == null) {
            JOptionPane.showMessageDialog(manageCollectionView, "Please select a card to view details.", "No Card Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            // Format the card details for display in a user-friendly way
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

    /**
     * Handles the action when the "Sell Card" button is pressed.
     * This method first checks if a card is selected in the view. If so, it displays
     * a {@link SellCardView} dialog to allow the user to specify how many units of the
     * selected card they wish to sell. It validates the sell amount, updates the user's
     * money in the {@link MainModel}, reduces the card's amount, and removes the card
     * entirely from the collection if its amount drops to zero. The card display is then refreshed.
     */
    private void sellCardButtonPressed() {
        System.out.println("ManageCollectionController: 'Sell Card' action received.");

        CardModel selectedCard = manageCollectionView.getSelectedCard();

        if (selectedCard == null) {
            JOptionPane.showMessageDialog(manageCollectionView, "Please select a card to sell.", "No Card Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            SellCardView sellCardView = new SellCardView(selectedCard);

            int result = JOptionPane.showConfirmDialog(
                manageCollectionView,
                sellCardView,
                "Sell Card",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                double amountToSell = sellCardView.getNewValue(); // This is the amount the user wants to sell
                double currentAmount = selectedCard.getAmount();

                // Basic validation for sell amount
                if (amountToSell <= 0 || amountToSell > currentAmount) {
                     JOptionPane.showMessageDialog(manageCollectionView,
                        "Invalid sell amount. Must be positive and not exceed current amount.",
                        "Sell Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update player money
                mainController.setMoney(mainModel.getMoney() + selectedCard.getTotalPrice((int) amountToSell)); // Cast to int if getTotalPrice expects int

                // Reduce card amount
                selectedCard.setAmount(currentAmount - amountToSell);

                // Remove card if amount drops to zero
                if (selectedCard.getAmount() == 0) {
                    mainModel.getCollectionModel().removeCard(selectedCard);
                    JOptionPane.showMessageDialog(manageCollectionView, "Card removed from collection as amount reached 0.", "Card Removed", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("ManageCollectionController: Card Removed (amount reached 0).");
                }
                refreshCardDisplay(); // Update the view to reflect changes
            } else {
                System.out.println("ManageCollectionController: Sell Card operation cancelled.");
            }
        }
    }
}