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
        System.out.println("ManageDeckController: Wah");
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
            String type = createDeckView.getDeckType();

            if (name.isEmpty() || type == null) {
                JOptionPane.showMessageDialog(manageDecksView, "Deck name and type are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DeckModel newDeck;
            if (type.equalsIgnoreCase("sell")) {
                newDeck = new SellableDeckModel(name);
            } else {
                newDeck = new DeckModel(name);
            }
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
            mainModel.getDecks().remove(selected);
            JOptionPane.showMessageDialog(manageDecksView, "Deck deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshDeckDisplay();
        }
    }
    /*
     * TODO
     */

    private void viewDeckButtonPressed() {

    }
    /*
     * TODO
     */

    private void addRemoveCardToDeckButtonPressed() {

    }
    /*
     * TODO
     */

    private void sellDeckButtonPressed() {

    }
}
