package com.ysl.rxjava.statics;

public class StaticTest {
    public static StaticMember staticMember = new StaticMember();

    static {
        System.out.println("static code initializer ");
    }

    private static class InnerClass {
        private static StaticTest staticTest = new StaticTest("load from InnerClass");
    }

    public StaticTest() {
    }

    public StaticTest(String a) {
        System.out.println(a);
    }

    public static void f(){

    }

    public void d(){

    }

    public static void e(){
        InnerClass.staticTest.d();
    }
}
