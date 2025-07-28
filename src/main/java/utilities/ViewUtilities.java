package main.java.utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;


/*
 * A Utility Class For Styling Views
 */
public class ViewUtilities {
    /**
     * Applies default styling for main menu buttons.
     * @param button The JButton to style.
     */
    public static void styleMainMenuButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(280, 70));
        button.setBackground(new Color(60, 90, 150));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(40, 60, 120), 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Applies default styling for "Back" buttons in sub-views.
     * @param button The JButton to style.
     */
    public static void styleBackButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(100, 130, 100)); // Lighter shade of green/blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(70, 100, 70), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void styleActionButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(250, 60));
        button.setBackground(new Color(80, 80, 130));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 100), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    



    public static void styleTextField(JTextField textField) {

    }

    public static void styleComboBox(JComboBox<?> comboBox) {
    }

    public static void styleSpinner(JSpinner spinner) {
 
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();

            textField.setHorizontalAlignment(JTextField.LEADING); 
        }
    }

}
