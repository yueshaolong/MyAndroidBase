package design_pattern.observer.myobserver;

import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer {
    private String name;
    private MyObservable myObservable;

    public MyObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        myObservable = (MyObservable)o;
        System.out.println(name + " 收到推送消息： " + myObservable.getMessage());
    }
}
