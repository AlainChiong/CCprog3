package main.java.model.classes;

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
}
