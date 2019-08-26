package design_pattern.singleton;

public class Singleton4 {
    private Singleton4() {
    }
    private static Singleton4 instance = new Singleton4();//饿汉式，如果没有使用到，会浪费内存，因为类一加载就创建对象了
    public static Singleton4 getInstance(){
        return instance;
    }
}
