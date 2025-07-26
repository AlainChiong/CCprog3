package main.java.view;

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

public class ManageCollectionView extends JPanel {

    private JButton addCardButton;
    private JButton modifyCardCountButton; 
    private JButton viewCardDetailsButton; 
    private JButton sellCardButton;      
    private JButton backButton;            


    public ManageCollectionView() {

        setLayout(new BorderLayout(10, 10)); 
        setBackground(new Color(50, 50, 80)); 


        JLabel titleLabel = new JLabel("Manage Collection", SwingConstants.CENTER);
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


        addCardButton = new JButton("Add New Card");
        ViewUtilities.styleActionButton(addCardButton); 
        buttonPanel.add(addCardButton, gridBagConstraints); 

        
        gridBagConstraints.gridy++;
        modifyCardCountButton = new JButton("Modify Card Amount");
        ViewUtilities.styleActionButton(modifyCardCountButton);
        buttonPanel.add(modifyCardCountButton, gridBagConstraints);

        
        gridBagConstraints.gridy++;
        viewCardDetailsButton = new JButton("View Card Details");
        ViewUtilities.styleActionButton(viewCardDetailsButton);
        buttonPanel.add(viewCardDetailsButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        sellCardButton = new JButton("Remove Card"); 
        ViewUtilities.styleActionButton(sellCardButton);
        buttonPanel.add(sellCardButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.insets = new Insets(30, 10, 10, 10); 
        backButton = new JButton("Back to Main Menu");
        ViewUtilities.styleBackButton(backButton);
        buttonPanel.add(backButton, gridBagConstraints);


        add(buttonPanel, BorderLayout.EAST);

    }


    public void setAddCardButtonActionListener(ActionListener listener) {
        addCardButton.addActionListener(listener);
    }

    public void setModifyCardCountButtonActionListener(ActionListener listener) {
        modifyCardCountButton.addActionListener(listener);
    }

    public void setViewCardDetailsButtonActionListener(ActionListener listener) {
        viewCardDetailsButton.addActionListener(listener);
    }

    public void setSellCardButtonActionListener(ActionListener listener) { 
        sellCardButton.addActionListener(listener);
    }

    public void setBackButtonActionListener(ActionListener listener) { 
        backButton.addActionListener(listener);
    }
}
