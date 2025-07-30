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
 * The `MainController` serves as the central orchestrator of the Trading Card Inventory System application.
 * It initializes the main model and view, manages the overall flow of the application,
 * and delegates specific responsibilities to sub-controllers (e.g., for managing collections, binders, and decks).
 * It handles global UI events and coordinates navigation between different application views.
 */
public class MainController {
    /**
     * Reference to the main application window, {@link MainView}, which manages the display of different panels.
     */
    private final MainView mainView;

    /**
     * Reference to the main application data model, {@link MainModel}, which holds the core application state.
     */
    private final MainModel mainModel;

    /**
     * The sub-controller responsible for managing the user's card collection.
     */
    private ManageCollectionController manageCollectionController;

    /**
     * The sub-controller responsible for managing the user's card binders.
     */
    private ManageBinderController manageBinderController;

    /**
     * The sub-controller responsible for managing the user's card decks.
     */
    private ManageDeckController manageDeckController;

    /**
     * Constructs the `MainController`.
     * Initializes the {@link MainView} and {@link MainModel}. It then creates instances
     * of all necessary sub-controllers, passing them references to the main model, main view,
     * and itself. Finally, it sets up all action listeners for various view components
     * and updates the initial player money display.
     */
    public MainController() {
        this.mainView = new MainView("Trading Card Inventory System");
        this.mainModel = new MainModel();

        // Initialize sub-controllers, passing necessary references for them to operate
        this.manageCollectionController = new ManageCollectionController(mainModel, mainView, this);
        this.manageDeckController = new ManageDeckController(mainModel, mainView, this);
        this.manageBinderController = new ManageBinderController(mainModel, mainView, this);

        setupAllViewListeners(); // Set up all GUI event listeners

        // Update initial display for player money
        mainView.updatePlayerMoneyDisplay(mainModel.getMoney());
    }

    /**
     * Sets up all `ActionListener` instances for various buttons and interactive elements
     * across different views in the application. This method serves as a central point
     * for wiring up UI events.
     */
    private void setupAllViewListeners() {
        setupMainMenuViewListeners();
    }

    /**
     * Sets up `ActionListener` instances specifically for buttons found on the
     * {@link MainMenuView}. This includes navigation buttons to other sections
     * of the application and the quit button. It also sets up "back" button listeners
     * for sub-views that return to the main menu.
     */
    private void setupMainMenuViewListeners() {
        // Main Menu Button Listeners
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

        // Back Button Listeners for sub-views that navigate back to Main Menu
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

    // --- Action methods triggered by MainMenuView and sub-views buttons ---

    /**
     * Handles the action when the "Manage Binders" button is pressed.
     * It instructs the {@link MainView} to display the binders management panel.
     */
    public void manageBindersButtonPressed() {
        System.out.println("Manage Binders Button Pressed"); // For debugging/console output
        mainView.showPanel((mainView.getManageBindersViewString()));
    }

    /**
     * Handles the action when the "Manage Decks" button is pressed.
     * It instructs the {@link MainView} to display the decks management panel.
     */
    public void manageDecksButtonPressed() {
        System.out.println("Manage Deck Button Pressed"); // For debugging/console output
        mainView.showPanel((mainView.getManageDecksViewString()));
    }

    /**
     * Handles the action when the "Manage Collection" button is pressed.
     * It instructs the {@link MainView} to display the collection management panel
     * and triggers the {@link ManageCollectionController} to refresh the card display.
     */
    public void manageCollectionButtonPressed() {
        System.out.println("Manage Collection Button Pressed"); // For debugging/console output
        mainView.showPanel((mainView.getManageCollectionViewString()));
        manageCollectionController.refreshCardDisplay(); // Ensure cards are up-to-date
    }

    /**
     * Handles the action when the "Quit" button is pressed.
     * It displays a confirmation dialog to the user, and if confirmed, exits the application.
     */
    public void quitButtonPressed() {
        int choice = mainView.showConfirmDialog(
                "Are you sure you want to quit the application?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0); // Terminate the application
        }
    }

    /**
     * Handles the action when a "Back" button is pressed from a sub-view.
     * It instructs the {@link MainView} to switch back to the main menu panel.
     */
    public void backButtonPressed() {
        mainView.showPanel(mainView.getMainMenuViewString());
    }

    /**
     * Sets the player's current money amount in the {@link MainModel} and
     * updates the money display in the {@link MainView}.
     * This method provides a way for sub-controllers or other components to
     * update the global money display.
     *
     * @param money The new money amount to set.
     */
    public void setMoney(double money) {
        mainModel.setMoney(money);
        mainView.updatePlayerMoneyDisplay(money);
    }
}