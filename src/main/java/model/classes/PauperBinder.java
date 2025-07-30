package main.java.model.classes;

import main.java.model.enums.Rarity;

public class PauperBinder extends BinderModel{
    
    public PauperBinder(String name){
        super(name);
        this.typeName = "Pauper Binder";
        this.type = "sell";
    }

    public boolean addCardB(CardModel card) {
        String rarity = card.getRarity().toString();
        if (!rarity.equalsIgnoreCase("Common") && !rarity.equalsIgnoreCase("Uncommon")) {
            System.out.println("Only Common or Uncommon cards are allowed in a Pauper Binder.");
            return false;
        }
        return super.addCardB(card);
    }

    @Override
    public boolean isCardAllowed(CardModel card) {
        return card.getRarity() == Rarity.COMMON || card.getRarity() == Rarity.UNCOMMON;
    }
}
