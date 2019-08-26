package design_pattern.observer.observer2;

public class Test {
    public static void main(String[] args) {
        WeChatServer server = new WeChatServer();

        Observer userZhang = new User("ZhangSan");
        Observer userLi = new User("LiSi");
        Observer userWang = new User("WangWu");

        server.registerObserver(userZhang);
        server.registerObserver(userLi);
        server.registerObserver(userWang);
        server.setInformation("PHP是世界上最好用的语言！");

        System.out.println("----------------------------------------------");

        server.unregisterObserver(userZhang);
        server.setInformation("JAVA是世界上最好用的语言！");

    }
}
