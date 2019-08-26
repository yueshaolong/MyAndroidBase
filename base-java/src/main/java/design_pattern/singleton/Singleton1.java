package design_pattern.singleton;

public class Singleton1 {
    private Singleton1() {
    }
    private static Singleton1 instance;//懒汉式
    public static Singleton1 getInstance(){//存在线程安全问题，假如两个线程同时判断到instance为空，就会创建两个对象了。
        if (instance == null)
            instance = new Singleton1();
        return instance;
    }
}
