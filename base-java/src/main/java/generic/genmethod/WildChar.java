package generic.genmethod;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class WildChar{

    public static void print(MyGeneric4<Fruit> p){
        System.out.println(p.getData());
    }

    public static void use(){
        MyGeneric4<Fruit> a = new MyGeneric4<>();
        print(a);
        MyGeneric4<Orange> b = new MyGeneric4<>();
        //print(b);//这里报错，因为print方法需要传入的是MyGeneric4<Fruit>；虽然里面的具体类型是继承关系；但对于泛型本身是没有继承关系的；
    }


    public static void print2(MyGeneric4<? extends Fruit> p){
        System.out.println(p.getData());
    }

    public static void use2(){
        MyGeneric4<Fruit> a = new MyGeneric4<>();
        print2(a);
        MyGeneric4<Orange> b = new MyGeneric4<>();
        print2(b);//现在是可以的，因为print2方法需要的参数中“? extends Fruit”表示fruit的子类都是可以的
        MyGeneric4<Food> f = new MyGeneric4<>();
        //print2(f);//这里报错，因为超出了通配符的限定范围

        MyGeneric4<? extends Fruit> c =  new MyGeneric4<>();
        Apple apple =  new Apple();
        Fruit fruit = new Fruit();
        Food food = new Food();
        //c.setData(apple);
        //c.setData(fruit);
        //c.setData(food);//set的时候，不论是子类、父类还是自己，都是不能成功的。

        Fruit data = c.getData();//得到的对象只能是fruit类型
    }

    public static void printSuper(MyGeneric4<? super Apple> p){
        System.out.println(p.getData());
    }

    public static void useSuper(){
        MyGeneric4<Fruit> fruitGenericType = new MyGeneric4<>();
        MyGeneric4<Apple> appleGenericType = new MyGeneric4<>();
        MyGeneric4<Orange> orangeGenericType = new MyGeneric4<>();
        printSuper(fruitGenericType);
        printSuper(appleGenericType);
        //printSuper(orangeGenericType);//这里会报错，因为orange不是apple的父类


        //表示GenericType的类型参数的下界是Apple
        MyGeneric4<? super Apple> x = new MyGeneric4<>();
        x.setData(new Apple());
        //x.setData(new Food());
        //x.setData(new Fruit());//set的时候，父类和子类都报错；只有自己本身才不会报错。

        Object data = x.getData();//get的时候，只能达到obj类型；因为不知道具体是什么类型，所以只能给了所有类的父类object。

    }

}
