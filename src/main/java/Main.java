package main.java;

import main.java.controller.MainController;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String args[]) {

        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    new MainController();
                }
            }
        );
    }     
}