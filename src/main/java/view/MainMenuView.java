package main.java.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.utilities.ViewUtilities;

public class MainMenuView extends JPanel {
    //Main Menu Buttons
    private JButton manageBindersButton;
    private JButton manageDecksButton;
    private JButton manageCollectionButton;
    private JButton quitButton;

    public MainMenuView() {
         setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 70));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(15, 15, 15, 15);
        gridBagConstraints.gridx = 0;

        JLabel titleLabel = new JLabel("Trading Card Inventory System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.ORANGE);
        gridBagConstraints.gridy = 0;
        add(titleLabel, gridBagConstraints);

        manageBindersButton = new JButton("Manage Binders");
        ViewUtilities.styleMainMenuButton(manageBindersButton);
        gridBagConstraints.gridy = 1;
        add(manageBindersButton, gridBagConstraints);

        manageDecksButton = new JButton("Manage Decks");
        ViewUtilities.styleMainMenuButton(manageDecksButton);
        gridBagConstraints.gridy = 2;
        add(manageDecksButton, gridBagConstraints);

        manageCollectionButton = new JButton("Manage Collection");
        ViewUtilities.styleMainMenuButton(manageCollectionButton);
        gridBagConstraints.gridy = 3;
        add(manageCollectionButton, gridBagConstraints);

        quitButton = new JButton("Quit");
        ViewUtilities.styleMainMenuButton(quitButton);
        gridBagConstraints.gridy = 4;
        add(quitButton, gridBagConstraints);
    }



    public void setManageBindersButtonActionListener(ActionListener listener) {
        manageBindersButton.addActionListener(listener);
    }
    
    public void setManageDecksButtonActionListener(ActionListener listener) {
        manageDecksButton.addActionListener(listener);
    }

    public void setManageCollectionButtonActionListener(ActionListener listener) {
        manageCollectionButton.addActionListener(listener);
    }

    public void setQuitButtonActionListener(ActionListener listener) {
        quitButton.addActionListener(listener);
    }
}
