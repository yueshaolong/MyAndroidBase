package classloader;

import java.util.List;

public class ClassLoaderTest {
    /*public static void main(String[] args) throws Exception{
        //获取系统/应用类加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统/应用类加载器：" + appClassLoader);
        //获取系统/应用类加载器的父类加载器，得到扩展类加载器
        ClassLoader extcClassLoader = appClassLoader.getParent();
        System.out.println("扩展类加载器" + extcClassLoader);
        System.out.println("扩展类加载器的加载路径：" + System.getProperty("java.ext.dirs"));
        //获取扩展类加载器的父加载器，但因根类加载器并不是用Java实现的所以不能获取
        System.out.println("扩展类的父类加载器：" + extcClassLoader.getParent());

        //打印结果：
        *//*系统/应用类加载器：sun.misc.Launcher$AppClassLoader@18b4aac2
        扩展类加载器sun.misc.Launcher$ExtClassLoader@4554617c
        扩展类加载器的加载路径：E:\Java\jdk1.8.0_144\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext
        扩展类的父类加载器：null*//*
    }*/

    public static void main(String[] args){
        //输出ClassLoaderText的类加载器名称
        System.out.println("ClassLoaderTest类的加载器的名称:"+ClassLoaderTest.class.getClassLoader().getClass().getName());
        System.out.println("System类的加载器的名称:"+System.class.getClassLoader());
        System.out.println("List类的加载器的名称:"+List.class.getClassLoader());

        ClassLoader cl = ClassLoaderTest.class.getClassLoader();
        while(cl != null){
            System.out.print(cl.getClass().getName()+"->");
            cl = cl.getParent();
        }
        System.out.println(cl);

        //输出结果：
        /*ClassLoaderTest类的加载器的名称:sun.misc.Launcher$AppClassLoader
        System类的加载器的名称:null
        List类的加载器的名称:null
        sun.misc.Launcher$AppClassLoader->sun.misc.Launcher$ExtClassLoader->null*/
        /*说明：
        1、ClassLoaderTest类是用户定义的类，位于CLASSPATH下，由系统/应用程序类加载器加载。
        2、System类与List类都属于Java核心类，由祖先类启动类加载器加载，而启动类加载器是在JVM内部通过C/C++实现的，
        并不是Java，自然也就不能继承ClassLoader类，自然就不能输出其名称。
        3、而箭头项代表的就是类加载的流程，层级委托，从祖先类加载器开始，直到系统/应用程序类加载器处才被加载。*/

    }
}
