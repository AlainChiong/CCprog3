package main.java.model;

import main.java.model.classes.CollectionModel;

import java.util.ArrayList;
import java.util.List;

import main.java.model.classes.BinderModel;
import main.java.model.classes.DeckModel;

public class MainModel {
    /*
     * 
     */
    private CollectionModel collectionModel;
    /*
     * 
     */
    private double money;

    private List<BinderModel> binders = new ArrayList<>();
    private List<DeckModel> decks = new ArrayList<>();

    /**
     * Constructor for MainModel.
     * Initializes all primary data components, such as the user's card collection.
     */
    public MainModel() {
        this.collectionModel = new CollectionModel();
        money = 0.0;
    }

    /**
     * Provides access to the user's card collection.
     * Controllers will use this getter to interact with the collection data.
     * @return The CollectionModel object.
     */
    public CollectionModel getCollectionModel() {
        return collectionModel;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public List<DeckModel> getDecks() {
        return decks;
    }

    public void setDecks(List<DeckModel> decks) {
        this.decks = decks;
    }

    // New getters/setters for binders
    public List<BinderModel> getBinders() {
        return binders;
    }

    public void setBinders(List<BinderModel> binders) {
        this.binders = binders;
    }
}
