package design_pattern.decorator;

public class Test {
    public static void main(String[] args) {
        //首先把实现类new出来
        People component = new XiaoMing();

        //把具体装饰者new出来
        People component1 = new DecoratorA(component);
        People component2 = new DecoratorB(component1);
        component2.method();

        //装饰模式：
        //1.在不改变原有功能下，对功能的增加
        //2.接口中实现的功能是基本功能，具体的装饰类DecoratorA、DecoratorB都是对基本功能的扩展
        //上面的例子也可以写成这样：
        DecoratorB decoratorB = new DecoratorB(new DecoratorA(new XiaoMing()));
        decoratorB.method();
    }
}
