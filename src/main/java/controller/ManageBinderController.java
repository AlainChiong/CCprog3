package main.java.controller;

import javax.swing.JOptionPane;

import main.java.model.MainModel;
import main.java.model.classes.BinderModel;
import main.java.model.classes.CollectorBinder;
import main.java.model.classes.LuxuryBinder;
import main.java.model.classes.PauperBinder;
import main.java.model.classes.RaresBinder;
import main.java.view.MainView;
import main.java.view.binder_views.ManageBindersView;

public class ManageBinderController {
    /**
     * Reference to the main application model, which holds the card collection data.
     */
    private final MainModel mainModel;
    /**
     * Reference to the main application view (JFrame), used for displaying sub-views.
     */
    private final MainView mainView;
    /**
     * Reference to the main application controller, used for general application
     * flow and state changes (e.g., updating user money).
     */
    private final MainController mainController;

    private final ManageBindersView manageBindersView;

    public ManageBinderController(MainModel mainModel, MainView mainView, MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;

        manageBindersView = mainView.getManageBindersView();
    }

    private void setupListeners() {
        manageBindersView.setCreateBinderButtonActionListener(e -> createBinderButtonPressed());
        manageBindersView.setDeleteBinderButtonActionListener(e -> deleteBinderButtonPressed());
        manageBindersView.setViewBinderButtonActionListener(e -> viewBinderButtonPressed());
        manageBindersView.setAddRemoveCardButtonActionListener(e -> addRemoveCardToBinderButtonPressed());
        manageBindersView.setTradeCardButtonActionListener(e -> tradeCardButtonPressed());
        manageBindersView.setSellBinderButtonActionListener(e -> sellBinderButtonPressed());
    }

	private void sellBinderButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'sellDeckButtonPressed'");
	}

	private void tradeCardButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'tradeCardButtonPressed'");
	}

	private void addRemoveCardToBinderButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addRemoveCardToDeckButtonPressed'");
	}

	private void viewBinderButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'viewDeckButtonPressed'");
	}

	private void deleteBinderButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteDeckButtonPressed'");
	}

	private void createBinderButtonPressed() {
		String[] binderTypes = {
            "Non-curated Binder",
            "Pauper Binder",
            "Rares Binder",
            "Luxury Binder",
            "Collector Binder"
        };

        String binderType = (String) JOptionPane.showInputDialog(
            manageBindersView,
            "Select the type of binder to create:",
            "Binder Type",
            JOptionPane.PLAIN_MESSAGE,
            null,
            binderTypes,
            binderTypes[0]
        );

        if (binderType == null) return;

        String name = JOptionPane.showInputDialog(manageBindersView, "Enter a name for the binder:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(manageBindersView, "Binder name cannot be empty.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean duplicate = mainModel.getBinders().stream().anyMatch(b -> b.getName().equalsIgnoreCase(name.trim()));
        if (duplicate) {
            JOptionPane.showMessageDialog(manageBindersView, "A binder with that name already exists.", "Duplicate Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BinderModel newBinder;

        switch (binderType) {
            case "Non-curated Binder":
                newBinder = new BinderModel(name); // already your non-curated binder
                break;
            case "Pauper Binder":
                newBinder = new PauperBinder(name);
                break;
            case "Rares Binder":
                newBinder = new RaresBinder(name);
                break;
            case "Luxury Binder":
                newBinder = new LuxuryBinder(name);
                break;
            case "Collector Binder":
                newBinder = new CollectorBinder(name);
                break;
            default:
                JOptionPane.showMessageDialog(manageBindersView, "Invalid binder type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        mainModel.getBinders().add(newBinder);
        JOptionPane.showMessageDialog(manageBindersView, "Binder created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        refreshBinderDisplay();
    }
}
