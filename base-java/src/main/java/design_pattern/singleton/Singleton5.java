package design_pattern.singleton;

public class Singleton5 {
    private Singleton5() {
    }
    private static Singleton5 instance;//饿汉式，如果没有使用到，会浪费内存，因为类一加载就创建对象了
    static {
        instance = new Singleton5();
    }
    public static Singleton5 getInstance(){
        return instance;
    }
}
