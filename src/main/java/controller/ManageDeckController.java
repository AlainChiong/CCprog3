package main.java.controller;

import main.java.model.MainModel;
import main.java.model.classes.CardModel;
import main.java.model.classes.DeckModel;
import main.java.model.classes.SellableDeckModel;
import main.java.model.classes.CollectionModel;
import main.java.view.MainView;
import main.java.view.deck_views.CreateDeckView;
import main.java.view.deck_views.ManageDecksView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The `ManageDeckController` is a sub-controller responsible for handling all user interactions
 * and business logic pertaining to the "Manage Decks" screen of the application.
 * It acts as an intermediary between the {@link ManageDecksView} and the deck-related data
 * within the {@link MainModel}. This includes operations such as creating, deleting, viewing,
 * adding/removing cards from, and selling decks.
 */
public class ManageDeckController {
    /**
     * Reference to the main application model, providing access to all application data, including decks and the card collection.
     */
    private final MainModel mainModel;
    /**
     * Reference to the main application view, used for displaying sub-views, dialogs, and managing panel transitions.
     */
    private final MainView mainView;
    /**
     * Reference to the main application controller, used for delegating global application state changes,
     * such as updating the player's money or navigating between main application panels.
     */
    private final MainController mainController;
    /**
     * Reference to the specific view managed by this controller, the {@link ManageDecksView}.
     */
    private final ManageDecksView manageDecksView;

    /**
     * Constructs a new `ManageDeckController`.
     * Initializes the controller with necessary references to the core application components.
     * It also obtains the specific {@link ManageDecksView} instance from the {@link MainView}
     * and proceeds to set up all action listeners for the interactive elements within that view.
     *
     * @param mainModel The main application model.
     * @param mainView The main application view.
     * @param mainController The main application controller.
     */
    public ManageDeckController(MainModel mainModel, MainView mainView, MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;
        this.manageDecksView = mainView.getManageDecksView();

        setupListeners();
    }
    
    /**
     * Configures and assigns action listeners to the buttons and interactive elements
     * on the {@link ManageDecksView}. These listeners trigger the corresponding
     * action methods within this controller.
     */
    private void setupListeners() {
        System.out.println("ManageDeckController: Setup Listeners");
        manageDecksView.setCreateDeckButtonActionListener(e -> createDeckButtonPressed());
        manageDecksView.setDeleteDeckButtonActionListener(e -> deleteDeckButtonPressed());
        manageDecksView.setViewDeckButtonActionListener(e -> viewDeckButtonPressed());
        manageDecksView.setAddRemoveCardButtonActionListener(e -> addRemoveCardToDeckButtonPressed());
        manageDecksView.setSellDeckButtonActionListener(e -> sellDeckButtonPressed());
    }

    /**
     * Refreshes the display of decks in the {@link ManageDecksView}.
     * This method retrieves the current list of decks from the {@link MainModel},
     * updates the view to reflect these decks, and sets up a selection listener
     * for individual deck views. It also dynamically enables or disables action buttons
     * based on whether there are any decks to manage.
     */
    public void refreshDeckDisplay() {
        List<DeckModel> currentDecks = mainModel.getDecks();

        manageDecksView.displayDecks(currentDecks, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeckModel selected = (DeckModel) e.getSource();
                manageDecksView.setSelectedDeck(selected);
            }
        });

        boolean isEmpty = currentDecks.isEmpty();
        manageDecksView.setEnableActionButtons(!isEmpty);
    }

    /**
     * Handles the action when the "Create Deck" button is pressed.
     * It opens a dialog for the user to input a new deck's name and type.
     * Upon successful creation and validation, the new deck is added to the model
     * and the deck display is refreshed.
     */
    private void createDeckButtonPressed() {
        System.out.println("ManageDeckController: Create Deck Button Action Recieved");
        CreateDeckView createDeckView = new CreateDeckView();

        int result = JOptionPane.showConfirmDialog(
            manageDecksView,
            createDeckView,
            "Create New Deck",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = createDeckView.getDeckName();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(manageDecksView, "Deck name and type are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DeckModel newDeck = createDeckView.getDeckType();

            mainModel.getDecks().add(newDeck);

            JOptionPane.showMessageDialog(manageDecksView, "Deck created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshDeckDisplay();
        }
    }

    /**
     * Handles the action when the "Delete Deck" button is pressed.
     * It prompts the user to select a deck and confirms the deletion.
     * If confirmed, all cards from the deleted deck are meticulously returned to the main collection,
     * the deck is removed from the model, and the display is refreshed.
     */
    private void deleteDeckButtonPressed() {
        DeckModel selected = manageDecksView.getSelectedDeck();
        if (selected == null) {
            JOptionPane.showMessageDialog(manageDecksView, "Please select a deck to delete.", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            manageDecksView,
            "Are you sure you want to delete this deck?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
        // Return all cards back to collection
        CollectionModel collection = mainModel.getCollectionModel();
        for (CardModel deckCard : selected.getCards()) {
            CardModel existing = CollectionModel.findCardInCollection(collection, deckCard.getName());
            if (existing != null && existing.matches(deckCard)) {
                existing.setAmount(existing.getAmount() + 1);
            } else {
                CardModel copy = new CardModel(deckCard.getName(), deckCard.getRarity(), deckCard.getVariant(), deckCard.getBaseValue());
                copy.setAmount(1);
                collection.addCard(copy);
            }
        }

        mainModel.getDecks().remove(selected);
        JOptionPane.showMessageDialog(manageDecksView, "Deck deleted and cards returned to collection.", "Success", JOptionPane.INFORMATION_MESSAGE);
        refreshDeckDisplay();
    }
    }

    /**
     * Handles the action when the "View Deck" button is pressed.
     * It displays a dialog showing the name and all cards within the currently
     * selected deck, along with the deck's total combined card value.
     */
    private void viewDeckButtonPressed() {
        DeckModel selected = manageDecksView.getSelectedDeck();
        if (selected == null) {
            JOptionPane.showMessageDialog(manageDecksView, "Please select a deck to view.", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder deckContents = new StringBuilder("Deck: " + selected.getName() + "\n\nCards:\n");
        double totalValue = 0.0;

        if (selected.getCards().isEmpty()) {
            deckContents.append("No cards in this deck.");
        } else {
            for (CardModel card : selected.getCards()) {
                double value = card.getBaseValue();
                totalValue += value;

                deckContents.append("- Name: ").append(card.getName()).append("\n")
                            .append("  Rarity: ").append(card.getRarity()).append("\n")
                            .append("  Variant: ").append(card.getVariant()).append("\n")
                            .append("  Value: $").append(String.format("%.2f", value)).append("\n\n");
            }

            deckContents.append("Total Deck Value: $").append(String.format("%.2f", totalValue));
        }

        JOptionPane.showMessageDialog(manageDecksView, deckContents.toString(), "View Deck", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the action when the "Add/Remove Card to Deck" button is pressed.
     * It provides an option for the user to either add a card from their main collection to the
     * selected deck, or remove a card from the selected deck and return it to the collection.
     * This method carefully manages the quantities of cards in both the deck and the main collection.
     */
    private void addRemoveCardToDeckButtonPressed() {
        DeckModel selectedDeck = manageDecksView.getSelectedDeck();
        if (selectedDeck == null) {
            JOptionPane.showMessageDialog(manageDecksView, "Please select a deck to modify.", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CollectionModel collection = mainModel.getCollectionModel();
        List<CardModel> allCards = collection.getCardsSortedByName();

        if (allCards.isEmpty()) {
            JOptionPane.showMessageDialog(manageDecksView, "Your collection is empty.", "No Cards", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = {"Add Card", "Remove Card"};
        int action = JOptionPane.showOptionDialog(manageDecksView, "Would you like to add or remove a card?",
                "Modify Deck", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (action == -1) return;

        List<CardModel> candidateCards;

        if (action == 0) { // Add mode
            candidateCards = allCards.stream()
                .filter(card -> card.getAmount() > 0 && !selectedDeck.getCards().contains(card))
                .toList();
        } else { // Remove mode
            candidateCards = new ArrayList<>(selectedDeck.getCards());
        }

        if (candidateCards.isEmpty()) {
            String msg = (action == 0) ? "No cards with available copies to add." : "This deck has no cards to remove.";
            JOptionPane.showMessageDialog(manageDecksView, msg, "No Cards", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cardNames = candidateCards.stream()
                .map(card -> card.getName() + " | " + card.getRarity() + " | " + card.getVariant())
                .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(manageDecksView,
                "Choose a card to " + (action == 0 ? "add" : "remove") + ":",
                "Select Card", JOptionPane.PLAIN_MESSAGE, null, cardNames, cardNames[0]);

        if (selected == null) return;

        CardModel chosenCard = candidateCards.get(java.util.Arrays.asList(cardNames).indexOf(selected));

        if (action == 0) { // Add
            if (selectedDeck.getCards().size() >= 10) {
                JOptionPane.showMessageDialog(manageDecksView, "Deck is already full (max 10 cards).", "Deck Full", JOptionPane.WARNING_MESSAGE);
                return;
            }

            selectedDeck.getCards().add(chosenCard);
            chosenCard.setAmount(chosenCard.getAmount() - 1);
            JOptionPane.showMessageDialog(manageDecksView, "Card added to deck.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else { // Remove
            selectedDeck.getCards().remove(chosenCard);

            CardModel existingInCollection = CollectionModel.findCardInCollection(collection, chosenCard.getName());
            if (existingInCollection != null && existingInCollection.matches(chosenCard)) {
                existingInCollection.setAmount(existingInCollection.getAmount() + 1);
            } else {
                // Card no longer in collection; add it back with amount 1
                CardModel copy = new CardModel(chosenCard.getName(), chosenCard.getRarity(), chosenCard.getVariant(), chosenCard.getBaseValue());
                copy.setAmount(1);
                collection.addCard(copy);
            }

            JOptionPane.showMessageDialog(manageDecksView, "Card removed from deck.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        refreshDeckDisplay();
    }

    /**
     * Handles the action when the "Sell Deck" button is pressed.
     * This method strictly allows only decks that are instances of {@link SellableDeckModel} to be sold.
     * If a sellable deck is selected and the user confirms the action, the deck's total monetary value
     * is added to the player's balance. The cards within the sold deck are considered permanently lost
     * (i.e., they are NOT returned to the main collection). The deck itself is then removed from the application.
     */
    private void sellDeckButtonPressed() {
        DeckModel selected = manageDecksView.getSelectedDeck();
        if (selected == null) {
            JOptionPane.showMessageDialog(manageDecksView, "Please select a deck to sell.", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!"sellable".equalsIgnoreCase(selected.getType())) {
            JOptionPane.showMessageDialog(manageDecksView, "Only sellable decks can be sold.", "Invalid Deck Type", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            manageDecksView,
            "Are you sure you want to sell this deck?\nAll cards in it will be lost permanently.",
            "Confirm Sell",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        double totalValue = 0.0;
        CollectionModel collection = mainModel.getCollectionModel();


        // Calculate the total value of all cards contained within the deck.
        // The cards are permanently consumed by selling the deck.
        // Their handling in the main collection (reduction of amount or removal) is assumed
        // to have occurred when they were initially moved into the deck.
        for (CardModel cardInDeck : selected.getCards()) {
            totalValue += cardInDeck.getBaseValue();

            // This block processes how cards in the sold deck affect the main collection.
            // If a card from the collection was moved into this deck, and it was the last copy,
            // this ensures it's fully removed from the collection's records if it still exists there with amount 0.
            CardModel inCollection = collection.getMatchingCard(cardInDeck);
            if (inCollection != null) {
                if (inCollection.getAmount() == 0) {
                    collection.removeCard(inCollection);
                }
                // If amount > 0, do nothing — card is still owned outside the deck
            }
        }

        // Add money
        double currentMoney = mainModel.getMoney();
        mainModel.setMoney(currentMoney + totalValue);
        mainController.setMoney(mainModel.getMoney());

        // Remove deck
        selected.getCards().clear();
        mainModel.getDecks().remove(selected);

        JOptionPane.showMessageDialog(
            manageDecksView,
            "Deck sold for $" + String.format("%.2f", totalValue),
            "Deck Sold",
            JOptionPane.INFORMATION_MESSAGE
        );

        refreshDeckDisplay();
    }
}
