package main.java.controller;

import main.java.model.MainModel;
import main.java.model.classes.CardModel;
import main.java.model.classes.DeckModel;
import main.java.model.classes.SellableDeckModel;
import main.java.model.classes.CollectionModel;
import main.java.view.MainView;
import main.java.view.deck_views.ManageDecksView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManageDeckController {
    
    private final MainModel mainModel;
    private final MainView mainView;
    private final MainController mainController;
    private final ManageDecksView manageDecksView;
    private final ArrayList<DeckModel> deckList;

    public ManageDeckController(MainModel mainModel, MainView mainView, MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;
        this.manageDecksView = mainView.getManageDecksView();

        setupListeners();
    }
    
    private void setupListeners() {
        manageDecksView.setCreateDeckButtonActionListener(e -> createDeckButtonPressed());
        manageDecksView.setDeleteDeckButtonActionListener(e -> deleteDeckButtonPressed());
        manageDecksView.setViewDeckButtonActionListener(e -> viewDeckButtonPressed());
        manageDecksView.setAddRemoveCardButtonActionListener(e -> addRemoveCardToDeckButtonPressed());
        manageDecksView.setSellDeckButtonActionListener(e -> sellDeckButtonPressed());
    }
}
