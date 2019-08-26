package design_pattern.decorator;

public class DecoratorB extends Decorator {

    public DecoratorB(People component) {
        super(component);
    }

    @Override
    public void method() {
        super.method();
        System.out.println("戴首饰");
    }
}
