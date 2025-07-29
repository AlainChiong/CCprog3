package main.java.view.collection_views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import main.java.controller.CardController;
import main.java.model.classes.CardModel;
import main.java.utilities.ViewUtilities;
import main.java.view.CardView;

/**
 * The `ManageCollectionView` class represents the graphical user interface for managing
 * a collection of cards. It displays a list of cards and provides buttons for various
 * actions such as adding new cards, modifying card amounts, viewing card details,
 * and selling cards. It is designed to be managed by the `ManageCollectionController`.
 *
 * This view includes:
 * <ul>
 * <li>A title label.</li>
 * <li>Action buttons: "Add New Card", "Modify Card Amount", "View Card Details", "Sell Card", "Back to Main Menu".</li>
 * <li>A scrollable panel (`cardsContainerPanel`) to dynamically display individual {@link CardView} components.</li>
 * </ul>
 */
public class ManageCollectionView extends JPanel {

    // Action Buttons
    /**
     * Button to initiate the process of adding a new card to the collection.
     */
    private JButton addCardButton;
    /**
     * Button to modify the quantity of a selected card in the collection.
     */
    private JButton modifyCardCountButton;
    /**
     * Button to display detailed information about a selected card.
     */
    private JButton viewCardDetailsButton;
    /**
     * Button to initiate the process of selling a selected card from the collection.
     */
    private JButton sellCardButton;
    /**
     * Button to navigate back to the main menu of the application.
     */
    private JButton backButton;

    // Panel to display the cards
    /**
     * A `JPanel` that acts as a container for displaying individual {@link CardView} components.
     * It uses a `FlowLayout` to arrange cards.
     */
    private JPanel cardsContainerPanel;
    /**
     * A list of {@link CardController} instances, each managing a `CardView` and its associated `CardModel`.
     * This list helps in managing the display and selection state of individual cards.
     */
    private List<CardController> cardControllers;
    /**
     * Stores the currently selected {@link CardModel} from the displayed collection.
     * This model is used by action buttons that operate on a single selected card.
     */
    private CardModel currentSelectedCardModel = null;

    /**
     * Constructs a new `ManageCollectionView`.
     * Initializes the panel's layout, background color, and calls `layoutComponents()`
     * to arrange all UI elements.
     */
    public ManageCollectionView() {
        this.cardControllers = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(50, 50, 80));

        layoutComponents();
    }

    /**
     * Lays out and styles all the Swing components within this `ManageCollectionView`.
     * This includes the title, action buttons, and the scrollable card display area.
     * Components are arranged using `BorderLayout` and `GridBagLayout`.
     */
    public void layoutComponents() {
        //TODO put style in ViewUtilities
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
        sellCardButton = new JButton("Sell Card");
        ViewUtilities.styleActionButton(sellCardButton);
        buttonPanel.add(sellCardButton, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.insets = new Insets(30, 10, 10, 10);
        backButton = new JButton("Back to Main Menu");
        ViewUtilities.styleBackButton(backButton);
        buttonPanel.add(backButton, gridBagConstraints);

        add(buttonPanel, BorderLayout.EAST);

        cardsContainerPanel = new JPanel();
        cardsContainerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        cardsContainerPanel.setBackground(new Color(50, 50, 80));
        // Scroll Pane for cards (crucial for many cards)
        JScrollPane scrollPane = new JScrollPane(cardsContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(50, 50, 80));
        scrollPane.getViewport().setBackground(new Color(50, 50, 80));

        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the Center
    }

    /**
     * Enables or disables the action buttons related to modifying, viewing, or selling cards.
     * The "Add New Card" and "Back to Main Menu" buttons are always enabled regardless of collection state.
     *
     * @param enable `true` to enable the buttons, `false` to disable them.
     */
    public void setEnableActionButtons(boolean enable) {
        modifyCardCountButton.setEnabled(enable);
        viewCardDetailsButton.setEnabled(enable);
        sellCardButton.setEnabled(enable);
        // addCardButton and backButton are always enabled
    }

    /**
     * Sets the `ActionListener` for the "Add New Card" button.
     *
     * @param listener The `ActionListener` to be registered for button clicks.
     */
    public void setAddCardButtonActionListener(ActionListener listener) {
        addCardButton.addActionListener(listener);
    }

    /**
     * Sets the `ActionListener` for the "Modify Card Amount" button.
     *
     * @param listener The `ActionListener` to be registered for button clicks.
     */
    public void setModifyCardCountButtonActionListener(ActionListener listener) {
        modifyCardCountButton.addActionListener(listener);
    }

    /**
     * Sets the `ActionListener` for the "View Card Details" button.
     *
     * @param listener The `ActionListener` to be registered for button clicks.
     */
    public void setViewCardDetailsButtonActionListener(ActionListener listener) {
        viewCardDetailsButton.addActionListener(listener);
    }

    /**
     * Sets the `ActionListener` for the "Sell Card" button.
     *
     * @param listener The `ActionListener` to be registered for button clicks.
     */
    public void setSellCardButtonActionListener(ActionListener listener) {
        sellCardButton.addActionListener(listener);
    }

    /**
     * Sets the `ActionListener` for the "Back to Main Menu" button.
     *
     * @param listener The `ActionListener` to be registered for button clicks.
     */
    public void setBackButtonActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    /**
     * Displays a list of `CardModel` objects by creating and adding
     * {@link CardView} and {@link CardController} instances for each.
     * This method clears any existing cards displayed and repopulates the panel.
     * If the `cards` list is empty, a message indicating an empty collection is displayed.
     *
     * @param cards The `List` of {@link CardModel} objects to display.
     * @param cardSelectionListener The `ActionListener` (typically from `ManageCollectionController`)
     * that will be notified when an individual `CardView` is clicked for selection.
     */
    public void displayCards(List<CardModel> cards, ActionListener cardSelectionListener) {
        cardsContainerPanel.removeAll(); // Clear existing card panels
        cardControllers.clear();       // Clear existing controllers
        currentSelectedCardModel = null; // Clear previous selection

        if (cards != null && !cards.isEmpty()) {
            for (CardModel card : cards) {
                CardView cardView = new CardView(card);

                CardController cardController = new CardController(card, cardView, cardSelectionListener);

                cardControllers.add(cardController);

                cardsContainerPanel.add(cardView);
            }
        } else {
            // Display a message if there are no cards
            JLabel noCardsLabel = new JLabel("No cards in your collection. Add some!");
            noCardsLabel.setForeground(Color.LIGHT_GRAY); // Style for the message
            noCardsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            cardsContainerPanel.add(noCardsLabel);
        }

        // Revalidate and repaint to ensure the new components are displayed correctly
        cardsContainerPanel.revalidate();
        cardsContainerPanel.repaint();
    }

    /**
     * Updates the selection state across all displayed {@link CardView} components.
     * This method is called by the `ManageCollectionController` whenever a card's selection changes.
     * It highlights the `CardView` corresponding to the `selectedModel` and unhighlights others.
     *
     * @param selectedModel The {@link CardModel} that is currently selected (or `null` if no card is selected).
     */
    public void setSelectedCard(CardModel selectedModel) {
        this.currentSelectedCardModel = selectedModel;

        for (CardController controller : cardControllers) {
            // Set the selected state for each CardView based on whether its model matches the selectedModel
            controller.setSelected(controller.getCardModel().equals(selectedModel));
        }
    }

    /**
     * Returns the {@link CardModel} of the currently selected card in the view.
     *
     * @return The currently selected {@link CardModel}, or `null` if no card is selected.
     */
    public CardModel getSelectedCard() {
        return currentSelectedCardModel;
    }
}