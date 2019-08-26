package design_pattern.decorator;

public abstract class Decorator implements People {
    private People component;

    public Decorator(People component) {
        this.component = component;
    }

    @Override
    public void method() {
        component.method();
    }
}
