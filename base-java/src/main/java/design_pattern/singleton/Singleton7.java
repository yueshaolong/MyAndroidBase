package design_pattern.singleton;

public enum Singleton7 {
    INSTANCE;
    //查看编译后的class文件，发现自动添加了私有的无参构造方法；和单例是很像的；
    // 也就是说这个类的实例对象INSTANCE是唯一的

    //下面就可以写各种对这个类的方法或业务逻辑处理了；
    public void method(){
    }
}
