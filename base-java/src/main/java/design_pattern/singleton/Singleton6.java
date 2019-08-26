package design_pattern.singleton;

public class Singleton6 {
    private Singleton6() {
    }
    private static class Singleton5Holder{
        private static Singleton6 instance = new Singleton6();
    }
    public static Singleton6 getInstance(){//静态内部类的方式，只有调用此方法了，才会创建对象，节省内存
        return Singleton5Holder.instance;
    }
}
