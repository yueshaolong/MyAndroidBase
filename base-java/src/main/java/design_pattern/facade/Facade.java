package design_pattern.facade;

public class Facade {
    private static Facade instance = new Facade();
    private final Rice rice;
    private final Fish fish;

    private Facade(){
        rice = new Rice();
        fish = new Fish();
    }
    public static Facade getInstance(){
        return instance;
    }

    public void cook(){
        rice.cook();
        fish.cook();
    }
}
