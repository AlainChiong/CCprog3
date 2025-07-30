package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.java.model.MainModel;
import main.java.view.MainView;
import main.java.view.collection_views.ManageCollectionView;
import main.java.view.deck_views.ManageDecksView;
import main.java.view.binder_views.ManageBindersView;

/**
 * The {@code MainController} serves as the central orchestrator of the Trading Card Inventory System application.
 * It initializes the main model and view, manages application flow, and delegates responsibilities
 * to sub-controllers for managing the collection, decks, and binders.
 * <p>
 * It also handles global UI events and navigation between various views.
 */
public class MainController {

    /**
     * Reference to the main application window, which manages the display of different panels.
     */
    private final MainView mainView;

    /**
     * Reference to the core data model holding application state such as the player's card inventory and money.
     */
    private final MainModel mainModel;

    /**
     * Controller responsible for managing the user's card collection.
     */
    private ManageCollectionController manageCollectionController;

    /**
     * Controller responsible for managing the user's card binders.
     */
    private ManageBinderController manageBinderController;

    /**
     * Controller responsible for managing the user's card decks.
     */
    private ManageDeckController manageDeckController;

    /**
     * Constructs a {@code MainController}.
     * <p>
     * Initializes the model and view, creates sub-controllers, wires up all necessary listeners,
     * and updates the initial money display.
     */
    public MainController() {
        this.mainView = new MainView("Trading Card Inventory System");
        this.mainModel = new MainModel();

        // Instantiate sub-controllers
        this.manageCollectionController = new ManageCollectionController(mainModel, mainView, this);
        this.manageDeckController = new ManageDeckController(mainModel, mainView, this);
        this.manageBinderController = new ManageBinderController(mainModel, mainView, this);

        setupAllViewListeners();

        // Initialize UI with current money value
        mainView.updatePlayerMoneyDisplay(mainModel.getMoney());
    }

    /**
     * Wires up all UI event listeners across the application.
     * This method serves as a central place to attach behavior to UI components.
     */
    private void setupAllViewListeners() {
        setupMainMenuViewListeners();
    }

    /**
     * Sets up {@link ActionListener} instances for buttons located in the main menu view
     * and sub-view "back" buttons that return to the main menu.
     */
    private void setupMainMenuViewListeners() {
        // Main menu navigation buttons
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

        // Back buttons in sub-views
        mainView.getManageCollectionView().setBackButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButtonPressed();
            }
        });

        mainView.getManageDecksView().setBackButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButtonPressed();
            }
        });

        mainView.getManageBindersView().setBackButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButtonPressed();
            }
        });
    }

    // --- Navigation Action Methods ---

    /**
     * Displays the binders management panel in the main view.
     */
    public void manageBindersButtonPressed() {
        System.out.println("Manage Binders Button Pressed");
        mainView.showPanel(mainView.getManageBindersViewString());
    }

    /**
     * Displays the decks management panel in the main view.
     */
    public void manageDecksButtonPressed() {
        System.out.println("Manage Deck Button Pressed");
        mainView.showPanel(mainView.getManageDecksViewString());
    }

    /**
     * Displays the collection management panel and refreshes the card display
     * to reflect the latest data from the model.
     */
    public void manageCollectionButtonPressed() {
        System.out.println("Manage Collection Button Pressed");
        mainView.showPanel(mainView.getManageCollectionViewString());
        manageCollectionController.refreshCardDisplay();
    }

    /**
     * Prompts the user for confirmation to quit the application.
     * If confirmed, the application exits.
     */
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

    /**
     * Returns to the main menu panel from any sub-view.
     */
    public void backButtonPressed() {
        mainView.showPanel(mainView.getMainMenuViewString());
    }

    /**
     * Updates the player's money in the model and refreshes the displayed value in the view.
     *
     * @param money the new money value to be set
     */
    public void setMoney(double money) {
        mainModel.setMoney(money);
        mainView.updatePlayerMoneyDisplay(money);
    }
}