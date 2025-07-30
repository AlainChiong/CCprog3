package main.java.view.binder_views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.utilities.ViewUtilities;
    /*
     * 
     */
public class ManageBindersView extends JPanel {

    private JButton createBinderButton;
    private JButton deleteBinderButton;
    private JButton addRemoveCardButton;
    private JButton tradeCardButton;
    private JButton viewBinderButton;
    private JButton sellBinderButton;
    private JButton backButton;
    public ManageBindersView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(50, 50, 80));
        layoutComponents();
    }

    public void layoutComponents() {
        JLabel titleLabel = new JLabel("Manage Binders", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(50, 50, 80));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;

        createBinderButton = new JButton("Create New Binder");
        ViewUtilities.styleActionButton(createBinderButton);
        buttonPanel.add(createBinderButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        deleteBinderButton = new JButton("Delete Binder");
        ViewUtilities.styleActionButton(deleteBinderButton);
        buttonPanel.add(deleteBinderButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        addRemoveCardButton = new JButton("Add/Remove Cards");
        ViewUtilities.styleActionButton(addRemoveCardButton);
        buttonPanel.add(addRemoveCardButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        addRemoveCardButton = new JButton("Trade Card");
        ViewUtilities.styleActionButton(addRemoveCardButton);
        buttonPanel.add(addRemoveCardButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        viewBinderButton = new JButton("View Binder");
        ViewUtilities.styleActionButton(viewBinderButton);
        buttonPanel.add(viewBinderButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        sellBinderButton = new JButton("Sell Binder");
        ViewUtilities.styleActionButton(sellBinderButton);
        sellBinderButton.setEnabled(false); // Only enable when a sellable deck is selected
        buttonPanel.add(sellBinderButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.insets = new Insets(30, 10, 10, 10);
        backButton = new JButton("Back to Main Menu");
        ViewUtilities.styleBackButton(backButton);
        buttonPanel.add(backButton, gridBagConstraints);

        add(buttonPanel, BorderLayout.EAST);
    }

    public void setCreateBinderButtonActionListener(ActionListener listener) {
        createBinderButton.addActionListener(listener);
    }
    public void setDeleteBinderButtonActionListener(ActionListener listener) {
        deleteBinderButton.addActionListener(listener);
    }
    public void setAddRemoveCardButtonActionListener(ActionListener listener) {
        addRemoveCardButton.addActionListener(listener);
    }
    public void setTradeCardButtonActionListener(ActionListener listener) {
        tradeCardButton.addActionListener(listener);
    }
    public void setViewBinderButtonActionListener(ActionListener listener) {
        viewBinderButton.addActionListener(listener);
    }
    public void setSellBinderButtonActionListener(ActionListener listener) {
        sellBinderButton.addActionListener(listener);
    }
    public void setBackButtonActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
