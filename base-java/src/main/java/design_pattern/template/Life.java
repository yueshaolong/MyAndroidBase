package design_pattern.template;

public abstract class Life {

    private void getUp(){
        System.out.println("早上起床！");
    }

    public void oneDay(){
        getUp();
        goToWork();
        work();
        goToHome();
    }

    public void goToHome() {
    }

    public abstract void work();

    public void goToWork() {
    }
}
