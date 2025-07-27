package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.java.model.MainModel;
import main.java.view.MainView;

public class MainController {
    private final MainView mainView;

    private final MainModel mainModel;
    private ManageCollectionController manageCollectionController;



    public MainController() {
        this.mainView = new MainView("Trading Card Inventory System");
        this.mainModel = new MainModel();

        this.manageCollectionController = new ManageCollectionController(
                mainModel,
                mainView
        );

        setupAllViewListeners();

        mainView.updatePlayerMoneyDisplay(mainModel.getMoney());
    }

    private void setupAllViewListeners() {
        setupMainMenuViewListeners();
    }

    /**
     * Sets up ActionListeners for buttons specifically on the MainMenuView.
     */
    private void setupMainMenuViewListeners() {
        mainView.getMainMenuView().setManageBindersButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageBindersButtonPressed();
            }
        });

        mainView.getMainMenuView().setManageDecksButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageDecksButtonPressed();
            }
        });

        mainView.getMainMenuView().setManageCollectionButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageCollectionButtonPressed();
            }
        });

        mainView.getMainMenuView().setQuitButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitButtonPressed();
            }
        });

        mainView.getManageCollectionView().setBackButtonActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                backButtonPressed();
            }
        });
    }

    // --- Action methods triggered by MainMenuView buttons ---

    public void manageBindersButtonPressed() {
        System.out.println("Manage Binders Button Pressed");
        mainView.showPanel((mainView.getManageBindersViewString()));
    }
    public void manageDecksButtonPressed() {
        System.out.println("Manage Deck Button Pressed");
        mainView.showPanel((mainView.getManageDecksViewString()));
    }
    public void manageCollectionButtonPressed() {
        System.out.println("Manage Collection Button Pressed");
        mainView.showPanel((mainView.getManageCollectionViewString()));
        manageCollectionController.refreshCardDisplay();
    }
    public void quitButtonPressed() {
       int choice = mainView.showConfirmDialog(
            "Are you sure you want to quit the application?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void backButtonPressed() {
        mainView.showPanel(mainView.getMainMenuViewString());
    }
}