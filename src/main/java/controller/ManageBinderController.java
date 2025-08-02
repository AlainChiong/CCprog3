package main.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main.java.model.MainModel;
import main.java.model.classes.BinderModel;
import main.java.model.classes.CardModel;
import main.java.model.classes.CollectionModel;
import main.java.model.classes.CollectorBinder;
import main.java.model.classes.LuxuryBinder;
import main.java.model.classes.PauperBinder;
import main.java.model.classes.RaresBinder;
import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;
import main.java.view.BinderView;
import main.java.view.MainView;
import main.java.view.binder_views.ManageBindersView;
import main.java.view.collection_views.AddCardView;

/**
 * Controller responsible for managing binders and their interactions
 * within the application, such as creating, deleting, trading, and modifying binders.
 */
public class ManageBinderController {

    /** Reference to the main model that holds application data. */
    private final MainModel mainModel;

    /** Reference to the main application view. */
    private final MainView mainView;

    /** Reference to the main application controller. */
    private final MainController mainController;

    /** The view used for managing binders. */
    private final ManageBindersView manageBindersView;

    /**
     * Constructs a ManageBinderController to manage binder operations.
     *
     * @param mainModel The application model.
     * @param mainView The main view of the application.
     * @param mainController The central controller for application state.
     */
    public ManageBinderController(MainModel mainModel, MainView mainView, MainController mainController) {
        this.mainModel = mainModel;
        this.mainView = mainView;
        this.mainController = mainController;

        manageBindersView = mainView.getManageBindersView();
        setupListeners();
    }

    /** Sets up all the listeners for UI actions related to binders. */
    private void setupListeners() {
        manageBindersView.setCreateBinderButtonActionListener(e -> createBinderButtonPressed());
        manageBindersView.setDeleteBinderButtonActionListener(e -> deleteBinderButtonPressed());
        manageBindersView.setViewBinderButtonActionListener(e -> viewBinderButtonPressed());
        manageBindersView.setAddRemoveCardButtonActionListener(e -> addRemoveCardToBinderButtonPressed());
        manageBindersView.setTradeCardButtonActionListener(e -> tradeCardButtonPressed());
        manageBindersView.setSellBinderButtonActionListener(e -> sellBinderButtonPressed());
    }

    /** Refreshes the display of binders and updates selection state. */
    private void refreshBinderDisplay() {
        manageBindersView.displayBinders(mainModel.getBinders(), e -> {
            BinderView clickedView = (BinderView) e.getSource();

            for (BinderController controller : manageBindersView.getBinderControllers()) {
                boolean isSelected = controller.getBinderView() == clickedView;
                controller.setSelected(isSelected);
                if (isSelected) {
                    manageBindersView.setSelectedBinder(controller.getBinderModel());
                }
            }
        });
    }
    /** Handles the action when the user chooses to sell a binder. */
	private void sellBinderButtonPressed() {
		BinderModel selected = manageBindersView.getSelectedBinder();
        if (selected == null) {
            JOptionPane.showMessageDialog(manageBindersView, "Please select a binder to sell.", "No Binder Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!selected.isSellable()) {
            JOptionPane.showMessageDialog(manageBindersView, "This binder type cannot be sold.", "Not Sellable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            manageBindersView,
            "Are you sure you want to sell this binder?\nAll cards in it will be lost permanently.",
            "Confirm Sell",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        double totalValue = 0.0;
        CollectionModel collection = mainModel.getCollectionModel();

        for (CardModel cardInBinder : selected.getCards()) {
            totalValue += cardInBinder.getValue();

            // Check if this card still exists in collection with amount == 0
            CardModel inCollection = collection.getMatchingCard(cardInBinder);
            if (inCollection != null && inCollection.getAmount() == 0) {
                collection.removeCard(inCollection);
            }
        }

        // Apply binder's custom sell multiplier
        totalValue *= selected.getSellMultiplier();

        // Update user balance
        double currentMoney = mainModel.getMoney();
        mainModel.setMoney(currentMoney + totalValue);
        mainController.setMoney(mainModel.getMoney());

        // Remove binder and its cards
        selected.getCards().clear();
        mainModel.getBinders().remove(selected);

        JOptionPane.showMessageDialog(
            manageBindersView,
            "Binder sold for $" + String.format("%.2f", totalValue),
            "Binder Sold",
            JOptionPane.INFORMATION_MESSAGE
        );

        refreshBinderDisplay();
	}
    /** Handles trading a card from a binder with a new card. */
	private void tradeCardButtonPressed() {
		BinderModel selectedBinder = manageBindersView.getSelectedBinder();
        if (selectedBinder == null) {
            JOptionPane.showMessageDialog(manageBindersView, "Please select a binder to trade from.", "No Binder Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!selectedBinder.isTradeable()) {
            JOptionPane.showMessageDialog(manageBindersView, "This binder does not support trading.", "Trading Not Allowed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedBinder.getCards().isEmpty()) {
            JOptionPane.showMessageDialog(manageBindersView, "This binder has no cards to trade.", "Empty Binder", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Pick card to trade away
        List<CardModel> binderCards = selectedBinder.getCards();
        String[] cardOptions = binderCards.stream()
            .map(card -> card.getName() + " | " + card.getRarity() + " | " + card.getVariant())
            .toArray(String[]::new);

        String selectedCardStr = (String) JOptionPane.showInputDialog(
            manageBindersView,
            "Select a card to trade away:",
            "Select Card",
            JOptionPane.PLAIN_MESSAGE,
            null,
            cardOptions,
            cardOptions[0]
        );

        if (selectedCardStr == null) return;

        CardModel cardToTrade = binderCards.get(java.util.Arrays.asList(cardOptions).indexOf(selectedCardStr));

        // Create a new card to trade in
        AddCardView addCardView = new AddCardView(); // dialog

        int result = JOptionPane.showConfirmDialog(
            manageBindersView,
            addCardView,
            "Create Incoming Card",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return;

        String name = addCardView.getCardName();
        Rarity rarity = addCardView.getRaritySelection();
        Variant variant = addCardView.getVariantSelection();
        String valueStr = addCardView.getValueText();

        if (name.trim().isEmpty() || valueStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(manageBindersView, "Card Name and Base Value are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double value;
        try {
            value = Double.parseDouble(valueStr);
            if (value <= 0) {
                JOptionPane.showMessageDialog(manageBindersView, "Base Value must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(manageBindersView, "Invalid Base Value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CardModel newCard = new CardModel(name.trim(), rarity, variant, value);

        // Enforce binder restriction
        if (!selectedBinder.isCardAllowed(newCard)) {
            JOptionPane.showMessageDialog(manageBindersView, "This card does not meet the binder's restrictions.", "Restriction Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Perform the trade ---

        // 1. Remove one copy of the old card from the binder
        selectedBinder.removeCard(cardToTrade.getName());

        // 2. Adjust collection for the traded-away card
        CardModel collectionMatch = CollectionModel.findCardInCollection(mainModel.getCollectionModel(), cardToTrade.getName());
        if (collectionMatch != null && collectionMatch.matches(cardToTrade)) {
            double currentAmount = collectionMatch.getAmount();
            if (currentAmount > 1) {
                collectionMatch.setAmount(currentAmount - 1);
            } else {
                mainModel.getCollectionModel().removeCard(collectionMatch); // remove if now 0
            }
        }

        // 3. Add the new card to the binder
        selectedBinder.addCardB(newCard);

        // 4. If new card doesn't exist in collection, add with amount 0
        CardModel match = CollectionModel.findCardInCollection(mainModel.getCollectionModel(), newCard.getName());
        boolean existsInCollection = match != null && match.matches(newCard);

        if (!existsInCollection) {
            CardModel seenCard = new CardModel(newCard.getName(), newCard.getRarity(), newCard.getVariant(), newCard.getBaseValue());
            seenCard.setAmount(0); // seen but not owned
            mainModel.getCollectionModel().addCard(seenCard);
        }

        JOptionPane.showMessageDialog(manageBindersView, "Card traded successfully!", "Trade Complete", JOptionPane.INFORMATION_MESSAGE);
        refreshBinderDisplay();
	}
    /** Handles adding or removing a card from the selected binder. */
	private void addRemoveCardToBinderButtonPressed() {
		BinderModel selectedBinder = manageBindersView.getSelectedBinder();
        if (selectedBinder == null) {
            JOptionPane.showMessageDialog(manageBindersView, "Please select a binder to modify.", "No Binder Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CollectionModel collection = mainModel.getCollectionModel();
        List<CardModel> allCards = collection.getCardsSortedByName();

        if (allCards.isEmpty()) {
            JOptionPane.showMessageDialog(manageBindersView, "Your collection is empty.", "No Cards", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = {"Add Card", "Remove Card"};
        int action = JOptionPane.showOptionDialog(manageBindersView, "Would you like to add or remove a card?",
                "Modify Binder", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (action == -1) return;

        List<CardModel> candidateCards;

        if (action == 0) { // Add
            candidateCards = allCards.stream()
                .filter(card -> card.getAmount() > 0)
                .toList();
        } else { // Remove
            candidateCards = new ArrayList<>(selectedBinder.getCards());
        }

        if (candidateCards.isEmpty()) {
            String msg = (action == 0) ? "No cards with available copies to add." : "This binder has no cards to remove.";
            JOptionPane.showMessageDialog(manageBindersView, msg, "No Cards", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cardNames = candidateCards.stream()
                .map(card -> card.getName() + " | " + card.getRarity() + " | " + card.getVariant())
                .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(manageBindersView,
                "Choose a card to " + (action == 0 ? "add" : "remove") + ":",
                "Select Card", JOptionPane.PLAIN_MESSAGE, null, cardNames, cardNames[0]);

        if (selected == null) return;

        CardModel chosenCard = candidateCards.get(java.util.Arrays.asList(cardNames).indexOf(selected));

        if (action == 0) { // Add
            if (selectedBinder.getCards().size() >= 20) {
                JOptionPane.showMessageDialog(manageBindersView, "Binder is already full (max 20 cards).", "Binder Full", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!selectedBinder.isCardAllowed(chosenCard)) {
                JOptionPane.showMessageDialog(manageBindersView, "This card does not meet the binder's restrictions.", "Invalid Card", JOptionPane.WARNING_MESSAGE);
                return;
            }

            selectedBinder.addCardB(chosenCard);
            chosenCard.setAmount(chosenCard.getAmount() - 1);
            JOptionPane.showMessageDialog(manageBindersView, "Card added to binder.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } else { // Remove
            selectedBinder.removeCard(chosenCard.getName());

            CardModel existingInCollection = CollectionModel.findCardInCollection(collection, chosenCard.getName());
            if (existingInCollection != null && existingInCollection.matches(chosenCard)) {
                existingInCollection.setAmount(existingInCollection.getAmount() + 1);
            } else {
                CardModel copy = new CardModel(chosenCard.getName(), chosenCard.getRarity(), chosenCard.getVariant(), chosenCard.getBaseValue());
                copy.setAmount(1);
                collection.addCard(copy);
            }

            JOptionPane.showMessageDialog(manageBindersView, "Card removed from binder.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        refreshBinderDisplay();
	}
    /** Displays the contents of the selected binder in a dialog. */
	private void viewBinderButtonPressed() {
		BinderModel selected = manageBindersView.getSelectedBinder();
        if (selected == null) {
            JOptionPane.showMessageDialog(manageBindersView, "Please select a binder to view.", "No Binder Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder binderContents = new StringBuilder("Binder: " + selected.getName() + "\n\nCards:\n");
        double totalValue = 0.0;

        if (selected.getCards().isEmpty()) {
            binderContents.append("No cards in this binder.");
        } else {
            for (CardModel card : selected.getCards()) {
                double amount = card.getAmount(); // how many copies of this card
                double value = card.getBaseValue();
                double subtotal = value * amount;
                totalValue += subtotal;

                binderContents.append("- Name: ").append(card.getName()).append("\n")
                            .append("  Rarity: ").append(card.getRarity()).append("\n")
                            .append("  Variant: ").append(card.getVariant()).append("\n")
                            .append("  Copies: ").append(amount).append("\n")
                            .append("  Value each: $").append(String.format("%.2f", value)).append("\n")
                            .append("  Total value: $").append(String.format("%.2f", subtotal)).append("\n\n");
            }

            binderContents.append("Total Binder Value: $").append(String.format("%.2f", totalValue));
        }

        JOptionPane.showMessageDialog(manageBindersView, binderContents.toString(), "View Binder", JOptionPane.INFORMATION_MESSAGE);
	}
    /** Handles deleting the selected binder and returning its cards to the collection. */
	private void deleteBinderButtonPressed() {
		BinderModel selectedBinder = manageBindersView.getSelectedBinder();

        if (selectedBinder == null) {
            JOptionPane.showMessageDialog(manageBindersView, "Please select a binder to delete.", "No Binder Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            manageBindersView,
            "Are you sure you want to delete the binder \"" + selectedBinder.getName() + "\"?\n" +
            "All cards will be returned to your collection.",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        // Move all cards back to the collection
        selectedBinder.getCards().forEach(card -> mainModel.getCollectionModel().addCard(card));

        // Remove binder from model
        mainModel.getBinders().remove(selectedBinder);

        // Show confirmation
        JOptionPane.showMessageDialog(manageBindersView, "Binder deleted and cards returned to collection.", "Binder Deleted", JOptionPane.INFORMATION_MESSAGE);

        // Refresh the view
        refreshBinderDisplay();
	}
    /** Creates a new binder based on user input. */
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
