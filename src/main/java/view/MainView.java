package main.java.view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.controller.*;

public class MainView extends JFrame {

    private final String mainMenuViewString = "MainMenuView";
    private final String manageBindersViewString = "ManageBindersView";
    private final String manageDecksViewString = "ManageDecksView";
    private final String manageCollectionViewString = "ManageCollectionViews";

    private MainMenuView mainMenuView;
    private ManageBindersView manageBindersView;
    private ManageDecksView manageDecksView;
    private ManageCollectionView manageCollectionView;

    private JPanel cardPanelContainer;
    private CardLayout cardLayout;


    public MainView(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        setVisible(true);


        cardLayout = new CardLayout();
        cardPanelContainer = new JPanel(cardLayout);
        add(cardPanelContainer);

        mainMenuView = new MainMenuView();
        manageBindersView = new ManageBindersView();
        manageDecksView = new ManageDecksView();
        manageCollectionView = new ManageCollectionView();
    
        cardPanelContainer.add(mainMenuView, mainMenuViewString);
        cardPanelContainer.add(manageBindersView, manageBindersViewString);
        cardPanelContainer.add(manageDecksView, manageDecksViewString);
        cardPanelContainer.add(manageCollectionView, manageCollectionViewString);
    }

    public String getMainMenuViewString() {
        return mainMenuViewString;
    }

    public String getManageBindersViewString() {
        return manageBindersViewString;
    }

    public String getManageDecksViewString() {
        return manageDecksViewString;
    }

    public String getManageCollectionViewString() {
        return manageCollectionViewString;
    }

    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }

    public ManageBindersView getManageBindersView() {
        return manageBindersView;
    }

    public ManageDecksView getManageDecksView() {
        return manageDecksView;
    }

    public ManageCollectionView getManageCollectionView() {
        return manageCollectionView;
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanelContainer, panelName);
        cardPanelContainer.requestFocusInWindow();
    }
    // --- Utility methods for displaying dialogs ---

    public void showMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public int showConfirmDialog(String message, String title, int optionType, int messageType) {
        return JOptionPane.showConfirmDialog(this, message, title, optionType, messageType);
    }
}
