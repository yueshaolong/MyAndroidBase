package design_pattern.strategy;

public class PostRequest implements RequestStrategy {
    @Override
    public String getResult() {
        return "post请求到数据";
    }
}
