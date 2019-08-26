package design_pattern.observer.myobserver;

import java.util.Observable;

public class MyObservable extends Observable{

    private String message;

    public void setInformation(String s) {
        this.message = s;
        System.out.println("微信服务更新消息： " + s);

        //消息更新，通知所有观察者
        setChanged();
        notifyObservers();
    }

    public String getMessage() {
        return message;
    }
}
