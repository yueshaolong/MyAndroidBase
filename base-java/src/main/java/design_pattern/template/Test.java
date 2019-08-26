package design_pattern.template;

public class Test {
    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.oneDay();

        System.out.println("--------------------------");

        Worker worker = new Worker();
        worker.oneDay();
    }
}
