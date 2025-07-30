package main.java.controller;

import main.java.model.MainModel;
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
        manageBindersView.setCreateBinderButtonActionListener(e -> createDeckButtonPressed());
        manageBindersView.setDeleteBinderButtonActionListener(e -> deleteDeckButtonPressed());
        manageBindersView.setViewBinderButtonActionListener(e -> viewDeckButtonPressed());
        manageBindersView.setAddRemoveCardButtonActionListener(e -> addRemoveCardToDeckButtonPressed());
        manageBindersView.setTradeCardButtonActionListener(e -> tradeCardButtonPressed());
        manageBindersView.setSellBinderButtonActionListener(e -> sellDeckButtonPressed());
    }

	private Object sellDeckButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'sellDeckButtonPressed'");
	}

	private Object tradeCardButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'tradeCardButtonPressed'");
	}

	private Object addRemoveCardToDeckButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addRemoveCardToDeckButtonPressed'");
	}

	private Object viewDeckButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'viewDeckButtonPressed'");
	}

	private Object deleteDeckButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteDeckButtonPressed'");
	}

	private Object createDeckButtonPressed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createDeckButtonPressed'");
	}
    
}
