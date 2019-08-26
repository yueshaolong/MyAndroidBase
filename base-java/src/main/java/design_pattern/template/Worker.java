package design_pattern.template;

public class Worker extends Life {
    @Override
    public void work() {
        System.out.println("干活");
    }

    @Override
    public void goToWork() {
        System.out.println("坐公交去");
    }

    @Override
    public void goToHome() {
        System.out.println("坐公交回");
    }
}
