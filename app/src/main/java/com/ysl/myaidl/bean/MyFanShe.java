package com.ysl.myaidl.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyFanShe {

    private static String string = "a";
    private final int WORLD = 10;
    public static final int PROTOCOL_BUF_VERSION_THREE = 3;

    private String name;
    public int age;
    public static MyData myData;
    public MyData myData2;

    private static class MyData{

    }

    public MyFanShe() {
        this(null, 0);
    }

    public MyFanShe(int age) {
        this(null, age);
    }

    public MyFanShe(String name) {
        this(name, 0);
    }

    public MyFanShe(String name, int age) {
        this.name = name;
        this.age = age;
        myData = new MyData();
        myData2 = new MyData();
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyFanShe{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        MyFanShe myFanShe = new MyFanShe("jj", 12);

        try {
            Class<? extends MyFanShe> aClass = myFanShe.getClass();
            Class<MyFanShe> aClass1 = MyFanShe.class;
            Class<?> aClass2 = Class.forName("com.ysl.myaidl.bean.MyFanShe");
            //三个拿到的是同一个对象，hashcode是一样的；
            System.out.println("aClass="+aClass.hashCode()+",aClass1="+aClass1.hashCode()+",aClass2="+aClass2.hashCode());

            Field[] fields = aClass2.getFields();//拿到全部公有的属性
            for (Field field : fields){
                System.out.println("fields:"+field.getName());
            }
            System.out.println("==================");
            Field age = aClass2.getField("age");//拿到特定的公有的属性
            System.out.println("fields:"+age.getName());
            System.out.println("==================");
            Field[] declaredFields = aClass2.getDeclaredFields();//可以拿到全部的属性
            for (Field field : declaredFields){
                System.out.println("declaredFields:"+field.getName());
            }
            System.out.println("==================");
            Field name = aClass2.getDeclaredField("name");//拿到特定的属性，包括公有的和私有的；一般用这个
            System.out.println("fields:"+name.getName());
            Object o4 = name.get(myFanShe);//获取对象（myFanShe）里面的此字段（name）的值；
            System.out.println("o4:"+o4);
            Object o = aClass2.getConstructor().newInstance();
            name.set(o, "kk");
            System.out.println("o:"+o);

            Field myData = aClass2.getDeclaredField("myData");
            System.out.println("fields:"+myData);
            Object o2 = myData.get(null);//获取对象（）里面的此字段（myData）的值；当myData变量为静态变量时，参数传null；
            System.out.println("o2:"+o2);

            Field myData2 = aClass2.getDeclaredField("myData2");
            System.out.println("fields:"+myData2);
            Object o3 = myData2.get(myFanShe);//获取对象（myFanShe）里面的此字段（myData2）的值；
            System.out.println("o3:"+o3);



            Method[] methods = aClass2.getMethods();
            for (Method method : methods){
                System.out.println("methods:"+method.getName());
            }
            Method setName = aClass2.getDeclaredMethod("setName", String.class);
            System.out.println("setName:"+setName.getName());
            Method getName = aClass2.getDeclaredMethod("getName");
            System.out.println("getName:"+getName.getName());

            Constructor<?>[] declaredConstructors = aClass2.getDeclaredConstructors();
            for (Constructor constructor : declaredConstructors){
                System.out.println("constructor:"+constructor.toString());
            }

            Constructor<?> declaredConstructor = aClass2.getDeclaredConstructor();
            o = declaredConstructor.newInstance();
            setName = aClass2.getDeclaredMethod("setName", String.class);
            setName.setAccessible(true);
            setName.invoke(o, "aa");

            Method setAge = aClass2.getDeclaredMethod("setAge", int.class);
            System.out.println("setAge:"+setAge.getName());
            setAge.invoke(o, 78);
            System.out.println(o);

            Constructor<?> declaredConstructor1 = aClass2.getDeclaredConstructor(String.class, int.class);
            Object o1 = declaredConstructor1.newInstance("sd",1);
            System.out.println(o1);


            System.out.println("--------------------------------------------");
            //String测试
            String s = "Hello";
            System.out.println(s.hashCode());
            System.out.println(System.identityHashCode(s));
            s = s + " world!";
            System.out.println(s.hashCode());
            System.out.println(System.identityHashCode(s));

            StringBuffer stringBuffer = new StringBuffer("Hello");
            stringBuffer.append(" world!");
            s = stringBuffer.toString();
            System.out.println(s.hashCode());
            System.out.println(System.identityHashCode(s));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
