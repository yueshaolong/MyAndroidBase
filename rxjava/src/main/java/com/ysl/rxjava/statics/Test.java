package com.ysl.rxjava.statics;

public class Test {
    public static void main(String[] args) {
        /**case1**/
        //不会执行静态代码块, 静态成员变量不会初始化, 也不会加载静态内部类
//        String simpleName = StaticTest.class.getSimpleName();

        /**case2**/
        //会执行静态代码块, 静态成员变量会初始化, 不会加载静态内部类
        //输出 StaticMember
        //    static code initializer
//        StaticMember staticMember = StaticTest.staticMember;

        /**case3**/
        //会执行静态代码块, 静态成员变量会初始化, 不会加载静态内部类
        //输出 StaticMember
        //    static code initializer
//        new StaticTest();

        /**case4**/
        //会执行静态代码块, 静态成员变量会初始化, 不会加载静态内部类
        //输出 StaticMember
        //    static code initializer
//        StaticTest.f();

        /**case5**/
        //不会执行静态代码块, 静态成员变量不会初始化, 也不会加载静态内部类
//        if (false) {
//            StaticTest.f();
//            StaticTest.e();
//        }

        /**case6**/
        //会执行静态代码块, 静态成员变量会初始化, 同时加载静态内部类
        // 输出：StaticMember
        //      static code initializer
        //      load from InnerClass
        StaticTest.e();
    }
}
