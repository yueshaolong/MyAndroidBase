package design_pattern.decorator;

public class DecoratorA extends Decorator {

    public DecoratorA(People component) {
        super(component);
    }

    @Override
    public void method() {
        super.method();
        System.out.println("摸化妆品");
    }
}
