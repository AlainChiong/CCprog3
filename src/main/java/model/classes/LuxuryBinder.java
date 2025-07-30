package main.java.model.classes;

import main.java.model.enums.Variant;

public class LuxuryBinder extends BinderModel{
    
    public LuxuryBinder(String name){
        super(name);
        this.typeName = "Luxury Binder";
        this.type = "sell";
    }

    public boolean addCardB(CardModel card) {
        String variant = card.getVariant().toString();
        if (variant.equalsIgnoreCase("Normal")) {
            System.out.println("Only non-Normal variant cards can be added to this Luxury Binder.");
            return false;
        }
        return super.addCardB(card);
    }

    @Override
    public boolean isCardAllowed(CardModel card) {
        return card.getVariant() != Variant.NORMAL;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public double getSellMultiplier() {
        return 1.1;
    }
}
