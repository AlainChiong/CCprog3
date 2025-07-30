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

public class ManageDeckController {
    
    private final MainModel mainModel;
    private final MainView mainView;
    private final MainController mainController;
    private final ManageDecksView manageDecksView;

    public ManageDeckController(MainModel mainModel, MainView mainView, MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;
        this.manageDecksView = mainView.getManageDecksView();

        setupListeners();
    }
    
    private void setupListeners() {
        System.out.println("ManageDeckController: Setup Listeners");
        manageDecksView.setCreateDeckButtonActionListener(e -> createDeckButtonPressed());
        manageDecksView.setDeleteDeckButtonActionListener(e -> deleteDeckButtonPressed());
        manageDecksView.setViewDeckButtonActionListener(e -> viewDeckButtonPressed());
        manageDecksView.setAddRemoveCardButtonActionListener(e -> addRemoveCardToDeckButtonPressed());
        manageDecksView.setSellDeckButtonActionListener(e -> sellDeckButtonPressed());
    }

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
    /*
     * TODO
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
    /*
     * TODO
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
    /*
     * TODO
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

        for (CardModel cardInDeck : selected.getCards()) {
            totalValue += cardInDeck.getBaseValue();

            // Check if it exists in collection
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
