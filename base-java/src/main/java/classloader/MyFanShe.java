package classloader;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyFanShe {

    private static String string = "a";
    private final int WORLD = 10;
    public static final int PROTOCOL_BUF_VERSION_THREE = 3;

    private String name;
    public int age;

    public MyFanShe() {
    }

    public MyFanShe(int age) {
        this.age = age;
    }

    public MyFanShe(String name) {
        this.name = name;
    }

    public MyFanShe(String name, int age) {
        this.name = name;
        this.age = age;
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
        Class<? extends MyFanShe> aClass = myFanShe.getClass();

        Class<MyFanShe> aClass1 = MyFanShe.class;

        try {
            Class<?> aClass2 = Class.forName("classloader.MyFanShe");
            System.out.println("aClass="+aClass.hashCode()+",aClass1="+aClass1.hashCode()+",aClass2="+aClass2.hashCode());

            Field[] fields = aClass2.getFields();//拿到全部公有的属性
            for (Field field : fields){
                System.out.println("fields:"+field.getName());
            }
            Field age = aClass2.getField("age");//拿到特定的公有的属性
            System.out.println("age:"+age.getName());
            Field[] declaredFields = aClass2.getDeclaredFields();//可以拿到全部的属性
            for (Field field : declaredFields){
                System.out.println("declaredFields:"+field.getName());
            }
            Field name = aClass2.getDeclaredField("name");//拿到特定的属性，包括公有的和私有的；一般用这个
            Object o = aClass2.getConstructor().newInstance();
            name.set(o, "kk");
            System.out.println("o:"+o);

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
            Object oo = declaredConstructor.newInstance();
            Method setName1 = aClass2.getDeclaredMethod("setName", String.class);
            setName.setAccessible(true);
            setName.invoke(oo, "aa");

            Method setAge = aClass2.getDeclaredMethod("setAge", int.class);
            System.out.println("setAge:"+setAge.getName());
            setAge.invoke(o, 78);
            System.out.println(o);

            Constructor<?> declaredConstructor1 = aClass2.getDeclaredConstructor(String.class, int.class);
            Object o1 = declaredConstructor1.newInstance("sd",1);
            System.out.println(o1);

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
