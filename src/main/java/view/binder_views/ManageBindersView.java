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
import java.util.List;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import main.java.model.classes.BinderModel;
import main.java.controller.BinderController;
import main.java.view.BinderView;
import main.java.utilities.ViewUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JPanel bindersContainerPanel;
    private List<BinderController> binderControllers;
    private BinderModel currentSelectedBinderModel = null;

    public ManageBindersView() {
        this.binderControllers = new ArrayList<>();
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        createBinderButton = new JButton("Create New Binder");
        ViewUtilities.styleActionButton(createBinderButton);
        buttonPanel.add(createBinderButton, gbc);

        gbc.gridy++;
        deleteBinderButton = new JButton("Delete Binder");
        ViewUtilities.styleActionButton(deleteBinderButton);
        buttonPanel.add(deleteBinderButton, gbc);

        gbc.gridy++;
        addRemoveCardButton = new JButton("Add/Remove Cards");
        ViewUtilities.styleActionButton(addRemoveCardButton);
        buttonPanel.add(addRemoveCardButton, gbc);

        gbc.gridy++;
        tradeCardButton = new JButton("Trade Card");
        ViewUtilities.styleActionButton(tradeCardButton);
        buttonPanel.add(tradeCardButton, gbc);

        gbc.gridy++;
        viewBinderButton = new JButton("View Binder");
        ViewUtilities.styleActionButton(viewBinderButton);
        buttonPanel.add(viewBinderButton, gbc);

        gbc.gridy++;
        sellBinderButton = new JButton("Sell Binder");
        ViewUtilities.styleActionButton(sellBinderButton);
        sellBinderButton.setEnabled(false);
        buttonPanel.add(sellBinderButton, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        backButton = new JButton("Back to Main Menu");
        ViewUtilities.styleBackButton(backButton);
        buttonPanel.add(backButton, gbc);

        add(buttonPanel, BorderLayout.EAST);

        bindersContainerPanel = new JPanel();
        bindersContainerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
        bindersContainerPanel.setBackground(new Color(50, 50, 80));

        JScrollPane scrollPane = new JScrollPane(bindersContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(50, 50, 80));
        scrollPane.getViewport().setBackground(new Color(50, 50, 80));

        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayBinders(List<BinderModel> binders, ActionListener binderSelectionListener) {
        bindersContainerPanel.removeAll();
        binderControllers.clear();
        currentSelectedBinderModel = null;

        if (binders != null && !binders.isEmpty()) {
            for (BinderModel binder : binders) {
                BinderView binderView = new BinderView(binder);
                // Create the controller and pass a wrapper ActionListener
            ActionListener wrappedListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Send a new ActionEvent where the source is the BinderView
                    binderSelectionListener.actionPerformed(
                        new ActionEvent(binderView, ActionEvent.ACTION_PERFORMED, binder.getName())
                    );
                }
            };

            BinderController controller = new BinderController(binder, binderView, wrappedListener);
                binderControllers.add(controller);
                bindersContainerPanel.add(binderView);
            }
        } else {
            JLabel noBindersLabel = new JLabel("No binders in your collection.");
            noBindersLabel.setForeground(Color.LIGHT_GRAY);
            noBindersLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            bindersContainerPanel.add(noBindersLabel);
        }

        bindersContainerPanel.revalidate();
        bindersContainerPanel.repaint();
    }

    public void setEnableActionButtons(boolean enabled) {
        deleteBinderButton.setEnabled(enabled);
        addRemoveCardButton.setEnabled(enabled);
        tradeCardButton.setEnabled(enabled);
        viewBinderButton.setEnabled(enabled);
        sellBinderButton.setEnabled(enabled);
    }

    public void setSelectedBinder(BinderModel selectedBinder) {
        this.currentSelectedBinderModel = selectedBinder;
        for (BinderController controller : binderControllers) {
            controller.setSelected(controller.getBinderModel().equals(selectedBinder));
        }
        sellBinderButton.setEnabled(selectedBinder != null && selectedBinder.isSellable());
    }

    public BinderModel getSelectedBinder() {
        return currentSelectedBinderModel;
    }

    public List<BinderController> getBinderControllers() {
        return binderControllers;
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
