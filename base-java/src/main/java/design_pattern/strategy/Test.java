package design_pattern.strategy;

public class Test {
    public static void main(String[] args) {
        RequestStrategy get = new GetRequest();
        RequestManager requestManager = new RequestManager(get);
        System.out.println(requestManager.getResult());

        System.out.println("------------------------------");

        RequestStrategy post = new PostRequest();
        requestManager.setRequestStrategy(post);
        System.out.println(requestManager.getResult());
    }
}
