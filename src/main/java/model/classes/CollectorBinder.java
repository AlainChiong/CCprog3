package main.java.model.classes;

import main.java.model.enums.Rarity;
import main.java.model.enums.Variant;

public class CollectorBinder extends BinderModel{
    
    public CollectorBinder(String name){
        super(name);
        this.typeName = "Collector Binder";
        this.type = "trade";
    }

    public boolean addCardB(CardModel card) {
        String rarity = card.getRarity().toString();
        String variant = card.getVariant().toString();
        if (!(rarity.equalsIgnoreCase("Rare") || rarity.equalsIgnoreCase("Legendary")) || variant.equalsIgnoreCase("Normal")) {
            System.out.println("Only Rare/Legendary cards with non-Normal variants can be added to this Collector Binder.");
            return false;
        }
        return super.addCardB(card);
    }

    @Override
    public boolean isCardAllowed(CardModel card) {
        return (card.getRarity() == Rarity.RARE || card.getRarity() == Rarity.LEGENDARY)
            && card.getVariant() != Variant.NORMAL;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }
}
