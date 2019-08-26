package design_pattern.observer.observer2;

/***
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 */
public interface Observable {
    public void registerObserver(Observer o);
    public void unregisterObserver(Observer o);
    public void notifyObserver();
}
