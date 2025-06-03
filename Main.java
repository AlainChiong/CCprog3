import com.forest.*;

public class Main {
    public static void main(String args[]) {
        Owl o1 = new Owl("Gray");
        Tree t1 = new Tree();

        o1.eat(t1.getMushroom(0));
        t1.setResident(o1);

        t1 = new Tree();

        o1.grow();
        t1.grow();

        System.out.println("Owl size: " + o1.getSize());
        System.out.println("First Mushroom size: " + t1.getMushroom(0).getSize());
        System.out.println("Second Mushroom size: " + t1.getMushroom(1).getSize());
    }
}