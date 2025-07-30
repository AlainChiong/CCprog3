package main.java.model.classes;

import main.java.model.enums.Rarity;

public class RaresBinder extends BinderModel{
    
    public RaresBinder(String name){
        super(name);
        this.typeName = "Rares Binder";
        this.type = "sell";
    }

    public boolean addCardB(CardModel card) {
        String rarity = card.getRarity().toString();
        if (!rarity.equalsIgnoreCase("Rare") && !rarity.equalsIgnoreCase("Legendary")) {
            System.out.println("Only Rare or Legendary cards are allowed in a Rares Binder.");
            return false;
        }
        return super.addCardB(card);
    }

    @Override
    public boolean isCardAllowed(CardModel card) {
        return card.getRarity() == Rarity.RARE || card.getRarity() == Rarity.MYTHIC;
    }
}
