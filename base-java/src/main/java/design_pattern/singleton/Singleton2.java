package design_pattern.singleton;

public class Singleton2 {
    private Singleton2() {
    }
    private static Singleton2 instance;//懒汉式
    public static synchronized Singleton2 getInstance(){//不存在线程安全问题，但每次获取对象时，都需要持有锁；在Java中锁是很耗资源和性能的
        if (instance == null)
            instance = new Singleton2();
        return instance;
    }
    public static Singleton2 getInstance1(){//不存在线程安全问题，但每次获取对象时，都需要持有锁；在Java中锁是很耗资源和性能的
        synchronized (Singleton2.class) {
            if (instance == null)
                instance = new Singleton2();
        }
        return instance;
    }
}
