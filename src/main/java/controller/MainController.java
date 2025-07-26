package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.java.model.MainModel;
import main.java.view.MainView;

public class MainController {
    private final MainView mainView;
    private final MainModel mainModel;



    public MainController() {
        this.mainView = new MainView("Trading Card Inventory System");
        this.mainModel = new MainModel(this);

        setupAllViewListeners();
    }

    private void setupAllViewListeners() {
        setupMainMenuViewListeners();
        setupManageCollectionViewListeners();
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
    }
    
    /**
     * Sets up ActionListeners for buttons specifically on the ManageCollectionView.
     */
    private void setupManageCollectionViewListeners() {
        mainView.getManageCollectionView().setAddCardButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCardButtonPressed();
            }
        });

        mainView.getManageCollectionView().setModifyCardCountButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyCardCountButtonPressed();
            }
        });

        mainView.getManageCollectionView().setViewCardDetailsButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCardDetailsButtonPressed();
            }
        });

        mainView.getManageCollectionView().setSellCardButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellCardButtonPressed();
            }
        });
        mainView.getManageCollectionView().setSellCardButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellCardButtonPressed();
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
         mainView.showPanel((mainView.getManageBindersViewString()));
    }
    public void manageDecksButtonPressed() {
         mainView.showPanel((mainView.getManageDecksViewString()));
    }
    public void manageCollectionButtonPressed() {
         mainView.showPanel((mainView.getManageCollectionViewString()));
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

    // --- Action methods triggered by ManageCollection buttons ---

    public void addCardButtonPressed() {

    }
    public void modifyCardCountButtonPressed() {
        
    }

    public void viewCardDetailsButtonPressed() {

    }

    public void sellCardButtonPressed() {

    }

    public void backButtonPressed() {
        mainView.showPanel(mainView.getMainMenuViewString());
    }
}