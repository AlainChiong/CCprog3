package main.java.model.classes;

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
}
