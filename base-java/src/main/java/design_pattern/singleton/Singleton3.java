package design_pattern.singleton;

public class Singleton3 {
    private Singleton3() {
    }
    private static Singleton3 instance;//懒汉式，双重检验锁
    public static Singleton3 getInstance(){//不存在线程安全问题，只有在为空时才加锁，然后再判空；减少了获取锁的消耗
        if (instance == null){
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}
