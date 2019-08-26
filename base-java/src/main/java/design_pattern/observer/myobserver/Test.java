package design_pattern.observer.myobserver;

import java.util.Observer;

public class Test {
    public static void main(String[] args) {
        MyObservable observable = new MyObservable();

        Observer ZhangSan = new MyObserver("ZhangSan");
        Observer LiSi = new MyObserver("LiSi");
        Observer WangWu = new MyObserver("WangWu");

        observable.addObserver(ZhangSan);
        observable.addObserver(LiSi);
        observable.addObserver(WangWu);

        observable.setInformation("PHP是世界上最好用的语言！");

        System.out.println("----------------------------------------------");

        observable.deleteObserver(ZhangSan);
        observable.setInformation("JAVA是世界上最好用的语言！");
    }
}
