package main.java.model;

import main.java.model.classes.CollectionModel;

public class MainModel {
    /*
     * 
     */
    private CollectionModel collectionModel;
    /*
     * 
     */
    private double money;

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
}
